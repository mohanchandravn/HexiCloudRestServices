package com.hexicloud.portaldb.security;

import com.hexicloud.portaldb.exceptions.UserNotFoundException;
import com.hexicloud.portaldb.util.encryption.EncryptionUtil;

import org.apache.log4j.Logger;

import org.springframework.security.crypto.password.PasswordEncoder;

public class HexiPasswordEncoder implements PasswordEncoder {
private static final Logger logger = Logger.getLogger(HexiPasswordEncoder.class);
    @Override
    public String encode(CharSequence rawPassword) {
        String hashed;
        try {
            hashed = EncryptionUtil.encryptString(rawPassword.toString());
        } catch (Exception e) {
            logger.error("Password encryption failed : " + e );
            throw new UserNotFoundException(e.getMessage());
        }

        //        String hashed = BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(12));
        return hashed;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            return EncryptionUtil.encryptString(rawPassword.toString()).equals(encodedPassword);
        } catch (Exception e) {
            logger.error("Password encryption failed : " + e );
            throw new UserNotFoundException(e.getMessage());
        }
        //        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
    }
}
