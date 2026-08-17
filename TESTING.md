# Guía de Pruebas Locales - Sabor Gourmet 🧪

## ⚡ Quick Start (5 minutos)

### 1. Iniciar Base de Datos en Docker

En la carpeta raíz del proyecto:

```powershell
docker compose up -d
```

Verificar que está listo:

```powershell
docker ps --filter "name=sabor_gourmet_db"
```

Debe mostrar **STATUS: "Up X seconds (healthy)"**

### 2. Construir la Aplicación

```bash
mvn clean install
```

### 3. Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

O ejecutar el JAR:

```bash
java -jar target/sabor-gourmet-reservas-1.0.0.jar
```

### 4. Abrir en Navegador

```
http://localhost:8080/sabor-gourmet
```

**¡Listo para pruebas!** ✅

---

## 📋 Datos de Prueba Incluidos

La base de datos viene preconfigurada con:

| Entidad | Cantidad | Descripción |
|---------|----------|-------------|
| **Clientes** | 20 | Clientes de prueba con nombres, emails y teléfonos |
| **Mesas** | 13 | Mesas con capacidades de 2 a 12 personas |
| **Reservas** | 36 | Reservas en varios estados (pendientes, confirmadas, completadas) |

### Acceder a la Base de Datos Directamente

```powershell
# Conectar a la BD y ejecutar queries
docker exec -it sabor_gourmet_db psql -U postgres -d sabor_gourmet_db

# O ejecutar una query directa
docker exec sabor_gourmet_db psql -U postgres -d sabor_gourmet_db -c "SELECT * FROM clientes LIMIT 5;"
```

---

## 🔄 Ciclo de Desarrollo

### Cambios en Código Java

```bash
# 1. Editar código en src/main/java
# 2. Recompilar
mvn clean install

# 3. Reiniciar la aplicación
# Detener Ctrl+C y ejecutar:
mvn spring-boot:run
```

### Cambios en Templates HTML

Con `spring.devtools.livereload.enabled=true` (en `application-dev.properties`), los cambios en templates se recargan automáticamente. Solo actualiza el navegador.

### Cambios en Base de Datos

Si necesitas resetear la BD:

```powershell
# Parar y eliminar el contenedor y volumen
docker compose down -v

# Relanzar (los scripts se ejecutarán de nuevo)
docker compose up -d
```

---

## 🐛 Solución de Problemas Comunes

### 1. Error: "Connection refused" (Puerto 5432)

```powershell
# Verificar que Docker está corriendo
docker ps

# Si no aparece sabor_gourmet_db, iniciar:
docker compose up -d
```

### 2. Puerto 8080 ya en uso

Cambiar en `src/main/resources/application.properties`:

```properties
server.port=8081
```

Luego acceder a: `http://localhost:8081/sabor-gourmet`

### 3. Las tablas no existen

Esto solo ocurre si Docker no ejecutó los scripts. Verificar:

```powershell
# Ver si la BD fue creada
docker exec sabor_gourmet_db psql -U postgres -d sabor_gourmet_db -c "SELECT COUNT(*) FROM clientes;"

# Si retorna error, resetear la BD
docker compose down -v
docker compose up -d
```

### 4. Cambios en código no se reflejan

```bash
# Limpiar compilación y recompilar
mvn clean install

# Reiniciar la aplicación
```

---

## 📊 Casos de Prueba Recomendados

### Test 1: Dashboard (Inicio)
1. Abrir `http://localhost:8080/sabor-gourmet`
2. Verificar que carga estadísticas
3. Verificar que muestra reservas pendientes

**Esperado**: Dashboard con datos de prueba

### Test 2: Crear Cliente
1. Ir a **Clientes** → **Nuevo Cliente**
2. Llenar: Nombre, Email, Teléfono
3. Click en **Crear Cliente**

**Esperado**: Cliente creado exitosamente

