package ar.com.ada.api.aladas.sistema.com;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

@Service
public class EmailService {

    enum TipoEnvio {
        SMTP, API
    }

    @Value("${emailSettings.apiKey}")
    private String apiKey;
    @Value("${emailSettings.apiBaseUri}")
    public String apiBaseUri;
    @Value("${emailSettings.apiBaseUri}")
    public String requestUri;
    @Value("${emailSettings.from}")
    public String from;
    @Value("${emailSettings.domain}")
    public String domain;
    @Value("${emailSettings.enabled}")
    public boolean enabled;

    // Basico
    public void SendEmail(String email, String subject, String message) throws UnirestException {

        if (!this.enabled) // si quiero utilizar la api sin mandar los emails debe ir a app properties y deshabilitar email setting 
            return;

        JsonNode r;
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + this.domain + "/messages")
                .basicAuth("api", this.apiKey)
                .field("from", this.from)
                .field("to", email)
                .field("subject", subject)
                .field("text", message).asJson();

        r = request.getBody();

    }
    
}
