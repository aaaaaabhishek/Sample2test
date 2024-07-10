package com.microservice.post.Service;

import com.microservice.post.Configuration.RestTemplateConfig;
import com.microservice.post.Entity.CommentDto;
import com.microservice.post.Entity.Post;
import com.microservice.post.ExternalService.Commentservice;
import com.microservice.post.Payload.Comment;
import com.microservice.post.Payload.PostDto;
import com.microservice.post.Repositry.PostRepositary;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostRepositary postRepo;
    @Autowired
    private RestTemplateConfig restTemplate;
    @Autowired
    private Commentservice commentservice;
    @Autowired
    private ModelMapper modelMapper;
    public PostDto savePost(PostDto postdto){
        String postId= UUID.randomUUID().toString();
        Post post=new Post();
        post.setId(postId);
        Post savedpost=postRepo.save(post);

        return modelMapper.map(savedpost,PostDto.class);
    }

    public PostDto findPostByid(String postId) {
       Post post= postRepo.findById(postId).get();
        return modelMapper.map(post,PostDto.class);
    }

    public PostDto getPostWithComments(String postId) {
        Post post = postRepo.findById(postId).get();
//        ArrayList comments = restTemplate.getRestTemplate().getForObject("http://COMMENT-SERVICE/api/comments/" + postId, ArrayList.class);
        Comment comment= commentservice.getPostWithComments(postId);
        PostDto postDto=modelMapper.map(post,PostDto.class);
//        PostDto postDto=new PostDto();
//        postDto.setPostId(post.getId());
//        postDto.setContent(post.getContent());
//        postDto.setTittle(post.getTittle());
//        postDto.setDescription(post.getDescription());
        postDto.setComments((List<Comment>) comment);
        return postDto;
    }

    public CommentDto saveComment(CommentDto commentDto) {
        return commentservice.saveComment(commentDto);
    }
}
