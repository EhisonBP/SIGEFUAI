package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect RolUsuario_Roo_Configurable {
    
    declare @type: RolUsuario: @Configurable;
    
}
