package cn.tedu.mall.filter;

import cn.tedu.mall.security.LoginPrinciple;
import cn.tedu.mall.utils.ConstUtils;
import cn.tedu.mall.utils.JwtUtils;
import cn.tedu.mall.web.JsonResult;
import cn.tedu.mall.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName JwtAuthorizationFilter
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/29、上午6:12
 */
@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {


    private static final String REQUEST_HEADER_AUTHORIZATION = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //清除上下文
        SecurityContextHolder.clearContext();
        //從請求頭獲取JWT
        String jwt = request.getHeader(REQUEST_HEADER_AUTHORIZATION);
        //沒有JWT直接放行
        if(!StringUtils.hasText(jwt)){
            filterChain.doFilter(request,response);
            return;
        }

        Claims claims = null;
        try {
            //解析JWT
            claims = JwtUtils.parse(jwt);
        } catch (ExpiredJwtException e) {
            log.debug("解析JWT失敗，JWT過期:{},{}",e.getClass().getName(),e.getMessage());
            String errorMessage= "登入失敗，登錄訊息已過期，請重新登入!!";
            jwtExceptionResponse(response, ServiceCode.ERR_JWT_EXPIRED, errorMessage);
            return;
        }catch (MalformedJwtException e){
            log.debug("解析JWT失敗，JWT數據有誤:{},{}",e.getClass().getName(),e.getMessage());
            String errorMessage= "登入失敗，獲取登入訊息失敗!!";
            jwtExceptionResponse(response,ServiceCode.ERR_JWT_INVALID, errorMessage);
            return;
        }catch (SignatureException e){
            log.debug("解析JWT失敗，密鑰解析失敗:{},{}",e.getClass().getName(),e.getMessage());
            String errorMessage= "登入失敗，獲取登入訊息失敗!!";
            jwtExceptionResponse(response,ServiceCode.ERR_JWT_INVALID, errorMessage);
            return;
        }catch (Throwable e){
            log.debug("解析JWT失敗，錯誤詳情:{},{}",e.getClass().getName(),e.getMessage());
            String errorMessage= "登入失敗，獲取登入訊息失敗!!";
            jwtExceptionResponse(response,ServiceCode.ERR_JWT_INVALID, errorMessage);
            e.printStackTrace();
            return;
        }
        log.debug("解析完成 claims>>>{}",claims.toString());

        //獲取用戶資料
        String login = (String)claims.get(ConstUtils.CLAIM_KEY_USERNAME);
        //使用fastjson將資料還原
        LoginPrinciple loginPrincipleString = JSON.parseObject(login, LoginPrinciple.class);
        //設置權限訊息
        List<String> authoritiesString = loginPrincipleString.getAuthorities();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String s : authoritiesString) {
            authorities.add(new SimpleGrantedAuthority(s));
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginPrincipleString.getUsername(), loginPrincipleString, authorities);

        //將獲取訊息添加到上下文
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        
        filterChain.doFilter(request,response);
    }

    private void jwtExceptionResponse(HttpServletResponse response, Integer serviceCode,String errorMessage) throws IOException {
        JsonResult jsonResult = JsonResult.fail(serviceCode, errorMessage);
        String jsonResultString = JSON.toJSONString(jsonResult);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().println(jsonResultString);
    }
}
