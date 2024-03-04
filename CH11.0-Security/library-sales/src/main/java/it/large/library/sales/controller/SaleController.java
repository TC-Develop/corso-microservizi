package it.large.library.sales.controller;

import it.large.library.sales.controller.payload.request.SaleRequest;
import it.large.library.sales.controller.payload.response.SaleResponse;
import it.large.library.sales.model.SaleModel;
import it.large.library.sales.service.SaleService;
import it.large.library.sales.utils.ConverterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("library/api/v1/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    /**
     *  Indica che questo metodo risponde a richieste HTTP di tipo POST al percorso base specificato per il controller
     * @param saleRequest
     * @param clientId
     * @return
     */
    @PostMapping(path = "")
    public ResponseEntity<SaleResponse> add(
            @Validated @RequestBody SaleRequest saleRequest,
            // @RequestHeader in Spring viene utilizzata per estrarre valori dalle intestazioni (headers) di una richiesta HTTP
            // Quando una richiesta HTTP viene inviata al tuo controller Spring, se l'intestazione "client_id" è presente, il valore sarà estratto e assegnato al parametro clientId.
            // Ad esempio, si invia una richiesta HTTP con un'intestazione "client_id" nel formato UUID, Spring la convertirà automaticamente in un oggetto UUID
            @RequestHeader(name = "client_id") UUID clientId
    ) {
        SaleModel saleModel = ConverterConfig.convertRequestToModel(saleRequest);
        // Imposto il campo client_id del RequestHeader direttamente all'oggetto model
        saleModel.setClientId(clientId);
        saleModel = saleService.add(saleModel);
        SaleResponse saleResponse = ConverterConfig.convertModelToResponse(saleModel);

        return ResponseEntity.ok(saleResponse);
    }

    /**
     * Indica che questo metodo risponde a richieste HTTP di tipo GET al percorso base specificato per il controller
     * @param clientId
     * @return
     */
    @GetMapping(path = "/{client_id}")
    public ResponseEntity<List<SaleResponse>> get(
            @PathVariable(name = "client_id") UUID clientId
    ) {
        List<SaleModel> saleModelList = saleService.get(clientId);
        List<SaleResponse> saleResponseList = ConverterConfig.convertModelListToResponseList(saleModelList);

        return ResponseEntity.ok(saleResponseList);
    }

    /**
     * Indica che questo metodo risponde a richieste HTTP di tipo GET al percorso base specificato per il controller
     * @param clientId
     * @return
     */
    @GetMapping(path = "/amount/{client_id}")
    public ResponseEntity<Double> getTotalAmountClient(
            @PathVariable(name = "client_id") UUID clientId
    ) {
        Double totalAmountPerClient = saleService.getTotalAmountPerClient(clientId);

        return ResponseEntity.ok(totalAmountPerClient);
    }


}