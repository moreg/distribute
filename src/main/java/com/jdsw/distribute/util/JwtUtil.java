package com.jdsw.distribute.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.security.KeyRep.Type.SECRET;
import static javax.crypto.Cipher.SECRET_KEY;
import static org.apache.poi.ss.formula.functions.NumericFunction.EXP;

public class JwtUtil {
    /**
     * 过期时间为一天
     * TODO 正式上线更换为15分钟
     */
    private static final long validity = 24*60*60*1000;

    /**
     * 设置Token签名密钥
     */
    private static final String SECRET_KEY = "156as4d16q51w654d1sa654";

    /**
     * 签发地:该JWT的签发者
     */
    private static final String issuer = "jdsw";


    /**
     * 生成签名,15分钟后过期
     * @param
     * @param
     * @return
     */
    public static String sign(String userName,String userId,String name,String pwd){
        //签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签发时间，直接获取系统毫秒数
        long nowTime = System.currentTimeMillis();
        Date nowDate = new Date(nowTime);
        //通过密钥签名JWT
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        String string = signatureAlgorithm.getJcaName();
        Key signingkey = new SecretKeySpec(apiKeySecretBytes, string);
        //设置JWT声明
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(userId)
                .claim("userName", userName)
                .claim("pwd", pwd)
                .claim("name", name)
                .setIssuedAt(nowDate)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingkey);
        //过期时间
        long expMillis = nowTime + validity;
        Date exp = new Date(expMillis);
        jwtBuilder.setExpiration(exp);
        //构建JWT并将其序列化为一个紧凑的url安全字符串
        return jwtBuilder.compact();

    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static boolean verity(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }

    }
    /**
     * Token解析方法
     */
    public static Claims parseJWT(String jwt) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).parseClaimsJws(jwt).getBody();
    }

    public static void main(String[] args) {
        String token = JwtUtil.sign("cz","2","陈真","12346");
       System.out.println(token);
        //解析Token
        Claims claims = JwtUtil.parseJWT(token);
        System.err.println(claims.get("userName"));
        System.out.println(JwtUtil.verity(token));

    }

}
