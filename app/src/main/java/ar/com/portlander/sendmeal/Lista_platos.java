package ar.com.portlander.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import ar.com.portlander.sendmeal.dao.Plato_DAO;
import ar.com.portlander.sendmeal.adapters.PlatosRecyclerAdapter;

public class Lista_platos extends AppCompatActivity {
    Plato_DAO daoplatos;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
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