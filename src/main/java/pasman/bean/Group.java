package pasman.bean;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Max on 17.08.2016.
 */
@Entity(name = "usergroup")
@NamedQuery(name = "Group.getAll", query = "SELECT m from usergroup m")
@XmlRootElement
public class Group {
    private Integer idgroup;
    private String userid;
    private String groupname;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getIdgroup() {
        return idgroup;
    }

    public void setIdgroup(Integer idgroup) {
        this.idgroup = idgroup;
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

        if (idgroup != null ? !idgroup.equals(group1.idgroup) : group1.idgroup != null) return false;
        if (userid != null ? !userid.equals(group1.userid) : group1.userid != null) return false;
        return groupname != null ? groupname.equals(group1.groupname) : group1.groupname == null;

    }

    @Override
    public int hashCode() {
        int result = idgroup != null ? idgroup.hashCode() : 0;
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (groupname != null ? groupname.hashCode() : 0);
        return result;
    }
}
