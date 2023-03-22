package priv.mKurtycz.blog.springbootblogrestapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.mKurtycz.blog.springbootblogrestapi.entity.Post;
import priv.mKurtycz.blog.springbootblogrestapi.payload.PostDTO;
import priv.mKurtycz.blog.springbootblogrestapi.repository.PostRepository;
import priv.mKurtycz.blog.springbootblogrestapi.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO) {

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post newPost = postRepository.save(post);

        PostDTO responsePost = new PostDTO();
        responsePost.setId(newPost.getId());
        responsePost.setTitle(newPost.getTitle());
        responsePost.setDescription(newPost.getDescription());
        responsePost.setContent(newPost.getContent());

        return responsePost;
    }
}
