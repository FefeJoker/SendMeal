package ar.com.portlander.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ar.com.portlander.sendmeal.model.Plato;
import ar.com.portlander.sendmeal.persistance.AppRepository;

public class Alta_plato extends AppCompatActivity implements AppRepository.OnResultCallback{
    private String titulo, descripcion;
    private Double precio;
    private Integer calorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_plato);
    }
    public void crearPlato(View view){

        EditText ettitulo, etdescripcion, etprecio, etcalorias;
        ettitulo = (EditText) findViewById(R.id.titulo);
        etdescripcion = (EditText) findViewById(R.id.descripcion);
        etprecio = (EditText) findViewById(R.id.precio);
        etcalorias = (EditText) findViewById(R.id.calorias);
        String msg = "";

        if(ettitulo.getText().toString().isEmpty()) msg += "Completar título.\n";
        if(etdescripcion.getText().toString().isEmpty()) msg += "Completar descripción.\n";
        if(etprecio.getText().toString().isEmpty()) msg += "Completar precio.\n";
        if(etcalorias.getText().toString().isEmpty()) msg += "Completar calorías.\n";

        if(msg.isEmpty()){
            titulo = ettitulo.getText().toString();
            descripcion = etdescripcion.getText().toString();
            precio = Double.parseDouble(etprecio.getText().toString());
            calorias = Integer.parseInt(etcalorias.getText().toString());

            Plato plato = new Plato(titulo,descripcion,precio,calorias);
            AppRepository ar = new AppRepository(this.getApplication(),this);
            ar.insertar(plato);
            //Toast.makeText(this, "Plato guardado correctamente", Toast.LENGTH_SHORT).show();

        }else {

            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onResult(List result) {
        Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
    }
}