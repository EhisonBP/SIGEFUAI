package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect Observacion_Roo_Configurable {
    
    declare @type: Observacion: @Configurable;
    
}
