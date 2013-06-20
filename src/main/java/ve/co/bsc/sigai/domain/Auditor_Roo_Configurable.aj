package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect Auditor_Roo_Configurable {
    
    declare @type: Auditor: @Configurable;
    
}
