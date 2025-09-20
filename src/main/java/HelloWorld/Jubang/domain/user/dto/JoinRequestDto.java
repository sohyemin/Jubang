package HelloWorld.Jubang.domain.user.dto;

import jakarta.validation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JoinRequestDto {

    @NotBlank(message = "이메일 필수 입력 항목입니다") // null , "", " " 허용하지 않음
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "이메일 형식이 올바르지 않습니다")
    private String email;
    private String username;
    private String password;

}
