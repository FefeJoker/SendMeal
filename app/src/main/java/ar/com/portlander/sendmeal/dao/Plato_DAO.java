package ar.com.portlander.sendmeal.dao;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ar.com.portlander.sendmeal.model.Plato;

public class Plato_DAO {
    private static final List<Plato> platos = new ArrayList<>();

    private void iniciar(){

        if(platos.isEmpty()){
            platos.add(new Plato("Ñoquis", "eeeee", 150.0,250));
            platos.add(new Plato("Ramen", "eeeee", 100.0,50));
            platos.add(new Plato("Guiso de Mondongo", "eeeee", 250.0,2250));
            platos.add(new Plato("Ñoquis", "eeeee", 150.0,250));
            platos.add(new Plato("Ramen", "eeeee", 100.0,50));
            platos.add(new Plato("Guiso de Mon", "eeeee", 250.0,2250));
        }
    }
    public List<Plato> list(){
        iniciar();
        return this.platos;
    }

    public Plato getPlato(String titulo){
        for(Plato p : platos){
            if(p.getTitulo().equals(titulo)) return p;
        }
        return null;
    }

    public void add(String nombre,String descrip,Double precio,Integer calorias){
        platos.add(new Plato(nombre, descrip, precio, calorias));
    }
}