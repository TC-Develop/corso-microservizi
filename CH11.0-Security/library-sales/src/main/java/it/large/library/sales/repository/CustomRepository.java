package it.large.library.sales.repository;

import it.large.library.sales.document.SaleDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Repository
public class CustomRepository {

    /*
     * MongoTemplate è una classe fornita da Spring Data MongoDB, che semplifica l'interazione con il database MongoDB in un'applicazione Spring.
     * Essa fornisce metodi convenienti per eseguire operazioni CRUD (Create, Read, Update, Delete) e operazioni avanzate come le query di aggregazione.
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<SaleDocument> filter(UUID clientId) {

        /*
         * Criteria è una classe di Spring Data MongoDB che consente di costruire criteri di query.
         * I criteri vengono utilizzati per specificare le condizioni che devono essere soddisfatte per selezionare i documenti da un database MongoDB.
         */
        List<Criteria> criterias = new LinkedList<>();
        criterias.add( Criteria.where("clientId").is(clientId) );
//        criterias.add( Criteria.where("amount").lt(35) );

        Criteria criteria = new Criteria().andOperator(criterias);

        Query query = new Query();
        query.addCriteria(criteria);


        return mongoTemplate.find(query, SaleDocument.class);

    }

}