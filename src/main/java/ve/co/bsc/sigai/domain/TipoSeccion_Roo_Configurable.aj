package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect TipoSeccion_Roo_Configurable {
    
    declare @type: TipoSeccion: @Configurable;
    
}
