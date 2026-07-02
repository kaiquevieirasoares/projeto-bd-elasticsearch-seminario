package com.exemplo.saas.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Column(nullable = false)
    private String razaoSocial;

    @Column(nullable = false)
    private String nomeFantasia;

    private String descricao;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "empresa_canais_venda", joinColumns = @JoinColumn(name = "empresa_id"))
    @Column(name = "canal")
    private List<String> canaisVenda;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;
}
