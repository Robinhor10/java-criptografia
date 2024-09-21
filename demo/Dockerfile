# Usar a imagem oficial do OpenJDK para Java 21
FROM openjdk:21-jdk-slim

# Diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o arquivo JAR gerado pelo Maven para o contêiner
# Usando um wildcard para pegar o .jar que está no diretório target
COPY target/*.jar app.jar

# Expor a porta que a aplicação usa
EXPOSE 8080

# Comando para executar a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
