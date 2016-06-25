/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtro;

import beans.LoginBean;
import beans.UsuarioBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andrei Andrade
 */
@WebFilter(filterName = "FiltroLogin", urlPatterns = {"*.xhtml"})
public class FiltroLogin implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        
        boolean redirecionar = false;
        if(!req.getRequestURI().endsWith("/login.xhtml")) {
            HttpSession sessao = req.getSession(false);
            if(sessao == null) {
                redirecionar = true;
            } else {
                LoginBean bean = (LoginBean)sessao.getAttribute("loginBean");
                if(bean == null)
                    redirecionar = true;
                else if(!bean.getUsuario().isLogado())
                        redirecionar = true;
            }
        }
        if(redirecionar)
            ((HttpServletResponse)response).sendRedirect(req.getContextPath()+"/faces/login.xhtml");
        else
            chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}

