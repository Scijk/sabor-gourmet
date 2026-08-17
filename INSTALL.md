# Guía de Instalación - Sabor Gourmet

## 📋 Prerequisitos

Asegúrate de tener instalados los siguientes software:

1. **Java Development Kit (JDK) 17 o superior**
   - Descargar desde: https://www.oracle.com/java/technologies/downloads/
   - Verificar: `java -version`

2. **PostgreSQL 12 o superior** (O usar Docker - ver abajo)
   - Descargar desde: https://www.postgresql.org/download/
   - Verificar: `psql --version`
   - Usuario por defecto: `postgres`
   - Contraseña: `postgres` (puede cambiar)

3. **Docker (Opcional - Recomendado para desarrollo)**
   - Descargar desde: https://www.docker.com/products/docker-desktop
   - Verificar: `docker --version`
   - **Ventaja**: No necesita instalar PostgreSQL localmente, todo corre en contenedor

4. **Maven 3.6 o superior**
   - Descargar desde: https://maven.apache.org/download.cgi
   - Verificar: `mvn --version`

4. **Git (Opcional)**
   - Descargar desde: https://git-scm.com/
   - Verificar: `git --version`

---

## 🛠️ Paso 1: Preparar la Base de Datos

### Opción A: PostgreSQL Local en Windows (Instalación Tradicional)

1. Abrir **pgAdmin** (generalmente instalado con PostgreSQL):
   - Iniciar → Buscar "pgAdmin" → Abrir

2. O usar línea de comandos:
   ```cmd
   # Abrir PostgreSQL
   psql -U postgres
   
   # Ingresa tu contraseña cuando se pida
   ```

3. Ejecutar los scripts SQL:

   **Opción A.1: Desde pgAdmin**
   - Conectar al servidor PostgreSQL
   - Hacer clic derecho en "Databases" → "Create" → "Database"
   - Nombre: `sabor_gourmet_db`
   - Guardar
   - Abrir "Query Tool"
   - Copiar y pegar contenido de `database/01-init-schema.sql`
   - Ejecutar (F5)
   - Repetir con `database/02-seed-data.sql`

   **Opción A.2: Desde línea de comandos**
   ```cmd
   # En la carpeta del proyecto
   psql -U postgres -f database\01-init-schema.sql
   psql -U postgres -d sabor_gourmet_db -f database\02-seed-data.sql
   ```

### Opción B: PostgreSQL Local en Linux/Mac

```bash
# Conectar a PostgreSQL
sudo -u postgres psql

# O sin sudo si PostgreSQL está configurado
psql -U postgres

# Ejecutar scripts (desde la carpeta del proyecto)
psql -U postgres -f database/01-init-schema.sql
psql -U postgres -d sabor_gourmet_db -f database/02-seed-data.sql
```

### ⭐ Opción C: Usando Docker (Recomendado para Desarrollo)

**Ventajas**: No necesita instalar PostgreSQL localmente, es reproducible y fácil de resetear.

#### Paso 1: Levantar el contenedor PostgreSQL

En la carpeta raíz del proyecto (donde está `docker-compose.yml`), ejecutar:

```powershell
# Windows PowerShell
docker compose up -d

# O si tienes una versión antigua de Docker
docker-compose up -d
```

Verás algo como:
```
Creating sabor_gourmet_db ... done
```

#### Paso 2: Verificar que PostgreSQL está listo

```powershell
# Ver logs del contenedor
docker compose logs -f db

# O esperar unos 5-10 segundos y verificar con:
docker ps

# La columna STATUS debe mostrar "healthy" o (healthy)
```

#### Paso 3: Los scripts se ejecutan automáticamente

⚡ **Importante**: Los scripts `01-init-schema.sql` y `02-seed-data.sql` de la carpeta `database/` se ejecutan **automáticamente** la primera vez que se inicia el contenedor. No necesitas ejecutarlos manualmente.

#### Paso 4: Verificar que la base de datos está lista

```powershell
# Conectar al contenedor y verificar
docker exec -it sabor_gourmet_db psql -U postgres -d sabor_gourmet_db -c "SELECT * FROM clientes LIMIT 1;"

# Si ves una fila con datos, ¡la BD está lista!
```

#### Comando para parar/reinicar (si es necesario)

```powershell
# Parar el contenedor
docker compose down

# Parar y ELIMINAR datos (para empezar de cero)
docker compose down -v

# Reiniciar el contenedor
docker compose restart db
```

