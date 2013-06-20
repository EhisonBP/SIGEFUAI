package ve.co.bsc.sigai.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.Municipios;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "municipios", automaticallyMaintainView = true, formBackingObject = Municipios.class)
@RequestMapping("/municipios/**")
@Controller
public class MunicipiosController {
}
