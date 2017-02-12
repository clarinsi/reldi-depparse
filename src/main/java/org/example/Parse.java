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
import is2.parser.Parser;
import is2.util.OptionsSuper;
import is2.parser.Options;
import is2.parser.Pipe;
import is2.parser.Decoder;
import is2.parser.Parser;
import java.util.Scanner;
import java.io.File;

public class Parse extends HttpServlet
{
    private  Parser p = null;

    private OptionsSuper options = null;

    public Parse() {
        File resourcesDirectory = new File("src/main/resources/set.hr.conll.MODEL");
        String[] args = new String[]{
                "-model" ,resourcesDirectory.getAbsolutePath()
        };
        this.options = new Options(args);
        this.p = new Parser(options);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        long start = System.currentTimeMillis();

        String input = java.net.URLDecoder.decode(request.getParameter("text"), "UTF-8");

        try {
            StringBuilder str = p.out(options, p.pipe, p.params, false, options.label, input);
            Decoder.executerService.shutdown();
            Pipe.executerService.shutdown();

            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(str.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] arg) throws Exception{

    }
}

