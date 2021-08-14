package ar.com.ada.api.aladas.entities;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "staff")
public class Staff extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Integer staffId;

    @OneToOne(mappedBy = "staff", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Usuario usuario;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        usuario.setStaff(this);
    }

    // relacion bi-direccional con reserva
    public void agregarReserva(Reserva reserva) {
        this.reservas.add(reserva);
        reserva.setStaff(this);
    }

}
