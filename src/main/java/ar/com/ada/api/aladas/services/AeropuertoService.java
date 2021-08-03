package ar.com.ada.api.aladas.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.repos.AeropuertoRepository;
import ar.com.ada.api.aladas.services.VueloService.ValidacionVueloDataEnum;

@Service
public class AeropuertoService {

    @Autowired
    private AeropuertoRepository repo;

    public void crear(Integer aeropuertoId, String nombre, String codigoIATA) {

        Aeropuerto aeropuerto = new Aeropuerto();
        
        aeropuerto.setAeropuertoId(aeropuertoId);
        aeropuerto.setNombre(nombre);
        aeropuerto.setCodigoIATA(codigoIATA);

        repo.save(aeropuerto);


    }
    public List<Aeropuerto> obtenerTodos(){
        return repo.findAll();
    }

    public Aeropuerto buscarPorCodigoIATA(String codigoIATA){

        return repo.findByCodigoIATA(codigoIATA);

    }
    
    public boolean validarCodigoIATA(Aeropuerto aeropuerto){
        if(aeropuerto.getCodigoIATA().length() != 3 ){
            return false;
        }

        String codigoIATA = aeropuerto.getCodigoIATA();

        for(int i = 0; i < codigoIATA.length(); i++){
            char letra = codigoIATA.charAt(i); // en cada vuelta (3, una por cada letra) chequea que esas letras esten 
            
            if (! (letra >= 'A' && letra <= 'Z')){
                return false;
            }
        } return true;
    }

    //public Aeropuerto buscarPorId()
    
    public Aeropuerto buscarPorId(Integer id) {
        
        return repo.findByAeropuertoId(id);         

        
    }

    public boolean verificarAeropuertoExiste(Integer id){
        if (this.buscarPorId(id) == null){
            return false;
        } else return true;
    }


}
