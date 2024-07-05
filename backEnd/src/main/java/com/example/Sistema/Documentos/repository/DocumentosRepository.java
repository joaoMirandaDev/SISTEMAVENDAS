package com.example.Sistema.Documentos.repository;

import com.example.Sistema.Documentos.model.Documentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentosRepository extends JpaRepository<Documentos,Integer> {
    @Query(nativeQuery = true, value = "select * FROM financeiro.arquivos_upload WHERE id= :id")
    Optional<Documentos> find(Short id);

    @Query(nativeQuery = true, value = "select * FROM financeiro.arquivos_upload WHERE route= :key")
    Documentos findByRoute(String key);
}
