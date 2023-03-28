package priv.mKurtycz.blog.springbootblogrestapi.service;

import priv.mKurtycz.blog.springbootblogrestapi.payload.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(Long postId, CommentDTO commentDTO);

    List<CommentDTO> getCommentsByPostId(Long postId);

    CommentDTO getCommentById(Long postId, Long commentId);

    CommentDTO updateCommentById(Long postId, Long commentId, CommentDTO commentDTO);

    void deleteCommentById(Long postId, Long commentId);
}
