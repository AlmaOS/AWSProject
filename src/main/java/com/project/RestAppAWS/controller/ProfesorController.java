package com.project.RestAppAWS.controller;

import com.project.RestAppAWS.dto.ProfesorDTO;
import com.project.RestAppAWS.dto.model.ProfesorData;
import com.project.RestAppAWS.services.ProfesorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesores")
@CrossOrigin(origins = {"*"})
public class ProfesorController {
    private final ProfesorService profesorService;

    public ProfesorController(ProfesorService profesorService){
        this.profesorService = profesorService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProfesorDTO>> getAllProfesors() {
        List<ProfesorDTO> profesores = this.profesorService.getProfesores();
        return new ResponseEntity<>(profesores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDTO> getProfesorById(@PathVariable int id){
        ProfesorDTO newProfesor = this.profesorService.getProfesorById(id);
        return new ResponseEntity<>(newProfesor,HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new student")
    public ResponseEntity<ProfesorDTO> createProfesor(@Valid @RequestBody ProfesorData info){
        ProfesorDTO newProfesor = this.profesorService.createProfesor(info);
        return new ResponseEntity<>(newProfesor,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfesorDTO> editProfesorByid(@Valid @RequestBody ProfesorData info, @PathVariable int id){
        ProfesorDTO newProfesor = this.profesorService.actualizar(id,info);
        return new ResponseEntity<>(newProfesor,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProfesorDTO> delete(@PathVariable int id){
        ProfesorDTO newProfesor = this.profesorService.deleteProfesor(id);
        return new ResponseEntity<>(newProfesor,HttpStatus.OK);
    }

}
