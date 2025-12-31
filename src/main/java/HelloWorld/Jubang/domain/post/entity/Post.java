package HelloWorld.Jubang.domain.post.entity;

import HelloWorld.Jubang.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", nullable = false)
    private User user;

    private String title;
    private String content;
}
