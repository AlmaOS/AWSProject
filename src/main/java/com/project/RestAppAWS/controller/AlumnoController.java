package com.project.RestAppAWS.controller;

import com.project.RestAppAWS.schema.InicioSesion;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.RestAppAWS.dto.AlumnoDTO;
import com.project.RestAppAWS.dto.model.AlumnoData;
import com.project.RestAppAWS.services.AlumnoService;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

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
        AlumnoDTO alumno = this.alumnoService.getAlumnoById(id);
        if(alumno == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(alumno,HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo alumno")
    public ResponseEntity<AlumnoDTO> createAlumno(@Valid @RequestBody AlumnoData info){
        AlumnoDTO nuevoAlumno = this.alumnoService.createAlumno(info);
        return new ResponseEntity<>(nuevoAlumno,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoDTO> editAlumnoByid(@Valid @RequestBody AlumnoData info, @PathVariable int id){
        AlumnoDTO alumno = this.alumnoService.actualizar(id,info);
        return new ResponseEntity<>(alumno,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlumnoDTO> delete(@PathVariable int id){
        AlumnoDTO alumno = this.alumnoService.deleteAlumno(id);
        if(alumno != null){
            return new ResponseEntity<>(alumno,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/session/login")
    @Operation(summary = "Crear una sesion de alumno")
    public ResponseEntity<?> createSesion(@PathVariable int id, @RequestBody Map<String, Object> body){
        String password = (String) body.get("password");
        InicioSesion sesion = this.alumnoService.createSesion(id, password);
        if(sesion != null){
            return new ResponseEntity<>(sesion, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/{id}/session/verify")
    @Operation(summary = "Verificar sesion activa")
    public ResponseEntity<?> verifySesion(@PathVariable int id, @RequestBody InicioSesion sessionString){
        InicioSesion sesion = this.alumnoService.verifyActiveSesion(sessionString.getSessionString());
        if(sesion != null){
            return new ResponseEntity<>(sesion, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(sessionString,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/session/logout")
    @Operation(summary = "Cerrar sesion de alumno")
    public ResponseEntity<?> logout(@PathVariable int id, @RequestBody InicioSesion sessionString){
        InicioSesion sesion = this.alumnoService.logOut(sessionString.getSessionString());
        if(sesion != null){
            return new ResponseEntity<>(sesion, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(sessionString, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/fotoPerfil")
    @Operation(summary = "Subir foto de perfil a la cuenta")
    public ResponseEntity<?> uploadPhotho(@PathVariable int id,@RequestParam("foto") MultipartFile file){
        AlumnoDTO alumnoFoto = this.alumnoService.uploadPhoto(id, file);
        return new ResponseEntity<>(alumnoFoto, HttpStatus.OK);
    }

    @PostMapping("/{id}/email")
    @Operation(summary = "Enviar email con información")
    public ResponseEntity<?> sendEmail(@PathVariable int id){
        AlumnoDTO alumnoFoto = this.alumnoService.sendEmail(id);
        if (alumnoFoto != null){
            return new ResponseEntity<>(alumnoFoto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
