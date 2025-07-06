# Etapa 1: build da aplicação usando Maven com Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia o projeto (raiz) para dentro do container
COPY ../pom.xml .
COPY ../src ./src

# Compila o projeto e gera o JAR
RUN mvn clean package -DskipTests

# Etapa 2: cria a imagem final para rodar o app
FROM eclipse-temurin:21-jdk

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta (ajuste se sua aplicação usar outra)
EXPOSE 8080

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
