# ssBank
Bank application challenge

#########Instruções #############
A aplicação foi compilada com java 11 e utiliza o mongodb e também a imagem docker padrão do RabbitMQ

- Executar Imagem docker
docker run -d -p 5672:5672 -p 15672:15672 --name=rabbitmq rabbitmq:3.8.3-management

- Dentro da pasta mongoDbFiles se encontram os arquivos "creditCategory.json" e "parameter.json", eles são necessários para a execução do sistema, ou também é possível utilizar a opção de importação do mongoDb e importar o schema inteiro que se encontra na pasta mongoDbFiles/ssBank


