package com.project.RestAppAWS.dto;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AlumnoDTO {
    private int id;
    private String nombres;
    private String apellidos;
    private int matricula;
    private  double promedio;
}
