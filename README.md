# GFT Estudo API - Conversão monetária

API para realizar trocas entre moedas  

### Uso
Para realizar trocas ou ver informações do usuário é necesserio estar autenticado e ter o "bearer token"  

As rotas "/usuario/*\**\*" permitem ver informações do usuario, todas as trocas já feitas por ele, trocar a senha e excluir o usuario  

As rotas "/troca/****" permitem ver as moedas disponíveis para a troca e seus valores com base no Real, realizar trocas entre moedas e excluir trocas passadas  

Documentação swagger disponível na rota "/swagger-ui.html"

### Replit
Código disponível no replit (iniciar o projeto cajo já não esteja em excução)  
[https://replit.com/@Loukis/Estudo-API-Conversao-monetaria](https://replit.com/@Loukis/Estudo-API-Conversao-monetaria)

Acessar API neste link (iniciar o projeto cajo já não esteja em excução)  
[https://estudo-api-conversao-monetaria.loukis.repl.co/swagger-ui/index.html](https://estudo-api-conversao-monetaria.loukis.repl.co/swagger-ui/index.html)

<br>

#### extras
* Banco de dados utilizado - MongoDB provido pelo AtlasDB 
* valores monetários pegos de [https://www.currency-api.com](https://www.currency-api.com)