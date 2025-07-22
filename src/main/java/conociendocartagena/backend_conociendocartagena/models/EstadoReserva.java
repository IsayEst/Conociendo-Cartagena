package conociendocartagena.backend_conociendocartagena.models;

// Enum para definir los posibles estados de una reserva
public enum EstadoReserva { // ¡Aquí está el cambio: añadido 'public'!
    PENDIENTE,    // La reserva ha sido creada pero aún no confirmada
    CONFIRMADA,   // La reserva ha sido confirmada
    CANCELADA,    // La reserva ha sido cancelada
    COMPLETADA,   // La reserva ha sido utilizada (ej. después de la fecha/hora)
    RECHAZADA     // La reserva fue rechazada por el negocio
}