package HelloWorld.Jubang.domain.room.dto;

import HelloWorld.Jubang.domain.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomListResponse {
    long roomId;
    String roomName;
    String address;
    String thumbnailURL;

    public static RoomListResponse toDto(Room room) {
        return RoomListResponse.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .thumbnailURL(room.getImageList().getFirst().getImageName())
                .build();
    }
}
