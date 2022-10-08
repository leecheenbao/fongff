package fongff.model;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class SysFuncId implements Serializable {
    private static final long serialVersionUID = 478085060338629005L;
    @Size(max = 5)
    @NotNull
    @Column(name = "module", nullable = false, length = 5)
    private String module;

    @NotNull
    @Column(name = "indexR", nullable = false)
    private String indexR;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SysFuncId entity = (SysFuncId) o;
        return Objects.equals(this.module, entity.module) &&
                Objects.equals(this.indexR, entity.indexR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(module, indexR);
    }

}