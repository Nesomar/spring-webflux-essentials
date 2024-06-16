# spring-webflux-essentials
Projeto para estudar spring reactive

# Usando Gradle
gradle build

Construa a imagem Docker: Execute o comando abaixo para construir a imagem Docker:
docker build -t my-spring-boot-app .

Execute o container: Após construir a imagem, você pode executar o container:

docker run -p 8080:8080 my-spring-boot-app

# Passos para usar o Docker Compose
# Construir a imagem da aplicação Spring Boot
docker-compose build

# Rodar os containers
docker-compose up
Com essa configuração, a aplicação Spring Boot será executada no container springboot-app e se conectará ao banco de dados PostgreSQL no container postgres. A aplicação estará acessível em http://localhost:8080.