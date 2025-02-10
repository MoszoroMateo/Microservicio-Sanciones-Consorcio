CREATE TABLE IF NOT EXISTS complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    report_id INT,
    complaint_reason VARCHAR(255) NOT NULL,
    another_reason VARCHAR(255),
    complaint_state VARCHAR(20) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    state_reason VARCHAR(1000) NOT NULL,
    created_datetime DATETIME NOT NULL,
    created_user INT NOT NULL,
    last_updated_datetime DATETIME NOT NULL,
    last_updated_user INT NOT NULL
);

CREATE TABLE IF NOT EXISTS pictures (
    id INT AUTO_INCREMENT PRIMARY KEY,
    complaint_id INT,
    picture_url VARCHAR(255) NOT NULL,
    created_date DATETIME NOT NULL,
    created_user INT NOT NULL,
    last_updated_date DATETIME NOT NULL,
    last_updated_user INT NOT NULL,
    CONSTRAINT fk_complaint_id FOREIGN KEY (complaint_id) REFERENCES complaints(id) ON DELETE CASCADE
);

INSERT INTO complaints (user_id, report_id, complaint_reason, another_reason, complaint_state, description, state_reason, created_datetime, created_user, last_updated_datetime, last_updated_user)
VALUES
    (15, 1, 'Vehículos estacionados en zonas prohibidas', null, 'ATTACHED', 'Denuncio porque siento que me re molesta', 'Anexada', '2024-11-01 16:30:00', 7, '2024-11-05 10:10:00', 8),
    (2, 2, 'Desperdicio de agua en áreas verdes', null, 'ATTACHED', 'Quiero denunciar al lote 37', 'Anexada', '2024-11-02 08:40:00', 9, '2024-11-06 12:00:00', 10),
    (16, 3, 'Acceso no autorizado a áreas restringidas', null, 'ATTACHED', 'Denuncio porque estoy enojado', 'Anexada', '2024-11-03 13:15:00', 11, '2024-11-07 17:35:00', 12),
    (17, 4, 'Uso inapropiado de la parcela', null, 'ATTACHED', 'Quiero denunciar al lote 78', 'Anexada', '2024-11-04 11:00:00', 1, '2024-11-08 09:00:00', 2),
    (18, null, 'Presencia de ruido excesivo', null, 'PENDING', 'Quiero denunciar al lote 324', 'Pendiente', '2024-11-05 14:00:00', 3, '2024-11-09 12:20:00', 4),
    (19, null, 'Falta de mantenimiento', null, 'PENDING', 'Quiero denunciar al lote 3567', 'Pendiente', '2024-11-06 10:30:00', 5, '2024-11-10 15:15:00', 6),
    (10, null, 'Vehículos estacionados en zonas prohibidas', null, 'PENDING', 'Quiero denunciar al lote 375', 'Pendiente', '2024-11-07 09:45:00', 7, '2024-11-11 13:40:00', 8),
    (11, null, 'Desperdicio de agua en áreas verdes', null, 'PENDING', 'Denuncio porque siento que me re molesta lo que hace', 'Pendiente', '2024-11-08 11:15:00', 9, '2024-11-12 14:30:00', 10),
    (12, null, 'Acceso no autorizado a áreas restringidas', null, 'PENDING', 'Quiero denunciar al lote 357', 'Pendiente', '2024-11-09 16:20:00', 11, '2024-11-13 15:55:00', 12),
    (13, null, 'Uso inapropiado de la parcela', null, 'PENDING', 'Quiero denunciar al lote 35624', 'Pendiente', '2024-11-10 08:50:00', 1, '2024-11-14 10:45:00', 2),
    (14, null, 'Presencia de ruido excesivo', null, 'PENDING', 'Quiero denunciar al lote 21435', 'Pendiente', '2024-11-11 09:10:00', 3, '2024-11-15 12:50:00', 4),
    (11, null, 'Desperdicio de agua en áreas verdes', null, 'PENDING', 'Quiero denunciar al lote 4123', 'Pendiente', '2024-11-08 11:16:00', 9, '2024-11-12 14:31:00', 10),
    (12, null, 'Acceso no autorizado a áreas restringidas', null, 'PENDING', 'Quiero denunciar al lote 1414', 'Pendiente', '2024-11-09 16:21:00', 11, '2024-11-13 15:56:00', 12),
    (13, null, 'Uso inapropiado de la parcela', null, 'PENDING', 'Quiero denunciar al lote 142', 'Pendiente', '2024-11-10 08:51:00', 1, '2024-11-14 10:46:00', 2),
    (14, null, 'Presencia de ruido excesivo', null, 'REJECTED', 'Quiero denunciar al lote 14', 'Rechazada por falta de pruebas', '2024-11-11 09:11:00', 3, '2024-11-15 12:51:00', 4),
    (11, null, 'Desperdicio de agua en áreas verdes', null, 'REJECTED', 'Quiero denunciar al lote 14', 'Rechazada por falta de pruebas', '2024-11-08 11:17:00', 9, '2024-11-12 14:32:00', 10),
    (12, null, 'Acceso no autorizado a áreas restringidas', null, 'REJECTED', 'Quiero denunciar al lote 11423', 'Rechazada por falta de pruebas', '2024-11-09 16:22:00', 11, '2024-11-13 15:57:00', 12),
    (13, null, 'Uso inapropiado de la parcela', null, 'REJECTED', 'Quiero denunciar al lote 42', 'Rechazada por falta de pruebas', '2024-11-10 08:52:00', 1, '2024-11-14 10:47:00', 2),
    (14, null, 'Presencia de ruido excesivo', null, 'REJECTED', 'Quiero denunciar al lote 14', 'Rechazada por falta de pruebas', '2024-11-11 09:12:00', 3, '2024-11-15 12:52:00', 4),
    (15, null, 'Vehículos estacionados en zonas prohibidas', null, 'NEW', 'Denuncio porque siento que me re molesta', 'Nueva Denuncia', '2024-11-01 16:31:00', 7, '2024-11-05 10:11:00', 8),
    (2, null, 'Desperdicio de agua en áreas verdes', null, 'NEW', 'Quiero denunciar al lote 37', 'Nueva Denuncia', '2024-11-02 08:41:00', 9, '2024-11-06 12:01:00', 10),
    (16, null, 'Acceso no autorizado a áreas restringidas', null, 'NEW', 'Denuncio porque estoy enojado', 'Nueva Denuncia', '2024-11-03 13:16:00', 11, '2024-11-07 17:36:00', 12),
    (17, null, 'Uso inapropiado de la parcela', null, 'NEW', 'Quiero denunciar al lote 78', 'Nueva Denuncia', '2024-11-04 11:01:00', 1, '2024-11-08 09:01:00', 2),
    (18, null, 'Presencia de ruido excesivo', null, 'PENDING', 'Quiero denunciar al lote 324', 'Pendiente', '2024-11-05 14:01:00', 3, '2024-11-09 12:21:00', 4),
    (19, null, 'Falta de mantenimiento', null, 'PENDING', 'Quiero denunciar al lote 3567', 'Pendiente', '2024-11-06 10:31:00', 5, '2024-11-10 15:16:00', 6),
    (10, null, 'Vehículos estacionados en zonas prohibidas', null, 'PENDING', 'Quiero denunciar al lote 375', 'Pendiente', '2024-11-07 09:46:00', 7, '2024-11-11 13:41:00', 8),
    (11, null, 'Desperdicio de agua en áreas verdes', null, 'PENDING', 'Denuncio porque siento que me re molesta lo que hace', 'Pendiente', '2024-11-08 11:16:00', 9, '2024-11-12 14:31:00', 10),
    (12, null, 'Acceso no autorizado a áreas restringidas', null, 'PENDING', 'Quiero denunciar al lote 357', 'Pendiente', '2024-11-09 16:21:00', 11, '2024-11-13 15:56:00', 12),
    (13, null, 'Uso inapropiado de la parcela', null, 'PENDING', 'Quiero denunciar al lote 35624', 'Pendiente', '2024-11-10 08:51:00', 1, '2024-11-14 10:46:00', 2),
    (14, null, 'Presencia de ruido excesivo', null, 'PENDING', 'Quiero denunciar al lote 21435', 'Pendiente', '2024-11-11 09:11:00', 3, '2024-11-15 12:51:00', 4),
    (11, null, 'Desperdicio de agua en áreas verdes', null, 'PENDING', 'Quiero denunciar al lote 4123', 'Pendiente', '2024-11-08 11:17:00', 9, '2024-11-12 14:32:00', 10),
    (12, null, 'Acceso no autorizado a áreas restringidas', null, 'PENDING', 'Quiero denunciar al lote 1414', 'Pendiente', '2024-11-09 16:22:00', 11, '2024-11-13 15:57:00', 12),
    (13, null, 'Uso inapropiado de la parcela', null, 'PENDING', 'Quiero denunciar al lote 142', 'Pendiente', '2024-11-10 08:52:00', 1, '2024-11-14 10:47:00', 2),
    (14, null, 'Presencia de ruido excesivo', null, 'REJECTED', 'Quiero denunciar al lote 14', 'Rechazada por falta de pruebas', '2024-11-11 09:12:00', 3, '2024-11-15 12:52:00', 4),
    (11, null, 'Desperdicio de agua en áreas verdes', null, 'REJECTED', 'Quiero denunciar al lote 14', 'Rechazada por falta de pruebas', '2024-11-08 11:18:00', 9, '2024-11-12 14:33:00', 10),
    (12, null, 'Acceso no autorizado a áreas restringidas', null, 'REJECTED', 'Quiero denunciar al lote 11423', 'Rechazada por falta de pruebas', '2024-11-09 16:23:00', 11, '2024-11-13 15:58:00', 12),
    (13, null, 'Uso inapropiado de la parcela', null, 'REJECTED', 'Quiero denunciar al lote 42', 'Rechazada por falta de pruebas', '2024-11-10 08:53:00', 1, '2024-11-14 10:48:00', 2),
    (14, null, 'Presencia de ruido excesivo', null, 'REJECTED', 'Quiero denunciar al lote 14', 'Rechazada por falta de pruebas', '2024-11-11 09:13:00', 3, '2024-11-15 12:53:00', 4);

