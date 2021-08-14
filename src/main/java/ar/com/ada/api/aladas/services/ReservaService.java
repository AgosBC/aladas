package ar.com.ada.api.aladas.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.entities.Usuario;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Reserva.EstadoReservaEnum;
import ar.com.ada.api.aladas.repos.ReservaRepository;

@Service
public class ReservaService {
    @Autowired
    ReservaRepository repo;
    @Autowired
    VueloService vueloService;

    
    public Reserva generarReserva(Integer vueloId, Usuario usuario){

        Reserva reserva = new Reserva();

        Vuelo vuelo = vueloService.buscarPorId(vueloId);

        reserva.setFechaEmision(new Date());

        //crear fecha de vencimiento en 24hs usando el metodo de calendario

        Calendar c = Calendar.getInstance();//declaro la variable c tipo Calendar
        c.setTime(reserva.getFechaEmision());//seto a c una fecha de inicio (en este caso la fecha de emision)
        c.add(Calendar.DATE, 1); // agrego a c un field y amount(cantiadad de dias a aumentar)
         
        reserva.setFechaVencimiento(c.getTime()); // seteo esa fecha que me dio el ultimo paso

        reserva.setEstadoReservaId(EstadoReservaEnum.CREADA);

        

        //relaciones Bidireccionales
        switch(usuario.getTipoUsuario()){
            case PASAJERO:
            usuario.getPasajero().agregarReserva(reserva);
            break;

            case STAFF:
            usuario.getStaff().agregarReserva(reserva);
            break;


        }

        vuelo.agregarReserva(reserva);

        repo.save(reserva);
        return reserva;

    }
    
   /* public Reserva generarReserva(Integer vueloId, Staff staff){

        Reserva reserva = new Reserva();

        Vuelo vuelo = vueloService.buscarPorId(vueloId);

        reserva.setFechaEmision(new Date());

        //crear fecha de vencimiento en 24hs usando el metodo de calendario

        Calendar c = Calendar.getInstance();//declaro la variable c tipo Calendar
        c.setTime(reserva.getFechaEmision());//seto a c una fecha de inicio (en este caso la fecha de emision)
        c.add(Calendar.DATE, 1); // agrego a c un field y amount(cantiadad de dias a aumentar)
         
        reserva.setFechaVencimiento(c.getTime()); // seteo esa fecha que me dio el ultimo paso

        reserva.setEstadoReservaId(EstadoReservaEnum.CREADA);

        //relaciones Bidireccionales
        staff.agregarReserva(reserva);
        vuelo.agregarReserva(reserva);

        repo.save(reserva);
        return reserva;

    }*/

    public Reserva buscarPorId(Integer id) {
       return repo.findByReservaId(id);
    }
    
}
