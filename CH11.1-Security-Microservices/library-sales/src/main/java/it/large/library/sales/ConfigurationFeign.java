package it.large.library.sales;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class ConfigurationFeign {

    /*
     * Questo bean imposta il livello di logging per il client Feign. Puoi scegliere tra diversi livelli di logging, tra cui:
     * Logger.Level.FULL: Stampa tutte le informazioni relative alla richiesta e alla risposta.
     * Logger.Level.BASIC: Stampa informazioni di base sulla richiesta e sulla risposta.
     * Logger.Level.HEADERS: Stampa solo gli header della richiesta e della risposta.
     * Logger.Level.NONE: Non stampa alcun log.
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        // Configurazione del livello di logging per Feign
        return Logger.Level.FULL; // Puoi scegliere tra FULL, BASIC, HEADERS, NONE
    }

    /*
     * Questo bean configura il meccanismo di ripetizione per le richieste Feign.
     * In questo caso, viene utilizzato Retryer.Default(), che è la configurazione predefinita di Feign.
     * Questo significa che Feign riproverà la richiesta in caso di fallimento, utilizzando un approccio esponenziale con un ritardo crescente tra i tentativi.
     */
    @Bean
    public Retryer feignRetryer() {
        // Configurazione del meccanismo di ripetizione per Feign
        return new Retryer.Default(); // Utilizza la configurazione di ripetizione predefinita
    }

    /*
     * Questo bean configura il decodificatore degli errori per il client Feign.
     * Quando si verificano errori durante le chiamate Feign,
     * questo decodificatore personalizzato (CustomErrorDecoder) gestirà la logica di decodifica degli errori con una classe personalizzata.
     */
    @Bean
    public ErrorDecoder feignErrorDecoder() {
        // Configurazione del decodificatore degli errori per Feign
        return new CustomErrorDecoder(); // Implementa la tua logica per gestire gli errori
    }

}
