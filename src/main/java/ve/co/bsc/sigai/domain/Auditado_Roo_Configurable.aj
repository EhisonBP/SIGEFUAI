package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect Auditado_Roo_Configurable {
    
    declare @type: Auditado: @Configurable;
    
}
