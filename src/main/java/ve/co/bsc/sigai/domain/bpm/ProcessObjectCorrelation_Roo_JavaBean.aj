package ve.co.bsc.sigai.domain.bpm;

import java.lang.Long;
import java.lang.String;

privileged aspect ProcessObjectCorrelation_Roo_JavaBean {
    
    public Long ProcessObjectCorrelation.getItemId() {
        return this.itemId;
    }
    
    public void ProcessObjectCorrelation.setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    public Long ProcessObjectCorrelation.getProcessInstanceId() {
        return this.processInstanceId;
    }
    
    public void ProcessObjectCorrelation.setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
    
    public String ProcessObjectCorrelation.getClassname() {
        return this.classname;
    }
    
    public void ProcessObjectCorrelation.setClassname(String classname) {
        this.classname = classname;
    }
    
}
