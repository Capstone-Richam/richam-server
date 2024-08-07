package com.Nunbody.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 문제가 발생했습니다."),
    NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 로그인 정보는 존재하지 않습니다."),
    UNAUTHORIZED(HttpStatus.BAD_REQUEST, "권한이 없습니다."),
    // Success
    SUCCESS(HttpStatus.BAD_REQUEST, "요청에 성공하였습니다."),

    //auth
    IMAP_ERROR(HttpStatus.CONFLICT,  " IMAP 설정을 해주세요"),
    EMAIL_EXISTS_ERROR(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    ACCOUNT_EXISTS_ERROR(HttpStatus.PRECONDITION_FAILED, "이미 존재하는 아이디입니다."),
    NAME_EXISTS_ERROR(HttpStatus.PAYLOAD_TOO_LARGE, "이미 존재하는 닉네임입니다."),
    NAME_ERROR(HttpStatus.PAYLOAD_TOO_LARGE, "잘못"),
    INVALID_EMAIL_ERROR(HttpStatus.GONE, "존재하지 않는 이메일 정보입니다."),
    INVALID_PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "비밀번호를 확인해주세요. 카카오 계정이라면 카카오 로그인으로 시도해주세요."),
    INVALID_ACCESS_TOKEN_ERROR(HttpStatus.BAD_REQUEST,  "AccessToken 정보를 찾을 수 없습니다."),
    INVALID_REFRESH_TOKEN_ERROR(HttpStatus.BAD_REQUEST, "RefreshToken 정보를 찾을 수 없습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다. 재발급 받아주세요."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다. 재발급 받아주세요."),
    INVALID_REFRESH_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 값이 올바르지 않습니다."),
    INVALID_ACCESS_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "액세스 토큰의 값이 올바르지 않습니다."),
    NOT_MATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "일치하지 않는 리프레시 토큰입니다."),

    //member
    MEMBER_NOT_EXISTS_ERROR(HttpStatus.BAD_REQUEST,  "존재하지 않는 사용자입니다."),
    //namecard
    KEYWORD_NOT_EXISTS_ERROR(HttpStatus.BAD_REQUEST, "존재하지 않는 키워드입니다."),
    /**
     * 403 Forbidden
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, "리소스 접근 권한이 없습니다."),
    /**
     * 404 Not Found
     */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "엔티티를 찾을 수 없습니다."),
    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP method 요청입니다."),
    /**
     * 409 Conflict
     */
    CONFLICT(HttpStatus.CONFLICT, "이미 존재하는 리소스입니다.");

    private final HttpStatus httpStatus;
    private final String message;
    public Boolean getIsSuccess() {
        return httpStatus.is2xxSuccessful(); // HTTP 상태 코드가 2xx인지 확인
    }

    public int getCode() {
        return httpStatus.value(); // HTTP 상태 코드 값 반환
    }

    public String getMessage() {
        return message; // 메시지 반환
    }
}
