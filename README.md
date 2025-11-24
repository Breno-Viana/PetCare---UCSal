<h1>Projeto Banco de Dado I</h1>
<h2>Banco Usado: POSTGRESQL</h2>
<h2>Nome do Banco: vet_clinic_system</h2>
<h2>Usuario: postgres</h2>
<h2>Senha: postgres</h2>
<h2>script DDL</h2>

```sql
-- 1. Proprietarios (sem endereço)
CREATE TABLE proprietarios (
    id SERIAL PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    telefone VARCHAR(15),
    CONSTRAINT chk_cpf_valido CHECK (cpf ~ '^[0-9]{11}$')
);

-- 2. Enderecos (1:1 com proprietarios - relacionamento identificador)
CREATE TABLE enderecos (
proprietario_id INTEGER PRIMARY KEY REFERENCES proprietarios(id) ON DELETE CASCADE,
logradouro VARCHAR(100) NOT NULL,
cidade VARCHAR(60) NOT NULL,
uf CHAR(2) NOT NULL
);

-- 3. Veterinarios
CREATE TABLE veterinarios (
id SERIAL PRIMARY KEY,
crmv VARCHAR(20) NOT NULL UNIQUE,
nome VARCHAR(100) NOT NULL,
especialidade VARCHAR(50),
telefone VARCHAR(15) NOT NULL
);

-- 4. Animais
CREATE TABLE animais (
id SERIAL PRIMARY KEY,
proprietario_id INTEGER NOT NULL REFERENCES proprietarios(id) ON DELETE RESTRICT,
nome VARCHAR(80) NOT NULL,
especie VARCHAR(50) NOT NULL,
raca VARCHAR(50),
data_nascimento DATE,
peso NUMERIC(6,3),
CONSTRAINT chk_peso_positivo CHECK (peso > 0)
);

-- 5. Consultas
CREATE TABLE consultas (
id SERIAL PRIMARY KEY,
animal_id INTEGER NOT NULL REFERENCES animais(id) ON DELETE RESTRICT,
veterinario_id INTEGER NOT NULL REFERENCES veterinarios(id),
data_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
diagnostico TEXT,
valor NUMERIC(10,2) NOT NULL,
CONSTRAINT chk_valor_positivo CHECK (valor >= 0)
);

-- Índices recomendados
CREATE INDEX idx_consultas_animal ON consultas(animal_id);
CREATE INDEX idx_consultas_veterinario ON consultas(veterinario_id);
CREATE INDEX idx_consultas_data ON consultas(data_hora);
CREATE INDEX idx_animais_proprietario ON animais(proprietario_id);
