package com.spring.news.model.user;

import com.spring.news.model.news.News;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Login should not be empty")
    @Size(min = 3, message = "Login should not be less than 3 characters")
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull(message = "Password should not be empty")
    @Size(min = 3, message = "Password should not be less than 3 characters")
    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    @NotNull(message = "Password should not be empty")
    @Size(min = 3, message = "Password should not be less than 3 characters")
    transient private String confirmPassword;

    @NotNull(message = "Email should not be empty")
    @Size(min = 3, message = "Email should not be less than 3 characters")
    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id")
    private UserInfo userInfo;

    @Column(name = "is_active", nullable = false)
    private boolean  isActive;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<News> newsList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isActive == user.isActive && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(confirmPassword, user.confirmPassword) && Objects.equals(email, user.email) && Objects.equals(role, user.role) && Objects.equals(userInfo, user.userInfo) && Objects.equals(newsList, user.newsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, confirmPassword, email, role, userInfo, isActive, newsList);
    }
}
