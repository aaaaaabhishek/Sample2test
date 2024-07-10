package com.microservice.post.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="posts")
public class Post {
    @Id
    private String id;
    private String tittle;
    private String description;
    private String content;

}
