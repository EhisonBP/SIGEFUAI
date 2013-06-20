package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.DroolsRuleTypeEnum;

privileged aspect DroolsRule_Roo_JavaBean {
    
    public String DroolsRule.getNombre() {
        return this.nombre;
    }
    
    public void DroolsRule.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public DroolsRuleTypeEnum DroolsRule.getTipo() {
        return this.tipo;
    }
    
    public void DroolsRule.setTipo(DroolsRuleTypeEnum tipo) {
        this.tipo = tipo;
    }
    
    public byte[] DroolsRule.getRuleDefinition() {
        return this.ruleDefinition;
    }
    
    public void DroolsRule.setRuleDefinition(byte[] ruleDefinition) {
        this.ruleDefinition = ruleDefinition;
    }
    
}
