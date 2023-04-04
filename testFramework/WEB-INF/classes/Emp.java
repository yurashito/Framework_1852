package etu1852.model;
import  etu1852.annotation.*;
import etu1852.framework.*;
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
    public ModelView findAll1(){
        ModelView view = new ModelView();
        view.setView("../test.jsp");
        return view;
    }

    @Urls(value="select")
    public String select(){
        return "select" ;
    }
}