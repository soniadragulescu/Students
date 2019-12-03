package com.company.services;

import com.company.entities.Student;
import com.company.entities.Tema;
import com.company.repositories.XMLTemaFileRepository;
import com.company.validators.TemaValidator;
import com.company.validators.ValidatorException;

import javax.xml.bind.ValidationException;

public class TemaService {
    private XMLTemaFileRepository fileRepo;
    private TemaValidator validator;
    public TemaService(TemaValidator validator, XMLTemaFileRepository fileRepo) {
        this.validator=validator;
        this.fileRepo=fileRepo;
    }
    public Tema adauga(String id, String descriere, Integer startweek, Integer deadlineweek) throws ValidationException {
        Tema t=new Tema(id,descriere,startweek,deadlineweek);
        this.validator.validate(t);
        return this.fileRepo.save(t);
    }
    public Tema sterge(String id){
        if(id.equals("")){
            throw new ValidatorException("Id nu poate fi null! ");
        }
        return fileRepo.delete(id);
    }
    // public Tema modifica(String line) throws ValidationException;
    public Tema gaseste(String id){
        if(id.equals("")){
            throw new ValidatorException("Id nu poate fi null! ");
        }
        return fileRepo.findOne(id);
    }
    public Iterable<Tema> toate(){
        return fileRepo.findAll();
    }

}
