package com.chat.demo.migration;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Traslada, una sola vez y de forma idempotente, la columna legada
 * documents.area_id (relación de un solo área por documento) a la nueva
 * tabla documents_areas (many-to-many). No hay Flyway/Liquibase en este
 * proyecto (ddl-auto=update no migra datos, solo agrega columnas/tablas),
 * así que este runner corre en todos los perfiles, no solo "dev".
 */
@Component
@RequiredArgsConstructor
public class DocumentAreaMigrationRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DocumentAreaMigrationRunner.class);

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        Boolean hasLegacyColumn = jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM information_schema.columns " +
                        "WHERE table_name = 'documents' AND column_name = 'area_id')",
                Boolean.class);

        if (Boolean.FALSE.equals(hasLegacyColumn)) {
            return;
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "SELECT id, area_id FROM documents WHERE area_id IS NOT NULL");

        if (rows.isEmpty()) {
            return;
        }

        int migrated = 0;
        for (Map<String, Object> row : rows) {
            int updated = jdbcTemplate.update(
                    "INSERT INTO documents_areas (document_id, area_id) VALUES (?, ?) ON CONFLICT DO NOTHING",
                    row.get("id"), row.get("area_id"));
            migrated += updated;
        }

        log.info("Migración documents.area_id -> documents_areas: {} fila(s) migrada(s) de {} candidata(s)",
                migrated, rows.size());
    }
}
