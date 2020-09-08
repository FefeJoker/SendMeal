package ar.com.portlander.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Activar y desactivar CVV dependiendo del numero de la tarjeta
        EditText numTarjeta = (EditText)findViewById(R.id.numTarjeta);
        numTarjeta.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                EditText ccv = (EditText)findViewById(R.id.ccv);
                if(!hasFocus && ((EditText)view).getText().length() == 22){
                    ccv.setEnabled(true);
                }
                else{
                    ccv.setText(null);
                    ccv.setEnabled(false);
                }
            }
        });

        //Insertar 10 anios de vencimiento.
        Spinner anios = (Spinner)findViewById(R.id.anios);
        Integer anioActual = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<Integer> listaAnios = new ArrayList<Integer>();
        for(int i = anioActual ; i < anioActual+10 ; i++){
            listaAnios.add(i);
        }
        anios.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, listaAnios));

        //Insertar meses
        Spinner meses = (Spinner)findViewById(R.id.meses);
        int[] temp = getResources().getIntArray(R.array.meses);
        ArrayList<Integer> listaMeses = new ArrayList<Integer>();
        for(int i = 0 ; i < temp.length ; i++){
            listaMeses.add(temp[i]);
        }
        meses.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, listaMeses));

    }

    public void enableSeekBar(View view){
        View grupoCargaInicial = findViewById(R.id.grupoCargaInicial);
        if(((Switch)view).isChecked()){
            grupoCargaInicial.setVisibility(View.VISIBLE);
        }
        else{
            grupoCargaInicial.setVisibility(View.GONE);
        }
    }
    public void  terminosCheck(View view){
        Button botonRegistrar = (Button)findViewById(R.id.botonRegistrar);
        if(((CheckBox)view).isChecked()){
            botonRegistrar.setEnabled(true);
        }
        else{
            botonRegistrar.setEnabled(false);
        }
    }
    public void verificarRegistrar(View view){
        String message = "";
        EditText nombre = (EditText)findViewById(R.id.nombre);
        EditText password = (EditText)findViewById(R.id.password);
        EditText retry_password = (EditText)findViewById(R.id.retryPassword);
        EditText email = (EditText)findViewById(R.id.email);
        RadioGroup tiposTarjeta = (RadioGroup) findViewById(R.id.tiposTarjeta);
        EditText numTarjeta = (EditText)findViewById(R.id.numTarjeta);
        EditText ccv = (EditText)findViewById(R.id.ccv);
        Spinner meses = (Spinner)findViewById(R.id.meses);
        Spinner anios = (Spinner)findViewById(R.id.anios);
        Switch cargaInicial = (Switch)findViewById(R.id.cargaIncial);
        SeekBar seekCredito = (SeekBar) findViewById(R.id.seekCredito);

        //Verificacion de campos obligatorios vacios
        if(nombre.getText().toString().isEmpty())    message += "Nombre vacio.\n";
        if(password.getText().toString().isEmpty())    message += "Contraseña vacia.\n";
        if(retry_password.getText().toString().isEmpty())    message += "Repeticion de contraseña vacia.\n";
        if(email.getText().toString().isEmpty())    message += "Email vacio.\n";
        if(numTarjeta.getText().toString().isEmpty())    message += "Numero de tarjeta vacio.\n";
        if(ccv.getText().toString().isEmpty())    message += "CCV vacio.\n";

        //Verificacion de coincidencia entre claves
        if(!password.getText().toString().equals(retry_password.getText().toString()))   message += "Contraseñas desiguales.\n";

        //Verificacion de email valido
        if(!email.getText().toString().contains("@") || email.getText().toString().length() - email.getText().toString().indexOf('@') < 4)   message += "Email invalido.\n";

        //Verificacion de fecha de vencimiento que sea despues de los proximos 3 meses
        Integer mesActual = Calendar.getInstance().get(Calendar.MONTH);
        Integer anioActual = Calendar.getInstance().get(Calendar.YEAR);
        if((Integer)anios.getSelectedItem() * 12 + (Integer)meses.getSelectedItem() - anioActual * 12 - mesActual - 1 < 4)    message += "Fecha de vencimiento invalida.\n";

        //Verificacion de que, si se activo el switch, la seekbar tengo un valor mayor a 0
        if(cargaInicial.isChecked() && seekCredito.getProgress() == 0)  message += "Monto inicial igual a 0.\n";

        if(message.isEmpty()){
            message = "Exito al ingresar datos";
        }
        else{
            message = "Error al ingresar datos:\n" + message;
        }
        Toast.makeText(this , message, Toast.LENGTH_SHORT).show();
    }
}