package ve.co.bsc.sigai.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.HistorialCambios;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "historialcambios", automaticallyMaintainView = true, formBackingObject = HistorialCambios.class)
@RequestMapping("/historialcambios/**")
@Controller
public class HistorialCambiosController {
}
