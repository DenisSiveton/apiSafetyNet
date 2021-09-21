package com.safetynet.apiSafetyNet.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
public class RequestLogging extends CommonsRequestLoggingFilter {

    private static final Logger logger = LogManager.getLogger(RequestLogging.class);

    public RequestLogging(){
        super.setIncludeQueryString(true);
        super.setIncludePayload(true);
        super.setMaxPayloadLength(10000);
    }

    @Override
    public void beforeRequest(HttpServletRequest request, String message) {
        logger.info("HTTP request :" +
                "\nMethod :"+ request.getMethod() +
                "\nURL :" + request.getRequestURL().toString() + "?"+ request.getQueryString() +
                "\nHEADERS :" + displayHeaders(request));
    }

    private String displayHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        String result = "";
        while(headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            result +="\n\t" + headerName + " :" + request.getHeader(headerName);
        }
        return result;
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
    }
}
