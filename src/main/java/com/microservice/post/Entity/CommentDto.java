package com.microservice.post.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String body;
    private String commentId;
    private String email;
    private String name;
    private String postId;

}
