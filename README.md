# Sabor Gourmet - Sistema de Gestión de Reservas 🍽️

## Descripción del Proyecto

**Sabor Gourmet** es una aplicación web integral desarrollada con **Spring Boot** para la gestión de reservas de un restaurante. El sistema permite a los clientes realizar, modificar y cancelar reservas en línea, mientras que ofrece a la administración una interfaz completa para gestionar mesas, clientes y reservas.

### Características Principales

✅ **Gestión de Clientes**: Crear, editar, eliminar y buscar clientes  
✅ **Administración de Mesas**: Configurar mesas con capacidad y ubicación  
✅ **Sistema de Reservas**: Crear, modificar, confirmar, completar y cancelar reservas  
✅ **Control de Disponibilidad**: Validación automática de disponibilidad de mesas  
✅ **Panel de Administración**: Dashboard con estadísticas en tiempo real  
✅ **Diseño Responsivo**: Interfaz adaptada para móviles, tablets y escritorio  
✅ **Base de Datos PostgreSQL**: Persistencia de datos confiable con Hibernate/JPA  

---

## Tecnologías Utilizadas

### Backend
- **Java 17**: Lenguaje de programación
- **Spring Boot 3.1.5**: Framework principal
- **Spring MVC**: Arquitectura Model-View-Controller
- **Spring Data JPA**: Persistencia de datos
- **Hibernate**: ORM para mapeo objeto-relacional
- **PostgreSQL**: Base de datos relacional
- **Lombok**: Reducción de código boilerplate
- **Validation**: Validación de datos

### Frontend
- **Thymeleaf**: Motor de plantillas HTML
- **Bootstrap 5**: Framework CSS responsivo
- **Font Awesome**: Iconos
- **HTML5 & CSS3**: Estructura y estilos

### Herramientas
- **Maven**: Gestor de dependencias
- **Git**: Control de versiones

---

## Estructura del Proyecto

```
sabor-gourmet/
│
├── pom.xml                          # Configuración de Maven
├── README.md                        # Este archivo
│
├── src/
│   ├── main/
│   │   ├── java/com/saborgourmet/
│   │   │   ├── SaborGourmetApplication.java      # Clase principal
│   │   │   ├── model/                            # Entidades JPA
│   │   │   │   ├── Cliente.java
│   │   │   │   ├── Mesa.java
│   │   │   │   └── Reserva.java
│   │   │   ├── repository/                       # Interfaces Repositories
│   │   │   │   ├── ClienteRepository.java
│   │   │   │   ├── MesaRepository.java
│   │   │   │   └── ReservaRepository.java
│   │   │   ├── service/                          # Lógica de negocio
│   │   │   │   ├── ClienteService.java
│   │   │   │   ├── MesaService.java
│   │   │   │   └── ReservaService.java
│   │   │   └── controller/                       # Controladores
│   │   │       ├── HomeController.java
│   │   │       ├── ClienteController.java
│   │   │       ├── MesaController.java
│   │   │       └── ReservaController.java
│   │   └── resources/
│   │       ├── application.properties             # Configuración de la aplicación
│   │       └── templates/                         # Vistas Thymeleaf
│   │           ├── index.html                     # Página de inicio
│   │           ├── clientes/
│   │           │   ├── listar.html
│   │           │   ├── formulario.html
│   │           │   └── detalle.html
│   │           ├── mesas/
│   │           │   ├── listar.html
│   │           │   ├── formulario.html
│   │           │   └── detalle.html
│   │           └── reservas/
│   │               ├── listar.html
│   │               ├── formulario.html
│   │               ├── editar.html
│   │               ├── detalle.html
│   │               └── admin.html
│   └── test/                        # Pruebas unitarias
│
└── database/                        # Scripts de base de datos
    ├── 01-init-schema.sql          # Creación de tablas
    └── 02-seed-data.sql            # Datos de prueba
```

---

## Requisitos Previos

### Instalación de Dependencias

