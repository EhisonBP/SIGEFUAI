package ve.co.bsc.sigai.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.EntradaBitacora;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import ve.co.bsc.sigai.domain.TipoEntradaBitacora;
import ve.co.bsc.sigai.form.TipoEntradaBitacoraForm;


@RooWebScaffold(path = "entradabitacora", automaticallyMaintainView = true, formBackingObject = EntradaBitacora.class)
@RequestMapping("/entradabitacora/**")
@SessionAttributes({"entradaBitacora_timeStamp_date_format"})
@Controller
public class EntradaBitacoraController {

    Logger logger = Logger.getLogger(EntradaBitacoraController.class);

    @RequestMapping(value = "/entradabitacora", method = RequestMethod.GET)
    public String list(
            @ModelAttribute("TiposEntradaBitacoraForm") TipoEntradaBitacoraForm form,
            ModelMap modelMap
            )
    {
        modelMap.addAttribute("tipoEntradasBitacora",TipoEntradaBitacora.findAllTipoEntradaBitacoras());
        //modelMap.addAttribute("entradaBitacora_timeStamp_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("FM", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("entradaBitacora_timeStamp_date_format",org.joda.time.format.DateTimeFormat.patternForStyle("SM", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        
        
        //logger.debug("***timeStamp "+ form.getTimeStamp() + "ip " +form.getIp() + "usuario " + form.getUsuario() + "tipoEntradaBitacora " + form.getTipo() );

        TipoEntradaBitacora tipo = form.getTipo();
        Long id;
        if (tipo!=null)
            id = tipo.getId();
        else
            id = null;

       

        
        List<EntradaBitacora> entradas = null;
        entradas =  EntradaBitacora.findEntradasBitacorasByParams(form.getTimeStampDesde(), (form.getTimeStampDesde()!=null),form.getTimeStampHasta(), (form.getTimeStampHasta()!=null), form.getIp(), (form.getIp()!=null), form.getUsuario(), (form.getUsuario()!=null), id, (form.getTipo()!= null));
        modelMap.addAttribute("entradabitacoras",entradas);


        return "entradabitacora/list";
    }

    @RequestMapping(value = "/entradabitacora/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id,
            ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("entradaBitacora_timeStamp_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("FM", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("entradaBitacora", EntradaBitacora.findEntradaBitacora(id));
        return "entradabitacora/show";
    }


}
