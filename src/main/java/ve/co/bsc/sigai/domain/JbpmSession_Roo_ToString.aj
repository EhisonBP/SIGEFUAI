package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect JbpmSession_Roo_ToString {
    
    public String JbpmSession.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("SessionId: ").append(getSessionId());
        return sb.toString();
    }
    
}
