/**
 * 
 */
package com.preciouspaws.filter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author pavanrao
 *
 */
@Component
@WebFilter("/*")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hrq = (HttpServletRequest) request;
        HttpServletResponse hrs = (HttpServletResponse) response;
        hrs.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        hrs.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        hrs.setHeader("Access-Control-Allow-Headers", "*");
        hrs.setHeader("Access-Control-Allow-Credentials", "false");
        hrs.setHeader("Access-Control-Max-Age", "4800");
        
        System.out.println(hrq.getRequestURI()); 
        String doctorlogin= "test/api/doctor/";
        String doctorRegister = "test/api/doctor/add"; 
        String adminlogin= "test/api/vetOrg/";
        String adminRegister = "test/api/vetOrg/add"; 
        String ownerlogin= "test/api/petOwner/";
        String ownerRegister = "test/api/petOwner/add";
        String urll= hrq.getRequestURI();
        if(urll.contains(doctorlogin) || urll.contains(doctorRegister)|| urll.contains(adminlogin) || urll.contains(adminRegister) || urll.contains(ownerlogin)  || urll.contains(ownerRegister)) {
        
        chain.doFilter(request, response);
        
        }
        else {
     	   if(hrq.getHeader("authtoken") != null) {
     		   	TimeZone tz = TimeZone.getTimeZone("UTC");
     	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
     	        df.setTimeZone(tz);
     	        Calendar calendar = Calendar.getInstance();
     	        calendar.setTime(new Date());
     	        calendar.add(Calendar.MINUTE,1);
     	        Date currentdate = calendar.getTime(); 
     	        
     	        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
     	        String exp = hrq.getHeader("authtoken");
     	        Date d2;
 				try {
 					
 			   	        	chain.doFilter(request, response);
 			   	       
 				} catch (Exception e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
    	        int auth = Integer.valueOf(hrq.getHeader("auth")) ;
     	       }  
        }
    }
    
    private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = filterConfig;
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		 this.filterConfig = null;
	}

}
