package com.project.RestAppAWS.services;
import com.project.RestAppAWS.repositories.RepositorySesiones;
import com.project.RestAppAWS.schema.InicioSesion;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SesionService {
    private final RepositorySesiones repositorySesiones;

    public SesionService(RepositorySesiones sesionRepository) {
        this.repositorySesiones = sesionRepository;
    }

    public InicioSesion createSesion(InicioSesion sesion){
        String uuid = UUID.randomUUID().toString();
        sesion.setId(uuid);
        sesion.setActive(true);
        sesion.setFecha(System.currentTimeMillis());
        repositorySesiones.save(sesion);
        return sesion;
    }

    public InicioSesion verifySesion(String sessionString){
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
}
