# Guía Rápida - Bruno en Windows 🪟

## 📥 Descargar e Instalar Bruno

### Opción 1: Descargar Ejecutable (Más Fácil)

1. Ir a https://www.usebruno.com/downloads
2. Descargar para Windows (`.exe`)
3. Ejecutar el instalador
4. Seguir el asistente de instalación

### Opción 2: Instalar con Chocolatey

```powershell
choco install bruno
```

### Opción 3: Instalar con npm (CLI)

```powershell
npm install -g @usebruno/cli
```

---

## 🚀 Abrir la Colección en Bruno

### Método 1: Usando Bruno Desktop (Recomendado)

1. Abrir la aplicación Bruno
2. Click en **"Open Collection"** (botón azul principal)
3. Navegar a: `D:\Desarrollo\Repos\sabor-gourmet\bruno`
4. Click en **"Select Folder"**
5. ¡Listo! Verás la colección cargada

### Método 2: Usando Bruno CLI

```powershell
cd D:\Desarrollo\Repos\sabor-gourmet\bruno
bru run
```

### Método 3: Abrir Directamente Desde Explorer

1. Abrir Windows Explorer
2. Navegar a: `D:\Desarrollo\Repos\sabor-gourmet\bruno`
3. Hacer clic derecho → "Abrir con" → "Bruno"

---

## 📋 Estructura de la Colección

```
bruno/
├── bruno.json                    # Configuración del workspace
├── README.md                     # Documentación completa
├── .gitignore
│
├── clientes/                     # Endpoints de Clientes (5)
│   ├── 01 Listar Clientes.bru
│   ├── 02 Obtener Cliente por ID.bru
│   ├── 03 Crear Cliente.bru
│   ├── 04 Editar Cliente.bru
│   └── 05 Eliminar Cliente.bru
│
├── mesas/                        # Endpoints de Mesas (5)
│   ├── 01 Listar Mesas.bru
│   ├── 02 Obtener Mesa por ID.bru
│   ├── 03 Crear Mesa.bru
│   ├── 04 Editar Mesa.bru
│   └── 05 Eliminar Mesa.bru
│
├── reservas/                     # Endpoints de Reservas (9)
│   ├── 01 Listar Reservas.bru
│   ├── 02 Obtener Reserva por ID.bru
│   ├── 03 Crear Reserva.bru
│   ├── 04 Editar Reserva.bru
│   ├── 05 Confirmar Reserva.bru
│   ├── 06 Activar Reserva.bru
│   ├── 07 Completar Reserva.bru
│   ├── 08 Cancelar Reserva.bru
│   └── 09 Eliminar Reserva.bru
│
└── home/                         # Endpoints de Home (1)
    └── 01 Dashboard.bru

TOTAL: 21 endpoints (5 + 5 + 9 + 1)
```

---

## ⚙️ Configurar Entorno en Bruno

### Paso 1: Seleccionar Entorno

En Bruno, arriba a la derecha hay un botón con el icono de **engranaje** ⚙️

- Click en él
- Verás: **"Local Development"** (default) o **"Production"**

### Paso 2: Cambiar Variables (Opcional)

Si necesitas cambiar URLs o IDs:

1. Click en **Variables** (otro botón en la parte superior)
2. Editar los valores deseados
3. Las variables se actualizarán automáticamente en todos los endpoints

### Variables Disponibles

| Variable | Valor Actual | Descripción |
|----------|--------------|-------------|
| `{{base_url}}` | `http://localhost:8080/sabor-gourmet` | URL principal |
| `{{api_base}}` | `http://localhost:8080/sabor-gourmet/api` | Base de API |
| `{{cliente_id}}` | `1` | ID de cliente para pruebas |
| `{{mesa_id}}` | `1` | ID de mesa para pruebas |
| `{{reserva_id}}` | `1` | ID de reserva para pruebas |

---

## 🧪 Ejecutar un Endpoint

### Paso 1: Seleccionar Endpoint

En el panel izquierdo, expandir una carpeta (ej: **clientes**) y hacer click en un endpoint.

Ej: **"01 Listar Clientes"**

### Paso 2: Ver Request

El panel central muestra:
- **URL**: `http://localhost:8080/sabor-gourmet/clientes`
- **Método**: GET, POST, etc.
- **Headers**: Accept, Content-Type, etc.
- **Body**: (si aplica) JSON o Form-urlencoded
- **Query**: Parámetros opcionales

