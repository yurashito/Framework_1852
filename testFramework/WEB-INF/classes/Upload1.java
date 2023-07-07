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
public class Upload1{
    FileUpload Fichier;
    public FileUpload getFichier(){
        return this.Fichier;
    }
    public void setFichier(FileUpload f){
        this.Fichier= f;
    }

    
    
    @Urls(value="/upload1.do")
    public ModelView Upload_fichier(){

        Fichier.uploadFichier( "E:\\sprint12\\upload\\");
        ModelView view = new ModelView();
        view.setData(new HashMap<String , Object>());
        view.setView("index.jsp");
        return view;
    }

    @Urls(value="/setiii.do")
    public void setId(int id){
        // this.Id=Integer.parseInt(id);
        // this.Id=id;
    }
}