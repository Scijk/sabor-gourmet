# Bruno API Collection - Sabor Gourmet 🍽️

Colección completa de endpoints para pruebas de la API de **Sabor Gourmet** usando [Bruno](https://www.usebruno.com/).

## 📋 Contenido

Esta colección incluye endpoints para:

### 👥 Clientes (5 endpoints)
- **Listar Clientes** - GET - Obtiene lista paginada de clientes
- **Obtener Cliente por ID** - GET - Detalles de cliente específico
- **Crear Cliente** - POST - Nuevo cliente
- **Editar Cliente** - POST - Actualizar datos de cliente
- **Eliminar Cliente** - POST - Borrar cliente

### 🪑 Mesas (5 endpoints)
- **Listar Mesas** - GET - Obtiene lista paginada de mesas
- **Obtener Mesa por ID** - GET - Detalles de mesa específica
- **Crear Mesa** - POST - Nueva mesa
- **Editar Mesa** - POST - Actualizar datos de mesa
- **Eliminar Mesa** - POST - Borrar mesa

### 📅 Reservas (9 endpoints)
- **Listar Reservas** - GET - Obtiene lista paginada de reservas
- **Obtener Reserva por ID** - GET - Detalles de reserva específica
- **Crear Reserva** - POST - Nueva reserva
- **Editar Reserva** - POST - Actualizar datos de reserva
- **Confirmar Reserva** - POST - Cambiar de PENDIENTE a CONFIRMADA
- **Activar Reserva** - POST - Cambiar de CONFIRMADA a ACTIVA
- **Completar Reserva** - POST - Cambiar de ACTIVA a COMPLETADA
- **Cancelar Reserva** - POST - Cambiar a CANCELADA
- **Eliminar Reserva** - POST - Borrar reserva

### 🏠 Home (1 endpoint)
- **Dashboard** - GET - Página principal con estadísticas

---

## 🚀 Instalación

### Opción 1: Usando Bruno CLI

```bash
# Instalar Bruno globalmente
npm install -g @usebruno/cli

# Navegar a la carpeta del proyecto
cd bruno

# Abrir la colección
bru open .
```

### Opción 2: Usando Bruno Desktop

1. Descargar [Bruno desde aquí](https://www.usebruno.com/)
2. Instalar y ejecutar
3. Click en "Open Collection"
4. Seleccionar la carpeta `bruno/` del proyecto

---

## ⚙️ Configuración del Entorno

La colección incluye 2 entornos preconfigurados:

### Entorno: Local Development (por defecto)
```
base_url: http://localhost:8080/sabor-gourmet
api_base: http://localhost:8080/sabor-gourmet/api
cliente_id: 1
mesa_id: 1
reserva_id: 1
```

### Entorno: Production
```
base_url: https://api.saborgourmet.com/sabor-gourmet
api_base: https://api.saborgourmet.com/sabor-gourmet/api
cliente_id: 1
mesa_id: 1
reserva_id: 1
```

**Para cambiar entorno en Bruno:**
1. Click en el ícono de engranaje (⚙️) arriba a la derecha
2. Seleccionar el entorno deseado

---

## 📝 Ejemplos de Uso

### 1. Crear Cliente

```bash
POST http://localhost:8080/sabor-gourmet/clientes

Body (JSON):
{
  "nombre": "Carlos García",
  "email": "carlos.garcia@example.com",
  "telefono": "9876543210",
  "notas": "Nuevo cliente"
}

Respuesta:
{
  "id": 21,
  "nombre": "Carlos García",
  "email": "carlos.garcia@example.com",
  "telefono": "9876543210",
  "notas": "Nuevo cliente",
  "fechaRegistro": "2024-08-16T14:30:00"
}
```

### 2. Crear Reserva

```bash
POST http://localhost:8080/sabor-gourmet/reservas

Body (JSON):
{
  "clienteId": 1,
  "mesaId": 2,
  "fechaReserva": "2024-08-25T20:00:00",
  "numeroComensales": 4,
  "observaciones": "Cumpleaños"
}

Respuesta:
{
  "id": 37,
  "cliente": { "id": 1, "nombre": "Juan Pérez" },
  "mesa": { "id": 2, "numeroMesa": "2", "capacidad": 6 },
  "fechaReserva": "2024-08-25T20:00:00",
  "numeroComensales": 4,
  "observaciones": "Cumpleaños",
  "estado": "PENDIENTE",
  "fechaCreacion": "2024-08-16T14:30:00"
}
```

### 3. Cambiar Estado de Reserva

```bash
# Confirmar
POST http://localhost:8080/sabor-gourmet/reservas/1/confirmar

# Activar
POST http://localhost:8080/sabor-gourmet/reservas/1/activar

# Completar
POST http://localhost:8080/sabor-gourmet/reservas/1/completar

# Cancelar
POST http://localhost:8080/sabor-gourmet/reservas/1/cancelar
Body (Form-urlencoded):
  motivoCancelacion: Cambio de planes
```

---

## 🔍 Variables de Entorno

Cada endpoint utiliza variables de entorno que se pueden personalizar:

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `{{base_url}}` | URL base de la aplicación | `http://localhost:8080/sabor-gourmet` |
| `{{api_base}}` | URL base de la API | `http://localhost:8080/sabor-gourmet/api` |
| `{{cliente_id}}` | ID de cliente para pruebas | `1` |
| `{{mesa_id}}` | ID de mesa para pruebas | `1` |
| `{{reserva_id}}` | ID de reserva para pruebas | `1` |

**Para cambiar variables:**
1. Click en Variables (ambiente icon)
2. Editar los valores
3. Cambiar entre entornos Local/Production

---

## 📊 Estados de Reservas

```
PENDIENTE → CONFIRMADA → ACTIVA → COMPLETADA
    ↓
 CANCELADA (desde PENDIENTE o CONFIRMADA)
```

### Cambios de Estado
- **Confirmar**: PENDIENTE → CONFIRMADA
- **Activar**: CONFIRMADA → ACTIVA
- **Completar**: ACTIVA → COMPLETADA
- **Cancelar**: PENDIENTE/CONFIRMADA → CANCELADA

---

## ✅ Flujo de Pruebas Recomendado

### Prueba 1: CRUD de Clientes
1. Listar Clientes
2. Crear Cliente (copiar ID)
3. Obtener Cliente por ID
4. Editar Cliente
5. Listar Clientes (verificar cambios)

### Prueba 2: CRUD de Mesas
1. Listar Mesas
2. Crear Mesa (copiar ID)
3. Obtener Mesa por ID
4. Editar Mesa
5. Listar Mesas (verificar cambios)

### Prueba 3: Ciclo Completo de Reserva
1. Crear Reserva (copiar ID)
2. Obtener Reserva por ID (verificar estado PENDIENTE)
3. Confirmar Reserva (debe cambiar a CONFIRMADA)
4. Activar Reserva (debe cambiar a ACTIVA)
5. Completar Reserva (debe cambiar a COMPLETADA)
6. Obtener Reserva por ID (verificar cambios finales)

### Prueba 4: Cancelar Reserva
1. Crear Reserva (copiar ID)
2. Confirmar Reserva
3. Cancelar Reserva (incluir motivo)
4. Obtener Reserva por ID (verificar estado CANCELADA)

### Prueba 5: Validaciones
1. Crear Cliente con email duplicado (debe fallar)
2. Crear Reserva con comensales > capacidad mesa (debe fallar)
3. Crear Reserva con fecha pasada (debe fallar)
4. Crear Mesa con número duplicado (debe fallar)

---

## 🐛 Solución de Problemas

### La conexión falla
- Verificar que la app está corriendo: `mvn spring-boot:run`
- Verificar que la BD está levantada: `docker compose up -d`
- Verificar que estás usando la URL correcta: `http://localhost:8080/sabor-gourmet`

### Error 404 Not Found
- Verificar que el ID existe (ej: cliente_id=1 existe)
- Verificar que usaste POST en lugar de GET (o viceversa)
- Revisar la documentación de cada endpoint

### Error 400 Bad Request
- Revisar validaciones en la documentación del endpoint
- Verificar que los parámetros están correctos
- Revisar formato de fechas: `YYYY-MM-DDTHH:mm:ss`

### Error 500 Server Error
- Ver logs de la aplicación: `mvn spring-boot:run`
- Verificar que la BD tiene datos
- Contactar al equipo de desarrollo

---

## 📚 Documentación Completa

Cada endpoint incluye:
- **Descripción**: Qué hace el endpoint
- **Parámetros**: De ruta, query o body
- **Validaciones**: Reglas de negocio
- **Respuesta**: Ejemplo JSON (HTTP 200)
- **Errores**: Códigos de error posibles

Para ver la documentación de un endpoint:
1. Hacer click en el endpoint en Bruno
2. Expandir la sección "Docs" en el panel derecho
3. Leer descripción, parámetros y ejemplos

---

## 🔐 Notas de Seguridad

- **No compartir tokens** en esta colección (actualmente no hay autenticación)
- **No incluir datos sensibles** en observaciones/notas
- En producción, implementar autenticación JWT/OAuth2
- Cambiar contraseñas por defecto de la BD

---

## 📞 Soporte

Para problemas o preguntas:
1. Ver documentación en cada endpoint
2. Revisar logs de la app
3. Leer `INSTALL.md` y `README.md` del proyecto
4. Contactar al equipo de desarrollo

---

## 🎯 Próximos Pasos

Después de verificar que todos los endpoints funcionan:

1. **Integración Continua**: Agregar tests automatizados
2. **Performance**: Ejecutar tests de carga
3. **Seguridad**: Implementar autenticación
4. **Documentación**: Generar OpenAPI/Swagger
5. **Despliegue**: Llevar a producción

---

**¡Listo para pruebas con Bruno! 🚀**

Abre la colección en Bruno Desktop o CLI y comienza a probar los endpoints.

Para más información sobre Bruno: https://www.usebruno.com/


