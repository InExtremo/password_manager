package pasman.bean;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Max on 17.08.2016.
 */
@Entity(name = "usergroup")
@NamedQuery(name = "Group.getAll", query = "SELECT m from usergroup m")
@XmlRootElement
public class Group implements Serializable {
    private Integer id;
    private String userid;
    private String groupname;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer idgroup) {
        this.id = idgroup;
    }

    @Basic
    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "groupName")
    public String getGroupName() {
        return groupname;
    }

    public void setGroupName(String group) {
        this.groupname = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group1 = (Group) o;

        if (id != null ? !id.equals(group1.id) : group1.id != null) return false;
        if (userid != null ? !userid.equals(group1.userid) : group1.userid != null) return false;
        return groupname != null ? groupname.equals(group1.groupname) : group1.groupname == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (groupname != null ? groupname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "idgroup=" + id +
                ", userid='" + userid + '\'' +
                ", groupname='" + groupname + '\'' +
                '}';
    }
}
