# 🏥 Sistema Hospital

> Sistema de gerenciamento hospitalar desenvolvido em Java, com interface web e arquitetura MVC.

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Java](https://img.shields.io/badge/Java-21-blue)
![Maven](https://img.shields.io/badge/build-Maven-red)
![Javalin](https://img.shields.io/badge/Javalin-6.7.0-purple)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1.5-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-316192)
![Docker](https://img.shields.io/badge/Docker-ready-2496ED)

---

## 📋 Sobre o Projeto

O **Sistema Hospital** é uma aplicação web desenvolvida em Java que centraliza o gerenciamento de informações hospitalares. O sistema permite registrar e consultar dados de pacientes, médicos e consultas de forma organizada, através de uma interface web com tema hospitalar azul.

---

## ✨ Funcionalidades

- 👤 **Cadastro de Pacientes** — Registro, edição, remoção e busca por nome ou convênio
- 👨‍⚕️ **Controle de Médicos** — Gerenciamento da equipe médica com busca por nome, especialidade ou turno
- 📅 **Registro de Consultas** — Controle e cancelamento de consultas com filtro por paciente, médico ou data
- 📊 **Relatórios** — Faturamento total, médico com mais consultas e paciente mais frequente
- 🗄️ **Persistência** — Dados salvos em PostgreSQL, mantidos entre reinicializações
- 🐳 **Docker** — Aplicação e banco containerizados, prontos para rodar com um comando

---

## 🛠️ Tecnologias Utilizadas

- **Java 21** — Linguagem principal
- **Maven** — Gerenciamento de dependências e build
- **Javalin 6.7.0** — Servidor web
- **Thymeleaf 3.1.5** — Motor de templates HTML
- **Bootstrap 5.3** — Estilização da interface
- **PostgreSQL 16** — Banco de dados relacional
- **Docker + Docker Compose** — Containerização da aplicação e banco
- **JUnit 5.11** — Testes unitários
- **REST Assured 5.4** — Testes de integração HTTP
- **Log4j 2** — Sistema de logs
- **IntelliJ IDEA** — IDE de desenvolvimento

---

## 🏗️ Arquitetura

O projeto segue o padrão **MVC (Model-View-Controller)** com camada de repositório para acesso ao banco:

```
src/
├── main/
│   ├── java/SistemaHospital/
│   │   ├── Controller/         ← Recebe requisições HTTP e coordena a resposta
│   │   │   ├── PacienteController.java
│   │   │   ├── MedicoController.java
│   │   │   ├── ConsultaController.java
│   │   │   └── RelatorioController.java
│   │   ├── Repository/         ← Acesso ao banco de dados (SQL)
│   │   │   ├── PacienteRepository.java
│   │   │   ├── MedicoRepository.java
│   │   │   └── ConsultaRepository.java
│   │   ├── Model/              ← Classes de dados (Paciente, Medico, Consulta)
│   │   ├── Enum/               ← Enumerações (Sexo)
│   │   ├── ConexaoDB.java      ← Configuração da conexão com PostgreSQL
│   │   ├── App.java            ← Ponto de entrada e configuração das rotas
│   │   └── ThymeleafConfig.java← Configuração do motor de templates
│   └── resources/
│       ├── templates/          ← HTMLs do Thymeleaf (View)
│       │   ├── pacientes/
│       │   ├── medicos/
│       │   ├── consultas/
│       │   └── relatorios/
│       ├── static/css/         ← Arquivos estáticos
│       └── log4j2.xml          ← Configuração de logs
└── test/
    └── java/Testes/
        ├── Gerencia/
        │   └── GerenciaHospitalTest.java ← Testes unitários
        └── WebTest.java                  ← Testes de integração HTTP
```

---

## 🚀 Como Rodar

### Com Docker (recomendado)

Pré-requisito: [Docker Desktop](https://www.docker.com/products/docker-desktop)

```bash
# 1. Clone o repositório
git clone https://github.com/DioguinhoGamer3D/sistema-hospital.git

# 2. Entre na pasta do projeto
cd sistema-hospital

# 3. Suba a aplicação e o banco
docker-compose up --build
```

Acesse em: **http://localhost:7070**

Para parar:
```bash
docker-compose down
```

---

### Localmente (sem Docker)

Pré-requisitos:
- [Java 21+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [PostgreSQL 16+](https://www.postgresql.org/download/)

```bash
# 1. Clone o repositório
git clone https://github.com/DioguinhoGamer3D/sistema-hospital.git

# 2. Entre na pasta do projeto
cd sistema-hospital

# 3. Crie o banco de dados no PostgreSQL
psql -U postgres -c "CREATE DATABASE hospital;"
psql -U postgres -d hospital -f init.sql

# 4. Configure a senha no ConexaoDB.java
# Edite src/main/java/SistemaHospital/ConexaoDB.java

# 5. Compile e execute
mvn clean package -DskipTests
java -jar target/sistema-hospital-1.0-jar-with-dependencies.jar
```

---

## 🧪 Testes

```bash
# Rodar todos os testes
mvn test
```

O projeto possui dois tipos de testes:

- **Testes unitários** — validam a lógica de negócio isolada (cadastro, remoção, faturamento, etc.)
- **Testes de integração** — sobem o servidor na porta 7071 e fazem requisições HTTP reais para validar todas as rotas

---

## 🗺️ Roadmap

- [x] Cadastro de pacientes, médicos e consultas
- [x] Interface web com Javalin e Thymeleaf
- [x] Arquitetura MVC com camada de repositório
- [x] Estilização com Bootstrap (tema hospitalar azul)
- [x] Filtros por nome, especialidade, turno, data e mais
- [x] Relatórios (faturamento, top médicos, top pacientes)
- [x] Persistência com PostgreSQL
- [x] Containerização com Docker e Docker Compose
- [x] Testes unitários e de integração
- [ ] CPFs banidos persistidos no banco
- [ ] Validações de formulário mais detalhadas
- [ ] Página de erro amigável
- [ ] Autenticação de usuários

---

## 👨‍💻 Autor

Feito por [DioguinhoGamer3D](https://github.com/DioguinhoGamer3D)

---

> ⚠️ Projeto em desenvolvimento — funcionalidades podem mudar.