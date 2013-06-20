package ve.co.bsc.sigai.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.bpm.ProcessObjectCorrelation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "processobjectcorrelation", automaticallyMaintainView = true, formBackingObject = ProcessObjectCorrelation.class)
@RequestMapping("/processobjectcorrelation/**")
@Controller
public class ProcessObjectCorrelationController {
}
