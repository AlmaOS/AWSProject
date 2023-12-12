package com.project.RestAppAWS.services;

import com.project.RestAppAWS.dto.model.ProfesorData;
import com.project.RestAppAWS.repositories.RepositoryProfesor;
import com.project.RestAppAWS.schema.ProfesorEntity;
import org.springframework.stereotype.Service;

import com.project.RestAppAWS.dto.ProfesorDTO;

import java.util.*;
@Service
public class ProfesorService {
    private RepositoryProfesor repositoryProfesor;

    public ProfesorService(RepositoryProfesor repositoryProfesor){
        this.repositoryProfesor = repositoryProfesor;
    }

    public List<ProfesorDTO> getProfesores(){
        return repositoryProfesor
                .findAll()
                .stream()
                .map(ProfesorDTO::getSchema)
                .toList();
    }

    public ProfesorDTO getProfesorById(int id){
        Optional<ProfesorEntity> profesor = repositoryProfesor
                .findById(id);
        return profesor.map(ProfesorDTO::getSchema).orElse(null);
    }

    public ProfesorDTO createProfesor(ProfesorData profesorAux){
        if(repositoryProfesor.findById(profesorAux.getId()).isPresent()){
            throw SameIDException
                    .builder()
                    .message("Este ID ya no está disponible")
                    .build();
        }

        ProfesorEntity profesorSchema = new ProfesorEntity();
        profesorSchema.setNombres(profesorAux.getNombres());
        profesorSchema.setApellidos(profesorAux.getApellidos());
        profesorSchema.setHorasClase(profesorAux.getHorasClase());
        profesorSchema.setNumeroEmpleado(profesorAux.getNumeroEmpleado());

        ProfesorEntity profesorGuadado = repositoryProfesor.save(profesorSchema);

        return ProfesorDTO.getSchema(profesorGuadado);

    }

    public ProfesorDTO actualizar(int id, ProfesorData profesorAux){
        Optional<ProfesorEntity> profesorExistente = repositoryProfesor.findById(id);

        if (profesorExistente.isPresent()) {
            ProfesorEntity profesor = profesorExistente.get();
            profesor.setNumeroEmpleado(profesorAux.getNumeroEmpleado());
            profesor.setNombres(profesorAux.getNombres());
            profesor.setApellidos(profesorAux.getApellidos());
            profesor.setHorasClase(profesorAux.getHorasClase());

            ProfesorEntity profesorActualizado = repositoryProfesor.save(profesor);

            return ProfesorDTO.getSchema(profesorActualizado);
        } else {
            return null;
        }
    }

    public ProfesorDTO deleteProfesor(int id){
        Optional<ProfesorEntity> profesorAEliminar = repositoryProfesor.findById(id);

        if (profesorAEliminar.isPresent()) {
            repositoryProfesor.deleteById(id);
            return ProfesorDTO.getSchema(profesorAEliminar.get());
        } else {
            return null;
        }
    }
}