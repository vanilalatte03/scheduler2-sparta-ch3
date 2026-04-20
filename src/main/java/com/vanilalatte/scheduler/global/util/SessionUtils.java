package com.vanilalatte.scheduler.global.util;

import com.vanilalatte.scheduler.global.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {

    /**
     * 세션에서 로그인한 사용자 ID를 조회합니다.
     *
     * @param session 현재 HTTP 세션
     * @return 로그인한 사용자 ID
     * @throws UnauthorizedException 세션에 userId가 없으면 발생
     */
    public static Long getLoginUserId(HttpSession session){
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        return loginUserId;
    }
}
