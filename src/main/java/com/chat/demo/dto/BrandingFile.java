package com.chat.demo.dto;

// Carrier técnico para servir el logo/favicon como bytes crudos (no viaja como JSON).
public record BrandingFile(byte[] content, String contentType) {
}
