package com.spring.news.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "title")
    private String title;
    @Column(name = "brief_news")
    private String briefNews;
    @Column(name = "content")
    private String content;
    @Column(name = "publication_date")
    private Date publicationDate;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "image_path")
    private String imagePath;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "users_id")
    private User author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id && isActive == news.isActive && Objects.equals(title, news.title) &&
                Objects.equals(briefNews, news.briefNews) && Objects.equals(content, news.content) &&
                Objects.equals(publicationDate, news.publicationDate) && Objects.equals(creationDate, news.creationDate) &&
                Objects.equals(imagePath, news.imagePath) && Objects.equals(author, news.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, briefNews, content, publicationDate, creationDate, isActive, imagePath, author);
    }
}
