package ve.co.bsc.sigai.form;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.format.annotation.DateTimeFormat;

public class DummyForm implements Serializable {

    @NotNull
    @Size(max=15)
    private String dummyText;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dummyDate;

    private Double dummyNumber;

    private Long dummyReferenceId;

    public Date getDummyDate() {
        return dummyDate;
    }

    public void setDummyDate(Date dummyDate) {
        this.dummyDate = dummyDate;
    }

    public Double getDummyNumber() {
        return dummyNumber;
    }

    public void setDummyNumber(Double dummyNumber) {
        this.dummyNumber = dummyNumber;
    }

    public Long getDummyReferenceId() {
        return dummyReferenceId;
    }

    public void setDummyReferenceId(Long dummyReferenceId) {
        this.dummyReferenceId = dummyReferenceId;
    }

    public String getDummyText() {
        return dummyText;
    }

    public void setDummyText(String dummyText) {
        this.dummyText = dummyText;
    }


    

    // este metodo sera invocado EN el state
    // "Prueba"
    public void validatePrueba(ValidationContext context) {

        context.getMessageContext().addMessage(new MessageBuilder().info().defaultText("(MENSAJE) El telefono debe tener 10 digitos numericos").build());
        
        //context.getMessageContext().addMessage(new MessageBuilder().error().source("CAMPO").defaultText("(MENSAJE) El telefono debe tener 10 digitos numericos").build());
    }
}
