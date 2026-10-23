# 🚀 SmartMenu SaaS — Production Deployment Runbook

**Target Architecture:**
- **Backend API & Swagger UI**: `https://api-smartmenu.techwareafrica.tech` (Host Port `3020`)
- **Frontend Web Application**: `https://smartmenu.techwareafrica.tech` (Static SPA Webroot)
- **Host VPS**: `95.111.251.192` (Ubuntu 24.04 LTS, Developer: `lwenaadam`, Port Block `3000–3099`)
- **Runtime Stack**: Java 21 LTS / Spring Boot 3.2.0 / PostgreSQL 16+ / React + Vite / Nginx

---

## 📋 Production Deployment Progress Tracker

- [x] **TASK 1**: Fix frontend customer-facing URLs (remove hardcoded `localhost:8080`).
- [x] **TASK 2**: Configure Spring Security & dynamic CORS for `https://smartmenu.techwareafrica.tech`.
- [x] **TASK 3**: Add OpenAPI configuration bean with production Swagger UI server URLs.
- [x] **TASK 4**: Enable reverse proxy header forwarding (`server.forward-headers-strategy=framework`).
- [x] **TASK 5**: Update `application.properties` and `.env.example` with dynamic PostgreSQL bindings.
- [x] **TASK 6**: Configure Maven build with Java 21 LTS and Lombok 1.18.36 annotation processing.
- [x] **TASK 7**: Test local compilation and artifact packaging (`target/smartmenu-backend-1.0.0.jar`).
- [x] **TASK 8**: Provision server directories & PostgreSQL database container (`smartmenu-postgres`) on VPS (`95.111.251.192`).
- [x] **TASK 9**: Build and deploy frontend static bundle to `/home/smartmenuweb/htdocs/smartmenu.techwareafrica.tech`.
- [x] **TASK 10**: Deploy backend Docker Compose stack (`smartmenu-api`) listening on port `3020`.
- [x] **TASK 11**: Configure CloudPanel / Nginx virtual hosts with SPA try_files support for both domains.
- [x] **TASK 12**: Provision Let's Encrypt SSL certificates via CloudPanel CLI (`clpctl`).
- [x] **TASK 13**: End-to-end verification (Health check, Swagger UI, User Registration & Authentication flow).

---

## 🛠️ Step-by-Step Executable Deployment Guide

### Phase 1: Database Setup on VPS

SSH into the VPS (`ssh lwenaadam@95.111.251.192`) and create the PostgreSQL database:

```bash
# 1. Access PostgreSQL
sudo -u postgres psql

# 2. Run SQL commands:
CREATE DATABASE smartmenu;
CREATE USER smartuser WITH ENCRYPTED PASSWORD 'ReplaceWithStrongDBPassword';
GRANT ALL PRIVILEGES ON DATABASE smartmenu TO smartuser;
\c smartmenu
GRANT ALL ON SCHEMA public TO smartuser;
\q
```

---

### Phase 2: Frontend Build & Deployment

Build the React frontend on your local development machine with the production API URL, then upload to the server:

```bash
# 1. From smart-menu-saas/frontend-react:
cd /home/lwena/TechWareAfrica/smart-menu-saas/frontend-react

# 2. Build production assets with production backend URL
VITE_API_URL=https://api-smartmenu.techwareafrica.tech/api npm run build

# 3. Package the build
cd dist && tar -czf ../smartmenu-frontend.tar.gz . && cd ..

# 4. Upload to the VPS
scp smartmenu-frontend.tar.gz lwenaadam@95.111.251.192:~/

# 5. Extract to webroot on VPS
ssh lwenaadam@95.111.251.192 "mkdir -p ~/smartmenu/webroot && tar -xzf ~/smartmenu-frontend.tar.gz -C ~/smartmenu/webroot && rm ~/smartmenu-frontend.tar.gz"
```

---

### Phase 3: Backend Build & Deployment

Package the Spring Boot application using Java 21 LTS and deploy to the VPS:

```bash
# 1. From smart-menu-saas/backend:
cd /home/lwena/TechWareAfrica/smart-menu-saas/backend

# 2. Build production runnable JAR
JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64 mvn clean package -DskipTests

# 3. Create server deployment bundle directory
ssh lwenaadam@95.111.251.192 "mkdir -p ~/smartmenu/backend"

# 4. Upload JAR and production env file
scp target/smartmenu-backend-1.0.0.jar lwenaadam@95.111.251.192:~/smartmenu/backend/app.jar
scp .env.example lwenaadam@95.111.251.192:~/smartmenu/backend/.env
```

