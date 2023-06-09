package etu1852.model;
import  etu1852.annotation.*;
import etu1852.framework.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.*;
public class Emp{
    int Id=12;
    String Nom="fehizor0";

    @Urls(value="/setId.do")
    public void setId(int id){
        // this.Id=Integer.parseInt(id);
        this.Id=id;
    }
    @Urls(value="/setNom.do")
    public void setNom(String nom){
        this.Nom=nom;
    }

    @Urls(value="/getId.do")
    public int getId(){
        return this.Id;
    }

    @Urls(value="/getNom.do")
    public String getNom(){
        return this.Nom;
    }

    @Urls(value="/emp-all.do")
    public ModelView findAll(){
        ModelView view = new ModelView();
        view.setData(new HashMap<String , Object>());
        view.setView("index.jsp");
        // view.setView("../index.jsp");
        return view;
    }
    @Urls(value="/affiche.do")
    public ModelView findAll1() throws Exception{
        ModelView view = new ModelView();
        view.setData(new HashMap<String , Object>());
        view.setView("test.jsp");
        List<String> liste= new ArrayList<String>();
        liste.add(this.Id+"");
        liste.add(this.Nom+"");
        view.addItem("liste" , liste);
        return view;
    }

    @Urls(value="/select.do")
    public String select(){
        return "select "+this.Nom+" "+this.Id ;
    }

    @Urls(value="/sprint8.do")
    public String sprint8( String test){
        return test ;
    }

    @Urls(value="/affiche1.do")
    public ModelView findA() throws Exception{
        ModelView view = new ModelView();
        view.setData(new HashMap<String , Object>());
        view.setView("index.jsp");
        // List<String> liste= new ArrayList<String>();
        // liste.add(this.Id+"");
        // liste.add(this.Nom+"");
        // view.addItem("liste" , liste);
        return view;
    }
}