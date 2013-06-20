package ve.co.bsc.sigai.domain.bpm;

import java.lang.String;

privileged aspect ProcessObjectCorrelation_Roo_ToString {
    
    public String ProcessObjectCorrelation.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("ItemId: ").append(getItemId()).append(", ");
        sb.append("ProcessInstanceId: ").append(getProcessInstanceId()).append(", ");
        sb.append("Classname: ").append(getClassname());
        return sb.toString();
    }
    
}