On the VPS, edit `~/smartmenu/backend/.env` with your production DB password and JWT secret:
```bash
ssh lwenaadam@95.111.251.192 "nano ~/smartmenu/backend/.env"
```

Ensure `.env` contains:
```ini
PORT=3020
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/smartmenu
DB_USERNAME=smartuser
DB_PASSWORD=YourChosenPassword
JWT_SECRET=Minimum64CharactersRandomStringForTokenSecurity
FRONTEND_URL=https://smartmenu.techwareafrica.tech
API_BASE_URL=https://api-smartmenu.techwareafrica.tech
CORS_ALLOWED_ORIGINS=https://smartmenu.techwareafrica.tech
SHOW_SQL=false
HIBERNATE_DDL_AUTO=update
```

---

### Phase 4: Configure Backend as Systemd Service (Safe & Sound)

Create a dedicated systemd service to run the Spring Boot app with auto-restart on the VPS:

```bash
sudo tee /etc/systemd/system/smartmenu-backend.service > /dev/null << 'EOF'
[Unit]
Description=SmartMenu SaaS Backend API (Java 21 LTS)
After=syslog.target network.target postgresql.service

[Service]
Type=simple
User=lwenaadam
Group=lwenaadam
WorkingDirectory=/home/lwenaadam/smartmenu/backend
EnvironmentFile=/home/lwenaadam/smartmenu/backend/.env
ExecStart=/usr/bin/java -XX:+UseG1GC -XX:MaxRAMPercentage=75.0 -jar /home/lwenaadam/smartmenu/backend/app.jar
SuccessExitStatus=143
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF

# Reload systemd and start service
sudo systemctl daemon-reload
sudo systemctl enable smartmenu-backend
sudo systemctl restart smartmenu-backend

# Check service status and logs
sudo systemctl status smartmenu-backend
```

---

### Phase 5: Nginx Reverse Proxy Configuration

#### 1. Backend Domain: `/etc/nginx/sites-available/api-smartmenu.techwareafrica.tech`
```nginx
server {
    server_name api-smartmenu.techwareafrica.tech;

    location / {
        proxy_pass http://127.0.0.1:3020;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_cache_bypass $http_upgrade;

        client_max_body_size 25M;
    }
}
```

#### 2. Frontend Domain: `/etc/nginx/sites-available/smartmenu.techwareafrica.tech`
```nginx
server {
    server_name smartmenu.techwareafrica.tech;

    root /home/lwenaadam/smartmenu/webroot;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg|woff|woff2)$ {
        expires 1y;
        add_header Cache-Control "public, no-transform";
    }
}
```

#### 3. Enable Sites & Reload Nginx:
```bash
sudo ln -sf /etc/nginx/sites-available/api-smartmenu.techwareafrica.tech /etc/nginx/sites-enabled/
sudo ln -sf /etc/nginx/sites-available/smartmenu.techwareafrica.tech /etc/nginx/sites-enabled/
sudo nginx -t && sudo systemctl reload nginx
```

---

### Phase 6: Provision SSL Certificates (Let's Encrypt)

```bash
sudo certbot --nginx -d smartmenu.techwareafrica.tech -d api-smartmenu.techwareafrica.tech
```

---

## 🔍 Verification & Smoke Tests

| Target | Test URL | Expected Result |
|---|---|---|
| **Backend Health Check** | `https://api-smartmenu.techwareafrica.tech/api/health` | `HTTP 200 {"status": "UP"}` |
| **Swagger UI Documentation** | `https://api-smartmenu.techwareafrica.tech/swagger-ui.html` | Swagger UI loads with HTTPS servers and JWT Bearer Auth |
| **OpenAPI Spec** | `https://api-smartmenu.techwareafrica.tech/v3/api-docs` | Valid JSON OpenAPI specification |
| **Frontend Web App** | `https://smartmenu.techwareafrica.tech` | React login/dashboard page loads |
| **Customer QR Code View** | `https://smartmenu.techwareafrica.tech/customer-menu?table=1` | Public customer menu loads without CORS errors |

---

## 🚨 Emergency Operations & Troubleshooting

- **Check Backend Logs**:
  ```bash
  sudo journalctl -u smartmenu-backend -f -n 100
  ```
- **Check Port Status**:
  ```bash
  sudo ss -tulpn | grep 3020
  ```
- **Restart Application**:
  ```bash
  sudo systemctl restart smartmenu-backend
  ```
- **Rollback Frontend**:
  Upload previous `dist` tarball and extract to `/home/lwenaadam/smartmenu/webroot`.