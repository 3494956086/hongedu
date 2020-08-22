package com.hong.commonutils.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/*

iss：发行人
exp：到期时间
sub：主题
aud：用户
nbf：在此之前不可用
iat：发布时间
jti：JWT ID用于标识该JWT
header:
    String JWT_TYPE = "JWT";
    String TYPE = "typ";
    String CONTENT_TYPE = "cty";
    String COMPRESSION_ALGORITHM = "zip";
    @Deprecated
    String DEPRECATED_COMPRESSION_ALGORITHM = "calg";

parsePlaintextJwt 载荷为文本（不是Json），未签名
parseClaimsJwt 载荷为claims（即Json），未签名
parsePlaintextJws 载荷为文本（不是Json），已签名
parseClaimsJws 载荷为claims（即Json），已签名


*/

public class JwtUtils {
public static final long EXPIRE = 1000*60*60*24;  //24小时
    public static final String SALT = "hong";  //加盐
    public static String getJwtToken(String id,String nickname){
        String jwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")  //类型
                .setHeaderParam("alg", "HS256")  //加密
                .setSubject("hongedu-user")  //发布主题
                .setIssuedAt(new Date())  //发布时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))  //到期时间
                .claim("id", id)
                .claim("nickname", nickname)
                .signWith(SignatureAlgorithm.HS256, SALT)
                .compact();
        return jwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param token
     * @return
     */
    public static boolean checkToken(String token){
        if(StringUtils.isEmpty(token))return false;
        try{
             Jwts.parser().setSigningKey(SALT).parseClaimsJws(token);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request){

        try{
            String token = request.getHeader("token");
            if(StringUtils.isEmpty(token))return false;
            Jwts.parser().setSigningKey(SALT).parseClaimsJws(token);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取id
     * @param request
     * @return
     */
    public static String getMemberIdByToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token))return "";
        Jws<Claims> jws = Jwts.parser().setSigningKey(SALT).parseClaimsJws(token);
        Claims claims = jws.getBody();
        return (String) claims.get("id");
    }


}
