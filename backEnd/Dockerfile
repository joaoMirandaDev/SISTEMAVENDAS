# Fase de construção usando Maven e OpenJDK 17
FROM maven:3.8.3-openjdk-17 AS build

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo pom.xml e qualquer configuração necessária primeiro
COPY pom.xml ./

# Copia apenas os arquivos necessários para resolver dependências primeiro
# Isso ajuda a aproveitar o cache do Docker se as dependências não mudarem
RUN mvn dependency:go-offline

# Copia o restante dos arquivos do projeto
COPY . .

# Compila o projeto usando Maven e gera o JAR
RUN mvn clean install -DskipTests

# Fase de execução com OpenJDK 17
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho no contêiner para a execução
WORKDIR /app

# Copia o JAR gerado da fase de construção para o novo contêiner
COPY --from=build /app/target/*.jar /app/app.jar

# Define o comando de entrada padrão
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev" ,"/app/app.jar"]
