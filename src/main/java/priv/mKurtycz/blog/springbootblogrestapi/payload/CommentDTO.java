package priv.mKurtycz.blog.springbootblogrestapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    @NotEmpty(message = "Body should not be null or empty!")
    @Size(min = 10, message = "Comment body must be minimum 10 characters!")
    private String body;
    @NotEmpty(message = "Email should not be null or empty!")
    @Email(message = "Provide valid email!")
    private String email;
    @NotEmpty(message = "Name should not be null or empty!")
    private String name;
}
