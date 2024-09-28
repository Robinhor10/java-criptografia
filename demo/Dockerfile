# Usar a imagem oficial do OpenJDK para Java 21
FROM openjdk:21-jdk-slim

# Instalar curl para baixar o tracer do Datadog
RUN apt-get update && apt-get install -y curl

# Diretório de trabalho dentro do contêiner
WORKDIR /app

# Baixar o agente do Datadog
RUN curl -LO https://dtdg.co/latest-java-tracer && chmod +x latest-java-tracer

# Copiar o arquivo JAR gerado pelo Maven para o contêiner
COPY target/*.jar app.jar

# Expor a porta que a aplicação usa
EXPOSE 8080

# Comando para executar a aplicação Spring Boot com o agente do Datadog
ENTRYPOINT ["java", "-javaagent:/app/latest-java-tracer", "-Ddd.service=spring_app", "-Ddd.env=dev", "-Ddd.agent.host=datadog", "-Ddd.trace.agent.port=8126", "-jar", "app.jar"]
