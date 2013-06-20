package ve.co.bsc.sigai.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.TipoOrganismo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "tipoorganismo", automaticallyMaintainView = true, formBackingObject = TipoOrganismo.class)
@RequestMapping("/tipoorganismo/**")
@Controller
public class TipoOrganismoController {
}
