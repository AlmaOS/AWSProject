package com.project.RestAppAWS.repositories;

import com.project.RestAppAWS.schema.ProfesorEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryProfesor extends JpaRepository<ProfesorEntity, Integer> {
    @NotNull
    List<ProfesorEntity> findAll();
    Optional<ProfesorEntity> findById(int id);
}
