package com.microservice.post.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String postId;
    private String tittle;
    private String description;
    private String content;
    List<Comment> comments;

}
