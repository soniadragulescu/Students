package com.company.services;

import com.company.entities.Student;
import com.company.repositories.XMLStudentFileRepository;
import com.company.validators.StudentValidator;
import com.company.validators.ValidatorException;

import javax.xml.bind.ValidationException;

public class StudentService {
    private XMLStudentFileRepository fileRepo;
    private StudentValidator validator;
    public StudentService(StudentValidator validator, XMLStudentFileRepository fileRepo) {
        this.validator=validator;
        this.fileRepo=fileRepo;
    }
    public Student adauga(String id,String nume, Integer grupa, String email) throws ValidationException{
        Student s=new Student(id,nume,grupa,email);
        this.validator.validate(s);
        return this.fileRepo.save(s);
    }
    public Student sterge(String id){
        if(id.equals("")){
            throw new ValidatorException("Id nu poate fi null! ");
        }
        return fileRepo.delete(id);
    }
   // public Student modifica(String line) throws ValidationException;
    public Student gaseste(String id){
        if(id.equals("")){
            throw new ValidatorException("Id nu poate fi null! ");
        }
        return fileRepo.findOne(id);
    }
    public Iterable<Student> toate(){
        return fileRepo.findAll();
    }

}
