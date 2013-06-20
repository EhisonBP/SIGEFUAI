package ve.co.bsc.sigai.domain;

import java.util.Set;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect AsignarOrganismo_Roo_JavaBean {
    
    public Auditor AsignarOrganismo.getAnalista() {
        return this.analista;
    }
    
    public void AsignarOrganismo.setAnalista(Auditor analista) {
        this.analista = analista;
    }
    
    public Set<OrganismoEnte> AsignarOrganismo.getOrganismo() {
        return this.organismo;
    }
    
    public void AsignarOrganismo.setOrganismo(Set<OrganismoEnte> organismo) {
        this.organismo = organismo;
    }
    
}
