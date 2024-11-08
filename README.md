# SulWorkTech

# Visão geral do projeto
Foi desenvolvido o Back-end do projeto, o qual foi feito utilizando o Spring Boot.

# Entidade
O usuario é composto por: 

📌Id - Chave Primária da tabela Usuario
📌Cpf - Campo de valor único
📌nomeColaborador - Nome do colaborador
📌cafeDaManha - Opção de Café da manhã
📌dataCafe - Data que o Usuario vai participar do café

# Rotas - Porta 8080
Rotas para fazer as requisições http

📌Post Para Criar Usuario - http://localhost:8080/v1/usuario
📌Get Para Buscar Um Usuario especifico - http://localhost:8080/v1/usuario/{cpf}
📌Get Para Buscar Todos - http://localhost:8080/v1/usuario/todos
📌Put Para Atualizar Usuario - http://localhost:8080/v1/usuario/{cpf}
📌Delete Para Deletar Algum Usuario - http://localhost:8080/v1/usuario/{cpf}

# Banco de dados - sulwork
Para cadastrar os usuarios, foi utilizado o Mysql na porta padrão (3306)

📌Url de acesso - jdbc:mysql://localhost:3306/sulwork
