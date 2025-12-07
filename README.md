# API – Farmácia de Alto Custo  
API REST desenvolvida para localizar farmácias de alto custo com medicamentos específicos em estoque, utilizando geolocalização, cálculo de distância e integração com API externa.

Projeto desenvolvido por: **Nathalia Aparecida Costa Zaguini**  
Tecnologias: **Java 17, Spring Boot, JPA/Hibernate, MySQL, REST API**

---

## Funcionalidades Principais

✔ Cadastro automático de farmácias com geolocalização via API  
✔ Armazenamento de endereços e coordenadas (latitude/longitude)  
✔ Consulta de farmácias próximas com cálculo de distância (Haversine)  
✔ Busca de farmácias por medicamento em estoque  
✔ API 100% REST consumida pelo front-end  
✔ Query customizada com JOIN (critério do professor)  
✔ Arquitetura limpa e desacoplada (front separado)

---

## Arquitetura do Projeto

- **Controller** – Recebe requisições HTTP  
- **Service** – Regras de negócio (validações, cálculos, integrações)  
- **Repository** – Acesso ao MySQL via Spring Data  
- **Model/Entity** – Estruturas das tabelas (Farmacia, Endereco, Medicamento, Estoque)  

Padrão: **MVC + REST**

---

## Entidades Principais

### Farmacia
- id  
- nomeFantasia  
- razaoSocial  
- cnpj  
- email  
- telefone  
- latitude, longitude  
- senhaHash  
- enderecoFarmacia (OneToOne)  
- distanciaKm *(transient)*  

### EnderecoFarmacia
- cep  
- logradouro  
- numero  
- bairro  
- cidade  
- estado  

### Medicamento
- nome  
- fabricante  
- tarja  
- principio_ativo  
- indicacao  

### Estoque
- farmacia  
- medicamento  
- quantidade  

---

## 🌐 Endpoints da API

### ➤ **Cadastrar farmácia**
