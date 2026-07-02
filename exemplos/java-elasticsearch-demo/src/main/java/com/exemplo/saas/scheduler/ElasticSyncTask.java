package com.exemplo.saas.scheduler;

import com.exemplo.saas.model.document.EmpresaIndex;
import com.exemplo.saas.model.entity.Empresa;
import com.exemplo.saas.repository.elastic.EmpresaSearchRepository;
import com.exemplo.saas.repository.jpa.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ElasticSyncTask {

    private final EmpresaRepository jpaRepository;
    private final EmpresaSearchRepository elasticRepository;

    private LocalDateTime ultimaSincronizacao = LocalDateTime.now().minusDays(1);

    @Scheduled(fixedRate = 30000)
    public void sincronizarDados() {
        log.info("Iniciando sincronização com Elasticsearch... Última sync: {}", ultimaSincronizacao);

        List<Empresa> empresasAtualizadas = jpaRepository.findByDataAtualizacaoAfter(ultimaSincronizacao);

        if (empresasAtualizadas.isEmpty()) {
            log.info("Nenhuma empresa nova ou atualizada para sincronizar.");
            return;
        }

        List<EmpresaIndex> documentos = empresasAtualizadas.stream()
                .map(this::converterParaIndex)
                .collect(Collectors.toList());

        elasticRepository.saveAll(documentos);
        
        ultimaSincronizacao = LocalDateTime.now();
        log.info("Sincronização concluída! {} empresas indexadas no Elasticsearch.", documentos.size());
    }

    private EmpresaIndex converterParaIndex(Empresa empresa) {
        return EmpresaIndex.builder()
                .id(empresa.getId().toString())
                .cnpj(empresa.getCnpj())
                .razaoSocial(empresa.getRazaoSocial())
                .nomeFantasia(empresa.getNomeFantasia())
                .descricao(empresa.getDescricao())
                .canaisVenda(empresa.getCanaisVenda())
                .dataAtualizacao(empresa.getDataAtualizacao())
                .build();
    }
}
