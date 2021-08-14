package ar.com.ada.api.aladas.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private Integer reservaId;

    @ManyToOne
    @JoinColumn(name = "vuelo_id", referencedColumnName = "vuelo_id")
    private Vuelo vuelo;

    @ManyToOne
    @JoinColumn(name = "pasajero_id", referencedColumnName = "pasajero_id")
    private Pasajero pasajero;

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
    private Staff staff;

    @Column(name = "estado_reserva_id")
    private Integer estadoReservaId;

    @Column(name = "fecha_emision")
    private Date fechaEmision;

    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    // agrego pasaje. que servira para relacionar una reserva con un pasaje

    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Pasaje pasaje; // en linea 41: nombre del atributo que hace referencia a la tabla

    public Integer getReservaId() {
        return reservaId;
    }

    public void setReservaId(Integer reservaId) {
        this.reservaId = reservaId;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    
    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public EstadoReservaEnum getEstadoReservaId() {
        return EstadoReservaEnum.parse(estadoReservaId);
    }

    public void setEstadoReservaId(EstadoReservaEnum estadoReservaId) {
        this.estadoReservaId = estadoReservaId.getValue();
    }

    public Pasaje getPasaje() {
        return pasaje;
    }

    // se puede hacer un metodo asociar pasaje pero para achicar ser puede hacer en
    // el setter de pasaje
    // ya que los parametros que pasamos serian los mismos

    public void setPasaje(Pasaje pasaje) {
        this.pasaje = pasaje;
        pasaje.setReserva(this);
    }

    
    public enum EstadoReservaEnum {

        CREADA(1), TRANSMITIENDO_AL_PG(2), ERROR_AL_CONECTAR_PG(3), PENDIENTE_DE_PAGO(4), PAGADA(5),
        CANCELADO_POR_USUARIO(6), CANCELADO_POR_EMPRESA(7), PAGO_RECHAZADO(8), EXPIRADO(9), EMITIDA(10);

        private final Integer value;

        private EstadoReservaEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static EstadoReservaEnum parse(Integer id) {
            EstadoReservaEnum status = null; // Default
            for (EstadoReservaEnum item : EstadoReservaEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

}
