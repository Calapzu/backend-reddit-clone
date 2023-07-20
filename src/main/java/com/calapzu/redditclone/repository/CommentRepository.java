package com.calapzu.redditclone.repository;

import com.calapzu.redditclone.model.Comment;
import com.calapzu.redditclone.model.Post;
import com.calapzu.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
