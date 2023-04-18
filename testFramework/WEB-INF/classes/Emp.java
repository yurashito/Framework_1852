package etu1852.model;
import  etu1852.annotation.*;
import etu1852.framework.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
public class Emp{
    int id;
    String nom;
    @Urls(value="/emp-all")
    public ModelView findAll(){
        ModelView view = new ModelView();
        view.setView("../index.jsp");
        return view;
    }
    @Urls(value="/affiche")
    public ModelView findAll1() throws Exception{
        ModelView view = new ModelView();
        view.setData(new HashMap<String , Object>());
        view.setView("../test.jsp");
        List<String> liste= new ArrayList<String>();
        liste.add("fehizoro");
        liste.add("Stephane");
        liste.add("Noah");
        liste.add("Thony");
        view.addItem("liste" , liste);
        return view;
    }

    @Urls(value="select")
    public String select(){
        return "select" ;
    }
}