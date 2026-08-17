package com.saborgourmet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad que representa una reserva de mesa en el restaurante
 */
@Entity
@Table(name = "reservas", indexes = {
    @Index(name = "idx_cliente_id", columnList = "cliente_id"),
    @Index(name = "idx_mesa_id", columnList = "mesa_id"),
    @Index(name = "idx_fecha_reserva", columnList = "fecha_reserva"),
    @Index(name = "idx_estado", columnList = "estado")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La reserva debe asociarse a un cliente")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull(message = "La reserva debe asociarse a una mesa")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    @NotNull(message = "La fecha de reserva no puede ser nula")
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    @Min(value = 1, message = "El número de comensales debe ser mayor a 0")
    @Column(name = "numero_comensales", nullable = false)
    private Integer numeroComensales;

    @Column(length = 500)
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estado;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "fecha_cancelacion")
    private LocalDateTime fechaCancelacion;

    @Column(length = 500)
    private String motivoCancelacion;

    public enum EstadoReserva {
        PENDIENTE,
        CONFIRMADA,
        ACTIVA,
        COMPLETADA,
        CANCELADA
    }

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoReserva.PENDIENTE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }
}
