package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.AeropuertoService;
import ar.com.ada.api.aladas.services.AeropuertoService.ValidacionAeropuertoDataEnum;

@RestController
public class AeropuertoController {

    @Autowired
    public AeropuertoService service;

    @PostMapping("/api/aeropuertos")
    public ResponseEntity<GenericResponse> crear(@RequestBody Aeropuerto aeropuerto) {

        GenericResponse rta = new GenericResponse();

        ValidacionAeropuertoDataEnum aeroValido = service.validar(aeropuerto);

        if (aeroValido == ValidacionAeropuertoDataEnum.OK) { 

            service.crear(aeropuerto.getAeropuertoId(), aeropuerto.getNombre(), aeropuerto.getCodigoIATA());

            rta.isOk = true;
            rta.message = "El aeropuerto ha sido creado";

            return ResponseEntity.ok(rta);

        } else {
            rta.isOk = false;
            rta.message = "ERROR (" + aeroValido.toString() + ")";

            return ResponseEntity.badRequest().body(rta);
        }
    }

}
