package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.entities.Usuario;
import ar.com.ada.api.aladas.models.request.InfoReservaNueva;
import ar.com.ada.api.aladas.models.response.ReservaResponse;
import ar.com.ada.api.aladas.services.ReservaService;
import ar.com.ada.api.aladas.services.UsuarioService;

@RestController
public class ReservaController {
    @Autowired
    ReservaService service;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/api/reservas")
    public ResponseEntity<ReservaResponse> generarReserva(@RequestBody InfoReservaNueva infoReserva){

        ReservaResponse rta = new ReservaResponse();

        // obtengo a quien esta autenticado del otro lado 

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //de lo que se esta autenticando obtengo su nombre:
        String username = authentication.getName();
        //busco el usuario por username
        Usuario usuario = usuarioService.buscarPorUsername(username);

        //con el usuario obtengo el pasajero
        Reserva reservaNueva = service.generarReserva(infoReserva.vueloId, usuario);

        rta.fechaEmision = reservaNueva.getFechaEmision();
        rta.reservaId = reservaNueva.getReservaId();
        rta.fechaVencimiento = reservaNueva.getFechaVencimiento();
        rta.message ="Reserva realizada con exito";
        

        return ResponseEntity.ok(rta);
        

        
    }
    
}
