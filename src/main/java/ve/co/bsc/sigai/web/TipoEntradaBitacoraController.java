package ve.co.bsc.sigai.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.TipoEntradaBitacora;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "tipoentradabitacora", automaticallyMaintainView = true, formBackingObject = TipoEntradaBitacora.class)
@RequestMapping("/tipoentradabitacora/**")
@Controller
public class TipoEntradaBitacoraController {
}
