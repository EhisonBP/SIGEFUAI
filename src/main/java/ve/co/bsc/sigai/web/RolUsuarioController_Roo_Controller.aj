package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.TipoRol;

privileged aspect RolUsuarioController_Roo_Controller {
    
    @RequestMapping(value = "/rolusuario/{id}", method = RequestMethod.GET)
    public String RolUsuarioController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("rolUsuario_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("rolUsuario_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("rolUsuario", RolUsuario.findRolUsuario(id));
        return "rolusuario/show";
    }
    
    @RequestMapping(value = "/rolusuario", method = RequestMethod.GET)
    public String RolUsuarioController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("rolusuarios", RolUsuario.findRolUsuarioEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) RolUsuario.countRolUsuarios() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("rolusuarios", RolUsuario.findAllRolUsuarios());
        }
        modelMap.addAttribute("rolUsuario_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("rolUsuario_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "rolusuario/list";
    }
    
    @RequestMapping(value = "/rolusuario/{id}", method = RequestMethod.DELETE)
    public String RolUsuarioController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        RolUsuario.findRolUsuario(id).remove();
        return "redirect:/rolusuario?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByNombreEquals/form", method = RequestMethod.GET)
    public String RolUsuarioController.findRolUsuariosByNombreEqualsForm(ModelMap modelMap) {
        return "rolusuario/findRolUsuariosByNombreEquals";
    }
    
    @RequestMapping(value = "find/ByNombreEquals", method = RequestMethod.GET)
    public String RolUsuarioController.findRolUsuariosByNombreEquals(@RequestParam("nombre") String nombre, ModelMap modelMap) {
        if (nombre == null || nombre.length() == 0) throw new IllegalArgumentException("A Nombre is required.");
        modelMap.addAttribute("rolusuarios", RolUsuario.findRolUsuariosByNombreEquals(nombre).getResultList());
        return "rolusuario/list";
    }
    
    @RequestMapping(value = "find/ByNombreNotEquals/form", method = RequestMethod.GET)
    public String RolUsuarioController.findRolUsuariosByNombreNotEqualsForm(ModelMap modelMap) {
        return "rolusuario/findRolUsuariosByNombreNotEquals";
    }
    
    @RequestMapping(value = "find/ByNombreNotEquals", method = RequestMethod.GET)
    public String RolUsuarioController.findRolUsuariosByNombreNotEquals(@RequestParam("nombre") String nombre, ModelMap modelMap) {
        if (nombre == null || nombre.length() == 0) throw new IllegalArgumentException("A Nombre is required.");
        modelMap.addAttribute("rolusuarios", RolUsuario.findRolUsuariosByNombreNotEquals(nombre).getResultList());
        return "rolusuario/list";
    }
    
    @RequestMapping(value = "find/ByTipoRol/form", method = RequestMethod.GET)
    public String RolUsuarioController.findRolUsuariosByTipoRolForm(ModelMap modelMap) {
        modelMap.addAttribute("tiporols", TipoRol.findAllTipoRols());
        return "rolusuario/findRolUsuariosByTipoRol";
    }
    
    @RequestMapping(value = "find/ByTipoRol", method = RequestMethod.GET)
    public String RolUsuarioController.findRolUsuariosByTipoRol(@RequestParam("tipoRol") TipoRol tipoRol, ModelMap modelMap) {
        if (tipoRol == null) throw new IllegalArgumentException("A TipoRol is required.");
        modelMap.addAttribute("rolusuarios", RolUsuario.findRolUsuariosByTipoRol(tipoRol).getResultList());
        return "rolusuario/list";
    }
    
}
