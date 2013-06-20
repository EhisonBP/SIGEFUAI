/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ve.co.bsc.sigai.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.form.DummyForm;

/**
 *
 * @author jdeoliveira
 */
@Service
public class DummyService {

    public DummyForm createFormDummy() {
        //ModelMap modelMap = new ModelMap();


        // tomado del aspect de actuacion controller
        //modelMap.addAttribute("elformulario", new DummyForm());
        //modelMap.addAttribute("auditors", Auditor.findAllAuditors());

        //return modelMap;

        return new DummyForm();
    }

    /*public void createActuacion(Actuacion x) {
        if (actuacion == null) throw new IllegalArgumentException("A actuacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("actuacion", actuacion);
            modelMap.addAttribute("auditors", Auditor.findAllAuditors());
            return "actuacion/create";
        }
        actuacion.persist();
    }*/

}
