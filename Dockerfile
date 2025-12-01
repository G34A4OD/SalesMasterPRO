# Etapa 1: Construcción
# Usamos una imagen de Maven compatible
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copiamos TODO el proyecto al contenedor
COPY . .

# IMPORTANTE: Entramos a la carpeta interna donde está tu código real
# (Basado en tu captura de pantalla donde se ve una carpeta dentro de otra)
WORKDIR /app/SalesMasterPRO

# Compilamos el proyecto ignorando los tests para ir más rápido
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
# Usamos Eclipse Temurin (la alternativa moderna y estable a OpenJDK que te dio error)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiamos el JAR generado desde la carpeta correcta de la etapa anterior
COPY --from=build /app/SalesMasterPRO/target/*.jar app.jar

# Exponemos el puerto
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]