# 🏥 Sistema Hospital

> Sistema de gerenciamento hospitalar desenvolvido em Java, com interface web e arquitetura MVC.

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Java](https://img.shields.io/badge/Java-21-blue)
![Maven](https://img.shields.io/badge/build-Maven-red)
![Javalin](https://img.shields.io/badge/Javalin-6.3.0-purple)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1.2-green)
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
- 🚨 **Páginas de Erro** — Página 404 para recursos não encontrados e 500 para erros internos
- 🗄️ **Persistência** — Dados salvos em PostgreSQL, mantidos entre reinicializações
- 🐳 **Docker** — Aplicação e banco containerizados, prontos para rodar com um comando

---

## 🛠️ Tecnologias Utilizadas

- **Java 21** — Linguagem principal
- **Maven** — Gerenciamento de dependências e build
- **Javalin 6.3.0** — Servidor web
- **Thymeleaf 3.1.2** — Motor de templates HTML
- **Bootstrap 5.3** — Estilização da interface
- **PostgreSQL 16** — Banco de dados relacional
- **Docker + Docker Compose** — Containerização da aplicação e banco
- **JUnit 5.8** — Testes unitários
- **REST Assured 5.4** — Testes de integração HTTP
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
│   │   ├── Exceptions/         ← Exceções customizadas
│   │   │   ├── ErroDeServidor.java
│   │   │   └── RecursoNaoEncontrado.java
│   │   ├── ConexaoDB.java      ← Configuração da conexão com PostgreSQL
│   │   ├── App.java            ← Ponto de entrada e configuração das rotas
│   │   └── ThymeleafConfig.java← Configuração do motor de templates
│   └── resources/
│       ├── templates/          ← HTMLs do Thymeleaf (View)
│       │   ├── pacientes/
│       │   ├── medicos/
│       │   ├── consultas/
│       │   ├── relatorios/
│       │   ├── erro404.html    ← Página de recurso não encontrado
│       │   └── erro500.html    ← Página de erro interno
│       └── static/
│           ├── css/            ← Estilos
│           └── images/         ← Imagens estáticas
└── test/
    └── java/Testes/
        ├── Gerencia/
        │   ├── HospitalTest.java         ← Testes unitários de paciente
        │   └── GerenciaHospitalTest.java ← Testes unitários de médico, consulta e relatórios
        └── WebTest/
            └── WebTest.java              ← Testes de integração HTTP
```

---

## 🚨 Tratamento de Erros

O sistema possui duas páginas de erro amigáveis:

- **404** — exibida quando um recurso não é encontrado (ex: paciente com código inexistente)
- **500** — exibida quando ocorre um erro interno no servidor ou no banco de dados

As exceções são tratadas de forma centralizada no `App.java` via handlers do Javalin:

```
Erro no Repository
      ↓
Lança RecursoNaoEncontrado ou ErroDeServidor
      ↓
Handler do Javalin captura
      ↓
Renderiza erro404.html ou erro500.html
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

# 3. Copie o arquivo de variáveis de ambiente
cp .env.example .env
# Edite o .env com suas configurações se necessário

# 4. Suba a aplicação e o banco
docker-compose up --build
```

Acesse em: **http://localhost:7070**

Comandos úteis:
```bash
# Subir em segundo plano
docker-compose up -d

# Parar
docker-compose down

# Parar e apagar os dados do banco
docker-compose down -v
```

---

### Localmente (sem Docker)

Pré-requisitos:
- [Java 17+](https://adoptium.net/)
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

# 4. Configure as credenciais no ConexaoDB.java
# src/main/java/SistemaHospital/ConexaoDB.java

# 5. Compile e execute
mvn clean package -DskipTests
java -jar target/Projetos-1.0-SNAPSHOT.jar
```

---

## 🔐 Variáveis de Ambiente

Crie um arquivo `.env` baseado no `.env.example`:

```bash
DB_URL=jdbc:postgresql://localhost:5432/hospital
DB_USER=postgres
DB_PASSWORD=sua_senha_aqui
```

> ⚠️ Nunca suba o `.env` com senhas reais para o GitHub. Ele já está no `.gitignore`.

---

## 🧪 Testes

```bash
# Rodar todos os testes
mvn test
```

O projeto possui dois tipos de testes:

**Testes unitários** — validam a lógica de negócio isolada sem servidor nem banco:
- Cadastro, remoção e pesquisa de pacientes
- Cadastro, remoção e pesquisa de médicos
- Cadastro, cancelamento e filtros de consultas
- Faturamento, médico com mais consultas e paciente mais frequente
- Validação de CPF duplicado e consulta duplicada

**Testes de integração** — sobem o servidor na porta 7071 e fazem requisições HTTP reais:
- GET em todas as páginas (pacientes, médicos, consultas, relatórios)
- POST de cadastro com redirect
- POST de edição
- POST de remoção
- POST de cancelamento de consulta
- Validação de rota inexistente (404)

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
- [x] Páginas de erro amigáveis (404 e 500)
- [x] Exceções customizadas (RecursoNaoEncontrado, ErroDeServidor)
- [ ] CPFs banidos persistidos no banco
- [ ] Camada de Service para separação de responsabilidades
- [ ] Validações de formulário mais detalhadas
- [ ] Autenticação de usuários

---

## 👨‍💻 Autor

Feito por [DioguinhoGamer3D](https://github.com/DioguinhoGamer3D)

---

> ⚠️ Projeto em desenvolvimento — funcionalidades podem mudar.