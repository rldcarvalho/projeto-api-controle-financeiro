# API para Controle Financeiro Familiar

<p align="center">
  <img src="https://img.shields.io/static/v1?label=spring&message=2.7.2&color=blue&style=for-the-badge&logo=SPRING"/>
  <img src="http://img.shields.io/static/v1?label=Java&message=17&color=blue&style=for-the-badge&logo=JAVA"/>
<img src="https://img.shields.io/static/v1?label=MySQL&message=8.0.0&color=blue&style=for-the-badge&logo=mysql&logoColor=white"/>
  <img src="http://img.shields.io/static/v1?label=Docker&message=4.24.0&color=blue&style=for-the-badge&logo=docker"/>
  <img src="http://img.shields.io/static/v1?label=Maven&message=4.0.0&color=blue&style=for-the-badge&logo=Apache%20Maven"/>
  <img src="https://img.shields.io/static/v1?label=Heroku&message=deploy&color=blue&style=for-the-badge&logo=heroku"/>
  <img src="http://img.shields.io/static/v1?label=TESTES&message=38%20passed&color=GREEN&style=for-the-badge"/>
   
   <img src="http://img.shields.io/static/v1?label=STATUS&message=REFATORANDO&color=GREEN&style=for-the-badge"/>
</p>

### Descrição do Projeto

Esta é uma API REST que permite o gerenciamento das finanças mensais de uma residência. Oferece operações CRUD para receitas e despesas, além de elaborar um resumo com os dados mensais. A aplicação foi desenvolvida em Java, utilizando o Spring Framework. O banco de dados MySQL foi utilizado para o ambiente de desenvolvimento, e o H2 foi utilizado para os testes, que foram realizados com JUnit 5 e MocMvc. A autenticação dos usuários segue o padrão JWT. A aplicação foi implantada no Heroku, porém, a partir de novembro de 2022, deixou de oferecer o plano gratuito de hospedagem.

### Tecnologias Utilizadas:

| Descrição              | Tecnologia                                                                                                                              |
|------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| Linguagem              | [Java](https://www.java.com/)                                                                                                           |
| Framework              | [Spring Boot](https://spring.io/)                                                                                                       |
| Banco de Dados         | [MySQL](https://www.mysql.com/), [H2](https://www.h2database.com/html/main.html)                                                        |
| Documentação           | [SpringDoc Swagger](https://springdoc.org/)                                                                                             |
| Testes Unitários       | [Junit](https://junit.org/junit5/), [MockMvc](https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework.html) |
| Padrão de Autenticação | [JWT (Json Web Token)](https://github.com/jwtk/jjwt)                                                                                    |
| Conteinerização        | [Docker](https://www.docker.com/), [Lombok](https://projectlombok.org/)                                                                 |
| Ferramenta de Build    | [Apache Maven](https://maven.apache.org/)                                                                                               |
| Editor de Código       | [Intellij](https://www.jetbrains.com/idea/)                                                                                             |
| Versionamento          | [Git](https://git-scm.com/)                                                                                                             |

### Premissa do Projeto

A primeira versão desta aplicação para controle de orçamento familiar foi requisitada após testes com protótipos realizados pela equipe de UX de uma empresa. A aplicação deve permitir que os usuários cadastrem suas receitas e despesas mensais, além de gerar um relatório mensal.

As equipes de frontend e UI já estão trabalhando no layout e nas telas. Para o backend, as principais funcionalidades a serem implementadas incluem:

1. **API com rotas implementadas seguindo as boas práticas do modelo REST**.
2. **Validações de acordo com as regras de negócio**.
3. **Implementação de uma base de dados para a persistência das informações**.
4. **Serviço de autenticação/autorização para restringir o acesso às informações**.

## Endpoints da API

### Cadastro de Receita

`POST /receitas`

Permite o cadastro de uma nova receita. O corpo da requisição deve conter um objeto JSON com os campos `descricao`, `valor` e `data` como obrigatórios.

Formato do campo `data`: "dd/MM/yyyy"

**Exemplo de Requisição:**

```json
{
   "descricao": "Salário",
   "valor": "2500.00",
   "data": "15/09/2023"
}
```

**Exemplo de Resposta:**

```json
{
   "id": 1,
   "descricao": "Salário",
   "valor": 2500.00,
   "data": "2023-09-15"
}
```

### Listagem de Receitas

`GET /receitas`

Retorna todas as receitas cadastradas.

**Exemplo de Resposta:**
```json
[
  {
    "id": 1,
    "descricao": "Salário",
    "valor": 2500.00,
    "data": "2023-09-15"
  },
  {
    "id": 2,
    "descricao": "Freelance",
    "valor": 800.00,
    "data": "2023-09-10"
  }
  // Outras receitas...
]
```
### Consulta Detalhada de Receita por ID

`GET /receitas/{id}`

Retorna a receita correspondente ao ID fornecido.

**Exemplo de Resposta:**

```json
{
   "id": 1,
   "descricao": "Salário",
   "valor": 2500.00,
   "data": "2023-09-15"
}
```

### Consulta de Receitas por Mês e Ano

`GET /receitas/{ano}/{mes}`

Retorna as receitas do mês e ano fornecidos.

**Exemplo de Resposta:**

```json
[
   {
      "id": 1, 
      "descricao": "Salário", 
      "valor": 2500.00, 
      "data": "2023-09-15"
   },
   {
      "id": 2, 
      "descricao": "Freelance", 
      "valor": 800.00, 
      "data": "2023-09-10"
   }
   // Outras receitas do mês e ano fornecidos...
]
```

### Atualização de Receita

`PUT /receitas/{id}`

Permite a atualização dos campos descricao, valor e data da receita correspondente ao ID informado no corpo da requisição. Retorna um JSON com os dados da receita atualizada.

**Exemplo de Requisição:**

```json
{
    "descricao": "Salário",
    "valor": "2800.00",
    "data": "15/09/2023"
}
```
**Exemplo de Resposta:**

```json
{
   "id": 1,
   "descricao": "Salário",
   "valor": 2800.00,
   "data": "2023-09-15"
}
```

### Remoção de Receita

`DELETE /receitas/{id}`

Permite excluir a receita correspondente ao ID fornecido.

### Cadastro de Despesa

`POST /despesas`

Permite o cadastro de uma nova despesa. O corpo da requisição deve conter um objeto JSON com os campos `descricao`, `valor`, `data` e `categoria` como obrigatórios.

Formato do campo `data`: "dd/MM/yyyy"

**Exemplo de Requisição:**

```json
{
   "descricao": "Aluguel",
   "valor": "1200.00",
   "data": "15/09/2023",
   "categoria": "Moradia"
}
```

**Exemplo de Resposta:**

```json
{
   "id": 1,
   "descricao": "Aluguel",
   "valor": 1200.00,
   "data": "2023-09-15",
   "categoria": "Moradia"
}
```

### Enums de Categoria

A categoria deve ser uma das seguintes opções:

- "Alimentação"
- "Saúde"
- "Moradia"
- "Transporte"
- "Educação"
- "Lazer"
- "Imprevistos"
- "Outras"

Isso deve ser especificado no campo "categoria" ao cadastrar ou atualizar uma despesa.

### Listagem de Despesas

`GET /despesas`

Retorna todas as despesas cadastradas.

**Exemplo de Resposta:**

```json
[
   {
      "id": 1, 
      "descricao": "Aluguel", 
      "valor": 1200.00, 
      "data": "2023-09-15",
      "categoria": "Moradia"
   },
   {
      "id": 2, 
      "descricao": "Supermercado", 
      "valor": 300.00, 
      "data": "2023-09-10",
      "categoria": "Alimentação"
   }
]
```

### Busca de Despesa por ID

`GET /despesas/{id}`

Busca uma despesa com o ID informado no endereço.

**Exemplo de Resposta:**

```json
{
   "id": 1,
   "descricao": "Aluguel",
   "valor": 1200.00,
   "data": "2023-09-15",
   "categoria": "Moradia"
}
```

### Busca de Despesa por Mês

`GET /despesas/{ano}/{mes}`

Busca despesas no mês informado no endereço.

**Exemplo de Resposta:**

```json
[
   {
      "id": 1, 
      "descricao": "Aluguel", 
      "valor": 1200.00, 
      "data": "2023-09-15",
      "categoria": "Moradia"
   },
   {
      "id": 2, 
      "descricao": "Supermercado", 
      "valor": 300.00, 
      "data": "2023-09-10",
      "categoria": "Alimentação"
   }
]
```

### Atualização de Despesa

`PUT /despesas/{id}`

Permite a atualização dos campos descricao, valor, data e categoria da despesa correspondente ao ID informado no corpo da requisição.

**Exemplo de Requisição:**

```json
{
    "descricao": "Aluguel",
    "valor": "1300.00",
    "data": "15/09/2023",
    "categoria": "Moradia"
}
```
**Exemplo de Resposta:**

```json
{
   "id": 1,
   "descricao": "Aluguel",
   "valor": 1300.00,
   "data": "2023-09-15",
   "categoria": "Moradia"
}
```

### Exclusão de Despesa

`DELETE /despesas/{id}`

Permite excluir a despesa correspondente ao ID fornecido.

### Consulta do Resumo Mensal

`GET /resumo/{ano}/{mes}`

Retorna um resumo contendo o valor total das receitas e despesas, o saldo final e a somatória de despesas em cada categoria no mês informado.

`ano`: O ano desejado para o resumo (ex: 2023)
`mes`: O mês desejado para o resumo (ex: 9 para setembro)

**Exemplo de Requisição:**

```bash
GET /resumo/2023/9
```

**Exemplo de Resposta:**

```json
{
   "totalReceitas": 5000.00,
   "totalDespesas": 3000.00,
   "saldoFinal": 2000.00,
   "totalPorCategoria": [
      {
         "categoria": "Alimentação",
         "valor": 800.00
      },
      {
         "categoria": "Moradia",
         "valor": 1200.00
      },
      {
         "categoria": "Transporte",
         "valor": 500.00
      }
   ]
}
```

## Autenticação

A autenticação na aplicação é feita por meio de um endpoint disponibilizado pelo `AutenticacaoController`. Este endpoint recebe as credenciais de login por meio de um objeto `LoginForm`, autentica o usuário com base nessas credenciais utilizando o `AuthenticationManager` e, se a autenticação for bem-sucedida, gera um token de acesso utilizando o `TokenService`.

### Endpoint de Autenticação

`POST /auth`

Realiza a autenticação do usuário e gera um token de acesso.

**Exemplo de Requisição:**

```json
{
   "usuario": "exemplo_usuario",
   "senha": "exemplo_senha"
}
```

**Exemplo de Resposta:**

```json
{
   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
   "tipoToken": "Bearer"
}
```

## Documentação

Este projeto utiliza o [Swagger](https://springdoc.org/) para gerar a documentação, que pode ser acessada pelo link abaixo após executar a API:

[localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Executando o projeto localmente

Para executar a aplicação localmente deve-se fazer inicialmente o clone desse repositório:

```bash
# Clone o repositório
git clone https://github.com/rldcarvalho/projeto-api-controle-financeiro.git

# Navegue até o repositório clonado
cd projeto-api-controle-financeiro
```
### Configurando o banco de dados

O projeto utiliza o banco de dados MySQL. Para executá-lo, crie um banco e exporte as seguintes variáveis de ambiente para executar o perfil de produção do projeto:

```properties
${DB_URL} // Url do banco de dados
${DB_USERNAME} // Nome de usuário do banco
${DB_PASSWORD} // Senha do banco de dados
```

### Execução

Execute o projeto como seguinte comando:

```bash
mvn spring-boot:run
```

Para fazer as requisições HTTP aos endpoints da API, utilize ferramentas como [Postman](https://www.postman.com/) ou [Insomnia](https://insomnia.rest/download).

## Licença

The [MIT License](https://github.com/rldcarvalho/projeto-api-controle-financeiro/blob/main/LICENSE) (MIT)

Copyright :copyright: 2022 - Api Controle Financeiro
