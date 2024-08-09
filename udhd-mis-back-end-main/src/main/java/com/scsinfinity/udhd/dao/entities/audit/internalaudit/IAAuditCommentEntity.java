package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.audit.AuditCriteriaEntity;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.services.audit.dto.AuditCommentDTO;
import com.scsinfinity.udhd.services.audit.dto.IAAuditCommentDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IA_AUDIT_COMMENT")
public class IAAuditCommentEntity extends BaseIdEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2379708470665930994L;

    @NotBlank
    @Size(min = 3, max = 2000)
    private String comment;


    @JsonIgnore
    public IAAuditCommentDTO getDTO() {
        return IAAuditCommentDTO.builder().comment(comment).id(getId()).build();
    }
}
