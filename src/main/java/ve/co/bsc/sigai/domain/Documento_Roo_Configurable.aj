package ve.co.bsc.sigai.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect Documento_Roo_Configurable {
    
    declare @type: Documento: @Configurable;
    
}
