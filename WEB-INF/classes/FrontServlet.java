package etu1852.framework.servlet;
import fonction.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class FrontServlet extends HttpServlet{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            PrintWriter out = response.getWriter();
            String url=request.getRequestURL().toString();
            try {
                out.println("resultat: "+new Utilitaire().url(url));
            }catch (Exception e){
                out.println(e);
            }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            processRequest(request, response);
        } catch (Exception e) {
            out.println(e);
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            processRequest(request, response);
        } catch (Exception e) {
            out.println(e);
        }
    }

}