package priv.mKurtycz.blog.springbootblogrestapi.payload;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String body;
    private String email;
    private String name;
}
