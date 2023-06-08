package etu1852.framework;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileOutputStream;

public class FileUpload{
    String NomFichier;
    byte[] Fichier= new byte[12];

    
    public String getNomFichier(){
        return this.NomFichier;
    }

    public  void setNomFichier( String nomFichier){
        this.NomFichier= nomFichier;
    }
    
    public byte[] getFichier(){
        return this.Fichier;
    }

    public  void setFichier( byte[] Fichier){
        this.Fichier= Fichier;
    }

    public FileUpload(String nomFichier , byte[] fichier){
        this.NomFichier= nomFichier;
        this.Fichier= fichier;
    }
    
    public  void uploadFichier(String emplacement ) { 
        try (FileOutputStream fos = new FileOutputStream(emplacement+this.NomFichier)) {
            fos.write(this.Fichier);
            fos.close();
            System.out.println("Fichier créé avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}