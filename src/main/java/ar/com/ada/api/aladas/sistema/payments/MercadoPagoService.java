package ar.com.ada.api.aladas.sistema.payments;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.models.response.PagoReservaResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

@Service
public class MercadoPagoService {

    @Value("${mercadoPago.publicKey}")
    private String publicKey;

    @Value("${mercadoPago.accessToken}")
    private String accessToken;

    public PagoReservaResponse generarPreferenciaParaReserva(Reserva reserva) {

        // tenemos que crear el CreatePreference Object
        JSONObject preferencia = generarPreferencia(reserva);

        // pegarle a la API con la preferencia
        String urlAPI = "https://api.mercadopago.com/checkout/preferences?access_token=" + accessToken;

        //aqui llamamos a la API
        HttpResponse<JsonNode> response = Unirest.request("POST", urlAPI) //Patron de diseño builder que construe paso a paso un request
            .header("content-type", "application/json")
            .body(preferencia).asJson();

        //leer el Init_point
        String initPoint = response.getBody().getObject().get("init_point").toString();

        PagoReservaResponse rta = new PagoReservaResponse();

        rta.isOk = response.isSuccess();
        rta.reservaId = reserva.getReservaId();
        rta.message = "Preferencia creada";
        rta.URLPago = initPoint;

        return rta;

    }

    public JSONObject generarPreferencia(Reserva reserva) {

        JSONObject joPreference = new JSONObject();
        JSONArray joItems = new JSONArray();
        JSONObject joItem = new JSONObject();

        joItem.put("title", "reserva #" + reserva.getReservaId()); //este tendra items, el primero de la docu + otros como el tipo de moneda
        joItem.put("descripcion", "reserva generada desde aladas");// en el nodo descripcion pone el valor reserva generada desde aladas
        joItem.put("quantity", 1);
        joItem.put("unit_price", reserva.getVuelo().getPrecio().toString());
        joItem.put("currency_id", reserva.getVuelo().getCodigoMoneda());
        //agregar item a coleccion de items
        joItems.put(joItem);
        
        //pongo en el nodo items  el array de items 
        joPreference.put("transaction_amount", reserva.getVuelo().getPrecio());
        joPreference.put("currency_id", reserva.getVuelo().getCodigoMoneda());
        joPreference.put("installments", 1); //installments  cuotas
        joPreference.put("items", joItems); // dentro de las preferencias estan todos los items(array)

        JSONObject joPayer = new JSONObject();

        if (!reserva.getPasajero().equals(null)) { //chequear esta logica!
            joPayer.put("first_name", reserva.getPasajero().getNombre());
            joPayer.put("email", reserva.getPasajero().getUsuario().getEmail());
                       
        }else{
            joPayer.put("first_name", reserva.getStaff().getNombre());
            joPayer.put("email", reserva.getStaff().getUsuario().getEmail());
        }

        joPreference.put("payer", joPayer);

        joPreference.put("external_id", "RESERVA" + reserva.getReservaId());// toda info que puede identificar algo  nuestro dentro de MP
        //joPreference.put("auto_return", "all"); //Redirige al cliente. Para ponerlo debo indicar cuales son las url de retorno (de error, success etc )

        // podria agregarse que la reserva vence en 48 hs

        return joPreference;
        
       
    }

}
 /*
        {
"items": [
    {
      "title": "Dummy Title",
      "description": "Dummy description",
      "picture_url": "http://www.myapp.com/myimage.jpg",
      "category_id": "cat123",
      "quantity": 1,
      "currency_id": "U$",
      "unit_price": 10
    }
  ],
  "payer": {
    "phone": {},
    "identification": {},
    "address": {}
  },
  "payment_methods": {
    "excluded_payment_methods": [
      {}
    ],
    "excluded_payment_types": [
      {}
    ]
  },
  "shipments": {
    "free_methods": [
      {}
    ],
    "receiver_address": {}
  },
  "back_urls": {},
  "differential_pricing": {},
  "tracks": [
    {
      "type": "google_ad"
    }
  ]
}

        */