package ar.com.ada.api.aladas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.models.request.EstVueloRequest;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.AeropuertoService;
import ar.com.ada.api.aladas.services.VueloService;
import ar.com.ada.api.aladas.services.VueloService.ValidacionVueloDataEnum;

@RestController
public class VueloController {

    @Autowired
    VueloService service;

    @Autowired
    AeropuertoService aeroService;

    /*
     * private VueloService service; private AeropuertoService aeroService;
     * 
     * public VueloController(VueloService service, AeropuertoService aeroService) {
     * this.service = service; this.aeroService = service; } // otra manera de
     * inyectar las dependencias
     */

    @PostMapping("/api/vuelos")
    public ResponseEntity<GenericResponse> postCrearVuelo(@RequestBody Vuelo vuelo) {

        GenericResponse rta = new GenericResponse();

        ValidacionVueloDataEnum resultadoValido = service.validar(vuelo);

        if (resultadoValido == ValidacionVueloDataEnum.OK) {

            Aeropuerto ao = aeroService.buscarPorId(vuelo.getAeropuertoOrigen());

            Aeropuerto ad = aeroService.buscarPorId(vuelo.getAeropuertoDestino());

            Vuelo vueloNuevo = service.crear(vuelo.getFecha(), vuelo.getCapacidad(), ao.getCodigoIATA(),
                    ad.getCodigoIATA(), vuelo.getPrecio(), vuelo.getCodigoMoneda());

            rta.isOk = true;
            rta.id = vueloNuevo.getVueloId();
            rta.message = "El vuelo ha sido creado exitosamente";

            return ResponseEntity.ok(rta);

        } else {
            rta.isOk = false;
            rta.message = "ERROR (" + resultadoValido.toString() + ")";

            return ResponseEntity.badRequest().body(rta);
        }

    }

    @PostMapping("/api/vuelos/v2")
    public ResponseEntity<GenericResponse> CrearVuelo(@RequestBody Vuelo vuelo) {

        GenericResponse rta = new GenericResponse();

        service.crear(vuelo);

        return ResponseEntity.ok(rta);

    }

    @PutMapping("api/vuelos/{id}/estados")
    public ResponseEntity<GenericResponse> putActualizarEstadoVuelo(@PathVariable Integer id,
            @RequestBody EstVueloRequest estadoVuelo) {

        GenericResponse r = new GenericResponse();

        Vuelo vuelo = service.buscarPorId(id);

        vuelo.setEstadoVueloId(estadoVuelo.estado);

        service.guardar(vuelo);

        r.isOk = true;
        r.message = "Vuelo actualizado";

        return ResponseEntity.ok(r);
    }

    @GetMapping("/api/vuelos/abiertos")
    public ResponseEntity<List<Vuelo>> getVuelosAbiertos() {
        return ResponseEntity.ok(service.traerVuelosAbiertos());
    }

}
