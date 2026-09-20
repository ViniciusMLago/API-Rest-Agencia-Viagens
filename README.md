# Agência de Viagens - API REST de Destinos

## Visão Geral do Problema
A agência de viagens está a modernizar os seus serviços digitais e necessita de uma API REST que permita a integração com aplicações de turismo, parceiros comerciais e futuras plataformas. Na primeira versão focou-se na estruturação e no gerenciamento de destinos de viagem (operações CRUD, pesquisa e sistema de avaliação). Na segunda fase, a aplicação evoluiu para incluir persistência definitiva numa base de dados relacional e mecanismos robustos de segurança e controle de acessos.

## Arquitetura Proposta
A aplicação foi estruturada utilizando a **Arquitetura em Camadas** (Layered Architecture). Essa estrutura é composta por camadas distintas com responsabilidades claras e separadas, favorecendo o baixo acoplamento:

1. **Model (Entity):** Aqui residem as entidades ou modelos de dados, representando as estruturas de dados utilizadas pela aplicação. Contém as classes `Destino` e `Usuario`, refletindo as tabelas da base de dados através do mapeamento objeto-relacional (ORM).
2. **Repository (Data):** Camada de acesso a dados que utiliza o Spring Data JPA para facilitar a interação com a base de dados, atuando como uma evolução do padrão DAO.
3. **Service:** Os serviços contêm a lógica de negócio da aplicação. Interagem com os repositórios para persistir ou recuperar informações, processando os dados recebidos dos controllers.
4. **Controller:** Esta camada é responsável por receber as requisições do cliente, direcioná-las para os respetivos serviços e mapear as solicitações HTTP.

A separação de responsabilidades entre essas camadas é crucial para manter um código organizado, de fácil manutenção e testabilidade, além de facilitar a identificação de falhas.

## Tecnologias e Frameworks Escolhidos
* **Java 17+:** Escolhida por ser uma plataforma sólida e confiável para a criação de aplicações web escaláveis e de alto desempenho.
* **Spring Boot:** Framework poderoso projetado para simplificar e agilizar a criação de aplicações robustas, oferecendo configuração rápida e simplificada.
* **PostgreSQL:** Base de dados relacional para a persistência definitiva das informações.
* **Spring Data JPA & Hibernate:** Utilizados para o mapeamento objeto-relacional (ORM), simplificando a integração dos objetos Java com a base de dados.
* **Spring Security:** Implementado para garantir a segurança da aplicação, fornecendo autenticação e autorização para proteger os recursos da API.

## Segurança e Perfis de Acesso
A API está protegida por **Autenticação Basic**. As palavras-passe dos utilizadores são armazenadas de forma segura utilizando o algoritmo de *hash* **BCrypt**. O acesso aos recursos é controlado por dois perfis de autorização distintos:
* **ROLE_ADMIN:** Possui privilégios totais. É o único perfil autorizado a realizar operações de modificação estrutural (cadastrar, atualizar completamente e eliminar destinos).
* **ROLE_USER:** Possui acesso restrito a operações de leitura (consultas públicas) e permissão para submeter avaliações de destinos.

### Utilizadores de Teste (Gerados Automaticamente)
A aplicação possui um `CommandLineRunner` que insere automaticamente dois utilizadores na base de dados para facilitar os testes de integração:
* **Administrador:** *Username:* `admin` | *Password:* `123456`
* **Utilizador Comum:** *Username:* `usuario_comum` | *Password:* `123456`

## Principais Endpoints

O protocolo HTTP define vários métodos para as solicitações do cliente ao servidor:

* **POST `/api/destinos`**: Envia dados do cliente para o servidor, usado para criar novos recursos (Cadastra um novo destino). **[Restrito a ADMIN]**
* **GET `/api/destinos`**: Solicita a obtenção de um recurso (Retorna a lista de todos os destinos). **[Público]**
* **GET `/api/destinos?termo=valor`**: Pesquisa destinos filtrando por parte do nome ou da localização. **[Público]**
* **GET `/api/destinos/{id}`**: Retorna os detalhes de um destino específico. **[Público]**
* **PUT `/api/destinos/{id}`**: Envia dados para atualizar um recurso existente no servidor de forma completa. **[Restrito a ADMIN]**
* **PATCH `/api/destinos/{id}/avaliar`**: Envia dados para atualizar partes de um recurso existente no servidor (Registra uma nota e atualiza a média. Corpo JSON: `{"nota": 4.5}`). **[Acesso para ADMIN e USER]**
* **DELETE `/api/destinos/{id}`**: Solicita a exclusão de um recurso específico no servidor. **[Restrito a ADMIN]**

## Instruções de Execução
1. Certifique-se de ter o **Java 17+** e o **Maven** instalados na sua máquina.
2. Certifique-se de possuir a base de dados **PostgreSQL** a correr localmente (geralmente na porta `5432`). Crie uma base de dados vazia com o nome `agencia_viagens`.
3. Clone este repositório via Git.
4. Navegue até à pasta raiz do projeto.
5. Abra o ficheiro `src/main/resources/application.properties` e ajuste as propriedades `spring.datasource.username` e `spring.datasource.password` com as credenciais da sua base de dados local.
6. Execute o comando: `mvn spring-boot:run`
   *(Nota: O Hibernate irá se encarregar de criar as tabelas automaticamente e o sistema populará os utilizadores de teste).*
7. A API estará disponível no endereço: `http://localhost:8080/api/destinos`.
8. Pode utilizar ferramentas como **Postman**, **Insomnia** ou **cURL** para enviar as requisições HTTP e testar a aplicação. Lembre-se de configurar o separador de Autorização (*Basic Auth*) para aceder aos endpoints protegidos.