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
import java.lang.*;
import java.util.Enumeration;
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
            Utilitaire function = new Utilitaire();    

            try {
                
                Class A = Class.forName(MappingUrls.get(url).getClassName());  
                
                // rechercher toutes les methodes dans la class
                Method[] methods = A.getDeclaredMethods();  
                for (int j=0 ; j<methods.length ; j++) {
                    if(methods[j].getName().equals(MappingUrls.get(url).getMethod())  ){
                        Method method= methods[j];
                        Object objet =  A.newInstance();
                        if( methods[j].getParameterCount()==0){
                            for(int i=0 ; i< A.getDeclaredFields().length ; i++){
                                Class type_attribut=A.getDeclaredFields()[i].getType();
                                Method meth;
                                String nom_attribut=A.getDeclaredFields()[i].getName();
                                if(request.getParameter(nom_attribut)!=null){
                                    if(type_attribut.getSimpleName().equals("int")){ 
                                        meth= A.getMethod("set"+nom_attribut ,type_attribut); 
                                        meth.invoke(objet , Integer.parseInt(request.getParameter(nom_attribut)));
                                    }else if(type_attribut.getSimpleName().equals("String")){ 
                                        meth= A.getMethod("set"+nom_attribut ,type_attribut);
                                        meth.invoke(objet , request.getParameter(nom_attribut));
                                    }else if(type_attribut.getSimpleName().equals("double")){  
                                        meth= A.getMethod("set"+nom_attribut ,type_attribut);
                                        meth.invoke(objet , Double.parseDouble(request.getParameter(nom_attribut)));
                                    }else { 
                                        meth= A.getMethod("set"+nom_attribut ,type_attribut);
                                        meth.invoke(objet , function.string_en_date(request.getParameter( nom_attribut)));
                                    }
                                }
                                Method meth1= A.getMethod("get"+nom_attribut );
                                out.println(meth1.invoke(objet ));
                            }
                            out.println(method.invoke(objet));
                        }else{
                                                    if(request.getParameterMap().size() ==  methods[j].getParameterCount()){
                                Object[] tableau_d_argument = new Object[methods[j].getParameterCount()];
                                Enumeration<String> parameterNames = request.getParameterNames();
                                int i=0 ; 
                                // Boucler sur tous les paramètres de la requête
                                while (parameterNames.hasMoreElements()) {
                                    // Récupérer le nom du paramètre
                                    String paramName = parameterNames.nextElement();
                                    // Récupérer la valeur du paramètre
                                    String paramValue = request.getParameter(paramName);
                    
                                    Class type_argument= methods[j].getParameters()[i].getType();                                
                                    if(type_argument.getSimpleName().equals("int")){ 
                                        tableau_d_argument[i]=Integer.parseInt(paramValue);;
                                    }else if(type_argument.getSimpleName().equals("String")){ 
                                        tableau_d_argument[i]= paramValue;
                                    }else if(type_argument.getSimpleName().equals("double")){  
                                        tableau_d_argument[i]= Double.parseDouble(paramValue);
                                    }else { 
                                        tableau_d_argument[i]=function.string_en_date(request.getParameter( paramValue));
                                    }
                                    i++;
                                }                             
                                out.println(method.invoke(objet  , tableau_d_argument));
                                break;

                            }

                        }
                                
                        ModelView afficher= (ModelView)method.invoke(objet);
                        Set<String> keys = afficher.getData().keySet();  
                        String[] keysArray = keys.toArray(new String[keys.size()]);
                        for(int i=0 ; i<keysArray.length ; i++){
                                request.setAttribute(keysArray[i] ,  afficher.getData().get(keysArray[i]));
                        }
                        RequestDispatcher dispat = request.getRequestDispatcher(afficher.getView());
                        dispat.forward(request, response);


                    }

                }
                
                
             


                // ModelView afficher= (ModelView)method.invoke(objet);

                // Set<String> keys = afficher.getData().keySet();  
                // String[] keysArray = keys.toArray(new String[keys.size()]);
                // for(int i=0 ; i<keysArray.length ; i++){
                //         request.setAttribute(keysArray[i] ,  afficher.getData().get(keysArray[i]));
                // }

             
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