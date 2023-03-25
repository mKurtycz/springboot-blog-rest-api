package priv.mKurtycz.blog.springbootblogrestapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.mKurtycz.blog.springbootblogrestapi.entity.Post;
import priv.mKurtycz.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import priv.mKurtycz.blog.springbootblogrestapi.payload.PostDTO;
import priv.mKurtycz.blog.springbootblogrestapi.repository.PostRepository;
import priv.mKurtycz.blog.springbootblogrestapi.service.PostService;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO) {

        Post post = mapToEntity(postDTO);

        Post newPost = postRepository.save(post);

        PostDTO responsePost = mapToDTO(newPost);

        return responsePost;
    }

    @Override
    public List<PostDTO> getAllPosts() {

        List<Post> posts = postRepository.findAll();

        if (posts.isEmpty()) {
            return null;
        }
        else {
            return posts.stream()
                    .map(post -> mapToDTO(post))
                    .toList();
        }
    }

    @Override
    public PostDTO getPostById(Long id) {
        return mapToDTO(postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id.toString())));
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id.toString()));

        if (postDTO.getTitle() != null) {
            post.setTitle(postDTO.getTitle());
        }
        else if (postDTO.getDescription() != null) {
            post.setDescription(postDTO.getDescription());
        }
        else if (postDTO.getContent() != null) {
            post.setContent(postDTO.getContent());
        }

        Post updatedPost = postRepository.save(post);

        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id.toString()));

        postRepository.delete(post);
    }

    private PostDTO mapToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());

        return postDTO;
    }

    private Post mapToEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        return post;
    }
}
