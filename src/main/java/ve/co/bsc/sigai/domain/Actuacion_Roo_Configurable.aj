package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect Actuacion_Roo_Configurable {
    
    declare @type: Actuacion: @Configurable;
    
}
