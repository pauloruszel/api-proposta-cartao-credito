# Api de Proposta de cartão de crédito

**Comece a gerenciar seus cartões de crédito de forma eficiente e escalável com este conjunto de três microserviços modulares e fáceis de usar.**

## ✨ Visão geral

* **Três microserviços:**
    * `cartoes-ms`: gerencia os cartões de crédito, cadastrando os cartões, clientes e propostas,
    * `emissor-ms`: processa propostas, emite cartões e notifica clientes
    * `email-ms`: envia e-mails de proposta aprovada ou reprovada e envia uma confirmação do status para `cartoes-ms`
      
* **Tecnologias:**
    * Java 17
    * Spring Boot 3.2.3
    * PostgreSQL
    * MongoDB
    * Kafka
    * SpringDoc OpenAPI
    * Lombok
    * ModelMapper
    * Java Mail
   
* **Estrutura modular:** cada microserviço possui seu próprio diretório com classes Java, recursos e configurações
* **Build manual:**
    ```
    cd cartoes-ms
    mvn clean install -DskipTests
    docker-compose up --build

    cd ..

    cd emissor-ms
    mvn clean install -DskipTests
    docker-compose up --build

    cd ..

    cd email-ms
    mvn clean install -DskipTests
    docker-compose up --build

    cd ..
    ```

##  Começando

1. **Clone este repositório:** `git clone https://github.com/seu-nome-de-usuario/projeto-microserviços.git`
2. **Acesse a pasta do microserviço desejado:** `cd cartoes-ms` (ou `emissor-ms` ou `email-ms`)

##  Desenho da Arquitetura

![api-cartao-credito-v1 drawio](https://github.com/pauloruszel/api-proposta-cartao-credito/assets/12766450/050ca266-7dbe-4601-84fd-8985e39c1b78)
