/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.co.bsc.sigai.service;

import bitronix.tm.TransactionManagerServices;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.CommandBasedWSHumanTaskHandler;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import ve.co.bsc.sigai.domain.DroolsRule;
import ve.co.bsc.sigai.domain.JbpmProcess;
import ve.co.bsc.sigai.domain.JbpmSession;
import ve.co.bsc.sigai.domain.bpm.ProcessObjectCorrelation;

/**
 *
 * @author jdeoliveira
 */
@Service
public class JbpmService implements InitializingBean, DisposableBean {

    private Logger logger = Logger.getLogger(JbpmService.class);
    private String resourcesPath;
    private KnowledgeBase kbase;
    private StatefulKnowledgeSession ksessionStatefull;
    private StatelessKnowledgeSession ksessionStateless;
    private boolean workItemRegistrado;
    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("Iniciando Servicio BPM");

        workItemRegistrado = false;
        
        if (kbase == null) {
            logger.warn("kbase es null, creando nuevo KnowledgeBase");
            kbase = KnowledgeBuilderFactory.newKnowledgeBuilder().newKnowledgeBase();

            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();


            logger.debug("Cargando definiciones de procesos");

            //load the processes definitions
            List<JbpmProcess> procesos = JbpmProcess.findAllJbpmProcesses();
            for (JbpmProcess p : procesos) {
                logger.debug("Cargando proceso [" + p.getId() + "] " + p.getNombre());
                kbuilder.add(ResourceFactory.newByteArrayResource(p.getProcessDefinition()), ResourceType.BPMN2);
                logger.debug("Proceso [" + p.getId() + "] " + p.getNombre() + " cargado OK");
            }
            if (resourcesPath != null) {
                logger.info("Cargando procesos desde sistema de archivos (" + resourcesPath + ")");
                File resourcesPathFile = new File(resourcesPath);
                for (File aFile : resourcesPathFile.listFiles(
                        new FilenameFilter() {

                            @Override
                            public boolean accept(File dir, String name) {
                                return name.endsWith(".bpmn");
                            }
                        })) {
                    logger.debug("Cargando " + aFile);
                    kbuilder.add(ResourceFactory.newFileResource(aFile), ResourceType.BPMN2);
                }
            }
            logger.debug("Procesos cargados");


            logger.debug("Cargando reglas de negocio");
            //load the rules definitions
            List<DroolsRule> reglas = DroolsRule.findAllDroolsRules();
            for (DroolsRule r : reglas) {
                logger.debug("Cargando regla [" + r.getId() + "] " + r.getNombre());
                kbuilder.add(ResourceFactory.newByteArrayResource(r.getRuleDefinition()), ResourceType.DRL);
                logger.debug("Regla [" + r.getId() + "] " + r.getNombre() + " cargado OK");
            }

            if (resourcesPath != null) {
                logger.info("Cargando reglas desde sistema de archivos (" + resourcesPath + ")");
                File resourcesPathFile = new File(resourcesPath);
                for (File aFile : resourcesPathFile.listFiles(
                        new FilenameFilter() {

                            @Override
                            public boolean accept(File dir, String name) {
                                return name.endsWith(".drl");
                            }
                        })) {
                    logger.debug("Cargando " + aFile);
                    kbuilder.add(ResourceFactory.newFileResource(aFile), ResourceType.DRL);
                }
            }
            logger.debug("Reglas cargadas");


            if (kbuilder.hasErrors()) {
                logger.error("ERROES EN KNOWLEDGE BASE: " + kbuilder.getErrors().toString());
                //throw new RuntimeException("ERROES EN KNOWLEDGE BASE: " + kbuilder.getErrors().toString());
            }
            kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

            for (KnowledgePackage kp : kbase.getKnowledgePackages()) {
                logger.debug("KP " + kp.getName());
                for (org.drools.definition.process.Process p : kp.getProcesses()) {
                    logger.debug("P " + p.getId() + " " + p.getName() + " " + p.getPackageName());
                }
            }
        }
        if (ksessionStatefull == null) {
            logger.warn("ksessionStatefull es null, creando nuevo StatefullKnowledgeSession");

            //EntityManagerFactory emf = entityManagerFactory.getObject();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");
            Environment env = KnowledgeBaseFactory.newEnvironment();
            env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
            env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());


            List<JbpmSession> sessions = JbpmSession.findAllJbpmSessions();