### Paso 3: Enviar Request

Click en el botón **"Send"** (azul, lado derecho arriba) o presionar `Ctrl+Enter`

### Paso 4: Ver Respuesta

El panel derecho mostrará:
- **Status**: HTTP 200, 404, 500, etc.
- **Body**: Respuesta JSON
- **Headers**: Headers de respuesta
- **Tiempo**: Cuánto tardó

---

## 💡 Tips Útiles

### Copiar Valores de Respuesta

A veces necesitas usar IDs de una respuesta en otro endpoint:

1. Ejecutar endpoint (ej: Crear Cliente)
2. En la respuesta, ver el `id` generado (ej: 21)
3. En Variables, cambiar `cliente_id: 21`
4. Usar ese ID en otros endpoints

### Precarga de Variables

Si ejecutas **"Crear Cliente"** primero:

```json
{
  "id": 21,  ← Copiar este ID
  "nombre": "Carlos García",
  "email": "carlos.garcia@example.com"
}
```

Luego cambiar en **Variables**: `cliente_id: 21`

Así los siguientes endpoints usarán el cliente creado.

### Ver Documentación de Endpoint

Cada endpoint tiene documentación integrada:

1. Click en el endpoint
2. En el panel derecho, expandir sección **"Docs"**
3. Verás descripción, parámetros, validaciones, ejemplos

### Historial de Requests

Bruno guarda el historial automáticamente:

1. Click en el ícono de **Reloj** (History)
2. Ver todas las requests enviadas
3. Click para volver a ejecutar

---

## 🔄 Flujo Completo de Ejemplo

### Crear → Obtener → Editar → Listar

```
1. Crear Cliente
   POST /clientes
   ↓
   Respuesta: id: 21

2. Copiar ID 21 en Variables
   ↓

3. Obtener Cliente por ID
   GET /clientes/21
   ↓
   Respuesta: Cliente con id: 21

4. Editar Cliente
   POST /clientes/21
   ↓
   Respuesta: Cliente actualizado

5. Listar Clientes
   GET /clientes
   ↓
   Respuesta: Cliente 21 en lista
```

---

## ⚠️ Problemas Comunes

### Error: "Failed to connect"
**Solución:**
- Verificar que la app está corriendo: `mvn spring-boot:run`
- Verificar que la BD está levantada: `docker compose up -d`
- Verificar que estás usando el puerto correcto (8080)

### Error: "404 Not Found"
**Solución:**
- El cliente/mesa/reserva no existe
- Verificar que el ID es correcto
- Crear un nuevo cliente/mesa/reserva primero

### Error: "400 Bad Request"
**Solución:**
- Revisar que los parámetros son válidos
- Ver la documentación del endpoint (Docs)
- Verificar formato de JSON/datos

### Request no se envía
**Solución:**
- Presionar `Ctrl+Enter` o click en "Send"
- Verificar que Bruno tiene conexión a internet
- Reiniciar Bruno

---

## 📞 Ayuda

### Documentación Oficial de Bruno
https://docs.usebruno.com/

### Atajo de Teclados Útiles
| Atajo | Acción |
|-------|--------|
| `Ctrl+Enter` | Enviar request |
| `Ctrl+L` | Limpiar salida |
| `Ctrl+K` | Buscar endpoint |
| `F12` | DevTools |

### Contacto
Para dudas sobre los endpoints o la API, ver:
- `bruno/README.md` - Documentación completa
- `README.md` - Documentación del proyecto
- `INSTALL.md` - Guía de instalación
- `TESTING.md` - Guía de pruebas

---

## ✅ Checklist de Configuración

- [ ] Bruno instalado en Windows
- [ ] Carpeta `bruno/` abierta en Bruno
- [ ] Entorno **"Local Development"** seleccionado
- [ ] Aplicación Sabor Gourmet corriendo (`mvn spring-boot:run`)
- [ ] Base de datos corriendo (`docker compose up -d`)
- [ ] Ejecutar "Listar Clientes" y obtener respuesta 200 OK
- [ ] Variables se actualizan correctamente

**Si todo está ✅, ¡estás listo para hacer pruebas!**

---

**¡Buenas pruebas! 🚀**


