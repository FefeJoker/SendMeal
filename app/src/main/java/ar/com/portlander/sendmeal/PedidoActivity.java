package ar.com.portlander.sendmeal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ar.com.portlander.sendmeal.adapters.NuevoPedidoRecyclerAdapter;
import ar.com.portlander.sendmeal.adapters.PlatosRecyclerAdapterForNuevoPedido;
import ar.com.portlander.sendmeal.dao.Plato_DAO;
import ar.com.portlander.sendmeal.model.Plato;

public class PedidoActivity extends AppCompatActivity {
    private List<Plato> nuevo_pedido = new ArrayList<Plato>();
    private Plato_DAO daoPlato = new Plato_DAO();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new NuevoPedidoRecyclerAdapter(nuevo_pedido, this);
        recyclerView.setAdapter(mAdapter);
    }

    public void agregarPlatos(View view) {
        Intent intent_nuevo_pedido = new Intent(this, Lista_platos.class);
        startActivityForResult(intent_nuevo_pedido, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Plato plato = daoPlato.getPlato(data.getStringExtra("NOMBRE"));

            nuevo_pedido.add(plato);
            mAdapter.notifyDataSetChanged();

            TextView auxTextView = findViewById(R.id.precio_total);
            String precioNuevo = Double.toString(Float.valueOf(auxTextView.getText().toString()) + plato.getPrecio());
            auxTextView.setText(precioNuevo);
        }
    }
}
