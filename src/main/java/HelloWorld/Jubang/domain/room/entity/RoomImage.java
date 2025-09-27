package HelloWorld.Jubang.domain.room.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomImage {

    private String imageName;

    private Integer ord;
}
