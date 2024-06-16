# Use a imagem base do OpenJDK 17
FROM openjdk:17-jdk-slim

# Adicione um argumento para o JAR do aplicativo
ARG JAR_FILE=build/libs/*.jar

# Copie o JAR do aplicativo para o container
COPY ${JAR_FILE} app.jar

# Exponha a porta que a aplicação irá rodar
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]