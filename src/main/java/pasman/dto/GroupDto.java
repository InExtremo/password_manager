package pasman.dto;

import pasman.bean.Group;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Max on 21.09.2016.
 */
@XmlRootElement
public class GroupDto {

    private Integer id;
    private String userid;
    private String groupname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer idgroup) {
        this.id = idgroup;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

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

        GroupDto group1 = (GroupDto) o;

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
        return "GroupDto{" +
                "idgroup=" + id +
                ", userid='" + userid + '\'' +
                ", groupname='" + groupname + '\'' +
                '}';
    }

    /**
     * Method for copy all data from Group bean.
     *
     * @param group Group bean for copy data.
     */
    public void setFromGroup(Group group){
        this.setId(group.getId());
        this.setUserid(group.getUserid());
        this.setGroupName(group.getGroupName());
    }
}
