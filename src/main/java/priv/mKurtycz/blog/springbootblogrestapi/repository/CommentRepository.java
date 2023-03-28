package priv.mKurtycz.blog.springbootblogrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import priv.mKurtycz.blog.springbootblogrestapi.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
