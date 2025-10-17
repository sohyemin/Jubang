package HelloWorld.Jubang.domain.room.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomImageRequest {

    @NotBlank(message = "이미지 이름(imageName)은 필수입니다.")
    private String imageName;

    @Min(value = 0, message = "이미지 순서(ord)는 0 이상이어야 합니다.")
    private Integer ord;
}