package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Pasaje;
import ar.com.ada.api.aladas.models.request.InfoPasajeNuevo;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.PasajeService;

@RestController
public class PasajeController {
    @Autowired
    PasajeService service;
    @PostMapping("api/pasajes")
    public ResponseEntity<GenericResponse> emitir(@RequestBody InfoPasajeNuevo pasajeNuevo){

        GenericResponse respuesta = new GenericResponse();
        
        Pasaje pasaje = service.emitir(pasajeNuevo.reservaId);

        respuesta.id = pasaje.getPasajeId();
        respuesta.isOk = true;
        respuesta.message = "su pasaje ha sido creado con exito";

        return ResponseEntity.ok(respuesta);
        
        
        
    }
    
}
