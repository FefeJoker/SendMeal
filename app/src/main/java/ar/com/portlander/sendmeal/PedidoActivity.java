package ar.com.portlander.sendmeal;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ar.com.portlander.sendmeal.adapters.NuevoPedidoRecyclerAdapter;
import ar.com.portlander.sendmeal.dao.Plato_DAO;
import ar.com.portlander.sendmeal.model.Pedido;
import ar.com.portlander.sendmeal.model.Plato;
import ar.com.portlander.sendmeal.persistance.AppRepository;

public class PedidoActivity extends AppCompatActivity implements AppRepository.OnResultCallback<Pedido> {
    private List<Plato> nuevo_pedido = new ArrayList<Plato>();
    private Plato_DAO daoPlato = new Plato_DAO();

    private static final Integer REQUEST_CODE=7;


    private LatLng localization;
    private NotificationPublisher receiver;
    private IntentFilter intentFilter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        receiver = new NotificationPublisher();
        intentFilter = new IntentFilter("ar.com.portlander.sendmeal.nuevo_pedido");

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new NuevoPedidoRecyclerAdapter(nuevo_pedido, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null)    return;
        if(requestCode == 1 ){
            Plato plato = daoPlato.getPlato(data.getStringExtra("NOMBRE"));

            nuevo_pedido.add(plato);
            mAdapter.notifyDataSetChanged();

            TextView auxTextView = findViewById(R.id.precio_total);
            String precioNuevo = Double.toString(Float.valueOf(auxTextView.getText().toString()) + plato.getPrecio());
            auxTextView.setText(precioNuevo);
        }
        else if(requestCode == REQUEST_CODE){
            localization = data.getExtras().getParcelable("localization");
        }
    }

    public void agregarPlatos(View view) {
        Intent intent_nuevo_pedido = new Intent(this, Lista_platos.class);
        startActivityForResult(intent_nuevo_pedido, 1);
    }

    public void confirmarUbicacion(View view){
        Intent intent_MapActivity = new Intent(this, MapActivity.class);
        startActivityForResult(intent_MapActivity,REQUEST_CODE);
    }

    public void confirmarPedido(View view){
        TextView auxEmail = findViewById(R.id.email);
        TextView auxCalle = findViewById(R.id.calle);
        TextView auxCalleNumero = findViewById(R.id.calle_numero);
        RadioGroup auxGroup = findViewById(R.id.tipo_pedido);
        String message = "";
        if(auxEmail.getText().toString().isEmpty()) message += "No se indico ningun email.\n";
        if(auxCalle.getText().toString().isEmpty()) message += "No se indico ninguna calle.\n";
        if(auxCalleNumero.getText().toString().isEmpty())   message += "No se indico ningun numero de casa.\n";
        if(mAdapter.getItemCount() == 0)   message += "No se selecciono ningun plato.\n";
        if(auxGroup.getCheckedRadioButtonId() == -1)  message += "No se selecciono ningun tipo de pedido.\n";
        if(localization == null) message += "No se selecciono ninguna ubicacion de envio.\n";
        final PedidoActivity aux = this;
        if(message.isEmpty()){
            ScheduledExecutorService auxExecutor = Executors.newSingleThreadScheduledExecutor();
            Runnable auxRunnable = new Runnable() {
                @Override
                public void run() {
                    Intent notificationIntent = new Intent("ar.com.portlander.sendmeal.nuevo_pedido");
                    notificationIntent.putExtra("MENSAJE", "Pedido confirmado.");
                    sendBroadcast(notificationIntent);
                }
            };
            auxExecutor.schedule(auxRunnable,5,TimeUnit.SECONDS);

            Pedido pedido = new Pedido();
            pedido.setCalle(auxCalle.getText().toString());
            pedido.setEnvio(((RadioButton)findViewById(R.id.envio)).isChecked());
            pedido.setMail(auxEmail.getText().toString());
            pedido.setNro(Integer.valueOf(auxCalleNumero.getText().toString()));
            pedido.setPlatos_pedido(nuevo_pedido);
            pedido.setUbicacion(localization);


            AppRepository repository = new AppRepository(this.getApplication(), this);
            repository.insertar(pedido);
        }
        else{
            Toast.makeText(this , message, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResult(List<Pedido> result) {

    }

    @Override
    public void onResult(Pedido result) {

    }

    @Override
    public void onInsert() {
    }
}
