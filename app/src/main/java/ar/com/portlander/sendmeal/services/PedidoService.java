package ar.com.portlander.sendmeal.services;

import java.util.List;

import ar.com.portlander.sendmeal.model.Pedido;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PedidoService {
    @GET("pedido/{id}")
    Call<Pedido> getPlato(@Path("id") String id);


    @GET("pedido/list")
    Call<List<Pedido>> getPlatoList();

    @POST("pedido")
    Call<Pedido> createPlato(@Body Pedido plato);
}
