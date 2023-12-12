package com.project.RestAppAWS.dto;
import com.project.RestAppAWS.schema.ProfesorEntity;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProfesorDTO {
    private int id;
    private int numeroEmpleado;
    private String nombres;
    private String apellidos;
    private  int horasClase;

    public static ProfesorDTO getSchema(ProfesorEntity profesorDB){
        return ProfesorDTO
                .builder()
                .id(profesorDB.getId())
                .nombres(profesorDB.getNombres())
                .apellidos(profesorDB.getApellidos())
                .horasClase(profesorDB.getHorasClase())
                .numeroEmpleado(profesorDB.getNumeroEmpleado())
                .build();
    }
}
