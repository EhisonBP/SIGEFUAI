package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect TipoUnidad_Roo_Configurable {
    
    declare @type: TipoUnidad: @Configurable;
    
}
