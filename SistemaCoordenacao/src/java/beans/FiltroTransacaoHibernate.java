/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.hibernate.Transaction;
import persistencia.HibernateUtil;

/**
 *
 * @author pauladbol
 */
public class FiltroTransacaoHibernate implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Transaction t = HibernateUtil.getSessionFactory().getCurrentSession().getTransaction();
        //if(t == null)
            HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        chain.doFilter(request, response);
        //if(t != null)
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    }

    @Override
    public void destroy() {
        
    }
    
    
    
}
