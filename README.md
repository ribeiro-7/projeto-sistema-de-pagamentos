Trabalho LP2 - Grupo 3 - Sistema de Pagamentos
Alunos: Arthur Ribeiro e Gabriel Vieira

API RESTful de um sistema de pagamentos com diversas funções: 
Usuário:
Criar usuário, Atualizar sua senha, Pesquisar um usuário pelo ID e Deletá-lo pelo ID.
Carteira: 
Cria a carteira de um usuário para que ele consiga realizar transações, Atualiza o saldo da carteira e Localiza o usuário da carteira pelo ID.
Transação:
Realiza uma transação verificando se há saldo suficiente na carteira do usuário, processa o pagamento caso ele seja aprovado, se for necessário reembolso há como reembolsar com o ID da transação, 
realiza uma checagem de status de uma transação pelo seu id (para saber se foi aprovada, ainda pendente ou falhou), encontra o usuário que fez certa transação pelo ID e deleta uma transação pelo ID.

Junto com a API tem um JSON dos testes feitos no Postman e a documentação feita no Swagger, acessavel pelo link: http://localhost:8080/swagger-ui/index.html#/

Como recomendo o consumo da API:
Criação de usuário -> criação de uma carteira para ele -> realiza transação -> processa a transação 

Docker: Não conseguimos aprender a dockerizar a API a tempo, espero que o senhor tenha como fazer os testes e considerar algo, as funcionalidades estão todas ok, mas esssa parte infelizmente não conseguimos 
fazer a tempo, pois foi a primeira vez fazendo uma API RESTful com SpringBoot, então tivemos que aprender fazendo praticamente. 
