package ar.com.ada.api.aladas.entities;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vuelo")
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vuelo_id")
    private Integer vueloId;

    private Date fecha;

    @Column(name = "estado_vuelo_id")
    private Integer estadoVueloId;

    private Integer capacidad;

    @Column(name = "aeropuerto_origen")
    private Integer aeropuertoOrigen;

    @Column(name = "aeropuerto_destino")
    private Integer aeropuertoDestino;

    private BigDecimal precio;

    @Column(name = "codigo_moneda")
    private String codigoMoneda;

    // lista de reservas mapeada
    @JsonIgnore
    @OneToMany(mappedBy = "vuelo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();

    public Integer getVueloId() {
        return vueloId;
    }

    public void setVueloId(Integer vueloId) {
        this.vueloId = vueloId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoVueloEnum getEstadoVueloId() {
        return EstadoVueloEnum.parse(estadoVueloId);
    }

    public void setEstadoVueloId(EstadoVueloEnum estadoVueloId) {
        this.estadoVueloId = estadoVueloId.getValue();
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Integer getAeropuertoOrigen() {
        return aeropuertoOrigen;
    }

    public void setAeropuertoOrigen(Integer aeropuertoOrigen) {
        this.aeropuertoOrigen = aeropuertoOrigen;
    }

    public Integer getAeropuertoDestino() {
        return aeropuertoDestino;
    }

    public void setAeropuertoDestino(Integer aeropuertoDestino) {
        this.aeropuertoDestino = aeropuertoDestino;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    // RELACION BI-DIRECCIONAL
    public void agregarReserva(Reserva reserva) {
        this.reservas.add(reserva);
        reserva.setVuelo(this);
    }

    public enum EstadoVueloEnum { // esta diferente al de las demas OJO
        GENERADO(1), PLANIFICADO(2), TRIPULACION_PREASIGNADA(3), ABIERTO(4), CONFIRMADO(5), REPROGRAMADO(6),
        CANCELADO(7), CERRADO(8); // NO AGREGAMOS MAS ESTADOS PORQUE ESTE SISTEMA ESTA ENFOCADO AL
                                  // SISTEMA DE RESERVA, NO AL TRAFICO AEREO (NO ES NECESARIO PARA LA
                                  // VISION QUE ESTAMOS ARMANDO)

        private final Integer value;

        private EstadoVueloEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static EstadoVueloEnum parse(Integer id) {
            EstadoVueloEnum status = null; // Default
            for (EstadoVueloEnum item : EstadoVueloEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

}
