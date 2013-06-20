package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect Proceso_Roo_Configurable {
    
    declare @type: Proceso: @Configurable;
    
}
