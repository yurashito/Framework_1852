package etu1852.framework.servlet;
import  etu1852.framework.*;
import fonction.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.lang.reflect.*;
public class FrontServlet extends HttpServlet{
    HashMap<String,Mapping> MappingUrls;
    

    public void init() throws ServletException {
        ServletContext context = getServletContext();
        String chemin_de_l_application = context.getRealPath("/");
        File file= new File(chemin_de_l_application+"WEB-INF\\classes\\");
        Utilitaire function = new Utilitaire();
        try{
            MappingUrls= function.tout_fichier(chemin_de_l_application+"WEB-INF\\classes\\" , file , new HashMap<String,Mapping>());
        }catch(Exception e){
            System.out.println(e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            PrintWriter out = response.getWriter();
            String url=request.getPathInfo();  
            try {
                out.println("resultat : "+MappingUrls.get(url).getClassName());
                out.println("resultat : "+MappingUrls.get(url).getMethod());
                out.println("url : "+url);
                Class A=Class.forName(MappingUrls.get(url).getClassName());
                Method method=A.getMethod(MappingUrls.get(url).getMethod());
                Object objet=  A.newInstance();
                ModelView afficher= (ModelView)method.invoke(objet);
                out.println(afficher.getView());
                //  response.sendRedirect("index.jsp");
                RequestDispatcher dispat = request.getRequestDispatcher(afficher.getView());
                dispat.forward(request, response);

            }catch (Exception e){
                out.println("ce cle n'existe pas , veillez verifie");
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