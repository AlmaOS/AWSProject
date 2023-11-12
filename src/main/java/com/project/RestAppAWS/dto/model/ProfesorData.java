package com.project.RestAppAWS.dto.model;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProfesorData {
    private int id;
    private int numeroEmpleado;
    private String nombres;
    private String apellidos;
    private  int horasClase;
}
