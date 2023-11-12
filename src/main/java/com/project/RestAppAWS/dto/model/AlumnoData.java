package com.project.RestAppAWS.dto.model;
import jakarta.validation.constraints.*;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AlumnoData {
    private int id;
    @NotNull
    @NotBlank
    private String nombres;
    @NotNull
    @NotBlank
    private String apellidos;
    @Pattern(regexp = "^[a-zA-Z]+\\d+$", message = "La matrícula no cumple con el formato adecuado, debe comenzar con una letra y posteriormente al menos un número")
    private int matricula;
    @DecimalMin(value = "0.0", inclusive = true, message = "Error: Ingresar un valor mayor a 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Error: Ingresar un valor menor o igual a 100")
    private  double promedio;
}