### Test 3: Crear Reserva
1. Ir a **Reservas** → **Nueva Reserva**
2. Seleccionar cliente y mesa
3. Seleccionar fecha/hora futura
4. Especificar número de comensales ≤ capacidad mesa
5. Click en **Crear Reserva**

**Esperado**: Reserva en estado "PENDIENTE"

### Test 4: Confirmar Reserva
1. Ir a **Reservas**
2. Seleccionar una reserva en estado "PENDIENTE"
3. Click en **Confirmar**

**Esperado**: Reserva cambia a "CONFIRMADA"

### Test 5: Cambiar Estado de Reserva
1. Ir a **Reservas** → **Administración** (`/sabor-gourmet/reservas/admin`)
2. Seleccionar una reserva confirmada
3. Cambiar estado a "ACTIVA" (cliente llega)
4. Cambiar a "COMPLETADA" (cliente se va)

**Esperado**: Estados cambian correctamente

### Test 6: Ver mesas disponibles
1. Ir a **Mesas** → **Ver disponibles** (`/sabor-gourmet/mesas/disponibles`)
2. Confirmar que solo aparecen mesas disponibles
3. Verificar que cada fila tiene enlace a detalle

**Esperado**: Vista funcional sin errores 404

### Test 7: Validaciones
1. Intentar crear reserva con:
   - Fecha en el pasado
   - Número de comensales > capacidad mesa
   - Mesa no disponible en esa hora

**Esperado**: Sistema muestra errores apropiados

---

## 🛠️ Comandos Útiles Docker

```powershell
# Ver logs en tiempo real
docker compose logs -f db

# Ver logs de la BD
docker logs sabor_gourmet_db

# Ejecutar comando en contenedor
docker exec -it sabor_gourmet_db bash

# Ver estado de salud
docker inspect --format='{{json .State.Health}}' sabor_gourmet_db | ConvertFrom-Json

# Parar contenedor (datos persisten)
docker compose stop

# Reanudar contenedor
docker compose start

# Reiniciar contenedor
docker compose restart db

# Eliminar todo (contenedor + volumen + datos)
docker compose down -v

# Ver volúmenes
docker volume ls | grep sabor-gourmet
```

---

## 📝 Verificación de Setup Completo

Ejecuta esta checklist antes de reportar bugs:

- [ ] Docker Desktop está corriendo
- [ ] `docker compose up -d` levanta el contenedor sin errores
- [ ] `docker ps` muestra sabor_gourmet_db con estado "healthy"
- [ ] `mvn clean install` compila sin errores
- [ ] `mvn spring-boot:run` inicia la app sin excepciones
- [ ] `http://localhost:8080/sabor-gourmet` carga correctamente
- [ ] Dashboard muestra datos (20 clientes, 13 mesas, 36 reservas)
- [ ] Puedo crear cliente nuevo
- [ ] Puedo crear reserva nueva
- [ ] Puedo cambiar estado de reserva

**Si todo está check ✅**: Sistema listo para pruebas completas

---

## 🔗 Referencias Rápidas

| Recurso | URL |
|---------|-----|
| Dashboard | `http://localhost:8080/sabor-gourmet` |
| Clientes | `http://localhost:8080/sabor-gourmet/clientes` |
| Mesas | `http://localhost:8080/sabor-gourmet/mesas` |
| Reservas | `http://localhost:8080/sabor-gourmet/reservas` |
| BD PostgreSQL | `localhost:5432` (usuario: postgres, password: postgres) |

---

## 💡 Tips para Pruebas Efectivas

1. **Usa diferentes navegadores**: Chrome, Firefox, Safari, Edge
2. **Prueba en dispositivos móviles**: Chrome DevTools → Toggle device toolbar
3. **Inspecciona la red**: Ver requests/responses en Network tab
4. **Revisa logs**: `docker compose logs db` para errores de BD
5. **Limpia caché**: Ctrl+Shift+Delete (navegador)

---

**¡Listo para empezar pruebas! 🚀**

Para más información, ver `INSTALL.md` y `README.md`


