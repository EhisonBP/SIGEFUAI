package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect JbpmProcess_Roo_JavaBean {
    
    public String JbpmProcess.getNombre() {
        return this.nombre;
    }
    
    public void JbpmProcess.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public byte[] JbpmProcess.getProcessDefinition() {
        return this.processDefinition;
    }
    
    public void JbpmProcess.setProcessDefinition(byte[] processDefinition) {
        this.processDefinition = processDefinition;
    }
    
}
