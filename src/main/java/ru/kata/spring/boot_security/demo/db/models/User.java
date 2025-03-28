package ru.kata.spring.boot_security.demo.db.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "users", uniqueConstraints =
        {
                @UniqueConstraint(columnNames = "id"),
                @UniqueConstraint(columnNames = "mail")
        })
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String name;

    @Column
    @NotNull
    @Digits(integer = 3, fraction = 0)
    @Positive
    private int age;

    @Column
    @NotBlank
    @Email
    private String mail;

    @Column
    @NotBlank
    private String password;

    @JoinTable(name = "user_roles")
    @ManyToMany
    private Set<Role> roles;


    public User(String name, int age, String mail, String password) {
        this.name = name;
        this.age = age;
        this.mail = mail;
        this.password = password;
    }

    public User(long id, String name, int age, String mail, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mail = mail;
        this.password = password;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return age;
    }

    public String getMail() {
        return (this.mail);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return (this.name + ", возраст: " + this.age + ", @mail: " + this.mail + " роли: "
                + Arrays.toString(this.roles.toArray()));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
