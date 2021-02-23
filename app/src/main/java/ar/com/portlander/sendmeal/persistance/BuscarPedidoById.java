package ar.com.portlander.sendmeal.persistance;

import android.os.AsyncTask;

import ar.com.portlander.sendmeal.dao.PedidoDao;
import ar.com.portlander.sendmeal.dao.PlatoDao;
import ar.com.portlander.sendmeal.model.Pedido;
import ar.com.portlander.sendmeal.model.Plato;

public class BuscarPedidoById extends AsyncTask<Long, Void, Pedido> {

    private PedidoDao dao;
    private AppRepository.OnResultCallback callback;

    public BuscarPedidoById(PedidoDao dao, AppRepository.OnResultCallback context) {
        this.dao = dao;
        this.callback = context;
    }

    @Override
    protected Pedido doInBackground(Long... id) {
        Pedido pedido = dao.buscar(id[0]);
        return pedido;
    }

    @Override
    protected void onPostExecute(Pedido pedido) {
        super.onPostExecute(pedido);
        callback.onResult(pedido);
    }
}
