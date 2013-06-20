package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect EstadoPlan_Roo_Configurable {
    
    declare @type: EstadoPlan: @Configurable;
    
}
