package ve.co.bsc.sigai.domain;

import java.lang.Integer;

privileged aspect JbpmSession_Roo_JavaBean {
    
    public Integer JbpmSession.getSessionId() {
        return this.sessionId;
    }
    
    public void JbpmSession.setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }
    
}
