# 🎯 API de Proposta de cartão de crédito

**Apis responsáveis por gerenciar o fluxo de solicitação cartões de crédito de forma eficiente e escalável com este conjunto de três microserviços.**

## ✨ Visão geral

* **Três microserviços:**
    * `cartoes-ms`: gerencia os cartões de crédito, cadastrando os cartões, clientes e propostas,
    * `emissor-ms`: processa propostas, emite cartões e notifica clientes
    * `email-ms`: envia e-mails de proposta aprovada ou reprovada e envia uma confirmação do status para `cartoes-ms`
      
## ✔️ Tecnologias e bibliotecas usadas
   - `Java 17`
   - `Spring Boot 3.2.3`
   - `REST API`
   - `PostgreSQL`
   - `MongoDB e Mongo Express`
   - `Kafka`
   - `Lombok`
   - `ModelMapper`
   - `Java Mail`

## 🚀 Como usar
## Clone o repositório:

```bash
git clone https://github.com/pauloruszel/api-proposta-cartao-credito.git
```

## 📁 Entre na pasta do projeto:

```bash
cd api-proposta-cartao-credito
```

  
## 🛠️ Build manual:
   ```bash
    cd cartoes-ms
    mvn clean install -DskipTests

    cd ..

    cd emissor-ms
    mvn clean install -DskipTests

    cd ..

    cd email-ms
    mvn clean install -DskipTests
   ```

## 🐳 Execute o docker-compose:
```bash
docker-compose up --build
```

## ✨ URL's importantes:

Mongo Express:

`http://localhost:8083/`

* Usuário: admin
* Senha: pass

PostgresSQL:

URL: `jdbc:postgresql://localhost:9042/cartoes`

Swagger:

`http://localhost:8080/swagger-ui.html`

Email:
* Use um e-mail temporário para salvar no cliente que deseja enviar:

`https://temp-mail.org/pt`

##  Desenho da Arquitetura

![api-cartao-credito-v1 drawio](https://github.com/pauloruszel/api-proposta-cartao-credito/assets/12766450/050ca266-7dbe-4601-84fd-8985e39c1b78)
