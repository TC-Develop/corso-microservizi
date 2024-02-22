package it.large.library.catalogue.service;

import it.large.library.catalogue.entity.AuthorEntity;
import it.large.library.catalogue.exception.NotFoundException;
import it.large.library.catalogue.model.AuthorModel;
import it.large.library.catalogue.repository.AuthorRepository;
import it.large.library.catalogue.utils.ConverterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
        return ConverterConfig.convertAuthorEntityToAuthorModel(authorEntityOpt.get());
    }

    public AuthorModel add(AuthorModel authorModel) {

        AuthorEntity authorEntity;
        authorEntity = ConverterConfig.converterAuthorModelToAuthorEntity(authorModel);
        authorEntity = authorRepository.save(authorEntity);

        return ConverterConfig.converterAuthorEntityToAuthorModel(authorEntity);
    }

    public AuthorModel edit(AuthorModel authorModel) {

        Optional<AuthorEntity> authorEntityOpt = authorRepository.findById(authorModel.getAuthorId());
        if (authorEntityOpt.isEmpty()) {
            throw new NotFoundException("Autore non trovato!");
        }

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity = ConverterConfig.converterAuthorModelToAuthorEntity(authorModel);
        authorEntity.setAuthorId(authorModel.getAuthorId());

        authorEntity = authorRepository.save(authorEntity);

        return ConverterConfig.converterAuthorEntityToAuthorModel(authorEntity);
    }

    public void remove(UUID authorId) {

        Optional<AuthorEntity> authorEntityOpt = authorRepository.findById(authorId);
        if (authorEntityOpt.isEmpty()) {
            throw new NotFoundException("Autore non trovato!");
        }

        authorRepository.delete(authorEntityOpt.get());
    }

}