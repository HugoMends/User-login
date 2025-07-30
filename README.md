# user-login API REST (Sistema de Cadastro de Usuários e Endereços)

![Badge de Status - Exemplo](https://img.shields.io/badge/Status-Concluído%20CRUD%20Básico-brightgreen)
---

## 📄 Sobre o Projeto

Este projeto é uma API REST desenvolvida em **Spring Boot** para gerenciar o cadastro de usuários e seus respectivos endereços. Foi construído com foco em boas práticas de desenvolvimento backend, incluindo a separação de camadas (Controller, Service, Repository), o uso de DTOs para entrada e saída de dados, e um tratamento de exceções robusto.

Este é um projeto em constante evolução, servindo como meu portfólio principal para demonstrar habilidades em desenvolvimento backend Java.

---

## ✨ Funcionalidades Principais (CRUD)

-   **Usuários:**
    -   Cadastro de novos usuários.
    -   Busca de usuário por ID.
    -   Listagem paginada de todos os usuários.
    -   Atualização de dados de usuário.
    -   Exclusão de usuário.
-   **Endereços (relacionamento 1:N com Usuário):**
    -   Cadastro de endereços para um usuário específico.
    -   Busca de todos os endereços de um usuário.
    -   Busca de um endereço específico por ID.
    -   Atualização de dados de endereço.
    -   Exclusão de endereço.
-   **Tratamento de Exceções Global:**
    -   Retorno de erros padronizados para recursos não encontrados (`404 Not Found`).
    -   Retorno de erros padpadronizados para falhas de validação (`422 Unprocessable Entity`) com detalhes de campo.
    -   Retorno de erros padronizados para violações de integridade de dados (ex: e-mail duplicado - `409 Conflict`).

---

## 🛠️ Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3.x**
* **Spring Data JPA** (para persistência de dados e acesso ao banco)
* **Hibernate** (implementação da especificação JPA)
* **H2 Database** (banco de dados em memória para desenvolvimento e testes)
* **Lombok** (para redução de código boilerplate)
* **Spring Boot DevTools** (para produtividade no desenvolvimento)
* **Bean Validation (Jakarta Validation)** (para validação de dados de entrada)
* **Maven** (gerenciador de dependências e build)

---

## 🚀 Como Rodar o Projeto Localmente

Siga estas etapas para configurar e executar a API em seu ambiente local:

1.  **Pré-requisitos:**
    * Java Development Kit (JDK) 21 instalado.
    * Maven instalado.
    * Uma IDE como Spring Tool Suite 4 (STS4) ou IntelliJ IDEA.
    * Git instalado.
    * Postman, Insomnia ou cURL para testar os endpoints.

2.  **Clonar o Repositório:**
    ```bash
    git clone [https://github.com/seuusuario/user-login.git](https://github.com/seuusuario/user-login.git) # Substitua 'seuusuario' pelo seu GitHub username
    cd user-login
    ```

3.  **Configuração do Banco de Dados (H2 em memória):**
    * O projeto utiliza o H2 Database em memória, que não requer configuração externa.
    * As configurações estão em `src/main/resources/application.properties`.
    * O console do H2 estará disponível em `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:userdb`, User: `sa`, Password: ` `).

4.  **Compilar e Executar:**
    * **Via IDE (STS4/IntelliJ):**
        * Importe o projeto como um projeto Maven existente.
        * Execute a classe `UserLoginApplication` (com a anotação `@SpringBootApplication`).
    * **Via Terminal:**
        ```bash
        mvn clean package
        java -jar target/user-login-0.0.1-SNAPSHOT.jar # O nome do JAR pode variar
        ```
    * A API estará rodando em `http://localhost:8080`.

---

## 💡 Endpoints da API

A API base está acessível em `http://localhost:8080`. Todos os endpoints retornam e esperam JSON.

### **Recurso: Usuários (`/users`)**

| Método HTTP | URL | Descrição | Corpo da Requisição (Exemplo `UserRequestDTO`) | Corpo da Resposta (Exemplo `UserResponseDTO`) |
| :---------- | :-- | :-------- | :--------------------------------------------- | :--------------------------------------------- |
| `POST`      | `/users` | Cria um novo usuário | `{ "name": "João", "email": "joao@email.com", "password": "Senha@123" }` | `201 Created` + `{ "id": 1, "name": "João", ... }` |
| `GET`       | `/users` | Lista todos os usuários (paginado) | `Nenhum` | `200 OK` + `Page<UserResponseDTO>` |
| `GET`       | `/users/{id}` | Busca usuário por ID | `Nenhum` | `200 OK` + `UserResponseDTO` |
| `PUT`       | `/users/{id}` | Atualiza usuário por ID | `{ "name": "João Silva", "email": "joao.silva@email.com", "password": "NovaSenha#456" }` | `200 OK` + `UserResponseDTO` atualizado |
| `DELETE`    | `/users/{id}` | Exclui usuário por ID | `Nenhum` | `204 No Content` |

### **Recurso: Endereços (`/users/{userId}/addresses`)**

| Método HTTP | URL | Descrição | Corpo da Requisição (Exemplo `AddressRequestDTO`) | Corpo da Resposta (Exemplo `AddressResponseDTO`) |
| :---------- | :-- | :-------- | :------------------------------------------------ | :------------------------------------------------- |
| `POST`      | `/users/{userId}/addresses` | Cria um endereço para um usuário | `{ "street": "Rua A", "number": "123", "complement": "Apto 1", "neighborhood": "Centro", "city": "Cidade", "state": "UF", "zipCode": "12345678" }` | `201 Created` + `{ "id": 1, "street": "Rua A", ..., "userId": 1 }` |
| `GET`       | `/users/{userId}/addresses` | Lista todos os endereços do usuário | `Nenhum` | `200 OK` + `List<AddressResponseDTO>` |
| `GET`       | `/users/{userId}/addresses/{addressId}` | Busca endereço por ID | `Nenhum` | `200 OK` + `AddressResponseDTO` |
| `PUT`       | `/users/{userId}/addresses/{addressId}` | Atualiza endereço por ID | `{ "street": "Rua B", "number": "456", ... }` | `200 OK` + `AddressResponseDTO` atualizado |
| `DELETE`    | `/users/{userId}/addresses/{addressId}` | Exclui endereço por ID | `Nenhum` | `204 No Content` |

---

## 🛣️ Melhorias Futuras (Roadmap)

Este projeto está planejado para ser expandido com as seguintes funcionalidades:

* **Spring Security:** Implementação de autenticação (Login com JWT) e autorização por perfil (User-Role).
* **Tratamento de Exceções Avançado:** Criação de um `CustomExceptionHandler` mais granular.
* **Testes:** Implementação de testes unitários e de integração.
* **Migrações de Banco de Dados:** Utilização de Flyway ou Liquibase para controle de versão do schema do DB.
* **Dockerização:** Criação de `Dockerfile` e `docker-compose` para containerização da aplicação e do DB.
* **Deploy (CI/CD):** Configuração de um pipeline de CI/CD (ex: GitHub Actions) para deploy automatizado em um ambiente de nuvem.
* **Outras Entidades e Relacionamentos:** Adição de novas entidades (ex: `Product`, `Order`) e exploração de relacionamentos N:N.

---

## 🤝 Autor

* **[Hugo Mendes]** - [https://github.com/HugoMends]