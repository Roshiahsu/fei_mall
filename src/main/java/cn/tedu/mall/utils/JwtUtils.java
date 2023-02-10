package cn.tedu.mall.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName JwtUtils
 * @Version 1.0
 * @Description JWT 工具包
 * @Date 2022/12/22、下午8:01
 */

public class JwtUtils {
    /**
     * jwt密鑰
     */
    private static final String SECRET_KEY="55688";
    /**
     * 到期時間，期限一天
     * 60sec * 60min * 24hour
     */
    private static final Integer EXPIRED = 60 * 60 * 24;

    private JwtUtils() {
    }

    /**
     * 封裝JWT
     * @param claims 封裝到payload的資料
     * @return 封裝完成的JWT String
     */
    public static String generate(Map<String,Object> claims){
        Date expiration = new Date(System.currentTimeMillis() + 1000 * EXPIRED );
        //生成jwt
        String jwt = Jwts.builder()
                .setHeaderParam("type","jwt")
                .setHeaderParam("alg","HS.256")
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();

        return jwt;
    }

    /**
     * 解析JWT
     * @param jwt
     * @return
     */
    public static Claims parse(String jwt){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
