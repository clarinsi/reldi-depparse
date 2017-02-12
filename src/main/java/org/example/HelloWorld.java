package org.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.util.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class HelloWorld extends HttpServlet
{
    final static Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    private static final String KEY_REQUEST_ID = "requestId";
    private final AtomicInteger volatileRequestId = new AtomicInteger(1);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        logger.info("Entering application.");

        logger.info("Exiting application.");
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello Servlet</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }

    public static void main(String[] arg) throws Exception{
        logger.info("Entering application.");

        logger.info("Exiting application.");
    }
}

