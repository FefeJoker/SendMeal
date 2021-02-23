package ar.com.portlander.sendmeal.persistance;

import java.util.List;

import ar.com.portlander.sendmeal.model.Plato;

interface OnPlatoResultCallback extends AppRepository.OnResultCallback<Plato>{
    void onResult(List<Plato> platos);
    void onResult(Plato plato);
}