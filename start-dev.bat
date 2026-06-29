@echo off
set POSTGRES_USER=postgres
set POSTGRES_PASSWORD=
set GROQ_API_KEY=
set JWT_SECRET=
set SPRING_PROFILES_ACTIVE=dev
cd /d %~dp0
mvnw.cmd spring-boot:run
