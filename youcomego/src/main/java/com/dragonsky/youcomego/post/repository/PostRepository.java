package com.dragonsky.youcomego.post.repository;

import com.dragonsky.youcomego.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
