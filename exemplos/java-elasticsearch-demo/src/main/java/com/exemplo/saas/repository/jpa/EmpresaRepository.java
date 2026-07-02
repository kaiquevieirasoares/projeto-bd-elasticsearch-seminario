package com.exemplo.saas.repository.jpa;

import com.exemplo.saas.model.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    
    List<Empresa> findByDataAtualizacaoAfter(LocalDateTime data);
    boolean existsByCnpj(String cnpj);
}
