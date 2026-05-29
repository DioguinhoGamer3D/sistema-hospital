# 🏥 Sistema Hospital

> Sistema de gerenciamento hospitalar desenvolvido em Java, com interface web e arquitetura MVC.

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Maven](https://img.shields.io/badge/build-Maven-red)
![Javalin](https://img.shields.io/badge/Javalin-6.3.0-purple)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1.2-green)

---

## 📋 Sobre o Projeto

O **Sistema Hospital** é uma aplicação web desenvolvida em Java que centraliza o gerenciamento de informações hospitalares. O sistema permite registrar e consultar dados de pacientes, médicos e consultas de forma organizada, através de uma interface web com tema hospitalar.

---

## ✨ Funcionalidades

- 👤 **Cadastro de Pacientes** — Registro, edição, remoção e busca por CPF
- 👨‍⚕️ **Controle de Médicos** — Gerenciamento da equipe médica com busca por especialidade
- 📅 **Registro de Consultas** — Controle e cancelamento de consultas realizadas
- 📊 **Relatórios** — Faturamento total, médico com mais consultas e paciente mais frequente
- 🚫 **Banimento de CPF** — Bloqueio de CPFs indesejados

---

## 🛠️ Tecnologias Utilizadas

- **Java 17** — Linguagem principal
- **Maven** — Gerenciamento de dependências e build
- **Javalin 6.3.0** — Servidor web
- **Thymeleaf 3.1.2** — Motor de templates HTML
- **Bootstrap 5.3** — Estilização da interface
- **JUnit 5** — Testes unitários
- **REST Assured 5.4** — Testes de integração
- **IntelliJ IDEA** — IDE de desenvolvimento

---

## 🏗️ Arquitetura

O projeto segue o padrão **MVC (Model-View-Controller)**:

```
src/
├── main/
│   ├── java/SistemaHospital/
│   │   ├── Controller/         ← Recebe requisições HTTP e chama a lógica de negócio
│   │   │   ├── PacienteController.java
│   │   │   ├── MedicoController.java
│   │   │   ├── ConsultaController.java
│   │   │   └── RelatorioController.java
│   │   ├── Model/              ← Classes de dados (Paciente, Medico, Consulta)
│   │   ├── Gerencias/          ← Lógica de negócio (GerenciaHospital)
│   │   ├── Enum/               ← Enumerações (Sexo, TipoEntidade)
│   │   ├── Exceptions/         ← Exceções customizadas
│   │   ├── App.java            ← Ponto de entrada e configuração das rotas
│   │   └── ThymeleafConfig.java← Configuração do motor de templates
│   └── resources/
│       ├── templates/          ← HTMLs do Thymeleaf (View)
│       │   ├── pacientes/
│       │   ├── medicos/
│       │   ├── consultas/
│       │   └── relatorios/
│       └── static/css/         ← Arquivos estáticos
└── test/
    └── java/Testes/
        ├── Gerencia/
        │   ├── HospitalTest.java       ← Testes unitários originais
        │   └── GerenciaHospitalTest.java← Testes unitários expandidos
        └── WebTest.java               ← Testes de integração HTTP
```

---

## 🚀 Como Rodar Localmente

### Pré-requisitos

- [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3.8+](https://maven.apache.org/download.cgi)

### Passos

```bash
# 1. Clone o repositório
git clone https://github.com/DioguinhoGamer3D/sistema-hospital.git

# 2. Entre na pasta do projeto
cd sistema-hospital

# 3. Compile o projeto
mvn clean install

# 4. Execute a aplicação
mvn exec:java
```

Acesse em: **http://localhost:7070**

---

## 🧪 Testes

```bash
# Rodar todos os testes
mvn test
```

O projeto possui dois tipos de testes:

- **Testes unitários** — validam a lógica de negócio isolada (cadastro, remoção, faturamento, etc.)
- **Testes de integração** — sobem o servidor e fazem requisições HTTP reais para validar as rotas

---

## 🗺️ Roadmap

- [x] Cadastro de pacientes, médicos e consultas
- [x] Interface web com Javalin e Thymeleaf
- [x] Arquitetura MVC
- [x] Estilização com Bootstrap (tema hospitalar)
- [x] Relatórios (faturamento, top médicos, top pacientes)
- [x] Testes unitários e de integração
- [ ] Persistência com PostgreSQL
- [ ] Autenticação de usuários
- [ ] Exportação de relatórios

---

## 👨‍💻 Autor

Feito por [DioguinhoGamer3D](https://github.com/DioguinhoGamer3D)

---

> ⚠️ Projeto em desenvolvimento — funcionalidades podem mudar.