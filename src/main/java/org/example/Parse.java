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
    private  Parser srParser = null;
    private  Parser hrParser = null;
    private  Parser slParser = null;

    private OptionsSuper optionsSr = null;
    private OptionsSuper optionsHr = null;
    private OptionsSuper optionsSl = null;

    public Parse() {
        File resourcesDirectorySr = new File("src/main/resources/set.sr.conll.MODEL");
        File resourcesDirectorySl = new File("src/main/resources/set.sl.conll.MODEL");
        File resourcesDirectoryHr = new File("src/main/resources/set.hr.conll.MODEL");

        String[] argsSr = new String[]{
                "-model" ,resourcesDirectorySr.getAbsolutePath()
        };
        String[] argsSl = new String[]{
                "-model" ,resourcesDirectorySl.getAbsolutePath()
        };
        String[] argsHr = new String[]{
                "-model" ,resourcesDirectoryHr.getAbsolutePath()
        };
        this.optionsSr = new Options(argsSr);
        this.optionsSl = new Options(argsSl);
        this.optionsHr = new Options(argsHr);


        this.srParser = new Parser(optionsSr);
        this.slParser = new Parser(optionsSl);
        this.hrParser = new Parser(optionsHr);


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        long start = System.currentTimeMillis();

        String lang = request.getParameter("lang");
        String input = java.net.URLDecoder.decode(request.getParameter("text"), "UTF-8");

        try {
            Parser pr = (lang.equals("Croatian")) ? hrParser : (lang.equals("Slovenian")) ? slParser : srParser;
            OptionsSuper opt = (lang.equals("Croatian")) ? optionsHr : (lang.equals("Slovenian")) ? optionsSl : optionsSr;
            StringBuilder str = pr.out(opt, pr.pipe, pr.params, false, opt.label, input);

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

