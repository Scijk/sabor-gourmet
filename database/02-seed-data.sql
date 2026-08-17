-- ============================================================================
-- SCRIPT DE DATOS ADICIONALES - Sabor Gourmet
-- Insertando más datos de prueba
-- ============================================================================

\c sabor_gourmet_db;

-- ============================================================================
-- INSERTAR MÁS CLIENTES
-- ============================================================================
INSERT INTO clientes (nombre, email, telefono, notas) VALUES
('Beatriz Moreno', 'beatriz.moreno@email.com', '3010234567', 'Organiza eventos corporativos'),
('David Jiménez', 'david.jimenez@email.com', '3011345678', 'Segunda residencia en la ciudad'),
('Patricia González', 'patricia.gonzalez@email.com', '3012456789', 'Cena de parejas cada mes'),
('Raúl Torres', 'raul.torres@email.com', '3013567890', 'Familia con niños pequeños'),
('Verónica Díaz', 'veronica.diaz@email.com', '3014678901', 'Amiga de Juan García'),
('Héctor Romero', 'hector.romero@email.com', '3015789012', 'Celebración de bodas'),
('Lorena Castro', 'lorena.castro@email.com', '3016890123', 'Grupo de trabajo'),
('Javier Medina', 'javier.medina@email.com', '3017901234', 'Reservas para dos personas'),
('Cynthia Rivas', 'cynthia.rivas@email.com', '3018012345', 'Cena especial'),
('Alfredo López', 'alfredo.lopez@email.com', '3019123456', 'Visitante frecuente');

-- ============================================================================
-- INSERTAR MÁS RESERVAS (PRÓXIMOS 30 DÍAS)
-- ============================================================================

-- Reservas para próximos 7 días (CONFIRMADAS)
INSERT INTO reservas (cliente_id, mesa_id, fecha_reserva, numero_comensales, observaciones, estado) VALUES
(11, 6, CURRENT_TIMESTAMP + INTERVAL '1 day 19:00', 6, 'Evento corporativo - cerveza de bienvenida', 'CONFIRMADA'),
(12, 3, CURRENT_TIMESTAMP + INTERVAL '1 day 20:30', 4, 'Celebración promoción laboral', 'CONFIRMADA'),
(13, 12, CURRENT_TIMESTAMP + INTERVAL '2 days 20:00', 2, 'Cena romántica', 'CONFIRMADA'),
(14, 4, CURRENT_TIMESTAMP + INTERVAL '2 days 18:30', 4, 'Familia - Niños menores de 5 años', 'CONFIRMADA'),
(15, 8, CURRENT_TIMESTAMP + INTERVAL '3 days 19:30', 8, 'Grupo de amigos', 'CONFIRMADA'),
(16, 7, CURRENT_TIMESTAMP + INTERVAL '3 days 20:30', 6, 'Cena de negocios - Vegetarianos', 'CONFIRMADA'),
(17, 5, CURRENT_TIMESTAMP + INTERVAL '4 days 19:00', 4, 'Reunión de trabajo', 'CONFIRMADA'),
(18, 1, CURRENT_TIMESTAMP + INTERVAL '4 days 20:00', 2, 'Cita especial', 'CONFIRMADA'),
(19, 2, CURRENT_TIMESTAMP + INTERVAL '5 days 20:30', 2, 'Pareja de amigos', 'CONFIRMADA'),
(20, 9, CURRENT_TIMESTAMP + INTERVAL '5 days 19:00', 8, 'Cumpleaños sorpresa', 'CONFIRMADA');

-- Reservas PENDIENTES (próximas)
INSERT INTO reservas (cliente_id, mesa_id, fecha_reserva, numero_comensales, observaciones, estado) VALUES
(11, 6, CURRENT_TIMESTAMP + INTERVAL '8 days 20:00', 4, '', 'PENDIENTE'),
(13, 7, CURRENT_TIMESTAMP + INTERVAL '10 days 20:00', 2, '', 'PENDIENTE'),
(14, 3, CURRENT_TIMESTAMP + INTERVAL '12 days 19:00', 4, '', 'PENDIENTE'),
(15, 8, CURRENT_TIMESTAMP + INTERVAL '15 days 20:30', 6, 'Gran grupo', 'PENDIENTE');

