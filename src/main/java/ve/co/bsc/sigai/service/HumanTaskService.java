/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.co.bsc.sigai.service;

import java.util.LinkedList;
import java.util.List;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.drools.SystemEventListenerFactory;
import org.jbpm.process.workitem.wsht.BlockingGetTaskResponseHandler;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.PermissionDeniedException;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.mina.MinaTaskClientConnector;
import org.jbpm.task.service.mina.MinaTaskClientHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskSummaryResponseHandler;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.sigai.domain.bpm.ProcessObjectCorrelation;
import ve.co.bsc.sigai.service.exception.ServiceTemporarilyUnavailableException;

/**
 *
 * @author jdeoliveira
 */
@Service
public class HumanTaskService implements InitializingBean {

    private Logger logger = Logger.getLogger(HumanTaskService.class);
    private long connectedClients = 0;
    private long maxClients = 500;
    private TaskClient currentClient;
    private String taskServerHost;
    private String taskServerPort;
    private String taskServerUrl;
    private boolean usuariosCargados;
    
    @Autowired
    private JbpmService jbpmService;

    public String getTaskServerHost() {
        return taskServerHost;
    }

    public void setTaskServerHost(String taskServerHost) {
        this.taskServerHost = taskServerHost;
    }

    public String getTaskServerPort() {
        return taskServerPort;
    }

    public void setTaskServerPort(String taskServerPort) {
        this.taskServerPort = taskServerPort;
    }

    @Override
    public void afterPropertiesSet() {

        logger.debug("Iniciando WS-HumanTaskService");

        usuariosCargados = false;

        logger.debug("Finalizada inicializacion de WS-HumanTaskService");
    }

    private void registrarRolesYUsuarios() {
        taskServerUrl = new StringBuilder("http://").append(taskServerHost).append(":").append(taskServerPort).toString();

        logger.debug("registrando roles");
        try {
            logger.debug("registrando rol Administrator");
            WebClient client = WebClient.create(taskServerUrl);
            client.path("taskserver/ts/user").post("Administrator");
            logger.debug("Rol Administrator registrado");
        } catch (Exception e) {
            logger.debug("Rol Administrator existente");
        }

        for (RolUsuario r : RolUsuario.findAllRolUsuarios()) {
            try {
                logger.debug("Registrando rol " + r.getNombre());
                WebClient client = WebClient.create(taskServerUrl);
                client.path("taskserver/ts/group").post(r.getNombre());
                logger.debug("Rol " + r.getNombre() + " registrado");
            } catch (Exception e) {
                logger.debug("Rol " + r.getNombre() + " existente");
            }
        }
        logger.debug("roles registrados");

        logger.debug("registrando usuarios");
        for (Usuario u : Usuario.findAllUsuarios()) {
            try {
                logger.debug("Registrando usuario: " + u.getLogin());
                WebClient client = WebClient.create(taskServerUrl);
                client.path("taskserver/ts/user").post(u.getLogin());
                logger.debug("Usuario " + u.getLogin() + " registrado");
            } catch (Exception e) {
                logger.debug("Usuario " + u.getLogin() + " existente");
            }
        }
        logger.debug("usuarios registrados");
    }
    
    public long getMaxClients() {
        return maxClients;
    }

    public void setMaxClients(long maxClients) {
        this.maxClients = maxClients;
    }

    public synchronized List<TaskSummary> getTasksAssignedAsPotentialOwnerToCurrentUser() {
        logger.debug("Buscando TaskAssignedAsPotentialOwnerToCurrentUser");
        TaskClient client = obtenerTaskClient();

        try {
            logger.debug("Solicitando tasks para el usuario " + Usuario.findUsuarioCurrentlyLoggedIn().getLogin());

            BlockingTaskSummaryResponseHandler taskSummaryResponseHandler = new BlockingTaskSummaryResponseHandler();
            Usuario user = Usuario.findUsuarioCurrentlyLoggedIn();

            client.getTasksAssignedAsPotentialOwner(user.getLogin(), user.getRolesAsString(), "en-UK", taskSummaryResponseHandler);
            List<TaskSummary> tasks = taskSummaryResponseHandler.getResults();

            logger.debug("Encontradas " + tasks.size() + " tareas");

            return tasks;

        } catch (RuntimeException outEx) {
            logger.error("Problema en getTasksAssignedAsPotentialOwnerToCurrentUser", outEx);
            currentClient = null;
            throw new ServiceTemporarilyUnavailableException("No se puede conectar a servicio de tareas de usuario");
        } finally {
            desconectarTaskClient(client);
        }
    }

