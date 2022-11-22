# API para Controle Financeiro Familiar

<p align="center">
  <img src="https://img.shields.io/static/v1?label=spring&message=2.7.2&color=blue&style=for-the-badge&logo=SPRING"/>
  <img src="http://img.shields.io/static/v1?label=Java&message=17&color=red&style=for-the-badge&logo=JAVA"/>
  <img src="http://img.shields.io/static/v1?label=Maven&message=4.0.0&color=red&style=for-the-badge&logo=Apache%20Maven"/>
  <img src="https://img.shields.io/static/v1?label=Heroku&message=deploy&color=blue&style=for-the-badge&logo=heroku"/>
  <img src="http://img.shields.io/static/v1?label=TESTES&message=38%20passed&color=GREEN&style=for-the-badge"/>
   <img src="http://img.shields.io/static/v1?label=License&message=MIT&color=green&style=for-the-badge"/>
   <img src="http://img.shields.io/static/v1?label=STATUS&message=CONCLUIDO&color=GREEN&style=for-the-badge"/>
</p>

### Descrição do Projeto

Esse projeto consiste em uma *API REST* para realizar o gerenciamento das finanças mensais de uma residência. A aplicação apresenta o *CRUD* de receitas, despesas e elabora um resumo com os dados mensais.
O mesmo foi desenvolvido em *Java* com auxílio do *Spring Framework*. Como banco de dados, utilizei o *MySQL* para desenvolvimento e *H2* para testes, realizados através do *JUnit 5*. A autenticação dos usuários foi elaborada seguindo o padrão *JWT*. 
Utilizando o *Docker*, foi realizado o deploy da aplicação no *Heroku*, entretanto a aplicação deixou de oferecer seu plano gratuito de hospedagem em novembro de 2022.

### Autenticação

Os endpoints da aplicação só podem ser acessados mediante autenticação via Web Token.

Para obter o token é necessário realizar uma requisição do tipo POST para ```/auth``` com os seguintes parâmetros:

```json
"username": "usuario",
"password": "12345"
```

### Receitas

Contém os seguintes campos:

```json
"Id", //Id auto gerado
"Descricao", //Descrição
"Valor", //Valor no formato 0.00
"Data", //Data no formato dd/MM/yyyy
```

Suporta das seguintes funcionalidades:

POST ```/receitas```
Realiza o cadastro de uma receita.

GET ```/receitas```
Retorna todas as receitas cadastradas.

GET ```/receitas/{id}```
Busca receita com o id informada no endereço.

GET ```/receitas/{ano}/{mes}```
Busca receitas no mês informado no endereço.

PUT ```/receitas/{id}```
Atualiza receita cujo id for informado no endereço.

DELETE ```/receitas/{id}```
Exclui a receita com id informado no endereço.

### Despesas

Contém os seguintes campos:

```json
"Id", //Id auto gerado
"Descricao", //Descrição
"Valor", //Valor no formato 0.00
"Data", //Data no formato dd/MM/yyyy
"Categoria" // Enum(Alimentação, Saúde, Moradia, Transporte, Educação, Lazer, Imprevistos, Outras) Assume o valor padrão "Outras" caso não seja informado no cadastro
```

Suporta das seguintes funcionalidades:

POST ```/despesas```
Realiza o cadastro de uma despesa.

GET ```/despesas```
Retorna todas as despesas cadastradas.

GET ```/despesas/{id}```
Busca despesa com o id informada no endereço.

GET ```/despesas/{ano}/{mes}```
Busca despesas no mês informado no endereço.

PUT ```/despesas/{id}```
Atualiza despesa cujo id for informado no endereço.

DELETE ```/despesas/{id}```
Exclui a despesa com id informado no endereço.

### Resumo Mensal

GET ```/resumo/{ano}/{mes}```
Retorna um resumo contendo o valor total das receitas e despesas, o saldo final e a somatória de despesas em cada categoria no mês informado.

## Premissa do Projeto

Após alguns testes com protótipos feitos pelo time de UX de uma empresa, foi requisitada a primeira versão de uma aplicação para controle de orçamento familiar. A aplicação deve permitir que uma pessoa cadastre suas receitas e despesas do mês, bem como gerar um relatório mensal.

Os times de frontend e UI já estão trabalhando no layout e nas telas. Para o back-end, as principais funcionalidades a serem implementadas são:

1. **API com rotas implementadas seguindo as boas práticas do modelo REST**;
2. **Validações feitas conforme as regras de negócio**;
3. **Implementação de base de dados para persistência das informações**;
4. **Serviço de autenticação/autorização para restringir acesso às informações**.

## Licença

The [MIT License](https://github.com/rldcarvalho/projeto-api-controle-financeiro/blob/main/LICENSE) (MIT)

Copyright :copyright: 2022 - Api Controle Financeiro
