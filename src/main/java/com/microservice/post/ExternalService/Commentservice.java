package com.microservice.post.ExternalService;

import com.microservice.post.Entity.CommentDto;
import com.microservice.post.Payload.Comment;
import com.microservice.post.Payload.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="COMMENT-SERVICE")
public interface Commentservice {
    @GetMapping("/api/comments/{postId}")
    Comment getPostWithComments(@PathVariable String postId);
    @PostMapping("/api/comments")
    CommentDto saveComment(@RequestBody CommentDto commentDto);

}