---

### 📌 Resumen: ¿Cuál opción elegir?

| Opción | Ventajas | Desventajas |
|--------|----------|-------------|
| **A (Windows Local)** | Fácil de instalar, datos persistentes por defecto | Necesita instalación previa, más pasos manuales |
| **B (Linux/Mac)** | Nativo del SO, buena integración | Necesita instalación y permisos |
| **C (Docker)** ⭐ | **Scripts automáticos, sin instalación, reproducible, fácil reset** | Necesita Docker instalado |

---

## 📁 Paso 2: Configurar el Proyecto

### 2.1 Descargar/Clonar el Proyecto

```bash
# Si tienes Git
git clone <url-del-repositorio>
cd sabor-gourmet

# O simplemente descarga la carpeta del proyecto
```

### 2.2 Configurar Credenciales de Base de Datos

Editar el archivo: `src/main/resources/application.properties`

**Si usaste Opción C (Docker)**, ya está configurado correctamente:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sabor_gourmet_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

**Si usaste Opción A o B (PostgreSQL Local)**, verifica que coincidan con tus credenciales:
- Si tu contraseña es diferente, cambiar `password=postgres` al valor correcto
- Si PostgreSQL está en otro host/puerto, ajustar `spring.datasource.url`

---

## 🏗️ Paso 3: Construir el Proyecto

Abrir terminal/CMD en la carpeta del proyecto (`sabor-gourmet/`)

```bash
# Limpiar y construir
mvn clean install

# Esto descargará todas las dependencias y compilará el proyecto
# Esperar a que termine (puede tomar 2-5 minutos la primera vez)
```

**Resultado esperado:**
```
BUILD SUCCESS
Total time: 45.123 s
```

Si hay error:
- Verificar que Java está instalado: `java -version`
- Verificar que Maven está instalado: `mvn --version`
- Verificar conexión a internet

---

## ▶️ Paso 4: Ejecutar la Aplicación

### Opción A: Usando Maven (Recomendado para desarrollo)

```bash
mvn spring-boot:run
```

Verás output como:
```
Started SaborGourmetApplication in 8.234 seconds
```

### Opción B: Ejecutar el JAR

```bash
java -jar target/sabor-gourmet-reservas-1.0.0.jar
```

---

## 🌐 Paso 5: Acceder a la Aplicación

1. Abrir navegador web (Chrome, Firefox, Edge, Safari)

2. Ir a la URL:
   ```
   http://localhost:8080/sabor-gourmet
   ```

3. Verás el dashboard con:
   - Estadísticas de reservas
   - Acciones rápidas
   - Reservas pendientes

4. Rutas principales de la aplicación:
   - `http://localhost:8080/sabor-gourmet/clientes`
   - `http://localhost:8080/sabor-gourmet/mesas`
   - `http://localhost:8080/sabor-gourmet/mesas/disponibles`
   - `http://localhost:8080/sabor-gourmet/reservas`
   - `http://localhost:8080/sabor-gourmet/reservas/admin`

> Importante: el proyecto usa `server.servlet.context-path=/sabor-gourmet`. Si aparecen URLs duplicadas como `/sabor-gourmet/sabor-gourmet/...`, se debe a enlaces hardcodeados en HTML; usa `th:href="@{...}"` y `th:action="@{...}"`.

---

## 📱 Navegación Principal

Una vez en la aplicación:

### 🏠 Inicio
- Panel de control con estadísticas
- Reservas pendientes de confirmación
- Accesos rápidos

### 👥 Clientes
- Ver lista de clientes
- Crear nuevo cliente
- Editar cliente existente
- Buscar clientes

### 🪑 Mesas
- Ver lista de mesas
- Crear nueva mesa
- Editar mesa
- Ver mesas disponibles

### 📅 Reservas
- Ver lista de reservas
- Crear nueva reserva
- Editar reserva
- Confirmar/Activar/Completar reservas
- Cancelar reservas

---

## 🧪 Datos de Prueba

La base de datos incluye:
- **10 clientes** de ejemplo
- **13 mesas** con diferentes capacidades
- **30+ reservas** en varios estados

Puedes:
- Ver reservas confirmadas
- Crear nuevas reservas
- Modificar datos existentes
- Probar todas las funcionalidades

---

## ⚙️ Cambiar Configuración

### Puerto de la Aplicación