    public synchronized List<TaskSummary> getTasksOwnedByCurrentUser() {
        logger.debug("Buscando TaskOwnedByCurrentUser");

        TaskClient client = obtenerTaskClient();

        try {
            logger.debug("Solicitando tasks para el usuario " + Usuario.findUsuarioCurrentlyLoggedIn().getLogin());

            BlockingTaskSummaryResponseHandler taskSummaryResponseHandler = new BlockingTaskSummaryResponseHandler();
            Usuario user = Usuario.findUsuarioCurrentlyLoggedIn();

            client.getTasksOwned(user.getLogin(), "en-UK", taskSummaryResponseHandler);

            List<TaskSummary> tasks = taskSummaryResponseHandler.getResults();

            logger.debug("Encontradas " + tasks.size() + " tareas");

            return tasks;

        } catch (RuntimeException outEx) {
            logger.error("Problema en getTasksOwnedByCurrentUser", outEx);
            currentClient = null;
            throw outEx;
        } finally {
            desconectarTaskClient(client);
        }
    }

    public synchronized void claimTaskByCurrentUser(long taskId) {
        logger.debug("Iniciando claim de human tasks");

        TaskClient client = obtenerTaskClient();

        try {

            logger.debug("Asignando task " + taskId + " para el usuario " + Usuario.findUsuarioCurrentlyLoggedIn().getLogin());

            BlockingTaskOperationResponseHandler taskOperationHandler = new BlockingTaskOperationResponseHandler();

            Usuario user = Usuario.findUsuarioCurrentlyLoggedIn();

            client.claim(taskId, user.getLogin(), user.getRolesAsString(), taskOperationHandler);

        } catch (RuntimeException outEx) {
            logger.error("Problema en claimTaskByCurrentUser", outEx);
            currentClient = null;
            throw outEx;
        } finally {
            desconectarTaskClient(client);
        }
    }

    public synchronized void completeTaskByCurrentUser(long taskId) {
        logger.debug("Completando task " + taskId + " para el usuario " + Usuario.findUsuarioCurrentlyLoggedIn().getLogin());

        TaskClient client = obtenerTaskClient();

        try {

            Usuario user = Usuario.findUsuarioCurrentlyLoggedIn();

            BlockingTaskOperationResponseHandler taskOperationHandler = null;

            try {
                logger.debug("Activate task");
                taskOperationHandler = new BlockingTaskOperationResponseHandler();
                client.activate(taskId, user.getLogin(), taskOperationHandler);
                taskOperationHandler.waitTillDone(1000);
                logger.debug("Activate task (end)");
            } catch (PermissionDeniedException e) {
                logger.debug("No se puede ACTIVATE (OK)");
            } catch (Exception e) {
                logger.debug("No se puede ACTIVATE", e);
            }

            try {
                logger.debug("CLAIM");
                taskOperationHandler = new BlockingTaskOperationResponseHandler();
                client.claim(taskId, user.getLogin(), user.getRolesAsString(), taskOperationHandler);

                taskOperationHandler.waitTillDone(1000);
                logger.debug("CLAIM (end)");
            } catch (Exception e) {
                logger.debug("No se puede CLAIM", e);
            }

            try {
                logger.debug("START");
                taskOperationHandler = new BlockingTaskOperationResponseHandler();
                client.start(taskId, user.getLogin(), taskOperationHandler);
                taskOperationHandler.waitTillDone(1000);
                logger.debug("START (end)");
            } catch (Exception e) {
                logger.debug("No se puede START", e);
            }

            logger.debug("COMPLETE");
            taskOperationHandler = new BlockingTaskOperationResponseHandler();
            client.complete(taskId, user.getLogin(), null, taskOperationHandler);
            taskOperationHandler.waitTillDone(1000);

            logger.debug("COMPLETE (end)");

            BlockingGetTaskResponseHandler responseHandler = new BlockingGetTaskResponseHandler();
            client.getTask(taskId, responseHandler);
            responseHandler.waitTillDone(1000);

            Task task = responseHandler.getTask();
            jbpmService.completeWorkItem(task.getTaskData().getWorkItemId());
            
        } catch (RuntimeException outEx) {
            logger.error("Problema en completeTaskByCurrentUser", outEx);
            currentClient = null;
            throw outEx;
        } finally {
            desconectarTaskClient(client);
        }
    }

