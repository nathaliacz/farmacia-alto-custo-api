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

## Endpoints da API

### ➤ **Cadastrar farmácia**
```
POST /api/v1/farmacias
```

### ➤ **Farmácias próximas (sem medicamento)**
```
GET /api/v1/farmacias/proximas?cep=08575-000&raio=5
```

### ➤ **Farmácias próximas com medicamento**
```
GET /api/v1/farmacias/proximas-por-medicamento?cep=08575-000&raio=20&medicamento=rituximab
```

---

## Query Customizada

```java
@Query("""
    SELECT e.farmacia
    FROM Estoque e
    JOIN e.medicamento m
    WHERE LOWER(m.nome) = LOWER(:nomeMedicamento)
""")
List<Farmacia> buscarFarmaciasQueTemMedicamentoPorNome(String nomeMedicamento);
```

---

## Segurança

- Validação de CNPJ e e-mail  
- Senha armazenada como hash  
- Uso de variáveis de ambiente (GOOGLE_API_KEY)  
- JPA evita SQL Injection  
- Tratamento de exceções  
- CORS configurado  

---

## 🛠 Como Rodar o Projeto

### Criar database:
```
CREATE DATABASE farmacia_alto_custo;
```

### Configurar `application.properties`
```
spring.datasource.url=jdbc:mysql://localhost:3306/farmacia_alto_custo
spring.datasource.username=root
spring.datasource.password=SENHA

google.api.key=${GOOGLE_API_KEY}
```

### Rodar a aplicação:
```
mvn spring-boot:run
```

---

## Status: 100% funcional ✔

