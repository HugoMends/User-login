# user-login API REST (Sistema de Cadastro de Usuários e Endereços)

![Badge de Status - Exemplo](https://img.shields.io/badge/Status-Concluído%20Segurança%20e%20CRUD-blue)

---

## 📄 Sobre o Projeto

Este projeto é uma API REST desenvolvida em **Spring Boot** para gerenciar o cadastro de usuários e seus respectivos endereços. O foco principal é demonstrar a implementação de um backend robusto, com segurança, validação e boas práticas de arquitetura.

Este é um projeto em constante evolução, servindo como meu portfólio principal para demonstrar habilidades em desenvolvimento backend Java.

---

## ✨ Funcionalidades Implementadas

-   **CRUD de Usuários e Endereços:** Gerenciamento completo de usuários e seus múltiplos endereços.
-   **Segurança Robusta (com Spring Security e JWT):**
    -   **Autenticação:** Login de usuário e geração de tokens JWT.
    -   **Autorização por Perfil:** Controle de acesso a endpoints com base em roles (`ADMIN`, `USER`).
    -   **Criptografia:** Senhas armazenadas com segurança usando **BCrypt**.
-   **Tratamento de Exceções Global:**
    -   Retorno de erros padronizados para recursos não encontrados (`404 Not Found`), validação (`422 Unprocessable Entity`) e conflitos de dados (`409 Conflict`).
-   **Validação de Dados de Entrada:**
    -   Garantia da integridade dos dados com **Bean Validation**, incluindo regras para e-mail, tamanho e complexidade de senhas.
-   **Estrutura e Boas Práticas:**
    -   Organização em camadas (Controller, Service, Repository).
    -   Uso do padrão **DTO** para entrada e saída de dados.
    -   Modelagem de relacionamentos `1:N` e `N:N` entre as entidades.

---

## 🛠️ Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3.x**
* **Spring Data JPA** e **Hibernate**
* **Spring Security** e **JWT** (biblioteca `com.auth0:java-jwt`)
* **H2 Database** (banco de dados em memória)
* **Lombok** e **Bean Validation**
* **Maven** (gerenciador de dependências)

---

## 🚀 Como Rodar o Projeto Localmente

Siga estas etapas para configurar e executar a API em seu ambiente local:

1.  **Pré-requisitos:**
    * Java Development Kit (JDK) 21, Maven e Git instalados.
2.  **Clonar o Repositório:**
    ```bash
    git clone https://github.com/HugoMends/user-login.git
    cd user-login
    ```
3.  **Configuração de Segredos:**
    * Configure a variável de ambiente `JWT_SECRET_KEY` em seu sistema com a chave secreta do projeto.
4.  **Compilar e Executar:**
    * No terminal, na pasta do projeto, execute:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
5.  A API estará rodando em `http://localhost:8080`.

---

## 💡 Endpoints da API

A API base está acessível em `http://localhost:8080`. Todos os endpoints retornam e esperam JSON.

### **Autenticação (`/auth`)**

| Método HTTP | URL | Descrição |
| :---------- | :-- | :---------------------------------- |
| `POST` | `/auth/login` | Autentica um usuário e retorna um token JWT. |

### **Recursos Protegidos**

| Método HTTP | URL | Descrição | Roles Necessárias |
| :---------- | :-- | :-------- | :---------------- |
| `GET`       | `/users` | Lista usuários (todos). | `qualquer usuário autenticado` |
| `POST`      | `/users` | Cria um novo usuário. | `ADMIN` |
| `PUT`       | `/users/{id}` | Atualiza um usuário. | `ADMIN` |
| `DELETE`    | `/users/{id}` | Exclui um usuário. | `ADMIN` |
| `GET`       | `/users/{userId}/addresses` | Lista endereços de um usuário. | `qualquer usuário autenticado` |
| `POST`      | `/users/{userId}/addresses` | Cria um endereço para um usuário. | `ADMIN` |
| `PUT`       | `/users/{userId}/addresses/{id}` | Atualiza um endereço. | `ADMIN` |
| `DELETE`    | `/users/{userId}/addresses/{id}` | Exclui um endereço. | `ADMIN` |

---

## 🛣️ Melhorias Futuras (Roadmap)

* **Testes:** Implementação de testes unitários e de integração.
* **Migrações de Banco de Dados:** Utilização de Flyway ou Liquibase.
* **Dockerização e Deploy:** Criação de `Dockerfile` e pipeline de CI/CD.
* **Outras Entidades e Relacionamentos:** Adição de novas entidades (`Product`, `Order`).

---

## 🤝 Autor

* **Hugo** - [https://github.com/HugoMends]

---