package ar.com.portlander.sendmeal.persistance;

import java.util.List;

import ar.com.portlander.sendmeal.model.Plato;

interface OnPlatoResultCallback {
    void onResult(List<Plato> plato);
}