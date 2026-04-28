package BancoDeDados.AulaPratica01;

import BancoDeDados.AulaPratica01.Enum.Sexo;

import java.time.LocalDate;

public class SistemaHospital {
    public static void main(String[] args) {

        GerenciaHospital sistema = new GerenciaHospital();

        Pacientes p1 = sistema.cadastrarPaciente("Maria", "111",
                Sexo.FEMININO, 25,"Unimed");
        Pacientes p2 = sistema.cadastrarPaciente("Carlos","222",
                Sexo.MASCULINO, 18,"Unimed");

        Medicos m1 = sistema.cadastrarMedico("José","555", Sexo.MASCULINO,
                "Geral" , "M", 3000);
        Medicos m2 = sistema.cadastrarMedico("Nicolau","777" ,Sexo.MASCULINO,
                "Pediatra", "M", 4000);
        Medicos m3 = sistema.cadastrarMedico("Raul","666" , Sexo.MASCULINO,
                "Oftamologista", "T", 1250);

        Consultas c1 = sistema.cadastrarConsulta(p1,m1,
                LocalDate.of(2026,1,23),
                "Gripe", 45.5);
        Consultas c2 = sistema.cadastrarConsulta(p2,m2,
                LocalDate.of(2026,2,9),
                "Dor de cabeca", 50);

        System.out.println("código do p1= " + p1.getCodP());
        System.out.println(sistema.pesquisarPaciente(1));
        System.out.println("código do p2= " + p2.getCodP());
        System.out.println(sistema.pesquisarPaciente(2));

        System.out.println("código do m1= " + m1.getCodM());
        System.out.println(sistema.pesquisarMedico(1));
        System.out.println("código do m2= " + m2.getCodM());
        System.out.println(sistema.pesquisarMedico(2));
        System.out.println("código do m3= " + m3.getCodM());
        System.out.println(sistema.pesquisarMedico(3));

        System.out.println("código do c1= " + c1.getCodC());
        System.out.println(sistema.pesquisarConsulta(1));
        System.out.println("código do c2= " + c2.getCodC());
        System.out.println(sistema.pesquisarConsulta(2));

        System.out.println(sistema.todosPacientes());
        System.out.println(sistema.todosMedicos());
        System.out.println(sistema.todasConsultas());
    }
}
