
 # Licencia de Software para Croydon Colombia
 #
 # Copyright (c) 2024 Croydon Colombia
 #
 # Este programa es software propietario de Croydon Colombia.
 # No está permitida su distribución, copia, modificación o uso no autorizado.
 # Cualquier intento de reproducción sin consentimiento será considerado una violación de esta licencia.
 #
 # CROYDON COLOMBIA NO OTORGA GARANTÍAS EXPRESAS O IMPLÍCITAS SOBRE ESTE SOFTWARE.
 # El uso de este software implica la aceptación de los términos y condiciones establecidos.
 #

# Usar una imagen base de Maven con OpenJDK 21 para compilar la aplicación
# Etapa 1: Construcción de la aplicación con Maven y OpenJDK 21
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias necesarias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el resto del código de la aplicación y compilarla
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen final con el JDK de OpenJDK 21 para ejecutar la aplicación
FROM eclipse-temurin:21-jdk-jammy

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el JAR generado desde la etapa de construcción
COPY --from=build /app/target/croydonistas-cart-service-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8082

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]


