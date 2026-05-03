package BancoDeDados.AulaPratica01;

import BancoDeDados.AulaPratica01.Enum.Sexo;

import java.time.LocalDate;

public class SistemaHospital {
    public static void main(String[] args) {

        GerenciaHospital sistema = new GerenciaHospital();

        Paciente p1 = sistema.cadastrarPaciente("Maria", "111",
                Sexo.FEMININO, 25,"Unimed");
        Paciente p2 = sistema.cadastrarPaciente("Carlos","222",
                Sexo.MASCULINO, 18,"Unimed");

        Medico m1 = sistema.cadastrarMedico("José","555", Sexo.MASCULINO,
                "Geral" , "M", 3000);
        Medico m2 = sistema.cadastrarMedico("Nicolau","777" ,Sexo.MASCULINO,
                "Pediatra", "M", 4000);
        Medico m3 = sistema.cadastrarMedico("Raul","666" , Sexo.MASCULINO,
                "Oftamologista", "T", 1250);

        Consulta c1 = sistema.cadastrarConsulta(p1,m1,
                LocalDate.of(2026,1,23),
                "Gripe", 45.5);
        Consulta c2 = sistema.cadastrarConsulta(p2,m2,
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

        System.out.println("Todos os Pacientes: "+sistema.todosPacientes());
        System.out.println("Todos os Médicos: "+sistema.todosMedicos());
        System.out.println("Todas as Consultas"+sistema.todasConsultas());

        System.out.println("Consultas por Paciente: "+sistema.consultasPorPaciente(1));
        System.out.println("Consultas por Médico: "+ sistema.consultasPorMedico(2));
        System.out.println("Consultas por Data: "+ sistema.consultasPorData(LocalDate.of(2026, 2, 9)));

        System.out.println("Paciente por CPF: "+ sistema.buscarPacientePorCpf("111"));

        System.out.println(sistema.consultaJaExiste(p1,m1,LocalDate.of(2026, 1, 23)));
    }
}
