package ar.com.ada.api.aladas.services;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Vuelo.EstadoVueloEnum;
import ar.com.ada.api.aladas.repos.VueloRepository;

@Service
public class VueloService {
    @Autowired
    private VueloRepository repo;

    @Autowired
    private AeropuertoService aeroService;

    public void crear(Vuelo vuelo) {

        repo.save(vuelo);
    }

    public Vuelo crear(Date fecha, Integer capacidad, String aeropOrigenIATA, String aeropDestinoIATA,
            BigDecimal precio, String codigoMoneda) {
        Vuelo vuelo = new Vuelo();

        vuelo.setFecha(fecha);
        vuelo.setCapacidad(capacidad);
        vuelo.setEstadoVueloId(EstadoVueloEnum.GENERADO);

        // buscar el aeropuerto por codigo iata, el set de la clase Vuelo, los aerop
        // estan por codigo integer
        // voy a necesitar llamar al service de aeropuerto para usar el
        // findbyCodigoIATA, lo declaro arriba
        Aeropuerto aeropuertoOrigen = aeroService.buscarPorCodigoIATA(aeropOrigenIATA);

        Aeropuerto aeropuertoDestino = aeroService.buscarPorCodigoIATA(aeropDestinoIATA);

        vuelo.setPrecio(precio);

        vuelo.setAeropuertoOrigen(aeropuertoOrigen.getAeropuertoId());
        vuelo.setAeropuertoDestino(aeropuertoDestino.getAeropuertoId());

        vuelo.setFecha(fecha);
        vuelo.setCodigoMoneda(codigoMoneda);

        return repo.save(vuelo);
    }

    public ValidacionVueloDataEnum validar(Vuelo vuelo) {

        if (!validarPrecio(vuelo))
            return ValidacionVueloDataEnum.ERROR_PRECIO;

        if (!validarAeropuertoOrigenDiffDestino(vuelo))
            return ValidacionVueloDataEnum.ERROR_AEROPUERTOS_IGUALES;

        if (!validarCapacidad(vuelo))
            return ValidacionVueloDataEnum.ERROR_CAPACIDAD_MINIMA;

        if (!validarOrigenNulo(vuelo)) {
            return ValidacionVueloDataEnum.ERROR_AEROPUERTO_ORIGEN;
        }

        if (!validarDestinoNulo(vuelo)) {
            return ValidacionVueloDataEnum.ERROR_AEROPUERTO_DESTINO;
        }

        return ValidacionVueloDataEnum.OK;

    }

    public boolean validarCapacidad(Vuelo vuelo) {

        if (vuelo.getCapacidad() <= 0) {
            return false;

        }
        return true;
    }

    public boolean validarPrecio(Vuelo vuelo) {

        if (vuelo.getPrecio() == null) {
            return false;
        }
        if (vuelo.getPrecio().doubleValue() > 0)
            return true;

        return false;
    }

    public boolean validarAeropuertoOrigenDiffDestino(Vuelo vuelo) {
        /*
         * if(vuelo.getAeropuertoDestino() != vuelo.getAeropuertoOrigen()) return true;
         * else return false;
         */

        return vuelo.getAeropuertoDestino().intValue() != vuelo.getAeropuertoOrigen().intValue(); // mmodificar a equals o agregar el .intValue()

    }

    public boolean validarOrigenNulo(Vuelo vuelo) {

        return aeroService.verificarAeropuertoExiste(vuelo.getAeropuertoOrigen());
    }

    public boolean validarDestinoNulo(Vuelo vuelo) {

        return aeroService.verificarAeropuertoExiste(vuelo.getAeropuertoDestino());
    }

    /*
     * public boolean validarDestinoNulo(Vuelo vuelo){
     * 
     * Aeropuerto destino = aeroService.buscarPorId(vuelo.getAeropuertoDestino());
     * 
     * if (destino == null);
     * 
     * return true; // este metodo no funciona, entre otras cosas estaba realizando
     * en el service de // vuelo algo que le compete a aeropuerto, como es la
     * validacion de aeropuertos
     * 
     * }
     */

    public Vuelo buscarPorId(Integer id) {

        return repo.findByVueloId(id);

    }

    public void guardar(Vuelo vuelo) {
        repo.save(vuelo);
    }

    public List<Vuelo> traerVuelosAbiertos() {

        return repo.findByEstadoVueloId(EstadoVueloEnum.ABIERTO.getValue());

    }

    public enum ValidacionVueloDataEnum {
        OK, ERROR_PRECIO, ERROR_AEROPUERTO_ORIGEN, ERROR_AEROPUERTO_DESTINO, ERROR_FECHA, ERROR_MONEDA,
        ERROR_CAPACIDAD_MINIMA, ERROR_CAPACIDAD_MAXIMA, ERROR_AEROPUERTOS_IGUALES, ERROR_GENERAL,
    }

}
