package com.company;

import com.company.entities.StructuraAn;
import com.company.entities.Student;
import com.company.entities.Tema;
import com.company.services.FilterService;
import com.company.services.NotaService;
import com.company.services.StudentService;
import com.company.services.TemaService;
import com.company.validators.ValidatorException;

import javax.xml.bind.ValidationException;
import java.util.Scanner;

public class UI {
    private StudentService serviceS;
    private TemaService serviceT;
    private NotaService serviceN;
    private FilterService filterService;

    public UI(StudentService serviceS, TemaService serviceT,NotaService serviceN, FilterService filterService) {
        this.serviceS = serviceS;
        this.serviceT=serviceT;
        this.serviceN=serviceN;
        this.filterService=filterService;
    }

    public void printMeniuPrincipal(){
        System.out.println("1.Studenti");
        System.out.println("2.Teme");
        System.out.println("3.Note");
        System.out.println("4.Filtrari");
        System.out.println("0.Exit!");
    }

    public void printMeniuStudenti(){
        System.out.println("1.Adauga student");
        System.out.println("2.Sterge student");
        System.out.println("3.Modifica student");
        System.out.println("4.Toti studentii");
        System.out.println("0.Exit!");
    }

    public void printMeniuTeme(){
        System.out.println("1.Adauga tema");
        System.out.println("2.Sterge tema");
        System.out.println("3.Modifica tema");
        System.out.println("4.Toate temele");
        System.out.println("0.Exit!");
    }

    public void printMeniuNote(){
        System.out.println("1.Adauga nota");
        System.out.println("2.Toate notele");
        System.out.println("0.Exit!");
    }

    public void printMeniuFiltrari(){
        System.out.println("1.Toți studenții unei grupe");
        System.out.println("2.Toți studenții care au predat o anumita tema");
        System.out.println("3.Toți studenții care au predat o anumita tema unui profesor anume");
        System.out.println("4.Toate notele la o anumita tema, dintr-o saptamana data");
        System.out.println("0.Exit!");
    }

