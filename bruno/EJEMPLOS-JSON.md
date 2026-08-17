# Ejemplos de JSON para Requests

Esta carpeta contiene ejemplos de payloads JSON para usar en los endpoints de Sabor Gourmet.

## 📋 Tabla de Contenidos

1. [Clientes](#clientes)
2. [Mesas](#mesas)
3. [Reservas](#reservas)
4. [Respuestas](#respuestas-comunes)

---

## 👥 Clientes

### Crear Cliente - Ejemplo 1 (Básico)

```json
{
  "nombre": "Juan Pérez",
  "email": "juan.perez@example.com",
  "telefono": "3001234567",
  "notas": "Cliente frecuente"
}
```

### Crear Cliente - Ejemplo 2 (VIP)

```json
{
  "nombre": "María García López",
  "email": "maria.garcia@empresas.com",
  "telefono": "3015551234",
  "notas": "Cliente VIP - Preferencia: mesa con vista - Aniversario: 15 de mayo"
}
```

### Crear Cliente - Ejemplo 3 (Corporativo)

```json
{
  "nombre": "Carlos Rodríguez",
  "email": "carlos.rodriguez@corporation.com",
  "telefono": "3108889999",
  "notas": "Empresa Acme Corp - Reuniones frecuentes - Presupuesto: $500.000"
}
```

### Editar Cliente - Ejemplo

```json
{
  "nombre": "Juan Pérez García",
  "email": "juan.nuevoemail@example.com",
  "telefono": "3009999999",
  "notas": "Cliente VIP actualizado - Nuevo teléfono"
}
```

---

## 🪑 Mesas

### Crear Mesa - Ejemplo 1 (Pequeña)

```json
{
  "numeroMesa": "A1",
  "capacidad": 2,
  "ubicacion": "Ventana lado A - Íntima"
}
```

### Crear Mesa - Ejemplo 2 (Mediana)

```json
{
  "numeroMesa": "B5",
  "capacidad": 4,
  "ubicacion": "Centro del restaurante - Sector B"
}
```

### Crear Mesa - Ejemplo 3 (Grande)

```json
{
  "numeroMesa": "C3",
  "capacidad": 8,
  "ubicacion": "Terraza - Sector C - Premium"
}
```

### Crear Mesa - Ejemplo 4 (VIP)

```json
{
  "numeroMesa": "VIP-1",
  "capacidad": 6,
  "ubicacion": "Salón privado VIP - Entrada exclusiva"
}
```

### Editar Mesa - Ejemplo

```json
{
  "numeroMesa": "A1-Premium",
  "capacidad": 3,
  "ubicacion": "Ventana lado A - Íntima - Remodelada 2024"
}
```

---

## 📅 Reservas

### Crear Reserva - Ejemplo 1 (Simple)

```json
{
  "clienteId": 1,
  "mesaId": 2,
  "fechaReserva": "2024-08-25T19:00:00",
  "numeroComensales": 2,
  "observaciones": ""
}
```

### Crear Reserva - Ejemplo 2 (Con Observaciones)

```json
{
  "clienteId": 5,
  "mesaId": 3,
  "fechaReserva": "2024-08-30T20:00:00",
  "numeroComensales": 4,
  "observaciones": "Cumpleaños de María - Traer decoraciones - No alergias conocidas"
}
```

### Crear Reserva - Ejemplo 3 (Grupo Grande)

```json
{
  "clienteId": 8,
  "mesaId": 7,
  "fechaReserva": "2024-09-02T18:30:00",
  "numeroComensales": 12,
  "observaciones": "Reunión corporativa - Acme Corp - Menu degustación - Bebidas premium"
}
```

### Crear Reserva - Ejemplo 4 (Próximo Fin de Semana)

```json
{
  "clienteId": 3,
  "mesaId": 4,
  "fechaReserva": "2024-08-24T21:00:00",
  "numeroComensales": 6,
  "observaciones": "Aniversario - Música romántica recomendada - No mesón"
}
```

### Crear Reserva - Ejemplo 5 (Desayuno/Brunch)

```json
{
  "clienteId": 2,
  "mesaId": 1,
  "fechaReserva": "2024-08-25T10:30:00",
  "numeroComensales": 3,
  "observaciones": "Brunch familiar - Niños pequeños - Silla alta disponible?"
}
```

### Editar Reserva - Ejemplo (Cambiar Fecha/Hora)

```json
{
  "clienteId": 1,
  "mesaId": 5,
  "fechaReserva": "2024-08-27T19:30:00",
  "numeroComensales": 4,
  "observaciones": "Actualizado - Cambio de mesa a sector premium"
}
```

### Cancelar Reserva - Ejemplo

```json
{
  "motivoCancelacion": "Cliente llamó para cancelar - Conflicto laboral - Posible reprogramación próximas semanas"
}
```

---

## 📊 Respuestas Comunes

### Cliente - Respuesta 200 OK (GET)

```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  "email": "juan.perez@example.com",
  "telefono": "3001234567",
  "notas": "Cliente frecuente",
  "fechaRegistro": "2024-08-15T10:30:45"
}
```

### Mesa - Respuesta 200 OK (GET)

```json
{
  "id": 2,
  "numeroMesa": "B5",
  "capacidad": 4,
  "ubicacion": "Centro del restaurante - Sector B",
  "estado": "DISPONIBLE"
}
```

### Reserva - Respuesta 201 Created (POST)

```json
{
  "id": 37,
  "cliente": {
    "id": 1,
    "nombre": "Juan Pérez",
    "email": "juan.perez@example.com",
    "telefono": "3001234567"
  },
  "mesa": {
    "id": 2,
    "numeroMesa": "B5",
    "capacidad": 4,
    "ubicacion": "Centro del restaurante - Sector B"
  },
  "fechaReserva": "2024-08-25T19:00:00",
  "numeroComensales": 2,
  "observaciones": "",
  "estado": "PENDIENTE",
  "fechaCreacion": "2024-08-16T14:30:00",
  "fechaModificacion": "2024-08-16T14:30:00"
}
```

### Reserva después de Confirmar - Respuesta 200 OK

```json
{
  "id": 37,
  "cliente": {
    "id": 1,
    "nombre": "Juan Pérez"
  },
  "mesa": {
    "id": 2,
    "numeroMesa": "B5",
    "estado": "RESERVADA"
  },
  "fechaReserva": "2024-08-25T19:00:00",
  "numeroComensales": 2,
  "estado": "CONFIRMADA",
  "fechaModificacion": "2024-08-16T14:35:00"
}
```

### Reserva después de Activar - Respuesta 200 OK

```json
{
  "id": 37,
  "cliente": {
    "id": 1,
    "nombre": "Juan Pérez"
  },
  "mesa": {
    "id": 2,
    "numeroMesa": "B5",
    "estado": "OCUPADA"
  },
  "fechaReserva": "2024-08-25T19:00:00",
  "numeroComensales": 2,
  "estado": "ACTIVA",
  "fechaModificacion": "2024-08-25T19:15:00"
}
```

### Reserva después de Completar - Respuesta 200 OK

```json
{
  "id": 37,
  "cliente": {
    "id": 1,
    "nombre": "Juan Pérez"
  },
  "mesa": {
    "id": 2,
    "numeroMesa": "B5",
    "estado": "DISPONIBLE"
  },
  "fechaReserva": "2024-08-25T19:00:00",
  "numeroComensales": 2,
  "estado": "COMPLETADA",
  "fechaModificacion": "2024-08-25T21:30:00"
}
```

### Reserva después de Cancelar - Respuesta 200 OK

```json
{
  "id": 37,
  "cliente": {
    "id": 1,
    "nombre": "Juan Pérez"
  },
  "mesa": {
    "id": 2,
    "numeroMesa": "B5",
    "estado": "DISPONIBLE"
  },
  "fechaReserva": "2024-08-25T19:00:00",
  "numeroComensales": 2,
  "estado": "CANCELADA",
  "fechaCancelacion": "2024-08-16T16:00:00",
  "motivoCancelacion": "Cliente llamó para cancelar - Conflicto laboral",
  "fechaModificacion": "2024-08-16T16:00:00"
}
```

### Listar Clientes - Respuesta 200 OK (Paginada)

```json
{
  "content": [
    {
      "id": 1,
      "nombre": "Juan Pérez",
      "email": "juan.perez@example.com",
      "telefono": "3001234567",
      "notas": "Cliente frecuente",
      "fechaRegistro": "2024-08-15T10:30:45"
    },
    {
      "id": 2,
      "nombre": "María García",
      "email": "maria.garcia@example.com",
      "telefono": "3015551234",
      "notas": "Cliente VIP",
      "fechaRegistro": "2024-08-14T09:15:00"
    }
  ],
  "totalElements": 20,
  "totalPages": 2,
  "currentPage": 0,
  "pageSize": 10
}
```

---

## 🛠️ Cómo Copiar y Usar Estos Ejemplos

### En Bruno:

1. Abrir el endpoint en Bruno
2. Click en **"Body"** tab
3. Click en el ícono **"Paste"** o simplemente pegar con `Ctrl+V`
4. Copiar el JSON de arriba y pegarlo
5. Modificar valores según sea necesario
6. Click en **"Send"**

### Tips:

- **Fechas**: Siempre usar formato ISO `YYYY-MM-DDTHH:mm:ss`
- **Teléfono**: Debe tener exactamente 10 dígitos
- **Email**: Debe ser válido y único en el sistema
- **Comensales**: No puede exceder la capacidad de la mesa
- **Observaciones**: Máximo 500 caracteres

---

**¡Listos para probar! 🚀**


