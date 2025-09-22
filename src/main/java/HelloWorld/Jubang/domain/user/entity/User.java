package HelloWorld.Jubang.domain.user.entity;

import HelloWorld.Jubang.domain.user.dto.JoinRequestDTO;
import HelloWorld.Jubang.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User {

    @Id
    private String email;
    private String name;
    private String password;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "use_role_list", joinColumns = @JoinColumn(name = "email"))
    @Column(name = "role")
    @Builder.Default
    private List<UserRole> userRoleList = new ArrayList<>();

    public void addRole(UserRole userRole) { userRoleList.add(userRole); }

    public void changeRole(UserRole role){
        this.userRoleList.clear();
        this.userRoleList.add(role);
    }

    public static User from(JoinRequestDTO request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .build();

        if (request.getEmail().contains("admin")) {
            user.addRole(UserRole.ADMIN);
        } else {
            user.addRole(UserRole.USER);
        }

        return user;
    }
}
