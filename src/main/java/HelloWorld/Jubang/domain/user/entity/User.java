package HelloWorld.Jubang.domain.user.entity;

import HelloWorld.Jubang.domain.user.dto.JoinRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User {

    @Id
    private String email;
    private String firstName;
    private String secondName;
    private String password;

    public static User from(JoinRequestDto request) {
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .secondName(request.getSecondName())
                .build();
    }
}
