package com.project.RestAppAWS.services;

import org.springframework.stereotype.Service;

import com.project.RestAppAWS.dto.ProfesorDTO;
import com.project.RestAppAWS.dto.model.ProfesorData;

import java.util.*;
@Service
public class ProfesorService {
    private List<ProfesorDTO> profesores = new ArrayList<>();

    public ProfesorService() {
        this.profesores = new ArrayList<>();
    }

    public List<ProfesorDTO> getProfesores() {
        return profesores;
    }

    public ProfesorDTO getProfesorById(int id){
        for (ProfesorDTO profesor : profesores) {
            if (profesor.getId() == id) {
                return profesor;
            }
        }
        return null;
    }

    public ProfesorDTO createProfesor(ProfesorData profesorAux){
        ProfesorDTO nuevoProfesor = new ProfesorDTO(profesorAux.getId(), profesorAux.getNumeroEmpleado(), profesorAux.getNombres(), profesorAux.getApellidos(), profesorAux.getHorasClase());

        profesores.add(nuevoProfesor);
        return nuevoProfesor;
    }

    public ProfesorDTO actualizar(int id, ProfesorData profesorAux){
        Optional<ProfesorDTO> profesorExistente = profesores.stream().filter(a -> a.getId() == id).findFirst();
        profesorExistente.ifPresent(value -> {
            value.setNumeroEmpleado(profesorAux.getNumeroEmpleado());
            value.setNombres(profesorAux.getNombres());
            value.setApellidos(profesorAux.getApellidos());
            value.setHorasClase(profesorAux.getHorasClase());
        });
        return null;
    }

    public ProfesorDTO deleteProfesor(int id){
        ProfesorDTO profesorAEliminar = null;
        for (ProfesorDTO profesor : profesores) {
            if (profesor.getId() == id) {
                profesorAEliminar = profesor;
                break;
            }
        }

        if (profesorAEliminar != null) {
            profesores.remove(profesorAEliminar);
            System.out.println("Profesor con ID " + id + " eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún profesor con el ID: " + id);
        }

        return profesorAEliminar;
    }

}