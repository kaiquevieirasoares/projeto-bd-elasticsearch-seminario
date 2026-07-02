package com.exemplo.saas.model.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.elasticsearch.annotations.DateFormat.date_time;

@Document(indexName = "empresas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaIndex {

    @Id
    private String id; 

    @Field(type = FieldType.Keyword)
    private String cnpj;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String razaoSocial;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String nomeFantasia;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String descricao;

    @Field(type = FieldType.Keyword)
    private List<String> canaisVenda;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSSSS||uuuu-MM-dd'T'HH:mm:ss.SSS||uuuu-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataAtualizacao;
}
