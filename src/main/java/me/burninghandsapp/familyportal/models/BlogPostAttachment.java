package me.burninghandsapp.familyportal.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class BlogPostAttachment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "blog_post_id", referencedColumnName = "id")
    private BlogPostItems blogPost;

    @Column(length = 255)
    private String fileName;

    @Column(length = 100)
    private String contentType;

    @Column(columnDefinition = "TEXT")
    private String blobUrl; // Azure Blob Storage URL

    @Column(name = "dateUploaded", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateUploaded;

    private Long fileSize; // File size in bytes

    public BlogPostAttachment() {
        this.dateUploaded = LocalDateTime.now();
    }
}