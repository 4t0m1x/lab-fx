package labfx.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * User: Vladislav
 * Date: 11.11.13
 * Time: 16:58
 */
@Entity
public class User {
    private int id;
    private StringProperty userName = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private StringProperty phone = new SimpleStringProperty();
    private StringProperty country = new SimpleStringProperty();
    private BooleanProperty admin = new SimpleBooleanProperty();
    private BooleanProperty banned = new SimpleBooleanProperty();

    public StringProperty userNameProperty() { return userName; }
    public StringProperty passwordProperty() { return password; }
    public StringProperty phoneProperty() { return phone; }
    public StringProperty countryProperty() { return country; }
    public BooleanProperty adminProperty() { return admin; }
    public BooleanProperty bannedProperty() { return banned; }

    @javax.persistence.Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @javax.persistence.Column(name = "userName")
    @Basic
    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    @javax.persistence.Column(name = "password")
    @Basic
    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    @javax.persistence.Column(name = "phone")
    @Basic
    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    @javax.persistence.Column(name = "country")
    @Basic
    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    @javax.persistence.Column(name = "admin")
    @Basic
    public Boolean getAdmin() {
        return admin.get();
    }

    public void setAdmin(Boolean admin) {
        this.admin.set(admin);
    }

    @javax.persistence.Column(name = "banned")
    @Basic
    public Boolean getBanned() {
        return banned.get();
    }

    public void setBanned(Boolean banned) {
        this.banned.set(banned);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (admin != null ? !admin.equals(user.admin) : user.admin != null) return false;
        if (banned != null ? !banned.equals(user.banned) : user.banned != null) return false;
        if (country != null ? !country.equals(user.country) : user.country != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        //noinspection RedundantIfStatement
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (admin != null ? admin.hashCode() : 0);
        result = 31 * result + (banned != null ? banned.hashCode() : 0);
        return result;
    }
}
