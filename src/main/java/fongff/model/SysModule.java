package fongff.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@ToString
@Table(name = "sys_module")
public class SysModule {
    @Id
    @Column(name = "id")
    public String id;
    @Column(name = "ename")
    public String ename;
    @Column(name = "cname")
    public String cname;

    @OneToMany

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SysModule that = (SysModule) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
