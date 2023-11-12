package com.project.RestAppAWS.services;

import org.springframework.stereotype.Service;

import com.project.RestAppAWS.dto.AlumnoDTO;
import com.project.RestAppAWS.dto.model.AlumnoData;

import java.util.*;
@Service
public class AlumnoService {
    private List<AlumnoDTO> alumnos = new ArrayList<>();

    public AlumnoService() {
        this.alumnos = new ArrayList<>();
    }

    public List<AlumnoDTO> getAlumnos() {
        return alumnos;
    }

    public AlumnoDTO getAlumnoById(int id){
        for (AlumnoDTO alumno : alumnos) {
            if (alumno.getId() == id) {
                return alumno;
            }
        }
        return null;
    }

    public AlumnoDTO createAlumno(AlumnoData alumnoAux){
        AlumnoDTO nuevoAlumno = new AlumnoDTO(alumnoAux.getId(), alumnoAux.getNombres(), alumnoAux.getApellidos(), alumnoAux.getMatricula(), alumnoAux.getPromedio());

        alumnos.add(nuevoAlumno);
        return nuevoAlumno;
    }

    public AlumnoDTO actualizar(int id, AlumnoData alumnoAux){
        Optional<AlumnoDTO> alumnoExistente = alumnos.stream().filter(a -> a.getId() == id).findFirst();
        alumnoExistente.ifPresent(value -> {
            value.setNombres(alumnoAux.getNombres());
            value.setApellidos(alumnoAux.getApellidos());
            value.setMatricula(alumnoAux.getMatricula());
            value.setPromedio(alumnoAux.getPromedio());
        });
        return null;
    }

    public AlumnoDTO deleteAlumno(int id){
        AlumnoDTO alumnoAEliminar = null;
        for (AlumnoDTO alumno : alumnos) {
            if (alumno.getId() == id) {
                alumnoAEliminar = alumno;
                break; //
            }
        }

        if (alumnoAEliminar != null) {
            alumnos.remove(alumnoAEliminar);
            System.out.println("Alumno con ID " + id + " eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún alumno con el ID: " + id);
        }

        return alumnoAEliminar;
    }

}