Si el puerto 8080 está ocupado, cambiar en `application.properties`:

```properties
server.port=8081
```

Luego acceder a: `http://localhost:8081/sabor-gourmet`

### Contexto de la Aplicación

Para cambiar la ruta base (`/sabor-gourmet`):

```properties
server.servlet.context-path=/mi-restaurante
```

Luego acceder a: `http://localhost:8080/mi-restaurante`

---

## 🐛 Solución de Problemas

### Error: "Connection refused" (PostgreSQL)

**Problema:** No se puede conectar a la base de datos

**Soluciones:**
1. Verificar que PostgreSQL está corriendo
   - Windows: Services → postgresql → está iniciado
   - Linux: `sudo systemctl start postgresql`
   - Mac: `brew services start postgresql@12`

2. Verificar que la base de datos existe:
   ```
   psql -U postgres -l | grep sabor_gourmet_db
   ```

3. Si no existe, crear manualmente:
   ```
   psql -U postgres
   CREATE DATABASE sabor_gourmet_db;
   \c sabor_gourmet_db
   ```

### Error: "BUILD FAILURE" al hacer mvn install

**Problema:** Maven no puede descargar las dependencias

**Soluciones:**
1. Limpiar caché de Maven:
   ```bash
   mvn clean
   ```

2. Verificar conexión a internet

3. Verificar que Java está instalado:
   ```bash
   java -version
   javac -version
   ```

### Error: Puerto 8080 ya está en uso

**Problema:** Otra aplicación usa el puerto 8080

**Soluciones:**
1. Cambiar el puerto en `application.properties` (ver arriba)

2. O encontrar qué aplicación lo usa:
   - Windows: `netstat -ano | findstr :8080`
   - Linux/Mac: `lsof -i :8080`

### La aplicación inicia pero las tablas no existen

**Problema:** Los scripts SQL no se ejecutaron correctamente

**Soluciones:**
1. Ejecutar manualmente los scripts:
   ```bash
   psql -U postgres -d sabor_gourmet_db -f database/01-init-schema.sql
   psql -U postgres -d sabor_gourmet_db -f database/02-seed-data.sql
   ```

2. Verificar en pgAdmin:
   - Conectar a `sabor_gourmet_db`
   - Expandir "Schemas" → "public" → "Tables"
   - Deben estar: `clientes`, `mesas`, `reservas`

---

## 📝 Logs y Depuración

### Ver logs en tiempo real

Al ejecutar `mvn spring-boot:run`, verás logs como:
```
INFO  - Started SaborGourmetApplication in 8.234 seconds
DEBUG - Handling GET request for ...
```

### Archivo de logs

Se guarda en: `logs/sabor-gourmet.log` (una vez configurado)

### Aumentar nivel de logging

Editar `application.properties`:
```properties
logging.level.com.saborgourmet=DEBUG
```

---

## 🔐 Seguridad en Desarrollo

**Importante para Producción:**

1. Cambiar contraseñas por defecto
2. Usar variables de entorno
3. Habilitar HTTPS
4. Implementar autenticación
5. Validar todas las entradas

Ver `application-prod.properties` para referencia.

---

## 📚 Recursos Útiles

- [Spring Boot Documentación](https://spring.io/projects/spring-boot)
- [PostgreSQL Documentación](https://www.postgresql.org/docs/)
- [Thymeleaf Documentación](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
- [Bootstrap Documentación](https://getbootstrap.com/docs/5.0/)
- [Maven Documentación](https://maven.apache.org/guides/)

---

## ✅ Verificación Final

Para confirmar que todo está funcionando:

1. ✓ PostgreSQL está corriendo
2. ✓ Base de datos `sabor_gourmet_db` existe
3. ✓ Tablas están creadas (clientes, mesas, reservas)
4. ✓ Proyecto se compila: `mvn clean install`
5. ✓ Aplicación inicia: `mvn spring-boot:run`
6. ✓ Accesible en: `http://localhost:8080/sabor-gourmet`
7. ✓ Dashboard carga correctamente

---

## 🆘 Soporte Adicional

Si encuentras problemas:

1. Revisar archivos de log
2. Verificar prerequisitos
3. Revisar la sección "Solución de Problemas"
4. Contactar al equipo de soporte

---

**¡Listo para comenzar! 🚀**

Ahora puedes acceder a la aplicación y empezar a usar el sistema de reservas.
