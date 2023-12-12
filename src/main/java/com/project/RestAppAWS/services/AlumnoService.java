package com.project.RestAppAWS.services;

import com.project.RestAppAWS.dto.model.AlumnoData;
import com.project.RestAppAWS.repositories.RepositoryAlumnos;
import com.project.RestAppAWS.repositories.RepositoryBucketS3;
import com.project.RestAppAWS.repositories.RepositorySNS;
import com.project.RestAppAWS.repositories.RepositorySesiones;
import com.project.RestAppAWS.schema.AlumnoEntity;
import com.project.RestAppAWS.schema.InicioSesion;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.project.RestAppAWS.dto.AlumnoDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
@Service
public class AlumnoService {
    private RepositoryAlumnos repositoryAlumnos;
    private RepositorySNS repositorySNS;
    private RepositorySesiones repositorySesiones;
    private RepositoryBucketS3 repositoryBucketS3;

    public AlumnoService(RepositoryAlumnos repositoryAlumnos, RepositorySNS repositorySNS, RepositorySesiones repositorySesiones, RepositoryBucketS3 repositoryBucketS3) {
        this.repositoryAlumnos = repositoryAlumnos;
        this.repositorySNS = repositorySNS;
        this.repositorySesiones = repositorySesiones;
        this.repositoryBucketS3 = repositoryBucketS3;
    }

    public List<AlumnoDTO> getAlumnos() {
        return repositoryAlumnos
                .findAll()
                .stream()
                .map(AlumnoDTO::getSchemaDB)
                .toList();
    }

    public AlumnoDTO getAlumnoById(int id){
        Optional<AlumnoEntity> alumno = repositoryAlumnos.findById(id);
        return alumno.map(AlumnoDTO::getSchemaDB).orElse(null);
    }

    public AlumnoDTO createAlumno(AlumnoData alumnoAux){
        if(repositoryAlumnos.findById(alumnoAux.getId()).isPresent()){
            throw SameIDException
                    .builder()
                    .message("Este ID ya no está disponible")
                    .build();
        }

        AlumnoEntity alumnoDB = new AlumnoEntity();
        alumnoDB.setNombres(alumnoAux.getNombres());
        alumnoDB.setApellidos(alumnoAux.getApellidos());
        alumnoDB.setMatricula(alumnoAux.getMatricula());
        alumnoDB.setPromedio(alumnoAux.getPromedio());
        alumnoDB.setPassword(alumnoAux.getPassword());

        AlumnoEntity alumnoGuardado = repositoryAlumnos.save(alumnoDB);

        return AlumnoDTO.getSchemaDB(alumnoGuardado);
    }

    public AlumnoDTO actualizar(int id, AlumnoData alumnoAux){
        Optional<AlumnoEntity> alumnoExistente = repositoryAlumnos.findById(id);
        if (alumnoExistente.isPresent()) {
            AlumnoEntity alumno = alumnoExistente.get();
            alumno.setNombres(alumnoAux.getNombres());
            alumno.setApellidos(alumnoAux.getApellidos());
            alumno.setMatricula(alumnoAux.getMatricula());
            alumno.setPromedio(alumnoAux.getPromedio());

            AlumnoEntity alumnoActualizado = repositoryAlumnos.save(alumno);

            return AlumnoDTO.getSchemaDB(alumnoActualizado);
        } else {
            return null;
        }
    }

    public AlumnoDTO deleteAlumno(int id){
        Optional<AlumnoEntity> alumnoAEliminar = repositoryAlumnos.findById(id);
        if (alumnoAEliminar.isPresent()) {
            repositoryAlumnos.deleteById(id);
            System.out.println("Alumno con ID " + id + " eliminado correctamente.");
            return AlumnoDTO.getSchemaDB(alumnoAEliminar.get());
        } else {
            System.out.println("No se encontró ningún alumno con el ID: " + id);
            return null;
        }
    }

    public InicioSesion createSesion(int alumnoId, String info){
        Optional<AlumnoEntity> alumnoOptional = repositoryAlumnos.findById(alumnoId);
        InicioSesion sesion = new InicioSesion();
        if(alumnoOptional.isPresent()) {
            AlumnoEntity alumno = alumnoOptional.get();

            if (info.equals(alumno.getPassword())) {
                String uuid = UUID.randomUUID().toString();
                sesion.setId(uuid);
                sesion.setAlumnoId(alumnoId);  // Asigna el alumno a la sesión si es necesario
                sesion.setActive(Boolean.TRUE);
                sesion.setFecha(System.currentTimeMillis());
                sesion.setSessionString(getRandomString(128));
                repositorySesiones.save(sesion);
                return sesion;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public InicioSesion verifyActiveSesion(String sessionString){
        InicioSesion sesionAux = repositorySesiones.getSessionBySessionString(sessionString);
        if (sesionAux != null && sesionAux.getActive()) {
            return sesionAux;
        }
        return null;
    }

    public InicioSesion logOut(String sessionString){
        InicioSesion sesionAux = repositorySesiones.getSessionBySessionString(sessionString);
        if (sesionAux != null && sesionAux.getActive()) {
            sesionAux.setActive(false);
            repositorySesiones.save(sesionAux);
            return sesionAux;
        }
        return null;
    }

    public static String getRandomString(int length) {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return  random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public AlumnoDTO uploadPhoto(int id, MultipartFile file){
        String filename = file.getOriginalFilename();
        try {
            File tempFile = convertMultiPartToFile(file);
            Optional<AlumnoEntity> alumnoExistente = repositoryAlumnos.findById(id);
            if (alumnoExistente.isPresent()) {
                AlumnoEntity alumno = alumnoExistente.get();
                alumno.setFotoPerfilUrl("https://s3.amazonaws.com/a17001203.com/"+filename);
                AlumnoEntity alumnoActualizado = repositoryAlumnos.save(alumno);
                repositoryBucketS3.uploadFileS3( "a17001203.com", filename,tempFile);
                return AlumnoDTO.getSchemaDB(alumnoActualizado);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public AlumnoDTO sendEmail(int id){
        Optional<AlumnoEntity> alumnoExistente = repositoryAlumnos.findById(id);
        if (alumnoExistente.isPresent()) {
            AlumnoEntity alumno = alumnoExistente.get();
            repositorySNS.sendEmail("Buen día, aquí se encuentra su información. \nID:"+alumno.getId()+"\nPromedio: "+alumno.getPromedio()+"\nNombres: "+ alumno.getNombres()+"\nApellidos: "+alumno.getApellidos(),"MensajeProyecto");
            return AlumnoDTO.getSchemaDB(alumno);
        }else{
            return null;
        }
    }

}