package ve.co.bsc.sigai.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.ItemPlanificacionActuacion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/*
 * Controlador para item planificacion actuacion (CRUD) generado por ROO.
 * Recibe todas las solicitudes a URLs con el patron
 * "/itemplanificacionactuacion/**"
 */
@RooWebScaffold(path = "itemplanificacionactuacion", automaticallyMaintainView = true, formBackingObject = ItemPlanificacionActuacion.class)
@RequestMapping("/itemplanificacionactuacion/**")
@Controller
public class ItemPlanificacionActuacionController {
}
