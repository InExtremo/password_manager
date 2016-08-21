package pasman.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Max on 17.08.2016.
 */
@Entity(name = "data")
@NamedQuery(name = "Data.getAll", query = "SELECT d from data d ")//left join fetch d.user
@XmlRootElement
public class Data {
    private Integer id;
    private String name;
    private String link;
    private String login;
    private String password;
    private String description;
    private List<User> user;

    public Data() {
    }

    public void setData(Data newData) {
        this.setDescription(newData.getDescription());
        this.setPassword(newData.getPassword());
        this.setLink(newData.getLink());
        this.setLogin(newData.getLogin());
        this.setName(newData.getName());
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (id != null ? !id.equals(data.id) : data.id != null) return false;
        if (name != null ? !name.equals(data.name) : data.name != null) return false;
        if (link != null ? !link.equals(data.link) : data.link != null) return false;
        if (login != null ? !login.equals(data.login) : data.login != null) return false;
        if (password != null ? !password.equals(data.password) : data.password != null) return false;
        return description != null ? description.equals(data.description) : data.description == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

//    @ManyToMany(mappedBy = "data")
//    public List<User> getUser() {
//        return user;
//    }
//
//    public void setUser(List<User> user) {
//        this.user = user;
//    }
}
