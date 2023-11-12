package com.project.RestAppAWS.services;

import org.springframework.stereotype.Service;

import com.project.RestAppAWS.dto.ProfesorDTO;
import com.project.RestAppAWS.dto.model.ProfesorData;

import java.util.*;
@Service
public class ProfesorService {
    private List<ProfesorDTO> listaProfesores;

    public ProfesorService() {
        this.listaProfesores = new ArrayList<>();
    }

    public List<ProfesorDTO> getListaProfesores() {
        return listaProfesores;
    }

    public ProfesorDTO getProfesorById(int id){
        for (ProfesorDTO profesor : listaProfesores) {
            if (profesor.getId() == id) {
                return profesor;
            }
        }
        return null;
    }

    public ProfesorDTO createProfesor(ProfesorData profesorAux){
        ProfesorDTO nuevoProfesor = new ProfesorDTO(profesorAux.getId(), profesorAux.getNumeroEmpleado(), profesorAux.getNombres(), profesorAux.getApellidos(), profesorAux.getHorasClase());

        listaProfesores.add(nuevoProfesor);
        return nuevoProfesor;
    }

    public ProfesorDTO actualizar(int id, ProfesorData profesorAux){
        Optional<ProfesorDTO> profesorExistente = listaProfesores.stream().filter(a -> a.getId() == id).findFirst();
        profesorExistente.ifPresent(value -> {
            value.setNumeroEmpleado(profesorAux.getNumeroEmpleado());
            value.setNombres(profesorAux.getNombres());
            value.setApellidos(profesorAux.getApellidos());
            value.setHorasClase(profesorAux.getHorasClase());
        });
        return profesorExistente.orElse(null);
    }

    public ProfesorDTO deleteProfesor(int id){
        ProfesorDTO eliminarProfesor = null;
        for (ProfesorDTO profesor : listaProfesores) {
            if (profesor.getId() == id) {
                eliminarProfesor = profesor;
                break;
            }
        }

        if (eliminarProfesor != null) {
            listaProfesores.remove(eliminarProfesor);
        }

        return eliminarProfesor;
    }

}