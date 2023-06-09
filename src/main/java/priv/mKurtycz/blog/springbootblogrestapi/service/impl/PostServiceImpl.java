package priv.mKurtycz.blog.springbootblogrestapi.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import priv.mKurtycz.blog.springbootblogrestapi.entity.Post;
import priv.mKurtycz.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import priv.mKurtycz.blog.springbootblogrestapi.payload.PostDTO;
import priv.mKurtycz.blog.springbootblogrestapi.payload.PostResponse;
import priv.mKurtycz.blog.springbootblogrestapi.repository.PostRepository;
import priv.mKurtycz.blog.springbootblogrestapi.service.PostService;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public PostDTO createPost(PostDTO postDTO) {

        Post post = mapToEntity(postDTO);

        Post newPost = postRepository.save(post);

        PostDTO responsePost = mapToDTO(newPost);

        return responsePost;
    }

    @Override
    public PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();

        if (listOfPosts.isEmpty()) {
            return null;
        }
        else {
            List<PostDTO> content =  listOfPosts.stream()
                                        .map(post -> mapToDTO(post))
                                        .toList();

            PostResponse postResponse = new PostResponse(content,
                    posts.getNumber(),
                    posts.getSize(),
                    posts.getTotalElements(),
                    posts.getTotalPages(),
                    posts.isLast());

            return postResponse;
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
        if (postDTO.getDescription() != null) {
            post.setDescription(postDTO.getDescription());
        }
        if (postDTO.getContent() != null) {
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
        // Mapping without ModelMapper
//        PostDTO postDTO = new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setDescription(post.getDescription());
//        postDTO.setContent(post.getContent());

        PostDTO postDTO = mapper.map(post, PostDTO.class);

        return postDTO;
    }

    private Post mapToEntity(PostDTO postDTO) {

        // Mapping without ModelMapper
//        Post post = new Post();
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());

        Post post = mapper.map(postDTO, Post.class);

        return post;
    }
}
