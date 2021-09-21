package com.safetynet.apiSafetyNet.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;


@Component
public class ResponseLogging implements HandlerInterceptor {

    private static final Logger logger = LogManager.getLogger(ResponseLogging.class);

    public ResponseLogging(){}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(response.getStatus()>= 200 && response.getStatus()< 205){
            logger.info("HTTP response :" +
                    "\nThis Request ended up in SUCCESS:" +
                    "\nMethod :"+ request.getMethod() +
                    "\nHTTP Code Status :" + response.getStatus() +
                    "\nHEADERS :" + displayHeaders(response));
        }
        else if (response.getStatus()>= 400 ){
            logger.error("HTTP response :" +
                    "\nThis Request ended up in an ERROR:" +
                    "\nHTTP Code Status :" + response.getStatus() +
                    "\nMethod :"+ request.getMethod() +
                    "\nHEADERS :" + displayHeaders(response));
        }
    }

    private String displayHeaders(HttpServletResponse response) {
        Collection headerNames = response.getHeaderNames();
        String result = "";
        for(Object headerName : headerNames){
            result +="\n\t" + headerName + " :" + response.getHeader((String) headerName);
        }
        return result;
    }
}
