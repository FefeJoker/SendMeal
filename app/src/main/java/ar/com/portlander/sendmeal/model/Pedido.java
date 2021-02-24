package ar.com.portlander.sendmeal.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

@Entity
public class Pedido {
    @PrimaryKey(autoGenerate = true)
    Long id;
    private String mail, calle;
    private int nro;
    private boolean envio; //true si es envio, false si es TakeAway
    private LatLng ubicacion;
    private List<Plato> platos_pedido;

    public Pedido(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public boolean isEnvio() {
        return envio;
    }

    public void setEnvio(boolean envio) {
        this.envio = envio;
    }

    public List<Plato> getPlatos_pedido() {
        return platos_pedido;
    }

    public void setPlatos_pedido(List<Plato> platos_pedido) {
        this.platos_pedido = platos_pedido;
    }


    public LatLng getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(LatLng ubicacion) {
        this.ubicacion = ubicacion;
    }

}
