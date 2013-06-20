/*
 * Licencia GPL v3
 * 
 * Copyright (C) 2013 Centro Nacional de Tecnologías de Información.
 * SIGEFUAI Sistema de Informacion Gerencial de Fortalecimiento de las Unidades de Auditoria Interna
 * 
 * Copyright (C) 2013 Ehison Perez, Gerardo Perez, Alexis Veliz. All Rights Reserved.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses
 */

package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findAuditadoesByLogin", "findAuditadoesByCedulaEquals", "findAuditadoesByIdOrganismoEnte" })
public class Auditado implements ObjetoNombreClase, Serializable {

    @NotNull
    private String nombre;

    @NotNull
    private String apellido;

    @NotNull
    private String cedula;

    @NotNull
    private String login;

    @NotNull
    @Size(min = 2)
    private String telefono;

    @NotNull
    @Size(min = 2)
    private String celular;

    @Size(min = 2)
    private String correo;

    @ManyToOne(targetEntity = CodigoArea.class)
    @JoinColumn
    private CodigoArea telfOficina;

    @ManyToOne(targetEntity = CodigoArea.class)
    @JoinColumn
    private CodigoArea telfCelular;

    @ManyToOne(targetEntity = OrganismoEnte.class)
    @JoinColumn
    private OrganismoEnte idOrganismoEnte;

    @ManyToOne(targetEntity = EstructuraOrganizativa.class)
    @JoinColumn
    private EstructuraOrganizativa idEstructura;

    @ManyToOne(targetEntity = EstructuraCargos.class)
    @JoinColumn
    private EstructuraCargos cargo;

    @ManyToOne(targetEntity = EstadoAuditor.class)
    @JoinColumn
    private EstadoAuditor estadoAuditado;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNombre()).append(" ");
        sb.append(getApellido()).append(" ");
        return sb.toString();
    }

    @Override
    public String getNombreClase() {
        return "Auditado";
    }
}
