package com.example.kubernetesWildfly.application;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@WebServlet({ "/livenessProbe" })
public class LivenessProbe extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 8532332299687592651L;

    private static final Date demmarrage = new Date();

    private static final long attente = 5 * 60 * 1000;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final PrintWriter writer = response.getWriter();

        Date maintenant = new Date();
        if (demmarrage.getTime() + attente > maintenant.getTime()){
            writer.println("OK");
        }else{
            response.sendError(500);
        }
        writer.println("OK");
        writer.flush();
    }

}
