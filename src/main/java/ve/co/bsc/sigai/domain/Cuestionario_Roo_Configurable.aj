package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect Cuestionario_Roo_Configurable {
    
    declare @type: Cuestionario: @Configurable;
    
}
