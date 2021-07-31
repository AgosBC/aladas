package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.AeropuertoService;
import ar.com.ada.api.aladas.services.VueloService;

@RestController
public class VueloController {

    @Autowired
    VueloService service;

    @Autowired
    AeropuertoService aeroService;
    
    /*private VueloService service;
    private AeropuertoService aeroService;

    public VueloController(VueloService service, AeropuertoService aeroService) {
        this.service = service;
        this.aeroService = service;
    } // otra manera de inyectar las dependencias*/

   

    @PostMapping("/api/vuelos")
    public ResponseEntity<GenericResponse> postCrearVueloV2(@RequestBody Vuelo vuelo){

        GenericResponse rta = new GenericResponse();

        Aeropuerto ao = aeroService.buscarPorId(vuelo.getAeropuertoOrigen());
        Aeropuerto ad = aeroService.buscarPorId(vuelo.getAeropuertoDestino());

        Vuelo vueloNuevo = service.crear(vuelo.getFecha(), vuelo.getCapacidad(), ao.getCodigoIATA(), ad.getCodigoIATA(), vuelo.getPrecio(), vuelo.getCodigoMoneda());

        rta.isOk = true;
        rta.id = vueloNuevo.getVueloId();
        rta.message = "El vuelo ha sido crado exitosamente";

        return ResponseEntity.ok(rta);


        
    }

    @PostMapping("/api/vuelos/v2")
    public ResponseEntity<GenericResponse> postCrearVuelo(@RequestBody Vuelo vuelo){

        GenericResponse rta = new GenericResponse();

        service.crear(vuelo);

        return ResponseEntity.ok(rta);


        
    }


    
}
