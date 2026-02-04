-- Script de inicialización de la base de datos
-- Tabla usuario con datos de prueba

-- Usuario administrador
INSERT INTO usuario (id, nombre, password, rol) VALUES (1, 'admin', 'admin123', 'admin');

-- Usuario normal
INSERT INTO usuario (id, nombre, password, rol) VALUES (2, 'user', 'user123', 'user');

-- Otro usuario administrador
INSERT INTO usuario (id, nombre, password, rol) VALUES (3, 'mateo', 'mateo123', 'admin');

-- Más usuarios de prueba
INSERT INTO usuario (id, nombre, password, rol) VALUES (5, 'maria', 'maria123', 'user');