-- Reservas ACTIVAS (hoy o en las próximas horas)
INSERT INTO reservas (cliente_id, mesa_id, fecha_reserva, numero_comensales, observaciones, estado) VALUES
(9, 3, CURRENT_TIMESTAMP + INTERVAL '6 hours', 4, '', 'CONFIRMADA'),
(10, 4, CURRENT_TIMESTAMP + INTERVAL '3 hours', 2, '', 'CONFIRMADA');

-- Reservas COMPLETADAS (pasadas)
INSERT INTO reservas (cliente_id, mesa_id, fecha_reserva, numero_comensales, observaciones, estado) VALUES
(9, 5, CURRENT_TIMESTAMP - INTERVAL '3 days 20:00', 4, '', 'COMPLETADA'),
(10, 6, CURRENT_TIMESTAMP - INTERVAL '5 days 19:00', 2, '', 'COMPLETADA'),
(11, 3, CURRENT_TIMESTAMP - INTERVAL '7 days 20:30', 6, '', 'COMPLETADA'),
(12, 4, CURRENT_TIMESTAMP - INTERVAL '10 days 20:00', 4, '', 'COMPLETADA'),
(13, 7, CURRENT_TIMESTAMP - INTERVAL '12 days 19:30', 2, '', 'COMPLETADA'),
(14, 8, CURRENT_TIMESTAMP - INTERVAL '14 days 21:00', 6, '', 'COMPLETADA');

-- Reservas CANCELADAS
INSERT INTO reservas (cliente_id, mesa_id, fecha_reserva, numero_comensales, observaciones, estado, fecha_cancelacion, motivo_cancelacion) VALUES
(1, 9, CURRENT_TIMESTAMP + INTERVAL '20 days 20:00', 8, '', 'CANCELADA', CURRENT_TIMESTAMP - INTERVAL '1 day', 'Cambio de planes'),
(2, 1, CURRENT_TIMESTAMP + INTERVAL '22 days 19:00', 2, '', 'CANCELADA', CURRENT_TIMESTAMP - INTERVAL '2 days', 'Enfermedad del cliente'),
(6, 2, CURRENT_TIMESTAMP + INTERVAL '25 days 20:30', 2, '', 'CANCELADA', CURRENT_TIMESTAMP - INTERVAL '3 days', 'Viaje inesperado');

-- ============================================================================
-- ACTUALIZAR ESTADO DE ALGUNAS MESAS
-- ============================================================================
UPDATE mesas SET estado = 'RESERVADA' WHERE numero_mesa IN ('Mesa 6', 'Mesa 7', 'Mesa 8', 'Terraza 1');
UPDATE mesas SET estado = 'OCUPADA' WHERE numero_mesa IN ('Mesa 3', 'Mesa 4');

-- ============================================================================
-- VERIFICACIÓN DE DATOS
-- ============================================================================
SELECT 'Datos insertados exitosamente!' as mensaje;

-- Mostrar resumen
SELECT 
    'CLIENTES' as tabla,
    COUNT(*) as registros
FROM clientes
UNION ALL
SELECT 
    'MESAS' as tabla,
    COUNT(*) as registros
FROM mesas
UNION ALL
SELECT 
    'RESERVAS' as tabla,
    COUNT(*) as registros
FROM reservas;

-- Mostrar estado de mesas
SELECT 
    estado,
    COUNT(*) as cantidad
FROM mesas
GROUP BY estado
ORDER BY cantidad DESC;

-- Mostrar estado de reservas
SELECT 
    estado,
    COUNT(*) as cantidad
FROM reservas
GROUP BY estado
ORDER BY cantidad DESC;
