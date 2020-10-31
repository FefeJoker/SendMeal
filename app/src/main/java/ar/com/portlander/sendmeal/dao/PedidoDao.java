package ar.com.portlander.sendmeal.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ar.com.portlander.sendmeal.model.Pedido;

//@Dao
public interface PedidoDao {
    @Insert
    void insertar(Pedido p);

    @Delete
    void borrar(Pedido p);

    @Update
    void actualizar(Pedido p);

    @Query("SELECT * FROM pedido WHERE id = :id LIMIT 1")
    Pedido buscar(String id);

    @Query("SELECT * FROM pedido")
    List<Pedido> buscarTodos();
}
