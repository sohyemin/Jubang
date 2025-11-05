package HelloWorld.Jubang.domain.reservation.controller;

import HelloWorld.Jubang.domain.reservation.dto.ReservationRequestDto;
import HelloWorld.Jubang.domain.reservation.service.ReservationService;
import HelloWorld.Jubang.dto.Response;
import HelloWorld.Jubang.security.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/apply")
    public Response<?> applyReservation(@Valid @RequestBody ReservationRequestDto dto,
                                        @AuthenticationPrincipal UserDTO userDTO){

        reservationService.makeReservation(dto, userDTO.getEmail());
        return Response.success("등록되었습니다");
    }
}
