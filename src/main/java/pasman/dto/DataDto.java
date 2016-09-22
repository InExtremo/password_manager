package pasman.dto;

import pasman.bean.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Max on 21.09.2016.
 */
@XmlRootElement
public class DataDto {
    private Integer id;
    private String name;
    private String link;
    private String login;
    private String password;
    private String description;
    private Integer userId;

    /**
     * Method for copy all data from Data bean.
     *
     * @param data Data bean for copy data.
     */
    public void setDataDtoFromData(Data data) {
        this.setDescription(data.getDescription());
        this.setPassword(data.getPassword());
        this.setLink(data.getLink());
        this.setLogin(data.getLogin());
        this.setName(data.getName());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataDto data = (DataDto) o;

        if (id != null ? !id.equals(data.id) : data.id != null) return false;
        if (userId != null ? !userId.equals(data.userId) : data.userId != null) return false;
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
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                '}';
    }
}
