package etu1852.framework;
import java.util.HashMap;
import java.lang.reflect.*;
import java.util.*;
public class ModelView{
    String View ;
    HashMap<String , Object> Data;
    HashMap<String , Object> Session;
    boolean IsJson= false;
    
    public void setIsJson(boolean IsJson){
        this.IsJson = IsJson;
    }
    
    public boolean getIsJson(){
        return this.IsJson;
    }

    public  HashMap<String , Object> getData(){
        return this.Data;
    }

    public void setData( HashMap<String , Object> data){
        this.Data= data;
    }
    
    public String getView(){
        return this.View;
    }

    public void setView(String view){
        this.View= view;
    }

    public void  setSession( HashMap<String , Object> Session){
        this.Session = Session;
    }

    public void addItem(String cle,Object objet) throws Exception{
        this.Data.put( cle , objet );
    }

    public void addSession(String cle,Object objet) throws Exception{
        this.Session.put( cle , objet );
    }
    public HashMap<String , Object> getSession(){
        return this.Session;
    }

    public static void main(String[] args){
        try{
        ModelView mv= new ModelView();
        mv.setData(new HashMap<String , Object>());
        List<String> liste= new ArrayList<String>();
        liste.add("fehizoro");
        liste.add("Stephane");
        liste.add("Noah");
        mv.addItem("liste" , liste);
        System.out.println("--");
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
}