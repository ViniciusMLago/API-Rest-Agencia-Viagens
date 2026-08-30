# Agência de Viagens - API REST de Destinos

## Visão Geral do Problema
A agência de viagens está modernizando seus serviços digitais e necessita de uma API REST que permita a integração com aplicativos de turismo, parceiros comerciais e futuras plataformas. Esta primeira versão foca na estruturação e no gerenciamento em memória de destinos de viagem (operações CRUD, pesquisa e sistema de avaliação).

## Arquitetura Proposta
A aplicação foi estruturada utilizando a **Arquitetura em Camadas** (Layered Architecture). Essa estrutura geralmente é composta por camadas distintas com responsabilidades claras e separadas.

1. **Model (Entity):** Aqui residem as entidades ou modelos de dados, representando as estruturas de dados utilizadas pela aplicação. Contém a classe `Destino` e sua lógica interna de recálculo de notas.
2. **Service:** Os serviços recebem dados dos controllers e processam esses dados de acordo com a lógica de negócio definida. É aqui que a manipulação da lista em memória e os filtros de pesquisa acontecem.
3. **Controller:** Essa camada é responsável por receber as requisições do cliente e direcioná-las para os respectivos serviços. Define os endpoints da API mapeando solicitações HTTP.

A separação de responsabilidades entre essas camadas é crucial para manter um código organizado, de fácil manutenção e testabilidade, além de facilitar a identificação de falhas.

## Tecnologias e Frameworks Escolhidos
* **Java:** Escolhida por ser uma plataforma sólida e confiável para a criação de aplicativos web escaláveis e de alto desempenho.
* **Spring Boot:** O Spring Boot é uma ferramenta poderosa projetada para simplificar e agilizar a criação de aplicações robustas. Ele oferece um ambiente de configuração mínima e integra automaticamente várias dependências, o que reduz drasticamente o tempo de configuração.

## Principais Endpoints

O HTTP define vários métodos para as solicitações do cliente ao servidor:

* **POST `/api/destinos`**: Envia dados do cliente para o servidor, usado para criar novos recursos (Cadastra um novo destino).
* **GET `/api/destinos`**: Solicita a obtenção de um recurso específico do servidor (Retorna a lista de todos os destinos). 
* **GET `/api/destinos?termo=valor`**: Pesquisa destinos filtrando por parte do nome ou da localização.
* **GET `/api/destinos/{id}`**: Retorna os detalhes de um destino específico.
* **PUT `/api/destinos/{id}`**: Envia dados do cliente para atualizar um recurso existente no servidor de forma completa.
* **PATCH `/api/destinos/{id}/avaliar`**: Envia dados do cliente para atualizar partes de um ou mais recursos existentes no servidor (Registra uma nota e atualiza a média. Corpo JSON: `{"nota": 4.5}`).
* **DELETE `/api/destinos/{id}`**: Solicita a exclusão de um recurso específico no servidor.

## Instruções de Execução
1. Certifique-se de ter o **Java 17+** e o **Maven** instalados na sua máquina.
2. Clone este repositório via Git.
3. Navegue até a pasta raiz do projeto.
4. Execute o comando: `mvn spring-boot:run`
5. A API estará disponível no endereço: `http://localhost:8080/api/destinos`.
6. Você pode utilizar ferramentas como **Postman**, **Insomnia** ou **cURL** para enviar as requisições HTTP e testar a aplicação.