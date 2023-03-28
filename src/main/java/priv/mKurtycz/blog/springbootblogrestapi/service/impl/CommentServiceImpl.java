package priv.mKurtycz.blog.springbootblogrestapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import priv.mKurtycz.blog.springbootblogrestapi.entity.Comment;
import priv.mKurtycz.blog.springbootblogrestapi.entity.Post;
import priv.mKurtycz.blog.springbootblogrestapi.exception.BlogAPIException;
import priv.mKurtycz.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import priv.mKurtycz.blog.springbootblogrestapi.payload.CommentDTO;
import priv.mKurtycz.blog.springbootblogrestapi.repository.CommentRepository;
import priv.mKurtycz.blog.springbootblogrestapi.repository.PostRepository;
import priv.mKurtycz.blog.springbootblogrestapi.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {

        Comment comment = mapToEntity(commentDTO);

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId.toString()));

        comment.setPost(post);

        Comment commentResponse = commentRepository.save(comment);

        return mapToDTO(commentResponse);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {

        List<Comment> comments = commentRepository.findByPostId(postId);

        if (comments.isEmpty()) {
            return null;
        }
        else {
            return comments.stream()
                    .map(comment -> mapToDTO(comment))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId.toString()));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId.toString()));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post!");
        }
        else {
            return mapToDTO(comment);
        }
    }

    @Override
    public CommentDTO updateCommentById(Long postId, Long commentId, CommentDTO commentDTO) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId.toString()));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId.toString()));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post!");
        }

        if (commentDTO.getName() != null) {
            comment.setName(commentDTO.getName());
        }
        if (commentDTO.getEmail() != null) {
            comment.setEmail(commentDTO.getEmail());
        }
        if (commentDTO.getBody() != null) {
            comment.setBody(commentDTO.getBody());
        }

        Comment commentUpdated = commentRepository.save(comment);

        return mapToDTO(commentUpdated);
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId.toString()));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId.toString()));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post!");
        }
        else {
            commentRepository.delete(comment);
        }
    }

    private CommentDTO mapToDTO (Comment comment) {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setBody(comment.getBody());
        commentDTO.setEmail(comment.getEmail());

        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();

        comment.setName(commentDTO.getName());
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());

        return comment;
    }
}
