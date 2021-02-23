package ar.com.portlander.sendmeal.persistance;

import android.app.Application;
import android.util.Log;

import java.util.List;

import ar.com.portlander.sendmeal.dao.PedidoDao;
import ar.com.portlander.sendmeal.dao.PlatoDao;
import ar.com.portlander.sendmeal.model.Pedido;
import ar.com.portlander.sendmeal.model.Plato;

public class AppRepository {
    private PlatoDao platoDao;
    private PedidoDao pedidoDao;
    private OnResultCallback callback;

    public AppRepository(Application application, OnResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        platoDao = db.platoDao();
        pedidoDao = db.pedidoDao();
        callback = context;
    }

    public void insertar(final Plato plato){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                platoDao.insertar(plato);
            }
        });
    }

    public void insertar(final Pedido pedido){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                pedidoDao.insertar(pedido);
            }
        });
    }

    public void borrar(final Plato plato){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                platoDao.borrar(plato);
            }
        });
    }

    public void borrar(final Pedido pedido){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                pedidoDao.borrar(pedido);
            }
        });
    }

    public void actualizar(final Plato plato){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                platoDao.actualizar(plato);
            }
        });
    }

    public void actualizar(final Pedido pedido){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                pedidoDao.actualizar(pedido);
            }
        });
    }

    public void buscarPlato(Long id) {
        new BuscarPlatoById(platoDao, callback).execute(id);
    }

    public void buscarTodosPlato() {
        new BuscarPlatos(platoDao, callback).execute();
    }

    public void buscarPedido(Long id) {
        new BuscarPedidoById(pedidoDao, callback).execute(id);
    }

    public interface OnResultCallback<T> {
        void onResult(List<T> result);
        void onResult(T result);
        void onInsert();
    }
}