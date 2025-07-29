# 🗳️ Deliberation API Service
API criada para gerenciar o fluxo de votação corporativa.

## 📬 Coleção Postman
🔗 Acesse a coleção para testar os endpoints:
https://documenter.getpostman.com/view/17818068/2sB3B7PEX1

Especificações:

## 📁 Repositório
🔗 GitHub: https://github.com/annarafaeladev/deliberation_api_service

## 🧾 Especificações
### 🛢️ Banco de Dados
* MongoDB (versão gratuita)

Para teste visual no MongoDB Compass Conexão de leitura:
```
mongodb+srv://deliberation_user:cimJZt6SsddDzhmj@deliberationapi.hayornf.mongodb.net/deliberationapidb
```

### 🚀 Deploy
Hospedado gratuitamente no Render

#### Base URL da API:
`https://deliberation-api-service.onrender.com/api/v1`

⚠️ Atenção: Como está em plano gratuito, a aplicação pode demorar alguns segundos para responder após período de inatividade (cold start).

#### 📖 Documentação da API (Swagger)
<a href="https://deliberation-api-service.onrender.com/api/swagger-ui/index.html">Acesse a documentação interativa</a>

### ✅ Validação de CPF
⚠️ Não foi utilizada a API externa sugerida na documentação original, pois o serviço encontra-se indisponível.

* Utilizado a biblioteca Stella da Caelum / Alura para validação de CPF:
https://mvnrepository.com/artifact/br.com.caelum.stella/caelum-stella-core
#### Validação de CPF:

##### Depedencia
``` yaml
    <dependency>
        <groupId>br.com.caelum.stella</groupId>
        <artifactId>caelum-stella-core</artifactId>
        <version>2.2.1</version>
    </dependency>

 ```
##### Validação 
``` java
var isValidDocument = new CPFValidator();
if (!isValidDocument.invalidMessagesFor(document).isEmpty()) {    
    throw new AssociateException("Associate document invalid");
}

 ```

* Existem algumas collections já alimentadas no banco de dados.

## 🚀 Como rodar a aplicação localmente
Pré-requisitos
Java 17+
Maven
Docker e Docker Compose

🐳 Subindo os containers com Docker
Para iniciar os serviços necessários (MongoDB, etc), execute o seguinte comando na raiz do projeto:

📁 O arquivo docker-compose.yml está localizado na pasta docker/ na raiz do projeto.

`docker-compose -f docker/docker-compose.yml up -d`

`mvn spring-boot:run`

## 📌 Observações Finais
A API foi desenvolvida com foco em boas práticas, validações robustas.

Ideal para simular processos de assembleias, deliberações e votações corporativas.
