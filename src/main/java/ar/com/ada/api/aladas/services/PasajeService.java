package ar.com.ada.api.aladas.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Pasaje;
import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.entities.Usuario;
import ar.com.ada.api.aladas.entities.Reserva.EstadoReservaEnum;
import ar.com.ada.api.aladas.entities.Vuelo.EstadoVueloEnum;
import ar.com.ada.api.aladas.sistema.com.EmailService;

@Service
public class PasajeService {

    @Autowired
    ReservaService reservaService;

    @Autowired
    VueloService vueloService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EmailService emailService;

    public Pasaje emitir(Integer reservaId) {

        Pasaje pasaje = new Pasaje();
        pasaje.setFechaEmision(new Date());

        Reserva reserva = reservaService.buscarPorId(reservaId);
        reserva.setEstadoReservaId(EstadoReservaEnum.EMITIDA);
        reserva.setPasaje(pasaje); // Relacion bi direccional: asociar pasaje
        Integer nuevaCapacidad = reserva.getVuelo().getCapacidad() - 1;
        reserva.getVuelo().setCapacidad(nuevaCapacidad);

        if (nuevaCapacidad.equals(0)) {
            reserva.getVuelo().setEstadoVueloId(EstadoVueloEnum.CERRADO);
        }

        /*
         * problema concurrencia
         * "update vuelo set capacidad = 29 where vueloid = 99 and capacidad = 30"
         * 
         * "update vuelo set capacidad = 29 where vueloid = 99 and capacidad = 30"
         */

        vueloService.crear(reserva.getVuelo());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        emailService.SendEmail(usuario.getEmail(), "Compra exitosa", "Su pasaje ha sido emitido con exito. El pasaje id es: " + pasaje.getPasajeId() + " Fecha de vuelo: " + pasaje.getReserva().getVuelo().getFecha());

        return pasaje;

    }

}
