package com.project.RestAppAWS.dto;
import com.project.RestAppAWS.schema.AlumnoEntity;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AlumnoDTO {
    private int id;
    private String nombres;
    private String apellidos;
    private String matricula;
    private  double promedio;
    private String fotoPerfilUrl;
    private String password;

    public AlumnoDTO(String nombres, String apellidos, String matricula, double promedio) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.matricula = matricula;
        this.promedio = promedio;
    }
    public static AlumnoDTO getSchemaDB(AlumnoEntity alumnoDB){
        return AlumnoDTO
                .builder()
                .id(alumnoDB.getId())
                .nombres(alumnoDB.getNombres())
                .apellidos(alumnoDB.getApellidos())
                .matricula(alumnoDB.getMatricula())
                .promedio(alumnoDB.getPromedio())
                .password(alumnoDB.getPassword())
                .fotoPerfilUrl(alumnoDB.getFotoPerfilUrl())
                .build();
    }
}
