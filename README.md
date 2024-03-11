# Api de Proposta de cartão de crédito

**Comece a gerenciar seus cartões de crédito de forma eficiente e escalável com este conjunto de três microserviços modulares e fáceis de usar.**

## ✨ Visão geral

* **Três microserviços:**
    * `cartoes-ms`: gerencia os cartões de crédito
    * `emissor-ms`: processa propostas, emite cartões e notifica clientes
    * `email-ms`: envia e-mails
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
* **Imagens Docker:** `docker-compose up --build`

##  Documentação

* **Descrição detalhada dos microserviços:**
    * `cartoes-ms`: https://learn.microsoft.com/es-es/microsoft-identity-manager/reference/get-smartcard-proposed-pin
    * `emissor-ms`: https://learn.microsoft.com/de-de/credentials/
    * `email-ms`: https://support.microsoft.com/es-es/office/crear-un-mensaje-de-correo-electr%C3%B3nico-en-outlook-147208af-ca8e-4cdf-b71f-77ba81a54069
* **Estrutura do projeto:** https://blog.runrun.it/8-documentos-essenciais-para-o-controle-de-projetos/
* **Contribuições:** https://www.gov.br/inss/pt-br/saiba-mais/seus-direitos-e-deveres/atualizacao-de-tempo-de-contribuicao/documentos-originais-para-comprovacao-de-tempo-de-contribuicao
* **Licença:** MIT License

##  Começando

1. **Clone este repositório:** `git clone https://github.com/seu-nome-de-usuario/projeto-microserviços.git`
2. **Acesse a pasta do microserviço desejado:** `cd cartoes-ms` (ou `emissor-ms` ou `email-ms`)

##  Desenho da Arquitetura

![api-cartao-credito-v1 drawio](https://github.com/pauloruszel/api-proposta-cartao-credito/assets/12766450/050ca266-7dbe-4601-84fd-8985e39c1b78)
