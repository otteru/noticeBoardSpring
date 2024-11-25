package hello.noticeboard.validation.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostEditForm {
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    @Size(min = 5, max = 400)
    private String body;
}
