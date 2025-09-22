package HelloWorld.Jubang.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
public class LoginRequestDTO {

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "패스워드를  입력해주세요")
    private String password;
}
