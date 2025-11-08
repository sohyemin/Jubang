package HelloWorld.Jubang.domain.reservation.controller;

import HelloWorld.Jubang.domain.reservation.dto.ReservationRequestDto;
import HelloWorld.Jubang.domain.reservation.dto.ReservationResponse;
import HelloWorld.Jubang.domain.reservation.service.ReservationService;
import HelloWorld.Jubang.dto.Response;
import HelloWorld.Jubang.security.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/host/list/page")
    public Response<Page<ReservationResponse>> hostPage1(
            @PageableDefault(page = 1, size = 10, direction = Sort.Direction.DESC, sort = "rsvId") Pageable pageable,
            @AuthenticationPrincipal UserDTO userDTO
    ) {
        return Response.success(reservationService.getAllReservations_ForHost(userDTO.getEmail(), pageable));
    }
}
