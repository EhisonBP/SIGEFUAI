package ve.co.bsc.sigai.web;

import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.sigai.service.HumanTaskService;
import ve.co.bsc.sigai.service.exception.ServiceTemporarilyUnavailableException;

@RequestMapping("/humantask/**")
@Controller
public class HumanTaskController {

    private Logger logger = Logger.getLogger(HumanTaskController.class);
    @Autowired
    HumanTaskService humanTaskService;

    @RequestMapping(value = "/humantask/list", method = RequestMethod.GET)
    public String list(ModelMap modelMap) {

        logger.debug("Iniciando lista de human tasks");

        try {

            List<TaskSummary> tasks = humanTaskService.getTasksAssignedAsPotentialOwnerToCurrentUser();
            modelMap.addAttribute("theTasks", tasks);
            logger.debug(tasks.size() + " tareas encontradas, redireccionando a la vista");

        } catch (ServiceTemporarilyUnavailableException e) {
            modelMap.addAttribute("theTasks", new LinkedList<TaskSummary>());
            modelMap.addAttribute("unavailable", e);
        }

        return "humantask/list";
    }

    @RequestMapping(value = "/humantask/listMine", method = RequestMethod.GET)
    public String listMine(ModelMap modelMap) {

        logger.debug("Iniciando lista de human tasks propias");

        try {

            List<TaskSummary> tasks = humanTaskService.getTasksOwnedByCurrentUser();
            modelMap.addAttribute("theTasks", tasks);
            logger.debug(tasks.size() + " tareas encontradas, redireccionando a la vista");

        } catch (ServiceTemporarilyUnavailableException e) {
            modelMap.addAttribute("theTasks", new LinkedList<TaskSummary>());
            modelMap.addAttribute("unavailable", e);
        }

        return "humantask/listMine";
    }

    @RequestMapping(value = "/humantask/claim/{id}", method = RequestMethod.POST)
    public String claim(@PathVariable("id") Long id, ModelMap modelMap) {
    
        humanTaskService.claimTaskByCurrentUser(id);

        logger.debug("Tarea " + id + " asignada al para el usuario " + Usuario.findUsuarioCurrentlyLoggedIn().getLogin() + " redireccionando a mis tareas");

        return "redirect:/humantask/list";
    }

    @RequestMapping(value = "/humantask/complete/{id}", method = RequestMethod.POST)
    public String complete(@PathVariable("id") Long id, ModelMap modelMap) {

        logger.debug("Iniciando complete de human tasks");

        humanTaskService.completeTaskByCurrentUser(id);

        logger.debug("Tarea " + id + " completada por el usuario " + Usuario.findUsuarioCurrentlyLoggedIn().getLogin() + " redireccionando a mis tareas");

        return "redirect:/humantask/list";
    }
}