    public void meniuFiltrari(){
        printMeniuFiltrari();
        String tId, profesor;
        Integer grupa,week;
        Scanner reader = new Scanner(System.in);
        String cmd = reader.nextLine();
        while (!cmd.equals("0")) {
            try {
                switch (cmd) {
                    case "1": {
                        System.out.println("Grupa: ");
                        grupa = Integer.parseInt(reader.nextLine());
                        filterService.getStudentsFromGroup(grupa).forEach(System.out::println);
                        printMeniuFiltrari();
                        cmd = reader.nextLine();
                        break;
                    }
                    case "2":
                        System.out.println("ID-ul temei:");
                        tId=reader.nextLine();
                        filterService.getStudentWithHomeworkGiven(tId).forEach(System.out::println);
                        printMeniuFiltrari();
                        cmd = reader.nextLine();
                        break;
                    case "3":
                        System.out.println("ID-ul temei:");
                        tId=reader.nextLine();
                        System.out.println("Profesorul: ");
                        profesor=reader.nextLine();
                        filterService.getStudentsWithHomeworkProfGiven(tId,profesor).forEach(System.out::println);
                        printMeniuFiltrari();
                        cmd = reader.nextLine();
                        break;
                    case "4": {
                        System.out.println("ID-ul temei:");
                        tId=reader.nextLine();
                        System.out.println("Saptamana:");
                        week=Integer.parseInt(reader.nextLine());
                        filterService.getMarksForHomeworkWeekGiven(tId,week).forEach(System.out::println);
                        printMeniuFiltrari();
                        cmd = reader.nextLine();
                        break;
                    }
                    default:
                        System.out.println("Comanda invalida!");
                        printMeniuStudenti();
                        cmd = reader.nextLine();

                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void meniuNote(Integer saptamana){
        printMeniuNote();
        int notaMax;
        String sId,tId,profesor, feedback;
        Integer grade;
        Scanner reader = new Scanner(System.in);
        String cmd = reader.nextLine();
        while (!cmd.equals("0")) {
            try {
                switch (cmd) {
                    case "1": {
                        System.out.println("Id-ul studentului: ");
                        sId = reader.nextLine();
                        System.out.println("Id-ul temei pe care o preda: ");
                        tId = reader.nextLine();
                        System.out.println("Nota: ");
                        grade = Integer.parseInt(reader.nextLine());
                        System.out.println("Profesor: ");
                        profesor = reader.nextLine();
                        System.out.println("Feedback: ");
                        feedback = reader.nextLine();
                        boolean motivat=false;
                        if(serviceN.isDeadlineInThePast(tId,saptamana)) {
                            System.out.println("Are studentul motivare?[y/n]");
                            String yn = reader.nextLine();
                            if (yn.equals("y"))
                                motivat = true;
                                    }

                        serviceN.adauga(sId, tId, grade, saptamana,profesor, feedback, saptamana, motivat);
                        printMeniuNote();
                        cmd = reader.nextLine();
                        break;
                    }
                    case "2":
                        serviceN.toate().forEach(System.out::println);
                        printMeniuNote();
                        cmd = reader.nextLine();
                        break;
                    default:
                        System.out.println("Comanda invalida!");
                        printMeniuNote();
                        cmd = reader.nextLine();

                }
            } catch (ValidatorException | ValidationException |IllegalArgumentException e) {
                System.out.println(e.getMessage());
                printMeniuNote();
                cmd = reader.nextLine();
            }
        }

    }

    public void meniuTeme(StructuraAn anUniversitar){
        printMeniuTeme();
        Scanner reader = new Scanner(System.in);
        String cmd = reader.nextLine();
        String id, descriere;
        Integer startweek, deadlineweek;
        startweek=anUniversitar.saptamana();
        while (!cmd.equals("0")) {
            try {
                switch (cmd) {
                    case "1": {
                        System.out.println("Id: ");
                        id=reader.nextLine();
                        System.out.println("Descriere: ");
                        descriere=reader.nextLine();
                        System.out.println("Suntem in saptamana "+startweek);
                        System.out.println("Deadline: ");
                        deadlineweek=Integer.parseInt(reader.nextLine());
                        Tema t=serviceT.adauga(id,descriere,startweek,deadlineweek);
                        if(t!=null)
                            System.out.println("Tema existenta!");
                        printMeniuTeme();
                        cmd = reader.nextLine();
                        break;
                    }
                    case "2":
                        System.out.println("ID-ul temei de sters:");
                        id=reader.nextLine();
                        Tema t1=serviceT.sterge(id);
                        if(t1!=null)
                            System.out.println("Nu exista tema!");
                        printMeniuTeme();
                        cmd = reader.nextLine();
                        break;
                    case "3":
                        printMeniuTeme();
                        cmd = reader.nextLine();
                        break;
                    case "4": {
                        serviceT.toate().forEach(System.out::println);
                        printMeniuTeme();
                        cmd = reader.nextLine();
                        break;
                    }
                    default:
                        System.out.println("Comanda invalida!");
                        printMeniuTeme();
                        cmd = reader.nextLine();

                }
            } catch (ValidatorException | ValidationException |IllegalArgumentException e) {
                System.out.println(e.getMessage());

            }
        }
    }

    public void meniuStudenti(){
        printMeniuStudenti();
        String id, nume, email;
        Integer grupa;
        Scanner reader = new Scanner(System.in);
        String cmd = reader.nextLine();
        while (!cmd.equals("0")) {
            try {
                switch (cmd) {
                    case "1": {
                        System.out.println("Id: ");
                        id = reader.nextLine();
                        System.out.println("Nume: ");
                        nume=reader.nextLine();
                        System.out.println("Grupa: ");
                        grupa=Integer.parseInt(reader.nextLine());
                        System.out.println("Email: ");
                        email=reader.nextLine();
                        Student s1=serviceS.adauga(id,nume,grupa,email);
                        if(s1!=null) {
                            System.out.println("Student existent!");
                        }
                        printMeniuStudenti();
                        cmd = reader.nextLine();
                        break;
                    }
                    case "2":
                        System.out.println("ID-ul studentului de sters:");
                        id=reader.nextLine();
                        Student s1=serviceS.sterge(id);
                        if(s1==null)
                            System.out.println("Nu exista studentul!");
                        printMeniuStudenti();
                        cmd = reader.nextLine();
                        break;
                    case "3":
                        /*System.out.println("StudentNou{ID;Nume;Medie}");
                        String line2=reader.nextLine();
                        Student s2=sServiceS.modifica(line2);
                        if(s2!=null)
                            System.out.println("Student updated!");*/
                        printMeniuStudenti();
                        cmd = reader.nextLine();
                        break;
                    case "4": {
                        serviceS.toate().forEach(System.out::println);
                        printMeniuStudenti();
                        cmd = reader.nextLine();
                        break;
                    }
                    default:
                        System.out.println("Comanda invalida!");
                        printMeniuStudenti();
                        cmd = reader.nextLine();

                }
            } catch (ValidatorException | ValidationException |IllegalArgumentException e) {
                System.out.println(e.getMessage());
                printMeniuStudenti();
                cmd = reader.nextLine();
            }
        }
    }

    public void run(){
        printMeniuPrincipal();
        StructuraAn anUniversitar=new StructuraAn();
        Integer saptamana=anUniversitar.saptamana();
        Scanner reader = new Scanner(System.in);
        String cmd = reader.nextLine();
        while(!cmd.equals("0")){
            switch (cmd){
                case "1":
                    meniuStudenti();
                    break;
                case "2":
                    meniuTeme(anUniversitar);
                    break;
                case"3":
                    System.out.println("Suntem in saptamana "+saptamana);
                    System.out.println("Adaugati notele pt aceasta saptamana?[y/n]");
                    String yn=reader.nextLine();
                    if(yn.equals("y")) {
                        meniuNote(saptamana);
                    }
                    else{
                        System.out.println("Introduceti sapt. pt care adaugati notele: ");
                        Integer sapt=Integer.parseInt(reader.nextLine());
                        meniuNote(sapt);
                    }
                    break;
                case "4":
                    meniuFiltrari();
                    break;
                default:
                    System.out.println("Comanda invalida!");
                    printMeniuPrincipal();
                    cmd=reader.nextLine();

            }
            printMeniuPrincipal();
            cmd=reader.nextLine();
        }
    }
}
