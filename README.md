# 🗳️ Deliberation API Service
API criada para gerenciar o fluxo de votação corporativa.

## 📬 Postman
* 🧾 🔗 Acesse a documentação para testar os endpoints: 
> https://documenter.getpostman.com/view/17818068/2sB3B7PEX1

Especificações:

## 📁 Repositório
🔗 GitHub: 
>  https://github.com/annarafaeladev/deliberation_api_service

## 🧾 Especificações
### 🛢️ Banco de Dados
* MongoDB (versão gratuita)

Para teste visual no MongoDB Compass Conexão de leitura:
```bash
mongodb+srv://deliberation_user:cimJZt6SsddDzhmj@deliberationapi.hayornf.mongodb.net/deliberationapidb
```

### 🚀 Deploy
Hospedado gratuitamente no Render

#### Base URL da API:
```bash 
https://deliberation-api-service.onrender.com/api/v1
```

⚠️ Atenção: Como está em plano gratuito, a aplicação pode demorar alguns segundos para responder após período de inatividade (cold start).

## 📖 Documentação da API (Swagger)
> <a href="https://deliberation-api-service.onrender.com/api/swagger-ui/index.html">Acesse a documentação interativa</a>

### ✅ Validação de CPF
⚠️ Não foi utilizada a API externa sugerida na documentação original, pois o serviço encontra-se indisponível.

* Utilizado a biblioteca Stella da Caelum / Alura para validação de CPF:
>  https://mvnrepository.com/artifact/br.com.caelum.stella/caelum-stella-core
#### Validação de CPF:

##### Depedencia
```bash yaml
    <dependency>
        <groupId>br.com.caelum.stella</groupId>
        <artifactId>caelum-stella-core</artifactId>
        <version>2.2.1</version>
    </dependency>

 ```
##### Validação 
``` bash java
var isValidDocument = new CPFValidator();
if (!isValidDocument.invalidMessagesFor(document).isEmpty()) {    
    throw new AssociateException("Associate document invalid");
}

 ```

* Existem algumas collections já alimentadas no banco de dados.

## 🚀 Como rodar a aplicação localmente

### Antes de rodar a aplicação, certifique-se de ter instalado em sua máquina:

* #### Java 17 ou superior

* ##### Maven

* #### Docker e Docker Compose

### 📂 Estrutura do projeto

```
deliberation_api_service/
│
├── docker/                 # Arquivos de configuração do Docker
│   └── docker-compose.yml
│
├── src/                    # Código-fonte da aplicação
│
├── pom.xml                 # Configuração do Maven
└── README.md               # Documentação do projeto
```
### 🐳 Subindo os containers com Docker
A aplicação depende de serviços como o MongoDB, que são orquestrados via Docker Compose.

> O arquivo `docker-compose.yml` está localizado em: `./docker/docker-compose.yml`
### Passos para iniciar o ambiente:
#### 1. Clone o repositório:

```bash
git clone https://github.com/annarafaeladev/deliberation_api_service.git
```
#### 2. Acesse o diretório do projeto:

```bash
cd deliberation_api_service
```
#### 3. Instale as dependências com o Maven:

```bash 
mvn clean install
```
#### 5. Suba os containers:

```bash
docker-compose -f docker/docker-compose.yml up -d
```
### ▶️ Rodando a aplicação
Com os containers ativos, execute:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:

```bash
http://localhost:8080
```
### 🧪 Testes
Para rodar os testes automatizados da aplicação:

```bash 
mvn test
```

## 📌 Observações Finais
A API foi desenvolvida com foco em boas práticas, validações robustas.

Ideal para simular processos de assembleias, deliberações e votações corporativas.
