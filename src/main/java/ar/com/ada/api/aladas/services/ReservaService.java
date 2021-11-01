package ar.com.ada.api.aladas.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.entities.Usuario;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Reserva.EstadoReservaEnum;
import ar.com.ada.api.aladas.models.response.PagoReservaResponse;
import ar.com.ada.api.aladas.repos.ReservaRepository;
import ar.com.ada.api.aladas.sistema.com.EmailService;
import ar.com.ada.api.aladas.sistema.payments.MercadoPagoService;

@Service
public class ReservaService {
    @Autowired
    ReservaRepository repo;
    @Autowired
    VueloService vueloService;
    @Autowired
    EmailService emailService;
    @Autowired
    MercadoPagoService mercadoPagoService;

    
    public Reserva generarReservaV2(Vuelo vuelo, Usuario usuario) {

        Reserva reserva = new Reserva();

        reserva.setFechaEmision(new Date());

        // crear fecha de vencimiento en 24hs usando el metodo de calendario

        Calendar c = Calendar.getInstance();// declaro la variable c tipo Calendar
        c.setTime(reserva.getFechaEmision());// seto a c una fecha de inicio (en este caso la fecha de emision)
        c.add(Calendar.DATE, 1); // agrego a c un field y amount(cantiadad de dias a aumentar)

        reserva.setFechaVencimiento(c.getTime()); // seteo esa fecha que me dio el ultimo paso

        reserva.setEstadoReservaId(EstadoReservaEnum.CREADA);

        // relaciones Bidireccionales
        switch (usuario.getTipoUsuario()) {
        case PASAJERO:
            usuario.getPasajero().agregarReserva(reserva);
            break;

        case STAFF:
            usuario.getStaff().agregarReserva(reserva);
            break;

        }

        vuelo.agregarReserva(reserva);

        repo.save(reserva);
        emailService.SendEmail(usuario.getEmail(), "Reserva Realizada",
                "Se realizo su reserva con exito. La fecha de vencimiento es: " + reserva.getFechaVencimiento());
        return reserva;

    }

    public PagoReservaResponse generarReservaConURL(Vuelo vuelo, Usuario usuario) {
        PagoReservaResponse r = new PagoReservaResponse();

        //1 generar la reserva
        Reserva reserva = generarReservaV2(vuelo, usuario);

        //2do decirle  MP que genere una preferencia de pago.
        r = mercadoPagoService.generarPreferenciaParaReserva(reserva);

        return r;
        

    }

    public Reserva buscarPorId(Integer id) {
        return repo.findByReservaId(id);
    }

    /*
     * metodo solo para generar pasajes para pasajeros (sin staff) public Reserva
     * generarReserva(Integer vueloId, Pasajero pasajero){
     * 
     * Reserva reserva = new Reserva();
     * 
     * Vuelo vuelo = vueloService.buscarPorId(vueloId);
     * 
     * reserva.setFechaEmision(new Date());
     * 
     * //crear fecha de vencimiento en 24hs usando el metodo de calendario
     * 
     * Calendar c = Calendar.getInstance();//declaro la variable c tipo Calendar
     * c.setTime(reserva.getFechaEmision());//seto a c una fecha de inicio (en este
     * caso la fecha de emision) c.add(Calendar.DATE, 1); // agrego a c un field y
     * amount(cantiadad de dias a aumentar)
     * 
     * reserva.setFechaVencimiento(c.getTime()); // seteo esa fecha que me dio el
     * ultimo paso
     * 
     * reserva.setEstadoReservaId(EstadoReservaEnum.CREADA);
     * 
     * //relaciones Bidireccionales pasajero.agregarReserva(reserva);
     * vuelo.agregarReserva(reserva);
     * 
     * repo.save(reserva); return reserva;
     * 
     * }
     */

}
