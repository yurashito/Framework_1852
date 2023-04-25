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
import java.util.Set;
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
            throws ServletException, IOException ,Exception{
            PrintWriter out = response.getWriter();
            String url=request.getPathInfo();  
            try {
                
                Class A=Class.forName(MappingUrls.get(url).getClassName());
                Method method=A.getMethod(MappingUrls.get(url).getMethod());
                Object objet=  A.newInstance();
                String test= new String();

                for(int i=0 ; i< A.getDeclaredFields().length ; i++){
                    Object[] tableau = new Object[1];
                    tableau[0]= request.getParameter(A.getDeclaredFields()[i].getName());
                    System.out.println(A.getDeclaredFields()[i].getType());
                    Method meth= A.getMethod("set"+A.getDeclaredFields()[i].getName() ,test.getClass());
                    meth.invoke(objet , request.getParameter(A.getDeclaredFields()[i].getName()));
                    Method meth1= A.getMethod("get"+A.getDeclaredFields()[i].getName() );
                    System.out.println(meth1.invoke(objet ));
                    // System.out.println(request.getParameter(A.getDeclaredFields()[i].getName()));
                }

                System.out.println(method.invoke(objet));

                // ModelView afficher= (ModelView)method.invoke(objet);

                // Set<String> keys = afficher.getData().keySet();  
                // String[] keysArray = keys.toArray(new String[keys.size()]);
                // for(int i=0 ; i<keysArray.length ; i++){
                //         request.setAttribute(keysArray[i] ,  afficher.getData().get(keysArray[i]));
                // }

                //  response.sendRedirect("index.jsp");
                // RequestDispatcher dispat = request.getRequestDispatcher(afficher.getView());
                // dispat.forward(request, response);

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