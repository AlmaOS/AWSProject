package com.project.RestAppAWS.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.RestAppAWS.dto.AlumnoDTO;
import com.project.RestAppAWS.dto.model.AlumnoData;
import com.project.RestAppAWS.services.AlumnoService;

import java.util.List;

@RestController
@RequestMapping("/alumnos")
@CrossOrigin(origins = {"*"})
public class AlumnoController {
    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService){
        this.alumnoService = alumnoService;
    }

    @GetMapping("")
    public ResponseEntity<List<AlumnoDTO>> getAllAlumnos() {
        List<AlumnoDTO> alumnos = this.alumnoService.getAlumnos();
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDTO> getAlumnoById(@PathVariable int id){
        AlumnoDTO newAlumno = this.alumnoService.getAlumnoById(id);
        return new ResponseEntity<>(newAlumno,HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new student")
    public ResponseEntity<AlumnoDTO> createAlumno(@Valid @RequestBody AlumnoData info){
        AlumnoDTO newAlumno = this.alumnoService.createAlumno(info);
        return new ResponseEntity<>(newAlumno,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoDTO> editAlumnoByid(@Valid @RequestBody AlumnoData info, @PathVariable int id){
        AlumnoDTO newAlumno = this.alumnoService.actualizar(id,info);
        return new ResponseEntity<>(newAlumno,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlumnoDTO> delete(@PathVariable int id){
        AlumnoDTO newAlumno = this.alumnoService.deleteAlumno(id);
        if(newAlumno != null){
            return new ResponseEntity<>(newAlumno,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
