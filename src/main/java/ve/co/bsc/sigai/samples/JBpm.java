package ve.co.bsc.sigai.samples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.workitem.wsht.WSHumanTaskHandler;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.mina.MinaTaskClientConnector;
import org.jbpm.task.service.mina.MinaTaskClientHandler;
import org.jbpm.task.service.responsehandlers.BlockingAddTaskResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskSummaryResponseHandler;

public class JBpm {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KnowledgeBase kbase = readKnowledgeBase();
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession, "test", 1000);
            ksession.getWorkItemManager().registerWorkItemHandler("Human Task", new WSHumanTaskHandler());
            // start a new process instance
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("employee", "krisv");
            ksession.startProcess("com.sample.evaluation", params);
            logger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("Evaluation.bpmn"), ResourceType.BPMN2);
        return kbuilder.newKnowledgeBase();
    }

    private void ejemploHumanTasks() {
        TaskClient client = new TaskClient(new MinaTaskClientConnector("client 1",
                new MinaTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));

        client.connect("127.0.0.1", 9123);


        // adding a task

        BlockingAddTaskResponseHandler addTaskResponseHandler = new BlockingAddTaskResponseHandler();

        Task task = null;

        client.addTask(task, null, addTaskResponseHandler);

        long taskId = addTaskResponseHandler.getTaskId();



        // getting tasks for user "bobba"

        BlockingTaskSummaryResponseHandler taskSummaryResponseHandler =
                new BlockingTaskSummaryResponseHandler();

        client.getTasksAssignedAsPotentialOwner("bobba", "en-UK", taskSummaryResponseHandler);

        List<TaskSummary> tasks = taskSummaryResponseHandler.getResults();


// starting a task

        BlockingTaskOperationResponseHandler responseHandler =
                new BlockingTaskOperationResponseHandler();

        client.start(taskId, "bobba", responseHandler);


// completing a task

        responseHandler = new BlockingTaskOperationResponseHandler();

        client.complete(taskId, "x"/*users.get("bobba").getId()*/, null, responseHandler);
    }
}
