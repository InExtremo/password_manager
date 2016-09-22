package pasman.dto;

import pasman.bean.UserClient;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Max on 21.09.2016.
 */
@XmlRootElement
public class UserDto {

    private Integer id;
    private String username;
    private String password;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

        UserDto userClient = (UserDto) o;

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
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * Method for copy all data from UserClient bean.
     *
     * @param user UserClient bean for copy
     */
    public void setUserDtoFromUser(UserClient user){
        this.setId(user.getId());
        this.setPassword(user.getPassword());
        this.setName(user.getName());
        this.setUsername(user.getUsername());
    }
}
