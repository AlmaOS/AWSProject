package com.project.RestAppAWS.dto.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProfesorData {
    private int id;
    @Positive(message = "Error: Ingresar un valor mayor a 0")
    private int numeroEmpleado;
    @NotNull
    @NotBlank
    private String nombres;
    @NotNull
    @NotBlank
    private String apellidos;
    @Positive(message = "Error: Ingresar un valor mayor a 0")
    private int horasClase;
}
