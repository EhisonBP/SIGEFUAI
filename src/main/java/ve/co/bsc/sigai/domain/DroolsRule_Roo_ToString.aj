package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect DroolsRule_Roo_ToString {
    
    public String DroolsRule.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Tipo: ").append(getTipo()).append(", ");
        sb.append("RuleDefinition: ").append(java.util.Arrays.toString(getRuleDefinition()));
        return sb.toString();
    }
    
}
