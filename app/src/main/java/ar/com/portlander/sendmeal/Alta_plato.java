package ar.com.portlander.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import ar.com.portlander.sendmeal.model.Plato;

public class Alta_plato extends AppCompatActivity {
    private String titulo, descripcion;
    private Double precio;
    private Integer calorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_plato);
    }
    public void crearPlato(View view){
        titulo = (String) findViewById(R.id.titulo);
        descripcion = (String) findViewById(R.id.descripcion);
        precio = (Double) findViewById(R.id.precio);
        calorias = (Integer) findViewById(R.id.calorias);

        Plato plato = new Plato(titulo,descripcion,precio,calorias);
    }
}