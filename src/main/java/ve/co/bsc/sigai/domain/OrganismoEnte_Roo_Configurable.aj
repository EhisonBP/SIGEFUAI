package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect OrganismoEnte_Roo_Configurable {
    
    declare @type: OrganismoEnte: @Configurable;
    
}
