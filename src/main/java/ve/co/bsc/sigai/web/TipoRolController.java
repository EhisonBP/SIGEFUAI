package ve.co.bsc.sigai.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.TipoRol;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "tiporol", automaticallyMaintainView = true, formBackingObject = TipoRol.class)
@RequestMapping("/tiporol/**")
@Controller
public class TipoRolController {
}
