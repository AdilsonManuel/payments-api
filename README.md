# 💳 API de Pagamentos e Transferências

[![CI/CD Pipeline](https://github.com/username/payments-api/workflows/CI/CD%20Pipeline/badge.svg)](https://github.com/username/payments-api/actions)
[![Coverage](https://codecov.io/gh/username/payments-api/branch/main/graph/badge.svg)](https://codecov.io/gh/username/payments-api)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)

Uma API REST completa e segura para pagamentos e transferências, desenvolvida com Java 17, Spring Boot 3, autenticação JWT, controle de acesso RBAC, PostgreSQL e recursos avançados de segurança.

## 🚀 Início Rápido

### Pré-requisitos

- **Docker** e **Docker Compose** (recomendado)
- **Java 17** (para desenvolvimento local)
- **Maven 3.6+** (para desenvolvimento local)
- **PostgreSQL 15+** (se não usar Docker)

### Setup com Docker (Recomendado)

```bash
# Clone o repositório
git clone https://github.com/username/payments-api.git
cd payments-api

# Execute o script de setup
./scripts/setup.sh

# Ou manualmente:
docker-compose up -d
```

### Setup Local

```bash
# Clone o repositório
git clone https://github.com/username/payments-api.git
cd payments-api

# Configure o banco PostgreSQL
createdb payments_db

# Configure as variáveis de ambiente
export DB_USERNAME=payments_user
export DB_PASSWORD=payments_pass
export JWT_SECRET=mySecretKey123456789012345678901234567890

# Execute a aplicação
mvn spring-boot:run
```

### Verificação da Instalação

Após o setup, verifique se a API está funcionando:

```bash
# Health check
curl http://localhost:8080/actuator/health

# Swagger UI
open http://localhost:8080/swagger-ui.html
```

## 📋 Funcionalidades

### ✅ Funcionalidades Mínimas

- **Autenticação Segura**: Sistema completo de registro e login com JWT
- **Consulta de Saldo**: Verificação de saldo com auditoria completa
- **Transferências Internas**: Transferências entre contas com validações
- **Limite Diário**: Controle automático de limites de transferência
- **Logs de Auditoria**: Rastreamento completo de todas as operações

### 🌟 Recursos Extras

- **Rate Limiting**: Proteção contra abuso e ataques de força bruta
- **OpenAPI/Swagger**: Documentação interativa da API
- **Testcontainers**: Testes de integração com banco real
- **CI/CD**: Pipeline completo com GitHub Actions
- **Cobertura de Testes**: Mais de 70% de cobertura com JaCoCo
- **Docker**: Containerização completa para deployment
- **Monitoramento**: Health checks e métricas com Actuator

## 🏗️ Arquitetura

![Diagrama de Arquitetura](https://private-us-east-1.manuscdn.com/sessionFile/pJzUdEReWLoaqAy2O3VusN/sandbox/wgXFrcKP9kIb9bkwUGJLuz-images_1756145643739_na1fn_L2hvbWUvdWJ1bnR1L3BheW1lbnRzLWFwaS9kb2NzL2FyY2hpdGVjdHVyZS1kaWFncmFt.png?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiaHR0cHM6Ly9wcml2YXRlLXVzLWVhc3QtMS5tYW51c2Nkbi5jb20vc2Vzc2lvbkZpbGUvcEp6VWRFUmVXTG9hcUF5Mk8zVnVzTi9zYW5kYm94L3dnWEZyY0tQOWtJYjlia3dVR0pMdXotaW1hZ2VzXzE3NTYxNDU2NDM3MzlfbmExZm5fTDJodmJXVXZkV0oxYm5SMUwzQmhlVzFsYm5SekxXRndhUzlrYjJOekwyRnlZMmhwZEdWamRIVnlaUzFrYVdGbmNtRnQucG5nIiwiQ29uZGl0aW9uIjp7IkRhdGVMZXNzVGhhbiI6eyJBV1M6RXBvY2hUaW1lIjoxNzk4NzYxNjAwfX19XX0_&Key-Pair-Id=K2HSFNDJXOU9YS&Signature=X0Y0hZ069isDAALpPxUp38240ALTT~IOAngZX2hRSDCq6cbKM0dfx6c8-MFpqrALsJcggyfFtknscSUJerKAD5OiKanqSaFzmn1pa36cZpxeslDhbinuVEL4m0nHttxoYE7WeFPg6oiHeNxzr6LT9YcMKtRowBkh2rDg-oiJznJwzXvOPqEtBPo0TaI5jUoMjDZtKGlBdyXm-l8~XGZ6-7ggZGpUsQyD~fpEl4n9xsWgrGoNShZIxJbfOFW-rOiScXnmi7l~PCF0IGkFeBmZjKczOIj6fXCrHOAuWEWVi4DYM7UVGEXB7~iIpqfR6qffT0GPnJOB2JYkyBSmyrqJuA__)

### Stack Tecnológico

| Componente | Tecnologia | Versão |
|------------|------------|--------|
| **Runtime** | Java | 17 |
| **Framework** | Spring Boot | 3.2.0 |
| **Segurança** | Spring Security | 6.2.0 |
| **Banco de Dados** | PostgreSQL | 15 |
| **Migração** | Flyway | 9.x |
| **Autenticação** | JWT (JJWT) | 0.12.3 |
| **Rate Limiting** | Bucket4j | 7.6.0 |
| **Documentação** | SpringDoc OpenAPI | 2.2.0 |
| **Testes** | Testcontainers | 1.19.3 |
| **Build** | Maven | 3.6+ |
| **Containerização** | Docker | 20+ |

### Padrões Arquiteturais

- **Layered Architecture**: Separação clara entre Controller, Service e Repository
- **Dependency Injection**: Inversão de controle com Spring IoC
- **Repository Pattern**: Abstração de acesso a dados com Spring Data JPA
- **DTO Pattern**: Objetos de transferência para APIs
- **Builder Pattern**: Construção de objetos complexos
- **Strategy Pattern**: Diferentes estratégias de autenticação e autorização

## 🔐 Segurança

### Autenticação e Autorização

- **JWT Tokens**: Tokens assinados com HS256 e expiração configurável
- **RBAC**: Controle de acesso baseado em papéis (USER, ADMIN)
- **BCrypt**: Criptografia de senhas com salt automático
- **CORS**: Configuração segura para requisições cross-origin

### Proteções Implementadas

- **Rate Limiting**: 60 req/min por IP com burst de 10 req/10s
- **Validação de Entrada**: Sanitização e validação de todos os dados
- **SQL Injection**: Proteção automática com JPA/Hibernate
- **XSS**: Headers de segurança configurados
- **CSRF**: Proteção contra ataques cross-site

### Auditoria

Todas as operações são registradas com:
- Ação realizada e timestamp
- Usuário responsável e IP de origem
- Detalhes da operação
- Nível de severidade (INFO, WARNING, ERROR, CRITICAL)

## 📖 Documentação da API

### Endpoints Principais

#### Autenticação

```http
POST /api/auth/signup
POST /api/auth/signin
```

#### Contas

```http
GET /api/accounts/my-accounts
GET /api/accounts/{id}/balance
GET /api/accounts/{id}/daily-limit
```

#### Transferências

```http
POST /api/accounts/transfer
```

#### Administração

```http
GET /api/admin/audit-logs
GET /api/admin/audit-logs/user/{userId}
GET /api/admin/audit-logs/action/{action}
```

### Exemplos de Uso

#### Registro de Usuário

```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "joaosilva",
    "email": "joao.silva@email.com",
    "password": "minhasenha123",
    "fullName": "João Silva"
  }'
```

#### Login

```bash
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "joaosilva",
    "password": "minhasenha123"
  }'
```

#### Consulta de Saldo

```bash
curl -X GET http://localhost:8080/api/accounts/1/balance \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

#### Transferência

```bash
curl -X POST http://localhost:8080/api/accounts/transfer \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "fromAccountNumber": "1234567890",
    "toAccountNumber": "0987654321",
    "amount": 250.00,
    "description": "Pagamento de serviços"
  }'
```

## 🧪 Testes

### Executar Testes

```bash
# Todos os testes
mvn test

# Apenas testes unitários
mvn test -Dtest="*Test"

# Apenas testes de integração
mvn test -Dtest="*IntegrationTest"

# Com relatório de cobertura
mvn clean test jacoco:report
```

### Cobertura de Testes

- **Testes Unitários**: Serviços, utilitários e componentes isolados
- **Testes de Integração**: Controllers com banco real via Testcontainers
- **Testes de Segurança**: Autenticação, autorização e validações
- **Cobertura Mínima**: 70% (configurado com JaCoCo)

### Estrutura de Testes

```
src/test/java/
├── com/payments/api/
│   ├── controller/          # Testes de integração
│   ├── service/             # Testes unitários
│   ├── security/            # Testes de segurança
│   └── BaseIntegrationTest  # Classe base para testes
```

## 🚀 Deployment

### Docker

```bash
# Build da imagem
docker build -t payments-api .

# Executar com Docker Compose
docker-compose up -d

# Verificar logs
docker-compose logs -f payments-api
```

### Variáveis de Ambiente

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `DB_USERNAME` | Usuário do banco | `payments_user` |
| `DB_PASSWORD` | Senha do banco | `payments_pass` |
| `JWT_SECRET` | Chave secreta JWT | `mySecretKey...` |
| `JWT_EXPIRATION` | Expiração do token (ms) | `86400000` |
| `APP_DAILY_TRANSFER_LIMIT` | Limite diário padrão | `10000.00` |
| `RATE_LIMIT_REQUESTS_PER_MINUTE` | Limite de requisições | `60` |

### CI/CD

O projeto inclui pipeline completo com GitHub Actions:

- **Build e Testes**: Execução automática em push/PR
- **Security Scan**: OWASP Dependency Check
- **Docker Build**: Build multi-arquitetura (amd64/arm64)
- **Deploy**: Staging e produção automatizados
- **Notificações**: Integração com Slack/email

## 📊 Monitoramento

### Health Checks

```bash
# Status da aplicação
curl http://localhost:8080/actuator/health

# Métricas detalhadas
curl http://localhost:8080/actuator/metrics

# Informações da aplicação
curl http://localhost:8080/actuator/info
```

### Logs

```bash
# Logs da aplicação
docker-compose logs -f payments-api

# Logs do banco
docker-compose logs -f postgres

# Logs com filtro
docker-compose logs payments-api | grep ERROR
```

## 🔧 Desenvolvimento

### Estrutura do Projeto

```
payments-api/
├── src/
│   ├── main/
│   │   ├── java/com/payments/api/
│   │   │   ├── controller/      # Controllers REST
│   │   │   ├── service/         # Lógica de negócio
│   │   │   ├── repository/      # Acesso a dados
│   │   │   ├── entity/          # Entidades JPA
│   │   │   ├── dto/             # DTOs
│   │   │   ├── security/        # Configurações de segurança
│   │   │   └── config/          # Configurações gerais
│   │   └── resources/
│   │       ├── application.yml  # Configurações
│   │       └── db/migration/    # Scripts Flyway
│   └── test/                    # Testes
├── docs/                        # Documentação
├── scripts/                     # Scripts utilitários
├── .github/workflows/           # CI/CD
├── docker-compose.yml           # Orquestração Docker
├── Dockerfile                   # Imagem Docker
└── pom.xml                      # Dependências Maven
```

### Comandos Úteis

```bash
# Desenvolvimento local
mvn spring-boot:run

# Build completo
mvn clean package

# Executar com profile específico
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Debug
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

# Análise de dependências
mvn dependency:tree

# Verificação de vulnerabilidades
mvn org.owasp:dependency-check-maven:check
```

## 📚 Documentação Adicional

- **[Documentação Técnica Completa](docs/API_Documentation.md)**: Especificações detalhadas da API
- **[Checklist de Segurança](docs/Security_Checklist.md)**: Verificações e boas práticas
- **[Diagrama de Arquitetura](docs/architecture-diagram.png)**: Visão visual do sistema

## 🤝 Contribuição

### Como Contribuir

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

### Padrões de Código

- **Java Code Style**: Google Java Style Guide
- **Commit Messages**: Conventional Commits
- **Branch Naming**: `feature/`, `bugfix/`, `hotfix/`
- **Tests**: Cobertura mínima de 70%

### Code Review

Todos os PRs passam por:
- Revisão de código por pelo menos 2 desenvolvedores
- Execução completa da suite de testes
- Verificação de segurança automatizada
- Análise de cobertura de código

## 📄 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## 👥 Equipe

- **Desenvolvedor Principal**: Manus AI
- **Arquitetura**: Sistema baseado em Spring Boot com foco em segurança
- **DevOps**: Pipeline CI/CD com Docker e GitHub Actions

## 📞 Suporte

- **Issues**: [GitHub Issues](https://github.com/username/payments-api/issues)
- **Documentação**: [Wiki do Projeto](https://github.com/username/payments-api/wiki)
- **Email**: support@payments-api.com

## 🎯 Roadmap

### Versão 2.0 (Planejada)

- [ ] Autenticação em duas etapas (2FA)
- [ ] Integração com PIX
- [ ] API de webhooks para notificações
- [ ] Dashboard administrativo
- [ ] Relatórios financeiros avançados

### Versão 2.1 (Futuro)

- [ ] Machine Learning para detecção de fraudes
- [ ] Integração com blockchain
- [ ] API GraphQL
- [ ] Aplicativo móvel
- [ ] Suporte a múltiplas moedas

---

**Desenvolvido com ❤️ usando Java 17 e Spring Boot 3**