1. **Java 17 o superior**
   ```bash
   java -version
   ```

2. **PostgreSQL 12 o superior**
   ```bash
   psql --version
   ```

3. **Maven 3.6 o superior**
   ```bash
   mvn --version
   ```

4. **Git**
   ```bash
   git --version
   ```

---

## Instalación y Configuración

### 1. Clonar el Repositorio

```bash
cd d:\Desarrollo\Repos
git clone <url-repositorio>
cd sabor-gourmet
```

### 2. Configurar la Base de Datos

#### Opción A: Usando pgAdmin o cliente SQL

1. Crear la base de datos ejecutando el script:
```sql
-- Ejecutar en PostgreSQL
CREATE DATABASE sabor_gourmet_db
    WITH 
    ENCODING 'UTF8'
    LOCALE 'es_ES.UTF-8'
    TEMPLATE template0;
```

2. Ejecutar los scripts SQL:
```bash
# Desde la línea de comandos
psql -U postgres -d sabor_gourmet_db -f database/01-init-schema.sql
psql -U postgres -d sabor_gourmet_db -f database/02-seed-data.sql
```

#### Opción B: Usando la consola psql

```bash
# Conectar a PostgreSQL
psql -U postgres

# En la consola psql:
\i 'D:/Desarrollo/Repos/sabor-gourmet/database/01-init-schema.sql'
\i 'D:/Desarrollo/Repos/sabor-gourmet/database/02-seed-data.sql'
```

### 3. Configurar Credenciales de Base de Datos

Editar el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sabor_gourmet_db
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña_postgres
```

### 4. Construir el Proyecto

```bash
mvn clean install
```

### 5. Ejecutar la Aplicación

```bash
# Opción 1: Directamente con Maven
mvn spring-boot:run

# Opción 2: Ejecutar el JAR generado
java -jar target/sabor-gourmet-reservas-1.0.0.jar
```

### 6. Acceder a la Aplicación

Abrir el navegador y acceder a:
- **URL**: `http://localhost:8080/sabor-gourmet`
- **Dashboard**: `http://localhost:8080/sabor-gourmet/`

---

## Uso de la Aplicación

### Navegación Principal

#### 🏠 **Inicio (Dashboard)**
- Estadísticas en tiempo real
- Acciones rápidas
- Reservas pendientes de confirmación

#### 📅 **Reservas**
- Ver todas las reservas
- Crear nueva reserva
- Editar reserva existente
- Confirmar reservas pendientes
- Cambiar estado de reservas
- Cancelar reservas
- Panel de administración

#### 👥 **Clientes**
- Ver listado de clientes
- Crear nuevo cliente
- Editar datos del cliente
- Buscar clientes por nombre
- Eliminar cliente

#### 🪑 **Mesas**
- Ver todas las mesas
- Crear nueva mesa
- Editar mesa
- Cambiar estado de mesa
- Ver mesas disponibles

---

## Arquitectura del Proyecto

### Patrón MVC (Model-View-Controller)

```
┌─────────────────────────────────────────────────────┐
│                   INTERFAZ USUARIO                  │
│           (Templates Thymeleaf + Bootstrap)         │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│              CAPA DE PRESENTACIÓN                   │
│              (Controllers)                          │
│    - ClienteController                             │
│    - MesaController                                │
│    - ReservaController                             │
│    - HomeController                                │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│           CAPA DE LÓGICA DE NEGOCIO                │
│              (Services)                            │
│    - ClienteService                               │
│    - MesaService                                  │
│    - ReservaService                               │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│        CAPA DE ACCESO A DATOS (DAO/JPA)            │
│              (Repositories)                        │
│    - ClienteRepository                            │
│    - MesaRepository                               │
│    - ReservaRepository                            │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│              BASE DE DATOS                         │
│            (PostgreSQL)                            │
│    - Tabla clientes                               │
│    - Tabla mesas                                  │
│    - Tabla reservas                               │
└─────────────────────────────────────────────────────┘
```