    public synchronized List<TaskSummary> getCorrelatedTasksAssignedAsPotentialOwnerToCurrentUser(Object correlationObject) {

        List<TaskSummary> tasks = new LinkedList<TaskSummary>();
        List<TaskSummary> alltasks = getTasksAssignedAsPotentialOwnerToCurrentUser();

        BeanWrapper wrapper = new BeanWrapperImpl(correlationObject);
        List<ProcessObjectCorrelation> correlations = ProcessObjectCorrelation.findProcessObjectCorrelationsByItem((Long) wrapper.getPropertyValue("id"), correlationObject.getClass().getName()).getResultList();

        logger.debug("Iniciando filtrado de " + alltasks.size() + " tareas contra " + correlations.size() + " correlaciones");
        for (TaskSummary task : alltasks) {
            for (ProcessObjectCorrelation c : correlations) {
                logger.debug("Verificando correlacion: task.processId = " + task.getProcessInstanceId() + ", correlation.processId = " + c.getProcessInstanceId().longValue());
                if (task.getProcessInstanceId() == c.getProcessInstanceId().longValue()) {
                    logger.debug("ENTRA");
                    tasks.add(task);
                }
            }
        }
        logger.debug("Finalizado filtrado de tareas, resultaron aplicables " + tasks.size() + " tareas");
        return tasks;
    }

    private synchronized TaskClient obtenerTaskClient() {

        /*
         * if (currentClient == null) { currentClient = conectarTaskClient(); }
         * return currentClient;
         */
        if(!usuariosCargados) {
            jbpmService.registerHumanTaskWorkItemHandler();
            registrarRolesYUsuarios();
            usuariosCargados = true;
        }

        return conectarTaskClient();

    }

    private synchronized TaskClient conectarTaskClient() {
        logger.debug("Instanciando cliente y estableciendo conexion");

        //TaskClient client = null;
        int tries = 0;
        while (tries < 3) {
            try {
                logger.debug("Estableciendo conexion (intento " + tries + ")");

                if (connectedClients < maxClients) {
                    if (currentClient == null) {
                        MinaTaskClientHandler handler = new MinaTaskClientHandler(SystemEventListenerFactory.getSystemEventListener());
                        MinaTaskClientConnector connector = new MinaTaskClientConnector("taskClientConnector", handler);
                        currentClient = new TaskClient(connector);
                        currentClient.connect("127.0.0.1", 9123);
                    } else {
                        //try { currentClient.disconnect(); } catch (Exception e) {}
                    }
                    connectedClients++;
                } else {
                    throw new ServiceTemporarilyUnavailableException("Numero maximo de clientes concurrentes de servicio de tareas alcanzado. Por favor intente nuevamente");
                }

                break;
            } catch (Exception e) {

                logger.warn("No se puede instanciar o conectar TaskClient (intento " + tries + ")", e);
                tries++;

                try {
                    currentClient.disconnect();
                } catch (Exception e3) {
                }
                currentClient = null;

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e2) {
                }

            }
        }

        if (currentClient == null) {
            throw new RuntimeException("No se puede conectar al gestor de tareas. La aplicacion debe ser reiniciada");
        } else {
            logger.debug("Cliente conectado");
            return currentClient;
        }
    }

    private synchronized void desconectarTaskClient(TaskClient client) {
        try {
            //client.disconnect();
            connectedClients--;
        } catch (Exception ex) {
            logger.error("No se pudo desconectar el task client, " + ex);
        }
    }

    /*
     * private void crearTaskClientConnector() { if (this.connector != null) {
     * try { this.connector.disconnect(); } catch (Exception e) {
     * logger.warn("Problema al desconectar TaskClientConnector", e); } finally
     * { this.connector = null; } }
     *
     * MinaTaskClientHandler handler = new
     * MinaTaskClientHandler(SystemEventListenerFactory.getSystemEventListener());
     * this.connector = new MinaTaskClientConnector("taskClientConnector",
     * handler); }
     */
    public void registerUser(Usuario u) {
        try {
            logger.debug("Registrando usuario: " + u.getLogin());
            WebClient client = WebClient.create(taskServerUrl);
            client.path("taskserver/ts/user").post(u.getLogin());
            logger.debug("Usuario " + u.getLogin() + " registrado");
        } catch (Exception e) {
            logger.debug("Usuario " + u.getLogin() + " existente");
        }
    }
}
