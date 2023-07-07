package fonction;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;
import java.lang.reflect.*;
import etu1852.annotation.*;
import java.util.*;
import etu1852.framework.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileOutputStream;
public class Utilitaire{
    public String url(String url) throws Exception{    
        String supprime_le_baseurl=url.split("\\?")[0];
        return supprime_le_baseurl;
    }
    
    public HashMap<String , Mapping> tout_fichier( String emplacement_des_classes, File dir , HashMap<String, Mapping> resultat  , HashMap<String , Object> classSingleton) throws Exception{
        File[] liste= dir.listFiles();
        for(int i=0 ; i< liste.length ; i++){
            if(liste[i].isDirectory()){
                resultat= tout_fichier(emplacement_des_classes,liste[i] , resultat , classSingleton );
            }else{
                if(liste[i].getName().contains(".class")){
                    String classe_avec_son_package=liste[i].toString().split("\\.")[0].replace(emplacement_des_classes , "").replace("\\" , ".");
                    Class A=Class.forName(classe_avec_son_package);
                    Class Singleton= Class.forName("etu1852.annotation.Singleton");
                    // if(A.isAnnotationPresent(Singleton)){
                    //     System.out.println( "-----------"+classe_avec_son_package +"  "+null );
                    //     classSingleton.put(  classe_avec_son_package , null);
                    // }
                    Method[] emp= A.getDeclaredMethods() ;
                    Class urls= Class.forName("etu1852.annotation.Urls");
                    for(int j=0 ; j<emp.length ; j++){
                        Urls u= (Urls)emp[j].getAnnotation(urls);
                        if(emp[j].isAnnotationPresent(urls) ){
                            System.out.println( "-----------"+emp[j]  );
                            resultat.put(  u.value(), new Mapping( classe_avec_son_package, emp[j].getName()));
                        }
                    }
                }
            }
        }
        return resultat;
    } 

    public Date string_en_date(String date) throws Exception{
        String dateString = date;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = format.parse(dateString);
        return dt;
    }

    public static void uploadFichier(String emplacement ,String filename ,byte[] tableauByte) {
        try (FileOutputStream fos = new FileOutputStream(emplacement+filename)) {
            fos.write(tableauByte);
            fos.close();
            System.out.println("Fichier créé avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean verificationSingleton( String classe_avec_son_package, HashMap<String , Object> classSingleton) throws Exception{
        Class A=Class.forName(classe_avec_son_package);
        Class Singleton= Class.forName("etu1852.annotation.Singleton");
        if(A.isAnnotationPresent(Singleton)){
           return true;
        }
        return false;
        
    }

    
  
   
}