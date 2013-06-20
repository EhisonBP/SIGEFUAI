package ve.co.bsc.sigai.web;

import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.JbpmProcess;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@RooWebScaffold(path = "jbpmprocess", automaticallyMaintainView = true, formBackingObject = JbpmProcess.class)
@RequestMapping("/jbpmprocess/**")
@Controller
public class JbpmProcessController {

    private static Logger logger = Logger.getLogger(JbpmProcessController.class);

    //we need a special property-editor that knows how to bind the data
    //from the request to a byte[]
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

    @RequestMapping(value = "/jbpmprocess", method = RequestMethod.POST)
    public String create(@Valid JbpmProcess jbpmProcess, BindingResult result, ModelMap modelMap) {
        if (jbpmProcess == null) throw new IllegalArgumentException("A jbpmProcess is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("jbpmProcess", jbpmProcess);
            return "jbpmprocess/create";
        }
        jbpmProcess.persist();
        return "redirect:/jbpmprocess/" + jbpmProcess.getId();
    }

    @RequestMapping(value = "/jbpmprocess/form", method = RequestMethod.GET)
    public String createForm(ModelMap modelMap) {
        modelMap.addAttribute("jbpmProcess", new JbpmProcess());
        return "jbpmprocess/create";
    }

    @RequestMapping(value = "/jbpmprocess/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("jbpmProcess", JbpmProcess.findJbpmProcess(id));
        return "jbpmprocess/show";
    }

    @RequestMapping(value = "/jbpmprocess", method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("jbpmprocesses", JbpmProcess.findJbpmProcessEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) JbpmProcess.countJbpmProcesses() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("jbpmprocesses", JbpmProcess.findAllJbpmProcesses());
        }
        return "jbpmprocess/list";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid JbpmProcess jbpmProcess, BindingResult result, ModelMap modelMap) {
        if (jbpmProcess == null) throw new IllegalArgumentException("A jbpmProcess is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("jbpmProcess", jbpmProcess);
            return "jbpmprocess/update";
        }
        jbpmProcess.merge();
        return "redirect:/jbpmprocess/" + jbpmProcess.getId();
    }

    @RequestMapping(value = "/jbpmprocess/{id}/form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("jbpmProcess", JbpmProcess.findJbpmProcess(id));
        return "jbpmprocess/update";
    }

    @RequestMapping(value = "/jbpmprocess/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        JbpmProcess.findJbpmProcess(id).remove();
        return "redirect:/jbpmprocess?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }

}
