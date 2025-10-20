package HelloWorld.Jubang.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
    XSS_INPUT_ERROR(HttpStatus.BAD_REQUEST, "XSS input error");

    private final HttpStatus status;
    private final String message;
}
