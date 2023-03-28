package priv.mKurtycz.blog.springbootblogrestapi.service;

import priv.mKurtycz.blog.springbootblogrestapi.payload.PostDTO;
import priv.mKurtycz.blog.springbootblogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir);
    PostDTO getPostById(Long id);
    PostDTO updatePost(PostDTO postDTO, Long id);
    void deletePostById(Long id);
}
