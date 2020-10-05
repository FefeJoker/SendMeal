package ar.com.portlander.sendmeal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.com.portlander.sendmeal.adapters.PlatosRecyclerAdapter;
import ar.com.portlander.sendmeal.dao.Plato_DAO;

public class Lista_platos extends AppCompatActivity {
    Plato_DAO daoplatos;

    private RecyclerView recyclerView;
    private PlatosRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_platos);

        daoplatos = new Plato_DAO();
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new PlatosRecyclerAdapter(daoplatos.list(),this);
        recyclerView.setAdapter(mAdapter);
    }
}