package priv.mKurtycz.blog.springbootblogrestapi.service;

import priv.mKurtycz.blog.springbootblogrestapi.payload.PostDTO;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
}
