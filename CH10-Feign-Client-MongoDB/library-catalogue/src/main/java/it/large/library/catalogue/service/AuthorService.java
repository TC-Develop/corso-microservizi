package it.large.library.catalogue.service;

import it.large.library.catalogue.entity.AuthorEntity;
import it.large.library.catalogue.exception.NotFoundException;
import it.large.library.catalogue.model.AuthorModel;
import it.large.library.catalogue.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static it.large.library.catalogue.utils.ConverterConfig.convertAuthorEntityToAuthorModel;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    /*
     * Metodo per recuperare un autore tramite il suo id
     */
    public AuthorModel get(UUID authorId) {
        Optional<AuthorEntity> authorEntityOpt = authorRepository.findById(authorId);
        if (authorEntityOpt.isEmpty()) {
            throw new NotFoundException("Autore non presente nel catalogo");
        }
        return convertAuthorEntityToAuthorModel(authorEntityOpt.get());
    }
}