package ar.com.ada.api.aladas.entities;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "pasajero")
public class Pasajero extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pasajero_id")
    private Integer pasajeroId;

    // relacion con reserva
    @OneToMany(mappedBy = "pasajero", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();

    @OneToOne(mappedBy = "pasajero", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Usuario usuario;

    public Integer getPasajeroId() {
        return pasajeroId;
    }

    public void setPasajeroId(Integer pasajeroId) {
        this.pasajeroId = pasajeroId;
    }

    
    // relacion bi-direccional con reserva
    public void agregarReserva(Reserva reserva) {
        this.reservas.add(reserva);
        reserva.setPasajero(this);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        usuario.setPasajero(this);
    }

}
