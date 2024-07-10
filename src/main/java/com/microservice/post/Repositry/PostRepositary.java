package com.microservice.post.Repositry;

import com.microservice.post.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepositary extends JpaRepository<Post,String> {
}
