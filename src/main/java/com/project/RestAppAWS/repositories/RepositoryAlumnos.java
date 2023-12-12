package com.project.RestAppAWS.repositories;

import com.project.RestAppAWS.schema.AlumnoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryAlumnos extends JpaRepository<AlumnoEntity, Integer> {
    @NotNull
    List<AlumnoEntity> findAll();
    Optional<AlumnoEntity> findById(int id);
}
