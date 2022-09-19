package com.itheima.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Java web token 工具类
 *
 * @author qiaokun
 * @date 2018/08/10
 */
public class JwtUtil {
    /**
     * 过期时间一天，
     * TODO 正式运行时修改为 7 天
     */
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;
    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "f26e587c28064d0e855e72c0a6a0e618";

    /**
     * 校验token是否正确
     *
     * @param token 密钥
     * @return 是否正确
     */
    public static Object verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET); // 使用了HMAC256加密算法。
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            // 获取 token 中的所有信息
            DecodedJWT jwt = verifier.verify(token);
            // 返回一个对象，包含了 uid 和 usertype
            Map<String, String> map = new HashMap<>();
            map.put("uid", jwt.getClaim("uid").asString());
            map.put("usertype", jwt.getClaim("usertype").asString());
            return map;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得token中的信息
     *
     * @return token中包含的用户名
     */
    public static String getUsertype(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET); // 使用了HMAC256加密算法。
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("usertype").asString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取登陆用户ID
     *
     * @param token
     * @return
     */
    public static String getUserUid(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET); // 使用了HMAC256加密算法。
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("uid").asString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成签名
     *
     * @param uid 用户名
     * @return 加密的token
     */
    public static String sign(String uid, String usertype) {
        try {
//            过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
//            私钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
//            设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            // 附带 uid,type 信息，生成签名
            return JWT.create()
                    .withHeader(header)
                    .withClaim("uid", uid)
                    .withClaim("usertype", usertype)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}