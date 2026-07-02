package com.exemplo.saas.controller;

import com.exemplo.saas.model.document.EmpresaIndex;
import com.exemplo.saas.model.entity.Empresa;
import com.exemplo.saas.repository.elastic.EmpresaSearchRepository;
import com.exemplo.saas.repository.jpa.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaRepository jpaRepository;
    private final EmpresaSearchRepository elasticRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empresa cadastrar(@RequestBody Empresa empresa) {
        if (jpaRepository.existsByCnpj(empresa.getCnpj())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CNPJ já cadastrado");
        }
        return jpaRepository.save(empresa);
    }

    @GetMapping("/{id}")
    public EmpresaIndex buscarPorId(@PathVariable String id) {
        return elasticRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada no índice"));
    }

    @GetMapping("/autocomplete")
    public List<EmpresaIndex> autocompletarPorCanal(@RequestParam String q) {
        return elasticRepository.findByCanaisVendaStartingWith(q);
    }

    @GetMapping("/search")
    public Iterable<EmpresaIndex> listarTodas() {
        return elasticRepository.findAll();
    }
}
