package ar.com.portlander.sendmeal.persistance;

import java.util.List;

import ar.com.portlander.sendmeal.model.Pedido;

interface OnPedidoResultCallback {
    void onResult(List<Pedido> plato);
}