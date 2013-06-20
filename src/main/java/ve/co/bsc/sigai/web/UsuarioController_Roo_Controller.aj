package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import java.util.List;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.Usuario;

privileged aspect UsuarioController_Roo_Controller {
    
    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
    public String UsuarioController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("usuarios", Usuario.findUsuarioEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Usuario.countUsuarios() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("usuarios", Usuario.findAllUsuarios());
        }
        return "usuario/list";
    }
    
    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.DELETE)
    public String UsuarioController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Usuario.findUsuario(id).remove();
        return "redirect:/usuario?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByLogin/form", method = RequestMethod.GET)
    public String UsuarioController.findUsuariosByLoginForm(ModelMap modelMap) {
        return "usuario/findUsuariosByLogin";
    }
    
    @RequestMapping(value = "find/ByLogin", method = RequestMethod.GET)
    public String UsuarioController.findUsuariosByLogin(@RequestParam("login") String login, ModelMap modelMap) {
        if (login == null || login.length() == 0) throw new IllegalArgumentException("A Login is required.");
        modelMap.addAttribute("usuarios", Usuario.findUsuariosByLogin(login).getResultList());
        return "usuario/list";
    }
    
    @RequestMapping(value = "find/ByRoles/form", method = RequestMethod.GET)
    public String UsuarioController.findUsuariosByRolesForm(ModelMap modelMap) {
        modelMap.addAttribute("rolusuarios", RolUsuario.findAllRolUsuarios());
        return "usuario/findUsuariosByRoles";
    }
    
    @RequestMapping(value = "find/ByRoles", method = RequestMethod.GET)
    public String UsuarioController.findUsuariosByRoles(@RequestParam("roles") List<RolUsuario> roles, ModelMap modelMap) {
        if (roles == null) throw new IllegalArgumentException("A Roles is required.");
        modelMap.addAttribute("usuarios", Usuario.findUsuariosByRoles(roles).getResultList());
        return "usuario/list";
    }
    
}
