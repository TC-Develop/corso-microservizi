package it.large.library.catalogue.utils;

import it.large.library.catalogue.model.AuthorModel;
import it.large.library.catalogue.model.BookModel;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

// N.B. -> DATABASE STATICO DI ESEMPIO, PER MANCANZA MOMENTANEA DI UN DB (DA NON UTILIZZARE PIU' SUCCESSIVAMENTE)
public class StaticDatabase {

        public static List<BookModel> storageBooks = new ArrayList<>();

    static {
        storageBooks.add(new BookModel(UUID.fromString("3b39fa84-b01a-42ce-8ab6-87e7c5e39c36"), "Le Cronache della Magia",
                new AuthorModel(UUID.fromString("98e1e93e-f1a2-4788-ae13-ceeff19f0ab0"), "Giovanni", "Rossi", parseDate("1990-01-15"), "Roma", null),
                new BigDecimal("39.99"), "Fantasy"));

        storageBooks.add(new BookModel(UUID.fromString("44c9896e-9ffb-4aae-b122-760c89a2691e"), "Segreti della Foresta Incantata",
                new AuthorModel(UUID.fromString("10c9d1a0-2913-4744-9dac-cdd194ebd2ea"), "Anna", "Verdi", parseDate("1985-07-22"), "Napoli", null),
                new BigDecimal("24.99"), "Fantasy"));

        storageBooks.add(new BookModel(UUID.fromString("011dc97f-499d-4178-b297-1a467c1e8178"), "Il Rifugio del Drago",
                new AuthorModel(UUID.fromString("ed693809-5341-401c-97a9-3360b0ab1ff9"), "Roberto", "Bianchi", parseDate("1978-03-10"), "Torino", parseDate("2020-12-05")),
                new BigDecimal("45.99"), "Fantasy"));

        storageBooks.add(new BookModel(UUID.fromString("981a86c2-90fa-4757-b578-77696300ba14"), "Stregoni e Meraviglie",
                new AuthorModel(UUID.fromString("44aeb594-6af5-4968-97dd-0c7ef85f4d36"), "Elisa", "Ferrari", parseDate("1995-12-05"), "Milano", null),
                new BigDecimal("29.99"), "Fantasy"));

        storageBooks.add(new BookModel(UUID.fromString("35f649dd-fc60-4c52-9a34-14431953606a"), "Ricerca della Corona di Cristallo",
                new AuthorModel(UUID.fromString("471eedd0-bb27-4265-b5ad-0e4be19ce36a"), "Michele", "Ricci", parseDate("1982-09-18"), "Venezia", null),
                new BigDecimal("34.99"), "Fantasy"));
    }

    private static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Errore nella conversione della data", e);
        }
    }

    public static List<BookModel> getAllBooksStatic() {
        return new ArrayList<>(storageBooks);
    }

}