package org.morib.server.global.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum SuccessMessage {
    /**
     * 200 Ok
     */
    SUCCESS(HttpStatus.OK, "요청이 성공했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
