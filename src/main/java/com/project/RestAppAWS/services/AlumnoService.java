package com.project.RestAppAWS.services;

import org.springframework.stereotype.Service;

import com.project.RestAppAWS.dto.AlumnoDTO;
import com.project.RestAppAWS.dto.model.AlumnoData;

import java.util.*;
@Service
public class AlumnoService {
    private List<AlumnoDTO> listaAlumnos;

    public AlumnoService() {
        this.listaAlumnos = new ArrayList<>();
    }

    public List<AlumnoDTO> getListaAlumnos() {
        return listaAlumnos;
    }

    public AlumnoDTO getAlumnoById(int id){
        for (AlumnoDTO alumno : listaAlumnos) {
            if (alumno.getId() == id) {
                return alumno;
            }
        }
        return null;
    }

    public AlumnoDTO createAlumno(AlumnoData alumnoAux){
        AlumnoDTO nuevoAlumno = new AlumnoDTO(alumnoAux.getId(), alumnoAux.getNombres(), alumnoAux.getApellidos(), alumnoAux.getMatricula(), alumnoAux.getPromedio());

        listaAlumnos.add(nuevoAlumno);
        return nuevoAlumno;
    }

    public AlumnoDTO actualizar(int id, AlumnoData alumnoAux){
        Optional<AlumnoDTO> alumnoExistente = listaAlumnos.stream().filter(a -> a.getId() == id).findFirst();
        alumnoExistente.ifPresent(value -> {
            value.setNombres(alumnoAux.getNombres());
            value.setApellidos(alumnoAux.getApellidos());
            value.setMatricula(alumnoAux.getMatricula());
            value.setPromedio(alumnoAux.getPromedio());
        });
        return alumnoExistente.orElse(null);    }

    public AlumnoDTO deleteAlumno(int id){
        AlumnoDTO eliminarAlumno = null;
        for (AlumnoDTO alumno : listaAlumnos) {
            if (alumno.getId() == id) {
                eliminarAlumno = alumno;
                break;
            }
        }

        if (eliminarAlumno != null) {
            listaAlumnos.remove(eliminarAlumno);
        }

        return eliminarAlumno;
    }

}