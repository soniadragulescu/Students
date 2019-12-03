package com.company;

import com.company.entities.Tema;
import com.company.repositories.XMLNotaFileRepository;
import com.company.repositories.XMLStudentFileRepository;
import com.company.repositories.XMLTemaFileRepository;
import com.company.services.FilterService;
import com.company.services.NotaService;
import com.company.services.StudentService;
import com.company.services.TemaService;
import com.company.validators.NotaValidator;
import com.company.validators.StudentValidator;
import com.company.validators.TemaValidator;

public class Main {

    public static void main(String[] args) {
	// write your code here
        StudentValidator validatorS=new StudentValidator();
        XMLStudentFileRepository fRepoS=new XMLStudentFileRepository("C:\\Users\\sonia\\IdeaProjects\\LaboratorUI\\data\\studenti.xml");
        StudentService serviceS=new StudentService(validatorS,fRepoS);

        TemaValidator validatorT=new TemaValidator();
        XMLTemaFileRepository fRepoT=new XMLTemaFileRepository("C:\\Users\\sonia\\IdeaProjects\\LaboratorUI\\data\\teme.xml");
        TemaService serviceT=new TemaService(validatorT,fRepoT);

        NotaValidator validatorN=new NotaValidator();
        XMLNotaFileRepository fRepoN=new XMLNotaFileRepository("C:\\Users\\sonia\\IdeaProjects\\LaboratorUI\\data\\note.xml");
        NotaService serviceN=new NotaService(validatorN,fRepoN, fRepoS,fRepoT);

        FilterService filterS=new FilterService(serviceS,serviceT,serviceN);

        UI ui=new UI(serviceS,serviceT,serviceN, filterS);
        ui.run();
    }
}
