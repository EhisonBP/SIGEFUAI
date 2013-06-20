package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect TipoOrganismo_Roo_Configurable {
    
    declare @type: TipoOrganismo: @Configurable;
    
}
