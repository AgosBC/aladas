package ar.com.ada.api.aladas.models.request;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import ar.com.ada.api.aladas.entities.Pais.TipoDocuEnum;
import ar.com.ada.api.aladas.entities.Usuario.TipoUsuarioEnum;

/**
 * RegistrationRequest
 */
public class RegistrationRequest {
    @NotBlank(message = "el nombre no puede ser nulo o vacio")
    //@NotNull //que no sea nulo
    //@NotEmpty //no puede estar vacio
    @Min(3)//minimo de caracteres
    public String fullName; // Nombre persona

    @Positive(message = "El codigo de pais debe ser positivo")
    //@ValidCountryCustom //crear tu propia notacion
    public int country; // pais del usuario

    //automaticamente valida contra nuestros enums
    public TipoDocuEnum  identificationType; // Tipo Documento

    @NotBlank(message = "No puede ID no puede ser nulo o vacio")
    public String identification; // nro documento

    @Past(message = "la fecha de nacimiento no puede ser a futuro")
    public Date birthDate; // fechaNacimiento

    @Email(message = "el email es invalido")
    public String email; // email

    public TipoUsuarioEnum userType;

    @NotBlank(message =  "la contraseña no puede ser vacia")
    @Size(min = 8, max = 16, message = "La contraseña debe tener entre 8 y 16 caracteres")
    public String password; // contraseña elegida por el usuario.

}