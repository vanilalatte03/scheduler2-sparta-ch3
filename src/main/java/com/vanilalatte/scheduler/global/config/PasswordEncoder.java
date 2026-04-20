package com.vanilalatte.scheduler.global.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * BCrypt 기반 비밀번호 암호화와 검증을 담당하는 컴포넌트입니다.
 */
@Component
public class PasswordEncoder {

    /**
     * 평문 비밀번호를 BCrypt 해시로 암호화합니다.
     *
     * @param rawPassword 평문 비밀번호
     * @return 암호화된 비밀번호
     */
    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    /**
     * 평문 비밀번호와 암호화된 비밀번호가 일치하는지 검증합니다.
     *
     * @param rawPassword 평문 비밀번호
     * @param encodedPassword 암호화된 비밀번호
     * @return 비밀번호 일치 여부
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}
