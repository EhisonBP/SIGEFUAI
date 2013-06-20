package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import javax.validation.Valid;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.bpm.ProcessObjectCorrelation;

privileged aspect ProcessObjectCorrelationController_Roo_Controller {
    
    @RequestMapping(value = "/processobjectcorrelation", method = RequestMethod.POST)
    public String ProcessObjectCorrelationController.create(@Valid ProcessObjectCorrelation processObjectCorrelation, BindingResult result, ModelMap modelMap) {
        if (processObjectCorrelation == null) throw new IllegalArgumentException("A processObjectCorrelation is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("processObjectCorrelation", processObjectCorrelation);
            return "processobjectcorrelation/create";
        }
        processObjectCorrelation.persist();
        return "redirect:/processobjectcorrelation/" + processObjectCorrelation.getId();
    }
    
    @RequestMapping(value = "/processobjectcorrelation/form", method = RequestMethod.GET)
    public String ProcessObjectCorrelationController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("processObjectCorrelation", new ProcessObjectCorrelation());
        return "processobjectcorrelation/create";
    }
    
    @RequestMapping(value = "/processobjectcorrelation/{id}", method = RequestMethod.GET)
    public String ProcessObjectCorrelationController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("processObjectCorrelation", ProcessObjectCorrelation.findProcessObjectCorrelation(id));
        return "processobjectcorrelation/show";
    }
    
    @RequestMapping(value = "/processobjectcorrelation", method = RequestMethod.GET)
    public String ProcessObjectCorrelationController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("processobjectcorrelations", ProcessObjectCorrelation.findProcessObjectCorrelationEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ProcessObjectCorrelation.countProcessObjectCorrelations() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("processobjectcorrelations", ProcessObjectCorrelation.findAllProcessObjectCorrelations());
        }
        return "processobjectcorrelation/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String ProcessObjectCorrelationController.update(@Valid ProcessObjectCorrelation processObjectCorrelation, BindingResult result, ModelMap modelMap) {
        if (processObjectCorrelation == null) throw new IllegalArgumentException("A processObjectCorrelation is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("processObjectCorrelation", processObjectCorrelation);
            return "processobjectcorrelation/update";
        }
        processObjectCorrelation.merge();
        return "redirect:/processobjectcorrelation/" + processObjectCorrelation.getId();
    }
    
    @RequestMapping(value = "/processobjectcorrelation/{id}/form", method = RequestMethod.GET)
    public String ProcessObjectCorrelationController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("processObjectCorrelation", ProcessObjectCorrelation.findProcessObjectCorrelation(id));
        return "processobjectcorrelation/update";
    }
    
    @RequestMapping(value = "/processobjectcorrelation/{id}", method = RequestMethod.DELETE)
    public String ProcessObjectCorrelationController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        ProcessObjectCorrelation.findProcessObjectCorrelation(id).remove();
        return "redirect:/processobjectcorrelation?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByItemId/form", method = RequestMethod.GET)
    public String ProcessObjectCorrelationController.findProcessObjectCorrelationsByItemIdForm(ModelMap modelMap) {
        return "processobjectcorrelation/findProcessObjectCorrelationsByItemId";
    }
    
    @RequestMapping(value = "find/ByItemId", method = RequestMethod.GET)
    public String ProcessObjectCorrelationController.findProcessObjectCorrelationsByItemId(@RequestParam("itemId") Long itemId, ModelMap modelMap) {
        if (itemId == null) throw new IllegalArgumentException("A ItemId is required.");
        modelMap.addAttribute("processobjectcorrelations", ProcessObjectCorrelation.findProcessObjectCorrelationsByItemId(itemId).getResultList());
        return "processobjectcorrelation/list";
    }
    
}
