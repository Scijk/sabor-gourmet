-- ============================================================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS - Sabor Gourmet
-- Sistema de Gestión de Reservas para Restaurantes
-- Base de Datos: PostgreSQL
-- ============================================================================

-- Crear base de datos
CREATE DATABASE sabor_gourmet_db
    WITH 
    ENCODING 'UTF8'
    LOCALE 'es_ES.UTF-8'
    TEMPLATE template0;

-- Conectar a la base de datos
\c sabor_gourmet_db;

-- ============================================================================
-- TABLA: CLIENTES
-- ============================================================================
CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    notas VARCHAR(500),
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para clientes
CREATE INDEX idx_clientes_email ON clientes(email);
CREATE INDEX idx_clientes_telefono ON clientes(telefono);
CREATE INDEX idx_clientes_fecha_registro ON clientes(fecha_registro);

-- ============================================================================
-- TABLA: MESAS
-- ============================================================================
CREATE TABLE mesas (
    id BIGSERIAL PRIMARY KEY,
    numero_mesa VARCHAR(50) NOT NULL UNIQUE,
    capacidad INTEGER NOT NULL,
    ubicacion VARCHAR(500),
    estado VARCHAR(20) NOT NULL DEFAULT 'DISPONIBLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para mesas
CREATE INDEX idx_mesas_numero ON mesas(numero_mesa);
CREATE INDEX idx_mesas_estado ON mesas(estado);
CREATE INDEX idx_mesas_capacidad ON mesas(capacidad);

-- ============================================================================
-- TABLA: RESERVAS
-- ============================================================================
CREATE TABLE reservas (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    mesa_id BIGINT NOT NULL,
    fecha_reserva TIMESTAMP NOT NULL,
    numero_comensales INTEGER NOT NULL,
    observaciones VARCHAR(500),
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_cancelacion TIMESTAMP,
    motivo_cancelacion VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    FOREIGN KEY (mesa_id) REFERENCES mesas(id) ON DELETE RESTRICT
);

-- Crear índices para reservas
CREATE INDEX idx_reservas_cliente_id ON reservas(cliente_id);
CREATE INDEX idx_reservas_mesa_id ON reservas(mesa_id);
CREATE INDEX idx_reservas_fecha_reserva ON reservas(fecha_reserva);
CREATE INDEX idx_reservas_estado ON reservas(estado);
CREATE INDEX idx_reservas_fecha_creacion ON reservas(fecha_creacion);

-- ============================================================================
-- DATOS DE PRUEBA
-- ============================================================================

-- Insertar clientes de prueba
INSERT INTO clientes (nombre, email, telefono, notas) VALUES
('Juan García López', 'juan.garcia@email.com', '3001234567', 'Cliente VIP - Alérgico a mariscos'),
('María Rodríguez', 'maria.rodriguez@email.com', '3002345678', 'Cumpleaños en enero'),
('Carlos Martínez', 'carlos.martinez@email.com', '3003456789', 'Preferencia: Mesa ventana'),
('Ana Fernández', 'ana.fernandez@email.com', '3004567890', 'Cena de negocios frecuente'),
('Luis Sánchez', 'luis.sanchez@email.com', '3005678901', 'Grupo grande (10+ personas)'),
('Sandra Pérez', 'sandra.perez@email.com', '3006789012', 'Vegetariana'),
('Roberto García', 'roberto.garcia@email.com', '3007890123', 'Primera reserva'),
('Marta López', 'marta.lopez@email.com', '3008901234', 'Aniversario en marzo'),
('Fernando Ruiz', 'fernando.ruiz@email.com', '3009012345', 'Reservas frecuentes'),
('Elena Torres', 'elena.torres@email.com', '3000123456', 'Requiere acceso silla de ruedas');

-- Insertar mesas de prueba
INSERT INTO mesas (numero_mesa, capacidad, ubicacion, estado) VALUES
('Mesa 1', 2, 'Entrada - Zona Privada', 'DISPONIBLE'),
('Mesa 2', 2, 'Entrada - Zona Privada', 'DISPONIBLE'),
('Mesa 3', 4, 'Centro - Zona General', 'DISPONIBLE'),
('Mesa 4', 4, 'Centro - Zona General', 'DISPONIBLE'),
('Mesa 5', 4, 'Centro - Zona General', 'DISPONIBLE'),
('Mesa 6', 6, 'Ventana - Zona Premium', 'DISPONIBLE'),
('Mesa 7', 6, 'Ventana - Zona Premium', 'DISPONIBLE'),
('Mesa 8', 8, 'Fondo - Eventos Privados', 'DISPONIBLE'),
('Mesa 9', 8, 'Fondo - Eventos Privados', 'DISPONIBLE'),
('Barra 1', 2, 'Barra Principal', 'DISPONIBLE'),
('Barra 2', 2, 'Barra Principal', 'DISPONIBLE'),
('Terraza 1', 4, 'Terraza Exterior', 'DISPONIBLE'),
('Terraza 2', 4, 'Terraza Exterior', 'DISPONIBLE');

-- Insertar reservas de prueba
INSERT INTO reservas (cliente_id, mesa_id, fecha_reserva, numero_comensales, observaciones, estado) VALUES
-- Reservas próximas (próximos 7 días)
(1, 6, CURRENT_TIMESTAMP + INTERVAL '2 days 20:00', 4, 'Mesa con vista al jardín', 'CONFIRMADA'),
(2, 3, CURRENT_TIMESTAMP + INTERVAL '3 days 19:30', 4, 'Cumpleaños - Pastel a las 21:00', 'CONFIRMADA'),
(3, 7, CURRENT_TIMESTAMP + INTERVAL '4 days 20:30', 6, '', 'CONFIRMADA'),
(4, 4, CURRENT_TIMESTAMP + INTERVAL '5 days 18:00', 2, 'Reunión de negocios', 'PENDIENTE'),
(5, 8, CURRENT_TIMESTAMP + INTERVAL '6 days 19:00', 10, 'Grupo de amigos', 'CONFIRMADA'),
(6, 5, CURRENT_TIMESTAMP + INTERVAL '1 day 20:00', 2, 'Menú vegetariano', 'CONFIRMADA'),
(7, 1, CURRENT_TIMESTAMP + INTERVAL '2 days 19:00', 2, '', 'PENDIENTE'),
(8, 2, CURRENT_TIMESTAMP + INTERVAL '7 days 20:00', 2, 'Aniversario - Champagne', 'CONFIRMADA'),

-- Reservas pasadas (completadas)
(9, 3, CURRENT_TIMESTAMP - INTERVAL '1 days 20:00', 4, '', 'COMPLETADA'),
(10, 4, CURRENT_TIMESTAMP - INTERVAL '2 days 19:00', 2, 'Acceso adaptado', 'COMPLETADA'),

-- Reserva cancelada
(1, 5, CURRENT_TIMESTAMP + INTERVAL '10 days 20:00', 4, '', 'CANCELADA');

-- ============================================================================
-- VISTAS ÚTILES PARA REPORTES
-- ============================================================================

-- Vista: Reservas activas
CREATE VIEW v_reservas_activas AS
SELECT 
    r.id,
    c.nombre as cliente_nombre,
    c.email,
    c.telefono,
    m.numero_mesa,
    m.capacidad,
    r.fecha_reserva,
    r.numero_comensales,
    r.estado,
    CASE 
        WHEN r.fecha_reserva > CURRENT_TIMESTAMP THEN 'Futura'
        WHEN r.fecha_reserva <= CURRENT_TIMESTAMP AND r.estado = 'ACTIVA' THEN 'En Curso'
        ELSE 'Pasada'
    END as tipo_reserva
FROM reservas r
JOIN clientes c ON r.cliente_id = c.id
JOIN mesas m ON r.mesa_id = m.id
WHERE r.estado IN ('CONFIRMADA', 'ACTIVA')
ORDER BY r.fecha_reserva ASC;

-- Vista: Disponibilidad de mesas por fecha
CREATE VIEW v_disponibilidad_mesas AS
SELECT 
    m.id,
    m.numero_mesa,
    m.capacidad,
    m.ubicacion,
    m.estado,
    COALESCE(COUNT(r.id), 0) as reservas_vigentes
FROM mesas m
LEFT JOIN reservas r ON m.id = r.mesa_id 
    AND r.estado IN ('CONFIRMADA', 'ACTIVA')
    AND r.fecha_reserva > CURRENT_TIMESTAMP
GROUP BY m.id, m.numero_mesa, m.capacidad, m.ubicacion, m.estado
ORDER BY m.numero_mesa;

-- Vista: Estadísticas de clientes
CREATE VIEW v_estadisticas_clientes AS
SELECT 
    c.id,
    c.nombre,
    c.email,
    COUNT(r.id) as total_reservas,
    COUNT(CASE WHEN r.estado = 'COMPLETADA' THEN 1 END) as reservas_completadas,
    COUNT(CASE WHEN r.estado = 'CANCELADA' THEN 1 END) as reservas_canceladas,
    MAX(r.fecha_reserva) as ultima_reserva
FROM clientes c
LEFT JOIN reservas r ON c.id = r.cliente_id
GROUP BY c.id, c.nombre, c.email
ORDER BY total_reservas DESC;

-- ============================================================================
-- PROCEDIMIENTOS ALMACENADOS
-- ============================================================================

-- Procedimiento: Obtener mesas disponibles para una fecha y capacidad
CREATE OR REPLACE FUNCTION obtener_mesas_disponibles(
    p_fecha_reserva TIMESTAMP,
    p_capacidad INTEGER,
    p_rango_horas INTEGER DEFAULT 2
)
RETURNS TABLE (
    mesa_id BIGINT,
    numero_mesa VARCHAR,
    capacidad INTEGER,
    ubicacion VARCHAR
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        m.id,
        m.numero_mesa,
        m.capacidad,
        m.ubicacion
    FROM mesas m
    WHERE 
        m.estado = 'DISPONIBLE'
        AND m.capacidad >= p_capacidad
        AND m.id NOT IN (
            SELECT r.mesa_id
            FROM reservas r
            WHERE r.estado IN ('CONFIRMADA', 'ACTIVA')
            AND r.fecha_reserva BETWEEN p_fecha_reserva - INTERVAL '1 hour' * p_rango_horas 
                                    AND p_fecha_reserva + INTERVAL '1 hour' * p_rango_horas
        )
    ORDER BY m.capacidad ASC;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Registrar nueva reserva
CREATE OR REPLACE FUNCTION crear_reserva(
    p_cliente_id BIGINT,
    p_mesa_id BIGINT,
    p_fecha_reserva TIMESTAMP,
    p_numero_comensales INTEGER,
    p_observaciones VARCHAR DEFAULT NULL
)
RETURNS BIGINT AS $$
DECLARE
    v_nueva_reserva_id BIGINT;
    v_mesa_capacidad INTEGER;
BEGIN
    -- Validar que la mesa existe y tiene capacidad
    SELECT capacidad INTO v_mesa_capacidad FROM mesas WHERE id = p_mesa_id;
    IF v_mesa_capacidad IS NULL THEN
        RAISE EXCEPTION 'Mesa no encontrada';
    END IF;
    
    IF p_numero_comensales > v_mesa_capacidad THEN
        RAISE EXCEPTION 'El número de comensales excede la capacidad de la mesa';
    END IF;
    
    -- Insertar la reserva
    INSERT INTO reservas (
        cliente_id,
        mesa_id,
        fecha_reserva,
        numero_comensales,
        observaciones,
        estado
    ) VALUES (
        p_cliente_id,
        p_mesa_id,
        p_fecha_reserva,
        p_numero_comensales,
        p_observaciones,
        'PENDIENTE'
    )
    RETURNING id INTO v_nueva_reserva_id;
    
    RETURN v_nueva_reserva_id;
END;
$$ LANGUAGE plpgsql;

-- ============================================================================
-- TRIGGERS
-- ============================================================================

-- Trigger: Actualizar fecha de modificación en reservas
CREATE OR REPLACE FUNCTION actualizar_fecha_modificacion_reservas()
RETURNS TRIGGER AS $$
BEGIN
    NEW.fecha_modificacion = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_actualizar_fecha_modificacion_reservas
BEFORE UPDATE ON reservas
FOR EACH ROW
EXECUTE FUNCTION actualizar_fecha_modificacion_reservas();

-- Trigger: Actualizar fecha de modificación en mesas
CREATE OR REPLACE FUNCTION actualizar_fecha_modificacion_mesas()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_actualizar_fecha_modificacion_mesas
BEFORE UPDATE ON mesas
FOR EACH ROW
EXECUTE FUNCTION actualizar_fecha_modificacion_mesas();

-- Trigger: Actualizar fecha de modificación en clientes
CREATE OR REPLACE FUNCTION actualizar_fecha_modificacion_clientes()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_actualizar_fecha_modificacion_clientes
BEFORE UPDATE ON clientes
FOR EACH ROW
EXECUTE FUNCTION actualizar_fecha_modificacion_clientes();

-- ============================================================================
-- CONFIRMACIÓN DE ÉXITO
-- ============================================================================
SELECT 'Base de datos Sabor Gourmet creada exitosamente!' as mensaje;
SELECT 
    (SELECT COUNT(*) FROM clientes) as total_clientes,
    (SELECT COUNT(*) FROM mesas) as total_mesas,
    (SELECT COUNT(*) FROM reservas) as total_reservas;
