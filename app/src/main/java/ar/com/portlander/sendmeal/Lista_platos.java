package ar.com.portlander.sendmeal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.com.portlander.sendmeal.adapters.PlatosRecyclerAdapter;
import ar.com.portlander.sendmeal.adapters.PlatosRecyclerAdapterForNuevoPedido;
import ar.com.portlander.sendmeal.dao.Plato_DAO;

public class Lista_platos extends AppCompatActivity {
    Plato_DAO daoplatos;

    private Toolbar tb_lista_plato;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_platos);

        tb_lista_plato = (Toolbar) findViewById(R.id.tb_lista_plato);
        setSupportActionBar(tb_lista_plato);

        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setTitle(null);
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        daoplatos = new Plato_DAO();
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        if(getCallingActivity() != null){
            mAdapter = new PlatosRecyclerAdapterForNuevoPedido(daoplatos.list(), this);
            recyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter = new PlatosRecyclerAdapter(daoplatos.list(),this);
            recyclerView.setAdapter(mAdapter);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_nuevo_pedido:
                Intent intent_nuevo_pedido = new Intent(this, PedidoActivity.class);
                startActivity(intent_nuevo_pedido);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_platos,menu);

        return true;

    }
}