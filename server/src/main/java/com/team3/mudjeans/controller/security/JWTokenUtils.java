package com.team3.mudjeans.controller.security;

import com.team3.mudjeans.exceptions.AuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Component
public class JWTokenUtils {
    public static final String JWT_ADMIN_CLAIM = "admin";

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.pass-phrase}")
    private String passphrase;

    @Value("${jwt.expiration-seconds}")
    private int expiration;

    @Value("${jwt.refresh-expiration-seconds}")
    private int refreshExpiration;

    public String encode(String id, boolean admin) {
        Key key = getKey(passphrase);

        String token = Jwts.builder()
                .claim(Claims.SUBJECT, id)
                .claim(JWT_ADMIN_CLAIM, Boolean.toString(admin))
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return token;
    }

    private Key getKey(String passPhrase) {
        byte[] hmcacKey = passPhrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmcacKey, SignatureAlgorithm.HS512.getJcaName());
    }

    public JWTokenInfo decode(String encodedToken, boolean expirationLenient) throws AuthenticationException {
        try {
            Key key = getKey(passphrase);

            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(passphrase.getBytes())
                    .build()
                    .parseClaimsJws(encodedToken);

            Claims claims = jws.getBody();

            return generateTokenInfo(claims);
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException | SignatureException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (ExpiredJwtException e) {
            if (!expirationLenient) {
                throw new AuthenticationException(e.getMessage());
            } else {
                return generateTokenInfo(e.getClaims());
            }
        }
    }

    private JWTokenInfo generateTokenInfo(Claims claims) {
        JWTokenInfo tokenInfo = new JWTokenInfo();
        tokenInfo.setEmail(claims.get(Claims.SUBJECT).toString());

        String isAdminString = claims.get(JWT_ADMIN_CLAIM).toString();
        tokenInfo.setAdmin(Boolean.parseBoolean(isAdminString));

        tokenInfo.setIssuedAt(claims.getIssuedAt());
        tokenInfo.setExpiration(claims.getExpiration());

        return tokenInfo;
    }

    public boolean isRenewable(JWTokenInfo tokenInfo) {
        if (tokenInfo.getExpiration().compareTo(new Date()) > 0) {
            return false;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(tokenInfo.getIssuedAt());
        cal.add(Calendar.SECOND, refreshExpiration);

        System.out.println("max refresh: " + cal.getTime());
        System.out.println("current date: " + new Date());

        return cal.getTime().compareTo(new Date()) > 0;
    }
}
