package com.spring.news.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull(message = "Name should not be null")
    @Size(min = 3, message = "Name should not be less than 3 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Surname should not be null")
    @Size(min = 3, message = "Surname should not be less than 3 characters")
    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "register_date")
    private Date registerDate = new Date();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(registerDate, that.registerDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, registerDate);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", registerDate=" + registerDate +
                '}';
    }
}
