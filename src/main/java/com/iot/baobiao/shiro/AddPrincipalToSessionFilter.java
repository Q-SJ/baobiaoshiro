package com.iot.baobiao.shiro;

import com.iot.baobiao.dao.UserDaoInterface;
import com.iot.baobiao.jooq.tables.pojos.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by jia on 16-12-5.
 */

//为了使用RemenberMe Cookie，RememberMe Cookie里面是加密的Principle，
// 所以需要从这个Principle找出对应的user_id和phonenum放到Session里面
public class AddPrincipalToSessionFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDaoInterface userDaoInterface;

    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered()) {
            String phonenum = subject.getPrincipal().toString();
            User user = userDaoInterface.fetchOneByPhonenum(phonenum);
            if (user == null) return;
            Session session = subject.getSession(false);
            session.setAttribute("phonenum", phonenum);
            session.setAttribute("user_id", user.getId());
            logger.debug("Add principal info to session: " + phonenum);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
