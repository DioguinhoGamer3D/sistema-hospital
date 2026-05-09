package SistemaHospital;

import SistemaHospital.Classes.Consulta;
import SistemaHospital.Classes.Medico;
import SistemaHospital.Classes.Paciente;
import SistemaHospital.Enum.Sexo;
import SistemaHospital.Exceptions.HospitalException;
import SistemaHospital.Gerencias.GerenciaHospital;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class SistemaHospital {

    static GerenciaHospital hospital = new GerenciaHospital();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== SISTEMA HOSPITAL =====");
            System.out.println("1. Pacientes");
            System.out.println("2. Médicos");
            System.out.println("3. Consultas");
            System.out.println("4. Relatórios");
            System.out.println("X. Fechar programa");
            System.out.print("Escolha: ");
            String opcao = sc.nextLine().trim();

            switch (opcao.toLowerCase()) {
                case "1" -> menuPacientes();
                case "2" -> menuMedicos();
                case "3" -> menuConsultas();
                case "4" -> menuRelatorios();
                case "x" -> {
                    System.out.println("Encerrando sistema...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    // ===================== MENU DOS PACIENTES =====================

    static void menuPacientes() {
        while (true) {
            System.out.println("\n--- PACIENTES ---");
            System.out.println("1. Cadastrar paciente");
            System.out.println("2. Buscar por código");
            System.out.println("3. Buscar por CPF");
            System.out.println("4. Listar todos");
            System.out.println("5. Remover paciente");
            System.out.println("6. Banir CPF");
            System.out.println("X. Voltar");
            System.out.print("Escolha: ");
            String opcao = sc.nextLine().trim();

            switch (opcao.toLowerCase()) {
                case "1" -> cadastrarPaciente();
                case "2" -> buscarPacientePorCodigo();
                case "3" -> buscarPacientePorCpf();
                case "4" -> listarPacientes();
                case "5" -> removerPaciente();
                case "6" -> banirCpf();
                case "x" -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    static void cadastrarPaciente() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        Sexo sexo = lerSexo();
        System.out.print("Idade: ");
        int idade = lerInt();
        System.out.print("Convênio: ");
        String convenio = sc.nextLine();

        try {
            Paciente p = hospital.cadastrarPaciente(nome, cpf, sexo, idade, convenio);
            System.out.println("Paciente cadastrado! Código: " + p.getCodP());
        } catch (HospitalException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void buscarPacientePorCodigo() {
        System.out.print("Código do paciente: ");
        int cod = lerInt();
        try {
            System.out.println(hospital.pesquisarPaciente(cod));
        } catch (HospitalException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void buscarPacientePorCpf() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        try {
            System.out.println(hospital.buscarPacientePorCpf(cpf));
        } catch (HospitalException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void listarPacientes() {
        List<Paciente> lista = hospital.todosPacientes();
        if (lista.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    static void removerPaciente() {
        System.out.print("Código do paciente: ");
        int cod = lerInt();
        try {
            hospital.removerPaciente(cod);
            System.out.println("Paciente removido.");
        } catch (HospitalException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void banirCpf() {
        System.out.print("CPF a banir: ");
        String cpf = sc.nextLine();
        hospital.naoDeNemAgua(cpf);
        System.out.println("CPF banido com sucesso.");
    }

    // ===================== MENU OS MÉDICOS =====================

    static void menuMedicos() {
        while (true) {
            System.out.println("\n--- MÉDICOS ---");
            System.out.println("1. Cadastrar médico");
            System.out.println("2. Buscar por código");
            System.out.println("3. Buscar por especialidade");
            System.out.println("4. Listar todos");
            System.out.println("5. Remover médico");
            System.out.println("X. Voltar");
            System.out.print("Escolha: ");
            String opcao = sc.nextLine().trim();

            switch (opcao.toLowerCase()) {
                case "1" -> cadastrarMedico();
                case "2" -> buscarMedicoPorCodigo();
                case "3" -> buscarPorEspecialidade();
                case "4" -> listarMedicos();
                case "5" -> removerMedico();
                case "x" -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    static void cadastrarMedico() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        Sexo sexo = lerSexo();
        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();
        System.out.print("Turno (manhã/tarde/noite): ");
        String turno = sc.nextLine();
        System.out.print("Salário: ");
        double salario = lerDouble();

        try {
            Medico m = hospital.cadastrarMedico(nome, cpf, sexo, especialidade, turno, salario);
            System.out.println("Médico cadastrado! Código: " + m.getCodM());
        } catch (HospitalException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void buscarMedicoPorCodigo() {
        System.out.print("Código do médico: ");
        int cod = lerInt();
        try {
            System.out.println(hospital.pesquisarMedico(cod));
        } catch (HospitalException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void buscarPorEspecialidade() {
        System.out.print("Especialidade: ");
        String esp = sc.nextLine();
        List<Medico> lista = hospital.buscarPorEspecialidade(esp);
        if (lista.isEmpty()) {
            System.out.println("Nenhum médico encontrado.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    static void listarMedicos() {
        List<Medico> lista = hospital.todosMedicos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum médico cadastrado.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    static void removerMedico() {
        System.out.print("Código do médico: ");
        int cod = lerInt();
        try {
            hospital.removerMedico(cod);
            System.out.println("Médico removido.");
        } catch (HospitalException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // ===================== MENU DAS CONSULTAS =====================

    static void menuConsultas() {
        while (true) {
            System.out.println("\n--- CONSULTAS ---");
            System.out.println("1. Cadastrar consulta");
            System.out.println("2. Buscar por código");
            System.out.println("3. Listar por paciente");
            System.out.println("4. Listar por médico");
            System.out.println("5. Listar por data");
            System.out.println("6. Cancelar consulta");
            System.out.println("7. Listar todas");
            System.out.println("X. Voltar");
            System.out.print("Escolha: ");
            String opcao = sc.nextLine().trim();

            switch (opcao.toLowerCase()) {
                case "1" -> cadastrarConsulta();
                case "2" -> buscarConsultaPorCodigo();
                case "3" -> consultasPorPaciente();
                case "4" -> consultasPorMedico();
                case "5" -> consultasPorData();
                case "6" -> cancelarConsulta();
                case "7" -> listarConsultas();
                case "x" -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    static void cadastrarConsulta() {
        System.out.print("Código do paciente: ");
        int codP = lerInt();
        System.out.print("Código do médico: ");
        int codM = lerInt();
        System.out.print("Data (AAAA-MM-DD): ");
        LocalDate data = lerData();
        if (data == null) return;
        System.out.print("Diagnóstico: ");
        String diagnostico = sc.nextLine();
        System.out.print("Preço: ");
        double preco = lerDouble();

        try {
            Paciente p = hospital.pesquisarPaciente(codP);
            Medico m = hospital.pesquisarMedico(codM);
            Consulta c = hospital.cadastrarConsulta(p, m, data, diagnostico, preco);
            System.out.println("Consulta cadastrada! Código: " + c.getCodC());
        } catch (HospitalException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void buscarConsultaPorCodigo() {
        System.out.print("Código da consulta: ");
        int cod = lerInt();
        try {
            System.out.println(hospital.pesquisarConsulta(cod));
        } catch (HospitalException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void consultasPorPaciente() {
        System.out.print("Código do paciente: ");
        int cod = lerInt();
        try {
            hospital.consultasPorPaciente(cod).forEach(System.out::println);
        } catch (HospitalException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void consultasPorMedico() {
        System.out.print("Código do médico: ");
        int cod = lerInt();
        try {
            hospital.consultasPorMedico(cod).forEach(System.out::println);
        } catch (HospitalException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void consultasPorData() {
        System.out.print("Data (AAAA-MM-DD): ");
        LocalDate data = lerData();
        if (data == null) return;
        try {
            hospital.consultasPorData(data).forEach(System.out::println);
        } catch (HospitalException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void cancelarConsulta() {
        System.out.print("Código da consulta: ");
        int cod = lerInt();
        try {
            hospital.cancelarConsulta(cod);
            System.out.println("Consulta cancelada.");
        } catch (HospitalException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void listarConsultas() {
        List<Consulta> lista = hospital.todasConsultas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma consulta cadastrada.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    // ===================== MENU DE RELATÓRIOS =====================

    static void menuRelatorios() {
        while (true) {
            System.out.println("\n--- RELATÓRIOS ---");
            System.out.println("1. Faturamento total");
            System.out.println("2. Faturamento por médico");
            System.out.println("3. Médico com mais consultas");
            System.out.println("4. Paciente mais frequente");
            System.out.println("X. Voltar");
            System.out.print("Escolha: ");
            String opcao = sc.nextLine().trim();

            switch (opcao.toLowerCase()) {
                case "1" -> System.out.printf("Faturamento total: R$ %.2f%n", hospital.calcularFaturamento());
                case "2" -> {
                    System.out.print("Código do médico: ");
                    int cod = lerInt();
                    try {
                        System.out.printf("Faturamento: R$ %.2f%n", hospital.faturamentoPorMedico(cod));
                    } catch (HospitalException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                case "3" -> hospital.medicoComMaisConsultas().forEach(System.out::println);
                case "4" -> hospital.pacienteMaisFrequente().forEach(System.out::println);
                case "x" -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    // ===================== UTILITÁRIOS =====================

    static int lerInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido, tente novamente: ");
            }
        }
    }

    static double lerDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido, tente novamente: ");
            }
        }
    }

    static LocalDate lerData() {
        try {
            return LocalDate.parse(sc.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Use o formato AAAA-MM-DD.");
            return null;
        }
    }

    static Sexo lerSexo() {
        System.out.println("Sexo: 1-Masculino  2-Feminino  3-Não binário  4-Prefiro não dizer");
        System.out.print("Escolha: ");
        return switch (lerInt()) {
            case 1 -> Sexo.MASCULINO;
            case 2 -> Sexo.FEMININO;
            case 3 -> Sexo.NAO_BINARIO;
            default -> Sexo.PREFIRO_NAO_DIZER;
        };
    }
}