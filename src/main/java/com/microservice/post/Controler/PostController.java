package com.microservice.post.Controler;
import com.microservice.post.Entity.CommentDto;
import com.microservice.post.Entity.Post;
import com.microservice.post.Payload.Comment;
import com.microservice.post.Payload.PostDto;
import com.microservice.post.Service.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
private static final Logger logger= LoggerFactory.getLogger(PostController.class);
    @PostMapping
    public ResponseEntity<PostDto> savepost(@RequestBody PostDto postDto) {
        PostDto newpost1 = postService.savePost(postDto);
        return new ResponseEntity<>(newpost1, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable String postId) {
        PostDto postByid = postService.findPostByid(postId);
       return new ResponseEntity<>(postByid, HttpStatus.CREATED);
    }
//    @GetMapping("/{postId}")
//    public Post getPostById(@PathVariable String postId) {
//        Post postByid = postService.findPostByid(postId);
//        return postByid;
//    }
@PostMapping("/{postId}/comments")
public ResponseEntity<CommentDto> saveComment(@PathVariable String postId, @RequestBody CommentDto commentDto) {
    commentDto.setPostId(postId); // Ensure the comment is associated with the correct post
    CommentDto newComment = postService.saveComment(commentDto);
    return new ResponseEntity<>(newComment, HttpStatus.CREATED);
}
@GetMapping("{postId}/comment")
@CircuitBreaker(name="commentBreaker", fallbackMethod = "commentFallback")
//@Retry(name="commentRetry",fallbackMethod = "commentFallback")
@RateLimiter(name = "userRateLimiter",fallbackMethod = "commentFallback")
public ResponseEntity<PostDto> getPostWithComments(@PathVariable String postId){
      PostDto postDto=postService.getPostWithComments(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
}
public ResponseEntity<PostDto> commentFallback(String postId,Exception ex){//whatever give fallback method all varibale are same
        System.out.println("Fallback is exicuted because service is down :"+ex.getMessage());
        logger.error("Fallback is exicuted because service is down :"+ex.getMessage());
        ex.printStackTrace();
        PostDto dto=new PostDto();
        dto.setPostId("1234");
        dto.setContent("service Down");
        dto.setTittle("service Down");
        dto.setDescription("service Down");
        return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
}

}