INSERT INTO pictures (complaint_id, picture_url, created_date, created_user, last_updated_date, last_updated_user)
VALUES
    (15, UUID(), '2024-11-01 16:30:00', 7, '2024-11-05 10:10:00', 8),
    (2, UUID(), '2024-11-02 08:40:00', 9, '2024-11-06 12:00:00', 10),
    (16, UUID(), '2024-11-03 13:15:00', 11, '2024-11-07 17:35:00', 12),
    (17, UUID(), '2024-11-04 11:00:00', 1, '2024-11-08 09:00:00', 2),
    (18, UUID(), '2024-11-05 14:00:00', 3, '2024-11-09 12:20:00', 4),
    (19, UUID(), '2024-11-06 10:30:00', 5, '2024-11-10 15:15:00', 6),
    (10, UUID(), '2024-11-07 09:45:00', 7, '2024-11-11 13:40:00', 8),
    (11, UUID(), '2024-11-08 11:15:00', 9, '2024-11-12 14:30:00', 10),
    (12, UUID(), '2024-11-09 16:20:00', 11, '2024-11-13 15:55:00', 12),
    (13, UUID(), '2024-11-10 08:50:00', 1, '2024-11-14 10:45:00', 2),
    (14, UUID(), '2024-11-11 09:10:00', 3, '2024-11-15 12:50:00', 4),
    (11, UUID(), '2024-11-08 11:16:00', 9, '2024-11-12 14:31:00', 10),
    (12, UUID(), '2024-11-09 16:21:00', 11, '2024-11-13 15:56:00', 12),
    (13, UUID(), '2024-11-10 08:51:00', 1, '2024-11-14 10:46:00', 2),
    (14, UUID(), '2024-11-11 09:11:00', 3, '2024-11-15 12:51:00', 4),
    (11, UUID(), '2024-11-08 11:17:00', 9, '2024-11-12 14:32:00', 10),
    (12, UUID(), '2024-11-09 16:22:00', 11, '2024-11-13 15:57:00', 12),
    (13, UUID(), '2024-11-10 08:52:00', 1, '2024-11-14 10:47:00', 2),
    (14, UUID(), '2024-11-11 09:12:00', 3, '2024-11-15 12:52:00', 4),
    (15, UUID(), '2024-11-01 16:31:00', 7, '2024-11-05 10:11:00', 8),
    (2, UUID(), '2024-11-02 08:41:00', 9, '2024-11-06 12:01:00', 10),
    (16, UUID(), '2024-11-03 13:16:00', 11, '2024-11-07 17:36:00', 12),
    (17, UUID(), '2024-11-04 11:01:00', 1, '2024-11-08 09:01:00', 2),
    (18, UUID(), '2024-11-05 14:01:00', 3, '2024-11-09 12:21:00', 4),
    (19, UUID(), '2024-11-06 10:31:00', 5, '2024-11-10 15:16:00', 6),
    (10, UUID(), '2024-11-07 09:46:00', 7, '2024-11-11 13:41:00', 8),
    (11, UUID(), '2024-11-08 11:16:00', 9, '2024-11-12 14:31:00', 10);