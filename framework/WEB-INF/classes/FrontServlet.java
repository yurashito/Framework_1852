package etu1852.framework.servlet;
import  etu1852.framework.*;
import com.google.gson.Gson;
import fonction.*;
import java.io.*;
import javax.servlet.http.HttpSession;
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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@MultipartConfig(maxFileSize=20000000)
public class FrontServlet extends HttpServlet{
    HashMap<String,Mapping> MappingUrls;
    HashMap<String , Object> ClassSingleton;
    

    public void init( ServletConfig config) throws ServletException {
        super.init(config);
        String sessionMaxInactiveInterval = config.getInitParameter("isConnected");
        System.out.println(sessionMaxInactiveInterval+"0000+++++++-------------------");
       



        ServletContext context = getServletContext();
        String chemin_de_l_application = context.getRealPath("/");
        File file= new File(chemin_de_l_application+"WEB-INF\\classes\\");
        Utilitaire function = new Utilitaire();
        ClassSingleton= new HashMap<String , Object>();
        try{
            MappingUrls= function.tout_fichier(chemin_de_l_application+"WEB-INF\\classes\\" , file , new HashMap<String,Mapping>()  , ClassSingleton);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException ,Exception{
            PrintWriter out = response.getWriter();
            HttpSession session= request.getSession();   
            String url=request.getRequestURI().replace(request.getContextPath() , "");
             Gson gson = new Gson();
            Utilitaire function = new Utilitaire();    

            try {
                
                Class A = Class.forName(MappingUrls.get(url).getClassName());
                Class restAPI= Class.forName("etu1852.annotation.RestAPI");

                // rechercher toutes les methodes dans la class
                Method[] methods = A.getDeclaredMethods();  
                for (int j=0 ; j<methods.length ; j++) {
                    if(methods[j].getName().equals(MappingUrls.get(url).getMethod())  ){
                        Method method= methods[j];
                        Object objet= null;
                        if(function.verificationSingleton(A.getName() , ClassSingleton)){
                            if( ClassSingleton!=null ){
                                if( ClassSingleton.containsKey(A.getName())== false){
                                    objet=A.newInstance();
                                    ClassSingleton.put(A.getName() ,objet);
                                }else{
                                    objet=ClassSingleton.get(A.getName()) ;  
                                    function.mettre_par_defaut_les_attributs(A.getDeclaredFields() , objet);                                  
                                }
                            }else{
                                objet =  A.newInstance();
                                ClassSingleton= new HashMap<String , Object>();
                                ClassSingleton.put(A.getName() ,objet);
                            }
                        }else{
                            objet =  A.newInstance();
                        }
                    



                        if( methods[j].getParameterCount()==0){
                            for(int i=0 ; i< A.getDeclaredFields().length ; i++){
                                Class type_attribut=A.getDeclaredFields()[i].getType();
                                Method meth;
                                String nom_attribut=A.getDeclaredFields()[i].getName();
                //-----------------------traitement pour l'upload----------------------------
                                if( type_attribut.getSimpleName().equals("FileUpload")){
                                    Part filepart= request.getPart("Fichier");
                                    if(filepart!= null){
                                        String filename= filepart.getSubmittedFileName();
                                        byte[] filebyte= new byte[(int) filepart.getSize()];
                                        filepart.getInputStream().read(filebyte);
                                        FileUpload file_uplaod= new FileUpload(filename , filebyte);
                                        meth= A.getMethod("set"+nom_attribut ,type_attribut);
                                        meth.invoke(objet , file_uplaod);
                                    }
                                }
                //--------------------------------------------------------------------------------
                                if(request.getParameter(nom_attribut)!=null){
                                    function.donne_un_valeur_a_un_attribut(  nom_attribut ,  type_attribut ,  objet ,  request.getParameter( nom_attribut)  , session.getAttribute(getServletConfig().getInitParameter("isConnected")) ,  session.getAttribute(getServletConfig().getInitParameter("isConnected")) );
                                }
                                Method meth1= A.getMethod("get"+nom_attribut );
                            }
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

                        if(method.isAnnotationPresent(restAPI)){
                            String json= gson.toJson(method.invoke(objet));
                            out.println(json);
                        } else{ 
                        ModelView afficher= null;
                        Class classAuth= Class.forName("etu1852.annotation.Auth");
                        if(verificationExistence(method) &&  method.getAnnotation(classAuth)==session.getAttribute(getServletConfig().getInitParameter("isConnected") )){
                            afficher= (ModelView)method.invoke(objet);
                        }
                        if( verificationExistence(method)== false){
                            afficher= (ModelView)method.invoke(objet);
                        }

                        if(afficher.getIsJson()){
                            out.println("transformation 000000en json");
                           
                            String json = gson.toJson(afficher.getData());
                            out.println(json);
                            request.setAttribute("json" ,  json);

                        } else{

                            Set<String> keys = afficher.getData().keySet();  
                            String[] keysArray = keys.toArray(new String[keys.size()]);
                            for(int i=0 ; i<keysArray.length ; i++){
                                request.setAttribute(keysArray[i] ,  afficher.getData().get(keysArray[i]));
                            }
                        }
                        if(  afficher.getSession()!=null){

                            Set<String> cleSession = afficher.getSession().keySet();
                            String[] tableau_cle_session = cleSession.toArray(new String[cleSession.size()]); 
                            for(int i=0 ; i<tableau_cle_session.length ; i++){
                                session.setAttribute(tableau_cle_session[i] ,  afficher.getSession().get(tableau_cle_session[i]));
                            }
                        
                        }
                        RequestDispatcher dispat = request.getRequestDispatcher(afficher.getView());
                        dispat.forward(request, response);
                    }

                    }

                }
                
                
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

    public boolean verificationExistence(Method meth )throws Exception{
        Class classAuth= Class.forName("etu1852.annotation.Auth");
        if(meth.isAnnotationPresent(classAuth)){
            return true ;
        }
        return false;
    }

}