package com.spring.news.model.news;

import com.spring.news.model.user.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "news")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Title should not be empty")
    @Size(min = 5, message = "Title should not be less than 5 characters")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Brief should not be empty")
    @Size(min = 5, message = "Brief should not be less than 5 characters")
    @Column(name = "brief_news")
    private String briefNews;

    @NotNull(message = "Content should not be empty")
    @Size(min = 5, message = "Content should not be less than 50 characters")
    @Column(name = "content")
    private String content;

    @Column(name = "publication_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publicationDate;

    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    @Column(name = "is_active")
    private boolean isActive;

    @NotNull(message = "Image path should not be empty")
    @Column(name = "image_path")
    private String imagePath;

    @NotNull(message = "Tag path should not be empty")
    @Column(name = "tag", columnDefinition = "ENUM('SPORT', 'POLITIC', 'TRAVEL')")
    @Enumerated(EnumType.STRING)
    private Tag tag;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "users_id")
    private User author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id && isActive == news.isActive && Objects.equals(title, news.title) && Objects.equals(briefNews, news.briefNews) && Objects.equals(content, news.content) && Objects.equals(publicationDate, news.publicationDate) && Objects.equals(creationDate, news.creationDate) && Objects.equals(imagePath, news.imagePath) && tag == news.tag && Objects.equals(author, news.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, briefNews, content, publicationDate, creationDate, isActive, imagePath, tag, author);
    }

}
