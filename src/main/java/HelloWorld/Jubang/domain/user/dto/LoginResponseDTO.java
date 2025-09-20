package HelloWorld.Jubang.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseDTO {

    private String email;
    private String name;
    private List<String> roles;
    private String accessToken;
    private String refreshToken; // 앱 전용 fresh token

}
