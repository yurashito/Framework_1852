package etu1852.model;
import  etu1852.annotation.*;
public class Emp{
    int id;
    String nom;
    @Urls(value="/emp-all")
    public String findAll(){
        return "nety lty a";
    }
    @Urls(value="emp-all1")
    public String findAll1(){
        return "nety lty a";
    }

    @Urls(value="select")
    public String select(){
        return "select" ;
    }
}