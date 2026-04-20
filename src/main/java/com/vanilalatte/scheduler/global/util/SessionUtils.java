package com.vanilalatte.scheduler.global.util;

import com.vanilalatte.scheduler.global.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {

    public static Long getLoginUserId(HttpSession session){
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        return loginUserId;
    }
}
