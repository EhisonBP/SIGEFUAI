package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect TipoRol_Roo_Configurable {
    
    declare @type: TipoRol: @Configurable;
    
}
