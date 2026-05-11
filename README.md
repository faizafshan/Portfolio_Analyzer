# Portfolio Analyzer — Multi-Agent AI Platform

A full-stack portfolio management and analysis platform powered by a multi-agent AI architecture. The system provides real-time portfolio monitoring, compliance checking, ESG analysis, drift detection, and AI-driven optimization recommendations.

![Tech Stack](https://img.shields.io/badge/Angular-19-red?logo=angular) ![Tech Stack](https://img.shields.io/badge/Spring%20Boot-3.2.5-green?logo=springboot) ![Tech Stack](https://img.shields.io/badge/Java-21-orange?logo=openjdk) ![Tech Stack](https://img.shields.io/badge/Chart.js-4-blue?logo=chartdotjs)

---

## Table of Contents

- [Architecture Overview](#architecture-overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Frontend Pages](#frontend-pages)
- [Configuration](#configuration)
- [Development Notes](#development-notes)

---

## Architecture Overview

```
┌────────────────────────────────────────────────────────────────┐
│                        Angular Frontend                         │
│          (localhost:4200 — proxy /api → localhost:8080)         │
├────────────────────────────────────────────────────────────────┤
│                     Spring Boot Backend                         │
│                        (localhost:8080)                         │
├──────────┬──────────┬──────────┬──────────┬───────────────────┤
│ Portfolio│   AI     │  Audit   │   ESG    │   Constraint      │
│ Service  │ Optimizer│ Service  │ Analysis │   Intelligence    │
└──────────┴──────────┴──────────┴──────────┴───────────────────┘
```

The platform simulates a **multi-agent AI system** where specialized agents handle:
- **Drift Detection** — Monitors allocation vs. target deviations
- **Optimization** — Generates rebalancing recommendations
- **Compliance** — Validates against investment policy constraints
- **ESG Scoring** — Environmental, Social, Governance analysis
- **Explainability** — Transparent reasoning chains for all decisions
- **Constraint Parsing** — NLP-based investment policy extraction

---

## Features

| Feature | Description |
|---------|-------------|
| **Dashboard** | KPI overview, executive AI summary, allocation charts, drift alerts, agent status |
| **Portfolio Analysis** | Radar scoring, sector exposure, holdings table, drift bars, compliance status |
| **AI Agent Control** | Real-time agent statuses, confidence metrics, reasoning chains |
| **Constraint Intelligence** | Natural language policy input → parsed executable constraints |
| **Recommendation Simulator** | Before/After optimization comparison with trade suggestions |
| **Audit Trail** | Full decision traceability with severity filtering and confidence scores |

---

## Tech Stack

### Frontend
| Technology | Version | Purpose |
|-----------|---------|---------|
| Angular | 19.2 | SPA framework (standalone components, signals, new control flow) |
| TypeScript | 5.x | Type-safe development |
| SCSS | — | Component styling with CSS custom properties |
| Chart.js | 4.x | Interactive charts (bar, radar, doughnut) |
| Angular CDK | 19.x | UI utilities |
| RxJS | 7.x | Reactive HTTP and state management |

### Backend
| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 21 | Runtime |
| Spring Boot | 3.2.5 | REST API framework |
| Spring Validation | — | Request validation |
| SpringDoc OpenAPI | 2.5.0 | Swagger UI & API documentation |
| Lombok | — | Boilerplate reduction |
| Maven | 3.9+ | Build tool |

---

## Project Structure

```
Portfolio_Analyzer/
├── backend/
│   ├── pom.xml
│   └── src/main/java/com/portfolio/analyzer/
│       ├── PortfolioAnalyzerApplication.java
│       ├── config/
│       │   └── CorsConfig.java
│       ├── controller/
│       │   ├── PortfolioController.java
│       │   ├── AiController.java
│       │   └── AuditController.java
│       ├── dto/
│       ├── entity/
│       └── service/
│           ├── PortfolioService.java
│           ├── OptimizationService.java
│           ├── ConstraintIntelligenceService.java
│           ├── ComplianceService.java
│           ├── EsgService.java
│           ├── ExplainabilityService.java
│           ├── AgentStatusService.java
│           └── AuditService.java
├── frontend/
│   ├── angular.json
│   ├── proxy.conf.json
│   ├── package.json
│   └── src/
│       ├── styles.scss              # Global design system
│       └── app/
│           ├── app.component.*      # Shell layout + sidebar
│           ├── app.routes.ts        # Lazy-loaded routing
│           ├── models/
│           │   └── portfolio.model.ts
│           ├── services/
│           │   ├── api.service.ts
│           │   └── app-state.service.ts
│           └── pages/
│               ├── dashboard/
│               ├── portfolio-analysis/
│               ├── agent-control/
│               ├── constraint-intelligence/
│               ├── simulator/
│               └── audit-trail/
└── docs/
```

---

## Prerequisites

- **Java 21** (or higher)
- **Maven 3.9+**
- **Node.js 20+** (use `nvm use 20` if using nvm)
- **npm 10+**
- **Angular CLI** (installed locally via npx)

---

## Getting Started

### 1. Start the Backend

```bash
cd backend
mvn spring-boot:run
```

The backend starts on **http://localhost:8080**.

Verify: `curl http://localhost:8080/api/portfolio`

### 2. Start the Frontend

```bash
cd frontend
npm install
npx ng serve --port 4200
```

The frontend starts on **http://localhost:4200** with API proxy to the backend.

### 3. Access the Application

| URL | Description |
|-----|-------------|
| http://localhost:4200 | Main application |
| http://localhost:8080/swagger-ui.html | Swagger API docs |
| http://localhost:8080/api-docs | OpenAPI JSON spec |

---

## API Documentation

### Portfolio Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/portfolio` | List all portfolios |
| GET | `/api/portfolio/{id}` | Get portfolio by ID |
| GET | `/api/portfolio/drift/{id}` | Get drift analysis |

### AI Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/ai/optimize` | Run portfolio optimization |
| POST | `/api/ai/constraints` | Parse investment policy constraints |
| GET | `/api/ai/compliance-check/{id}` | Run compliance check |
| GET | `/api/ai/explain/{id}` | Get explainability report |
| GET | `/api/ai/esg/{id}` | Get ESG analysis |
| GET | `/api/ai/agent-status` | Get all agent statuses |
| GET | `/api/ai/executive-summary/{id}` | Get AI executive summary |

### Audit Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/audit` | List all audit entries |
| GET | `/api/audit/{portfolioId}` | Get audit entries for a portfolio |

### Example Requests

```bash
# Get portfolio
curl http://localhost:8080/api/portfolio/PF-001

# Run optimization
curl -X POST http://localhost:8080/api/ai/optimize \
  -H "Content-Type: application/json" \
  -d '{"portfolioId":"PF-001","strategy":"BALANCED"}'

# Parse constraints
curl -X POST http://localhost:8080/api/ai/constraints \
  -H "Content-Type: application/json" \
  -d '{"policyText":"Maximum 10% in any single stock, ESG required"}'

# Get drift analysis
curl http://localhost:8080/api/portfolio/drift/PF-001
```

---

## Frontend Pages

### Dashboard (`/`)
- Portfolio health KPIs (Health Score, Compliance, ROI, Alerts)
- AI Executive Summary banner
- Allocation vs Target bar chart
- Sector doughnut chart
- Drift alerts and agent status indicators

### Portfolio Analysis (`/portfolio/:id`)
- 5-dimension radar chart (Health, Compliance, ESG, Return, Diversification)
- Sector exposure horizontal bar chart
- Holdings table with weight, price, beta, drift, ESG data
- Drift analysis with visual bars
- Compliance violations and passed checks

### AI Agent Control (`/agents`)
- Agent status cards with live metrics
- Confidence indicators
- Detailed reasoning chains and output logs

### Constraint Intelligence (`/constraints`)
- Natural language policy input textarea
- Template quick-fill buttons
- Parsed output: constraints grid, excluded sectors, risk profile
- Confidence scoring

### Recommendation Simulator (`/simulator`)
- Portfolio and strategy selectors
- Before vs After comparison metrics (4-column)
- Bar chart comparison visualization
- Trade recommendations with priority scoring and reasoning

### Audit Trail (`/audit`)
- Timeline-based entries with severity color coding
- Agent and severity filters
- 3-column detail panels (AI Reasoning, Constraint Mapping, Confidence)

---

## Configuration

### Backend (`application.yml`)

```yaml
server:
  port: 8080

spring:
  application:
    name: portfolio-analyzer

springdoc:
  swagger-ui:
    path: /swagger-ui.html
```

### Frontend Proxy (`proxy.conf.json`)

```json
{
  "/api": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true
  }
}
```

### CORS Configuration

Allowed origins are configured in `CorsConfig.java`:
- `http://localhost:4200` (Angular dev server)
- `http://localhost:5173` (Vite dev server — legacy)

---

## Development Notes

- **Design System**: Dark glass-morphism theme with CSS custom properties defined in `src/styles.scss`
- **State Management**: Angular Signals via `AppStateService` (no NgRx needed for this scale)
- **Routing**: Lazy-loaded routes for optimal bundle splitting (~105KB initial)
- **Charts**: Chart.js with manual `Chart.register(...registerables)` per component
- **Mock Data**: All backend data is hardcoded in service classes — no database or external LLM APIs
- **Angular Control Flow**: Uses `@if`, `@for`, `@switch` (new syntax, not `*ngIf`/`*ngFor`)

---

## License

This project is for demonstration and portfolio analysis purposes.
