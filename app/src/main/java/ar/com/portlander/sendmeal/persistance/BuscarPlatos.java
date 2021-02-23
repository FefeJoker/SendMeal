package ar.com.portlander.sendmeal.persistance;

import android.os.AsyncTask;

import java.util.List;

import ar.com.portlander.sendmeal.dao.PlatoDao;
import ar.com.portlander.sendmeal.model.Plato;

public class BuscarPlatos extends AsyncTask<String, Void, List<Plato>> {

    private PlatoDao dao;
    private AppRepository.OnResultCallback callback;

    public BuscarPlatos(PlatoDao dao, AppRepository.OnResultCallback context) {
        this.dao = dao;
        this.callback = context;
    }

    @Override
    protected List<Plato> doInBackground(String... strings) {
        List<Plato> platos = dao.buscarTodos();
        return platos;
    }

    @Override
    protected void onPostExecute(List<Plato> platos) {
        super.onPostExecute(platos);
        callback.onResult(platos);
    }
}