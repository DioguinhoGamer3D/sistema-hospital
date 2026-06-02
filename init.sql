CREATE TABLE IF NOT EXISTS pacientes (
    cod_p    SERIAL PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    cpf      VARCHAR(20)  NOT NULL UNIQUE,
    sexo     VARCHAR(20)  NOT NULL,
    idade    INT          NOT NULL,
    convenio VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS medicos (
    cod_m         SERIAL PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    cpf           VARCHAR(20)  NOT NULL UNIQUE,
    sexo          VARCHAR(20)  NOT NULL,
    especialidade VARCHAR(100) NOT NULL,
    turno         VARCHAR(20)  NOT NULL,
    salario       DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS consultas (
    cod_c       SERIAL PRIMARY KEY,
    cod_p       INT  NOT NULL REFERENCES pacientes(cod_p),
    cod_m       INT  NOT NULL REFERENCES medicos(cod_m),
    data        DATE NOT NULL,
    diagnostico TEXT NOT NULL,
    preco       DOUBLE PRECISION NOT NULL
);