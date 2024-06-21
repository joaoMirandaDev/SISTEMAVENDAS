package com.example.Sistema.Colaborador.repository;

import com.example.Sistema.Colaborador.model.Colaborador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboradorRepository extends PagingAndSortingRepository<Colaborador, Integer>, JpaRepository<Colaborador, Integer>, JpaSpecificationExecutor<Colaborador> {

    Colaborador findByCpf(String cpf);
}