            if (sessions.isEmpty()) {
                logger.debug("No hay sesion previa de StatefullKnowledgeSession");
                createNewJpaStatefullKnowledgeSession(env);
            } else {
                JbpmSession session = sessions.get(sessions.size() - 1);
                UserTransaction ut = null;
                try {
                    logger.debug("Sesion previa de StatefullKnowledgeSession (id: " + session.getSessionId() + ") encontrada, recuperandola");
                    ut = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
                    ut.begin();
                    ksessionStatefull = JPAKnowledgeService.loadStatefulKnowledgeSession(session.getSessionId().intValue(), kbase, null, env);
                    ut.commit();
                    logger.debug("Sesion previa de StatefullKnowledgeSession (id: " + session.getSessionId() + ") cargada satisfactoriamente");
                } catch (Exception e) {
                    logger.error("No se puede recuperar sesion existente de jBPM", e);
                    try {
                        ut.commit();
                    } catch (Exception ex) {
                    }
                    
                    createNewJpaStatefullKnowledgeSession(env);
                }
            }
        }
        if (ksessionStateless == null) {
            logger.warn("ksessionStateless es null, creando nuevo StatelessKnowledgeSession");
            ksessionStateless = kbase.newStatelessKnowledgeSession();
        }

        logger.debug("Instancias de procesos cargadas:");
        for (ProcessInstance pi : ksessionStatefull.getProcessInstances()) {
            logger.debug("id: " + pi.getId() + "; proc: " + pi.getProcessName() + "; state: " + pi.getState());
        }
        logger.debug("Fin de lista de instancias de procesos cargadas");

        KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksessionStatefull);


        logger.debug("Finalizada inicializacion de Servicio BPM");
    }

    private void createNewJpaStatefullKnowledgeSession(Environment env) {

        UserTransaction ut = null;
        try {
            logger.debug("Creando NUEVO StatefullKnowledgeSession");
            ut = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            ut.begin();

            ksessionStatefull =
                    JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);

            int sessionId = ksessionStatefull.getId();

            JbpmSession session = new JbpmSession();
            session.setSessionId(new Integer(sessionId));
            session.persist();

            ut.commit();

            logger.debug("NUEVO StatefullKnowledgeSession creado y registrado (id: " + sessionId + ")");
        } catch (Exception e) {
            logger.error("No se puede crear Statefull Knowledge Session", e);
            try {
                ut.rollback();
            } catch (Exception ex) {
            }
            throw new RuntimeException("No se puede crear Statefull Knowledge Session", e);
        }
    }

    public synchronized void registerHumanTaskWorkItemHandler() {
        if (!workItemRegistrado) {
            logger.debug("Registrando WS-HumanTaskHandler");
            ksessionStatefull.getWorkItemManager().registerWorkItemHandler("Human Task", new CommandBasedWSHumanTaskHandler(ksessionStatefull));
            workItemRegistrado = true;
            logger.debug("WS-HumanTaskHandler registrado");
        }
        
    }
    
    public ProcessInstance startProcess(String processName) {
        logger.debug("Iniciando proceso " + processName);
        UserTransaction ut = null;
        try {
            ut = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            ut.begin();
            ProcessInstance p = ksessionStatefull.startProcess(processName);
            ut.commit();
            logger.debug("Proceso " + processName + " iniciado");
            return p;
        } catch (Exception e) {
            logger.error("No se puede iniciar proceso " + processName, e);
            try {
                ut.rollback();
            } catch (Exception ex) {
            }
            throw new RuntimeException(e);
        }
    }

    public ProcessInstance startProcess(String processName, Map<String, Object> parameters) {
        logger.debug("Iniciando proceso " + processName + " con parametros: " + parameters.toString());

        KnowledgeBase kb = ksessionStatefull.getKnowledgeBase();
        for (KnowledgePackage kp : kb.getKnowledgePackages()) {
            logger.debug("KP " + kp.getName());
            for (org.drools.definition.process.Process p : kp.getProcesses()) {
                logger.debug("P " + p.getId() + " " + p.getName() + " " + p.getPackageName());
            }
        }

        UserTransaction ut = null;
        try {
            ut = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            ut.begin();
            ProcessInstance p = ksessionStatefull.startProcess(processName, parameters);
            ut.commit();

            logger.debug("Proceso " + processName + " iniciado");

            return p;

        } catch (Exception e) {
            logger.error("No se puede iniciar proceso " + processName, e);
            try {
                ut.rollback();
            } catch (Exception ex) {
            }
            throw new RuntimeException(e);
        }
    }

    public ProcessInstance startProcess(String processName, Map<String, Object> parameters, Object correlationObject) {

        logger.debug("Iniciando proceso con correlation");
        ProcessInstance p = startProcess(processName, parameters);
        logger.debug("Proceso inciado, creando correlation");

        BeanWrapper wrapper = new BeanWrapperImpl(correlationObject);
        ProcessObjectCorrelation correlation = new ProcessObjectCorrelation();
        correlation.setItemId((Long) wrapper.getPropertyValue("id"));
        correlation.setProcessInstanceId(p.getId());
        correlation.setClassname(correlationObject.getClass().getName());
        correlation.merge();

        logger.debug("Instancias de procesos cargadas:");
        for (ProcessInstance pi : ksessionStatefull.getProcessInstances()) {
            logger.debug("id: " + pi.getId() + "; proc: " + pi.getProcessName() + "; state: " + pi.getState());
        }
        logger.debug("Fin de lista de instancias de procesos cargadas");



        return p;
    }

    public void executeRulesStateless(Object subject) {
        logger.debug("Ejecutando reglas en session stateless para " + subject);
        ksessionStateless.execute(subject);
        logger.debug("Reglas en session stateless ejecutadas (para " + subject + ")");
    }

    public void executeRulesStateless(Iterable subjects) {
        logger.debug("Ejecutando reglas en session stateless para " + subjects);
        ksessionStateless.execute(subjects);
        logger.debug("Reglas en session stateless ejecutadas (para " + subjects + ")");
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }
//    public static void signalEvent(String eventType, Object data) {
//        KnowledgeBase kbase = KnowledgeBuilderFactory.newKnowledgeBuilder().newKnowledgeBase();
//        KnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
//        ksession.signalEvent(eventType, data, processInstanceId);
//    }

    public void completeWorkItem(long workItemId) {
        final Map<String, Object> results = new HashMap<String, Object>();
        
        // para garantizar que las tareas de humanos son CONTINUADAS
        // ante un restart
        ksessionStatefull.getWorkItemManager().completeWorkItem(workItemId, results);
    }

    @Override
    public void destroy() {
        /*
         * try { ksessionStatefull.dispose(); } catch (Exception e) {
         * logger.error("No se pudo cerrar ksessionStatefull", e); }
         */
    }
}
