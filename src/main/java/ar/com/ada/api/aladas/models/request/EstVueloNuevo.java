package ar.com.ada.api.aladas.models.request;

import ar.com.ada.api.aladas.entities.Vuelo.EstadoVueloEnum;

public class EstVueloNuevo {

    public EstadoVueloEnum estado;

    

    /* controller (Est Vuelonuevo) service.actualizarEstado (id, estVueloNuevo) 
    service {(int id, estVueloNuevo estVueloNuevo)} vuelo vuelo = this.buscar(id)
    
    vuelo.setTipoEstadovuelo --> esto es int por lo que deberia ver como lo paso a enum? */
}
