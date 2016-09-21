package pasman.bean;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Max on 17.08.2016.
 */

@Entity(name = "cleint")
@NamedQueries({
        @NamedQuery(name = "UsersByEmail", query = "SELECT u from cleint u where  u.username = ?1"),
        @NamedQuery(name = "User.getAll", query = "SELECT u from cleint u")
})
@XmlRootElement
public class UserClient implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String name;

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
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserClient userClient = (UserClient) o;

        if (id != null ? !id.equals(userClient.id) : userClient.id != null) return false;
        if (username != null ? !username.equals(userClient.username) : userClient.username != null) return false;
        if (password != null ? !password.equals(userClient.password) : userClient.password != null) return false;
        return name != null ? name.equals(userClient.name) : userClient.name == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserClient{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