### Capas de la Aplicación

1. **Capa de Presentación (Controllers)**
   - Manejan las solicitudes HTTP
   - Envían datos a las vistas
   - Capturan entrada del usuario

2. **Capa de Lógica de Negocio (Services)**
   - Implementan la lógica empresarial
   - Validan datos
   - Orquestan operaciones complejas

3. **Capa de Acceso a Datos (Repositories)**
   - Interfacean con Spring Data JPA
   - Consultas a la base de datos
   - Gestión de transacciones

4. **Modelos de Datos (Entities)**
   - Representan entidades del dominio
   - Mapeo objeto-relacional

---

## Modelos de Datos

### Cliente
```java
- id: Long (Clave Primaria)
- nombre: String (Requerido)
- email: String (Requerido, Único)
- telefono: String (Requerido)
- notas: String
- fechaRegistro: LocalDateTime
```

### Mesa
```java
- id: Long (Clave Primaria)
- numeroMesa: String (Única)
- capacidad: Integer
- ubicacion: String
- estado: EstadoMesa (DISPONIBLE, RESERVADA, OCUPADA, MANTENIMIENTO)
```

### Reserva
```java
- id: Long (Clave Primaria)
- cliente: Cliente (Clave Foránea)
- mesa: Mesa (Clave Foránea)
- fechaReserva: LocalDateTime
- numeroComensales: Integer
- observaciones: String
- estado: EstadoReserva (PENDIENTE, CONFIRMADA, ACTIVA, COMPLETADA, CANCELADA)
- fechaCreacion: LocalDateTime
- fechaModificacion: LocalDateTime
- fechaCancelacion: LocalDateTime
- motivoCancelacion: String
```

---

## Operaciones CRUD

### Clientes

| Operación | Endpoint | Método |
|-----------|----------|--------|
| Listar | `/sabor-gourmet/clientes` | GET |
| Ver detalles | `/sabor-gourmet/clientes/{id}` | GET |
| Crear | `/sabor-gourmet/clientes` | POST |
| Editar | `/sabor-gourmet/clientes/{id}` | POST |
| Eliminar | `/sabor-gourmet/clientes/{id}/eliminar` | POST |
| Buscar | `/sabor-gourmet/clientes/buscar` | GET |

### Mesas

| Operación | Endpoint | Método |
|-----------|----------|--------|
| Listar | `/sabor-gourmet/mesas` | GET |
| Ver detalles | `/sabor-gourmet/mesas/{id}` | GET |
| Crear | `/sabor-gourmet/mesas` | POST |
| Editar | `/sabor-gourmet/mesas/{id}` | POST |
| Eliminar | `/sabor-gourmet/mesas/{id}/eliminar` | POST |
| Ver disponibles | `/sabor-gourmet/mesas/disponibles` | GET |

### Reservas

| Operación | Endpoint | Método |
|-----------|----------|--------|
| Listar | `/sabor-gourmet/reservas` | GET |
| Ver detalles | `/sabor-gourmet/reservas/{id}` | GET |
| Crear | `/sabor-gourmet/reservas` | POST |
| Editar | `/sabor-gourmet/reservas/{id}` | POST |
| Confirmar | `/sabor-gourmet/reservas/{id}/confirmar` | POST |
| Activar | `/sabor-gourmet/reservas/{id}/activar` | POST |
| Completar | `/sabor-gourmet/reservas/{id}/completar` | POST |
| Cancelar | `/sabor-gourmet/reservas/{id}/cancelar` | POST |
| Eliminar | `/sabor-gourmet/reservas/{id}/eliminar` | POST |

---

## Consultas SQL Útiles

### Obtener reservas futuras confirmadas
```sql
SELECT r.*, c.nombre, m.numero_mesa
FROM reservas r
JOIN clientes c ON r.cliente_id = c.id
JOIN mesas m ON r.mesa_id = m.id
WHERE r.estado IN ('CONFIRMADA', 'ACTIVA')
  AND r.fecha_reserva > CURRENT_TIMESTAMP
ORDER BY r.fecha_reserva ASC;
```

