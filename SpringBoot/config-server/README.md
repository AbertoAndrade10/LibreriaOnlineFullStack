# Config Server Portfolio

## Requisitos previos

1. Clona este repositorio.
2. Crea una carpeta llamada `config` en la raíz del proyecto.
3. Copia el archivo `application-template.yml` en `config/application.yml`.
4. Completa `config/application.yml` con tus credenciales de GitHub:
   - Genera un token de acceso personal en GitHub: https://github.com/settings/tokens 
   - Asigna el token al campo `GIT_TOKEN`.

## Ejecución

1. Compila el proyecto:
   ```bash
   mvn clean package