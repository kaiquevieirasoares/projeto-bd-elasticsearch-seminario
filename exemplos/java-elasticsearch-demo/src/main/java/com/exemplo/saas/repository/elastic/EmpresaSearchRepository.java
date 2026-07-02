package com.exemplo.saas.repository.elastic;

import com.exemplo.saas.model.document.EmpresaIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaSearchRepository extends ElasticsearchRepository<EmpresaIndex, String> {
    
    Optional<EmpresaIndex> findByCnpj(String cnpj);

    List<EmpresaIndex> findByCanaisVendaStartingWith(String prefixo);
}
