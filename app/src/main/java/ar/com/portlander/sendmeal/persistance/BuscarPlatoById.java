package ar.com.portlander.sendmeal.persistance;

import android.os.AsyncTask;

import java.util.List;

import ar.com.portlander.sendmeal.dao.PlatoDao;
import ar.com.portlander.sendmeal.model.Plato;

public class BuscarPlatoById extends AsyncTask<Long, Void, Plato> {

    private PlatoDao dao;
    private OnPlatoResultCallback callback;

    public BuscarPlatoById(PlatoDao dao, OnPlatoResultCallback context) {
        this.dao = dao;
        this.callback = context;
    }

    @Override
    protected Plato doInBackground(Long... id) {
        Plato plato = dao.buscar(id[0]);
        return plato;
    }

    @Override
    protected void onPostExecute(Plato plato) {
        super.onPostExecute(plato);
        callback.onResult(plato);
    }
}