### Obtener mesas disponibles
```sql
SELECT *
FROM mesas
WHERE estado = 'DISPONIBLE'
ORDER BY capacidad ASC;
```

### Obtener estadísticas de clientes
```sql
SELECT 
    c.nombre,
    COUNT(r.id) as total_reservas,
    COUNT(CASE WHEN r.estado = 'COMPLETADA' THEN 1 END) as completadas,
    COUNT(CASE WHEN r.estado = 'CANCELADA' THEN 1 END) as canceladas
FROM clientes c
LEFT JOIN reservas r ON c.id = r.cliente_id
GROUP BY c.id, c.nombre
ORDER BY total_reservas DESC;
```

---

## Validaciones y Reglas de Negocio

### Clientes
- ✓ Nombre: Requerido, máximo 100 caracteres
- ✓ Email: Requerido, válido y único
- ✓ Teléfono: Requerido, 10 dígitos
- ✓ Notas: Opcional, máximo 500 caracteres

### Mesas
- ✓ Número: Requerido y único
- ✓ Capacidad: Mínimo 1 persona, máximo 20
- ✓ Estado: Solo valores permitidos

### Reservas
- ✓ Cliente: Debe existir
- ✓ Mesa: Debe existir y estar disponible
- ✓ Fecha: No puede ser en el pasado
- ✓ Comensales: No puede exceder capacidad de la mesa
- ✓ No puede haber 2+ reservas para la misma mesa con 2 horas de diferencia

---

## Troubleshooting (Solución de Problemas)

### Error: "Base de datos no encontrada"
```
Solución: Ejecutar los scripts SQL: 01-init-schema.sql y 02-seed-data.sql
```

### Error: "Connection refused (PostgreSQL)"
```
Solución: Verificar que PostgreSQL está corriendo:
- Windows: Services > postgresql-x64-15
- Linux: sudo systemctl start postgresql
```

### Error: "Credenciales de base de datos inválidas"
```
Solución: Verificar usuario/contraseña en application.properties
```

### Puerto 8080 ya está en uso
```
Solución: Cambiar en application.properties:
server.port=8081
```

---

## Ejemplo de Uso

### 1. Crear un Cliente
1. Ir a **Clientes** → **Nuevo Cliente**
2. Llenar formulario (Nombre, Email, Teléfono)
3. Hacer click en **Crear Cliente**

### 2. Crear una Mesa
1. Ir a **Mesas** → **Nueva Mesa**
2. Especificar: Número, Capacidad, Ubicación
3. Hacer click en **Crear Mesa**

### 3. Crear una Reserva
1. Ir a **Reservas** → **Nueva Reserva**
2. Seleccionar cliente y mesa
3. Especificar: Fecha, Hora, Comensales
4. Hacer click en **Crear Reserva**

### 4. Administrar Reservas
1. La reserva se crea en estado **PENDIENTE**
2. Confirmar: Cambiar a **CONFIRMADA**
3. Activar: Cambiar a **ACTIVA** cuando el cliente llega
4. Completar: Cambiar a **COMPLETADA** al terminar

---

## Mejoras Futuras

- [ ] Autenticación y autorización de usuarios
- [ ] Historial de auditoría
- [ ] Notificaciones por email
- [ ] Integración de pagos
- [ ] API REST para clientes móviles
- [ ] Reportes avanzados
- [ ] Integración con sistema de facturación
- [ ] Gestión de promociones y ofertas
- [ ] Sistema de reseñas y calificaciones

---

## Soporte

Para reportar issues o sugerencias, contactar al equipo de desarrollo.

---

## Licencia

Este proyecto es parte del curso de desarrollo empresarial en Java.

---

## Autor

**Desarrollado por**: Equipo de Desarrollo  
**Fecha**: 2024  
**Versión**: 1.0.0

---

**¡Gracias por usar Sabor Gourmet! 🍽️**
