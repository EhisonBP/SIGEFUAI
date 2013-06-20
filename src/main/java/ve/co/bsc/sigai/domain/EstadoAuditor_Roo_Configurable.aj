package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect EstadoAuditor_Roo_Configurable {
    
    declare @type: EstadoAuditor: @Configurable;
    
}
