package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect JbpmProcess_Roo_ToString {
    
    public String JbpmProcess.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("ProcessDefinition: ").append(java.util.Arrays.toString(getProcessDefinition()));
        return sb.toString();
    }
    
}
