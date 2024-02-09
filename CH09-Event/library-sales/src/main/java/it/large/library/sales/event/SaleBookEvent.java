package it.large.library.sales.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/*
 * La classe SaleBookEvent estende ApplicationEvent, che è una classe di base per tutti gli eventi pubblicati in un'applicazione Spring.
 * L'obiettivo principale di questa classe è fornire un meccanismo per notificare altri componenti dell'applicazione su determinati eventi.
 */
@Getter
@Setter
// L'estensione di ApplicationEvent fornisce funzionalità di base per la gestione degli eventi in un contesto Spring.
// La superclasse contiene una referenza all'oggetto sorgente (source) dell'evento.
public class SaleBookEvent extends ApplicationEvent {

    // Prende un parametro source che rappresenta la fonte dell'evento.
    // In questo caso, source potrebbe essere un oggetto SaleModel o qualunque altro oggetto che rappresenti l'evento specifico.
    public SaleBookEvent(Object source) {
        super(source);
    }

    //  Il Clock è utilizzato per fornire un'indicazione temporale dell'evento.
    //  Potrebbe essere utile se si desidera associare un timestamp specifico all'evento.
    public SaleBookEvent(Object source, Clock clock) {
        super(source, clock);
    }

}