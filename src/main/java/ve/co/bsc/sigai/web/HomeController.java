package ve.co.bsc.sigai.web;

import java.util.List;
import org.apache.log4j.Logger;
import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import ve.co.bsc.sigai.service.HumanTaskService;

@RequestMapping("/index")
@Controller
public class HomeController {

    private Logger logger = Logger.getLogger(HumanTaskController.class);

    @Autowired
    HumanTaskService humanTaskService;

    @RequestMapping
    public String index(ModelMap modelMap) {

        logger.debug("Iniciando lista de human tasks");

        List<TaskSummary> tasks = humanTaskService.getTasksAssignedAsPotentialOwnerToCurrentUser();

        modelMap.addAttribute("theTasks", tasks);

        logger.debug(tasks.size() + " tareas encontradas, redireccionando a la vista");

        return "index";
    }
}
