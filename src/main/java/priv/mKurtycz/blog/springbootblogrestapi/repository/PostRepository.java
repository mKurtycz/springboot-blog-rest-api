package priv.mKurtycz.blog.springbootblogrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import priv.mKurtycz.blog.springbootblogrestapi.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {}
