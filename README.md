# Proposta de Seminário: Projeto de Banco de Dados

**Título/Tema:** Motor de Busca e Análise de Dados para Plataforma SaaS B2B  
**Equipe:** Kaique Vieira Soares, Isaque Guimaraes Carreiro, Leandro Isaac Correia de Brito, Ismael Gomes da Silva, Eduardo Nascimento Santos, Luiz Henrique Felix Guedes, Caio Lucas Lopes Medeiros  

## SGBD Escolhido: Elasticsearch
O Sistema de Gerenciamento selecionado para este projeto é o **Elasticsearch**. Trata-se de um motor de busca e análise de dados distribuído, de código aberto, construído sobre a biblioteca Apache Lucene. Ele é classificado como um banco de dados NoSQL orientado a documentos (armazenados no formato JSON).

A escolha se justifica pela sua altíssima performance em buscas de texto completo (full-text search), indexação de grandes volumes de dados e análise em tempo real. Diferente de bancos relacionais tradicionais, o Elasticsearch utiliza índices invertidos, o que permite encontrar correspondências de palavras e realizar agregações complexas com respostas em milissegundos, sendo ideal para sistemas que dependem de pesquisas dinâmicas, catálogos extensos e monitoramento de logs de aplicação.

## Proposta Prática
O projeto prático consistirá na implementação do motor de busca e indexação central de uma plataforma SaaS B2B focada em anúncios e tendências de mercado locais. O objetivo é demonstrar as capacidades do Elasticsearch na recuperação de informações textuais e dados estruturados em alta velocidade.

Para demonstrar o funcionamento do SGBD, as seguintes funcionalidades serão implementadas:
* **Buscas Exatas e Estruturadas:** Utilização de consultas do tipo `term queries` para realizar pesquisas precisas por registros únicos. Será demonstrada a busca instantânea pelo CNPJ de empresas clientes na base, sem a necessidade de varredura completa.
* **Autocompletar e Buscas Parciais:** Implementação de consultas do tipo `prefix queries`. Isso será aplicado para criar uma funcionalidade de autocompletar rápida e dinâmica na interface, permitindo que os usuários pesquisem por canais de venda digitais digitando apenas os primeiros caracteres.
* **Listagens Abrangentes e Filtros Booleanos:** Demonstração do uso de consultas `match_all` combinadas com cláusulas booleanas (`must`, `should`, `must_not`) para gerar listagens padronizadas do catálogo e aplicar múltiplos filtros dinâmicos.
* **Indexação de Logs de Aplicação:** Configuração de uma estrutura de índices orientada a séries temporais para armazenar requisições de log da plataforma, demonstrando como o Elasticsearch ingere e disponibiliza dados não estruturados para monitoramento.

## Referências
* ELASTIC. *Elasticsearch Reference*. Documentação Oficial. Disponível em: https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html.
* GORMLEY, Clinton; TONG, Zachary. *Elasticsearch: The Definitive Guide*. 1. ed. O'Reilly Media, 2015.
* SADALAGE, Pramod J.; FOWLER, Martin. *NoSQL Distilled: A Brief Guide to the Emerging World of Polyglot Persistence*. Addison-Wesley Professional, 2012.