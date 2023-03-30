package priv.mKurtycz.blog.springbootblogrestapi.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import priv.mKurtycz.blog.springbootblogrestapi.payload.PostDTO;
import priv.mKurtycz.blog.springbootblogrestapi.payload.PostResponse;
import priv.mKurtycz.blog.springbootblogrestapi.service.PostService;
import priv.mKurtycz.blog.springbootblogrestapi.utils.AppConstants;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping()
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable Long id) {
        return new ResponseEntity<>(postService.updatePost(postDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePostById(id);

        return new ResponseEntity<>(String.format("Post with id: %s has been deleted!", id.toString()), HttpStatus.OK);
    }
}
