# Guia de Testes Práticos - Seminário Elasticsearch 🚀

Este guia contém os comandos passo a passo para testar a nossa aplicação de integração entre **PostgreSQL** e **Elasticsearch**. Copie e cole os comandos no seu terminal para acompanhar!

---

## 1. Subindo o Ambiente

Primeiro, precisamos iniciar nossos bancos de dados usando o Docker:
```bash
docker-compose up -d
```
> Aguarde de 15 a 30 segundos para garantir que o Elasticsearch iniciou completamente.
> Em seguida, inicie a aplicação Spring Boot (pela IDE ou rodando `./mvnw spring-boot:run`).

---

## 2. Testando a API da Aplicação (Java)

### 2.1 Cadastrar uma Empresa (Cai no PostgreSQL)
Ao rodar este comando, a empresa é salva no banco de dados relacional. Dentro de 30 segundos, nosso `ElasticSyncTask` vai varrer o banco e indexá-la automaticamente no Elasticsearch.

```bash
curl -X POST http://localhost:8080/api/empresas \
-H "Content-Type: application/json" \
-d '{
  "cnpj": "12345678000199",
  "razaoSocial": "Tech Solutions LTDA",
  "nomeFantasia": "Tech Sol",
  "descricao": "Empresa focada em inovacao e IA",
  "canaisVenda": ["B2B", "E-commerce"]
}'
```
*(Se você tentar rodar o mesmo comando de novo, vai receber nossa mensagem de erro "CNPJ já cadastrado" com status 409 Conflict).*

**Atenção:** Guarde o `id` retornado (provavelmente será `1`) para o teste seguinte.

### 2.2 Buscar a Empresa Sincronizada (Consulta no Elasticsearch)
Após o tempo de sincronização passar, você já consegue consultar a empresa diretamente do nosso índice chamando o ID:

```bash
curl -X GET http://localhost:8080/api/empresas/1
```

### 2.3 Testar o Autocomplete (Consulta no Elasticsearch)
Testando a funcionalidade rápida de *autocomplete* para buscar quais empresas vendem pelo canal "B2":

```bash
curl -X GET "http://localhost:8080/api/empresas/autocomplete?q=B2"
```

---

## 3. Consultas Nativas Direto no Elasticsearch

Podemos também ignorar o Java e conversar diretamente com o motor do Elasticsearch rodando na porta `9200`. 

### 3.1 Ver os Índices ("Tabelas")
Lista todos os índices criados, o status de saúde deles e a quantidade de documentos cadastrados. O parâmetro `?v` (`verbose`) serve para mostrar o cabeçalho das colunas.
```bash
curl -X GET "http://localhost:9200/_cat/indices?v"
```

### 3.2 Buscar por um campo específico no Elasticsearch (Usando JSON Body)
Diferente dos bancos relacionais com SQL (`WHERE descricao LIKE '%inovacao%'`), no Elasticsearch a forma mais recomendada e poderosa de buscar é enviando um JSON com a *Query DSL*.

Procurar empresas pela "descricao" (Busca Textual - Full Text Search):
```bash
curl -X GET "http://localhost:9200/empresas/_search?pretty" \
-H "Content-Type: application/json" \
-d '{
  "query": {
    "match": {
      "descricao": "inovacao"
    }
  }
}'
```

Procurar empresas pelo exato CNPJ (Busca exata - Term Query):
```bash
curl -X GET "http://localhost:9200/empresas/_search?pretty" \
-H "Content-Type: application/json" \
-d '{
  "query": {
    "term": {
      "cnpj": "12345678000199"
    }
  }
}'
```

*(O sufixo `?pretty` na URL serve para o Elasticsearch devolver o JSON de resposta formatado com indentação, facilitando a leitura).*
