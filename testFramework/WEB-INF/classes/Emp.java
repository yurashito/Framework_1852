package etu1852.model;
import  etu1852.annotation.*;
import  fonction.*;
import etu1852.framework.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

@Singleton
public class Emp{
    int A;
    int B; 
    public int getA(){ return A; }
    public int getB(){ return B; }
    public void setA(int A){ this.A= A ; }
    public void setB(int B){ this.B= B ; }

    // public FileUpload getFichier(){
    //     return this.Fichier;
    // }
    // public void setFichier(FileUpload f){
    //     this.Fichier= f;
    // }

    
    
    @Urls(value="/up.do")
    public ModelView setId() throws Exception{
        ModelView view = new ModelView();
        view.setData(new HashMap<String , Object>());
        System.out.println(A+"  "+B);
        view.addItem("liste" , "a :"+A+" b: "+B);
        view.setView("index.jsp");
        return view;
    }

    @Urls(value="/login.do")
    public ModelView Login() throws Exception{
        ModelView view = new ModelView();
        view.setData(new HashMap<String , Object>());
        view.setSession(new HashMap<String , Object>());
        view.addSession("isConnected" , true);
        view.addSession("profil" , "admin");
        view.setIsJson(true); 
        view.addItem("liste" , "a :"+A+" b: "+B);
        view.setView("index.jsp");
        return view;
    }

    @RestAPI
     @Urls(value="/Liste.do")
    public String[] Liste(){
        String[] resultat= new String[3];
        resultat[0]="fehizoro";
        resultat[1]="fehizoro";
        resultat[2]="fehizoro";
        return resultat;
    }
}