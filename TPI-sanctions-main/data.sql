
-- Tabla report_reasons
CREATE TABLE IF NOT EXISTS report_reasons (
    id INT PRIMARY KEY AUTO_INCREMENT,
    report_reason VARCHAR(1000),
    base_amount DOUBLE,
    created_datetime DATETIME,
    created_user INT,
    last_updated_datetime DATETIME,
    last_updated_user INT
    );

-- Tabla reports
CREATE TABLE IF NOT EXISTS reports (
    id INT PRIMARY KEY AUTO_INCREMENT,
    report_state VARCHAR(255) NOT NULL,
    state_reason VARCHAR(1000),
    report_reason_id INT NOT NULL,
    plot_id INT NOT NULL,
    description VARCHAR(1000),
    created_datetime DATETIME,
    created_user INT,
    last_updated_datetime DATETIME,
    last_updated_user INT,
    FOREIGN KEY (report_reason_id) REFERENCES report_reasons(id)
    );

-- Tabla fines
CREATE TABLE IF NOT EXISTS fines (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fine_state VARCHAR(255),
    state_reason VARCHAR(1000),
    report_id INT,
    discharge_date DATE,
    amount DOUBLE,
    created_datetime DATETIME,
    created_user INT,
    last_updated_datetime DATETIME,
    last_updated_user INT,
    FOREIGN KEY (report_id) REFERENCES reports(id)
    );

-- Tabla disclaimers
CREATE TABLE IF NOT EXISTS disclaimers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fine_id INT,
    disclaimer VARCHAR(1000),
    created_datetime DATETIME,
    created_user INT,
    last_updated_datetime DATETIME,
    last_updated_user INT,
    FOREIGN KEY (fine_id) REFERENCES fines(id)
    );

-- Tabla warnings
CREATE TABLE IF NOT EXISTS warnings (
                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                        report_id INT,
                                        active BOOLEAN,
                                        created_datetime DATETIME,
                                        created_user INT,
                                        last_updated_datetime DATETIME,
                                        last_updated_user INT,
                                        FOREIGN KEY (report_id) REFERENCES reports(id)
    );
-- /////////////////////////// INSERTS ///////////////////////////////////
-- Inserts para REPORT_REASONS
INSERT INTO report_reasons (
    report_reason,
    base_amount,
    created_datetime,
    created_user,
    last_updated_datetime,
    last_updated_user
)
VALUES
    ('Uso inapropiado de la parcela', 100, '2024-10-05 08:45:00', 1, '2024-10-05 09:00:00', 1),
    ('Falta de mantenimiento', 100, '2024-09-25 15:30:00', 2, '2024-09-28 10:10:00', 2),
    ('Falta de iluminación en áreas comunes', 100, '2024-10-01 09:20:00', 1, '2024-10-02 12:15:00', 1),
    ('Presencia de ruido excesivo', 100, '2024-10-03 11:00:00', 2, '2024-10-04 10:50:00', 2),
    ('Daños en el equipo de gimnasio', 100, '2024-10-05 14:30:00', 3, '2024-10-06 09:45:00', 3),
    ('Fugas de agua en la piscina', 100, '2024-10-07 08:00:00', 4, '2024-10-08 14:10:00', 4),
    ('Problemas de seguridad en estacionamiento', 100, '2024-10-09 16:20:00', 5, '2024-10-10 18:25:00', 5),
    ('Mantenimiento pendiente en el ascensor', 100, '2024-10-11 07:40:00', 6, '2024-10-12 11:30:00', 6),
    ('Desperdicio de agua en áreas verdes', 100, '2024-10-13 13:15:00', 7, '2024-10-14 10:55:00', 7),
    ('Acceso no autorizado a áreas restringidas', 100, '2024-10-15 17:45:00', 8, '2024-10-16 15:05:00', 8),
    ('Vehículos estacionados en zonas prohibidas', 100, '2024-10-17 20:20:00', 1, '2024-10-18 19:00:00', 1),
    ('Ruidos molestos en horario nocturno', 100, '2024-11-02 21:45:00', 1, '2024-11-03 22:45:00', 1),
    ('Mal estado de las instalaciones deportivas', 100, '2024-10-19 06:50:00', 2, '2024-10-20 08:40:00', 2);

-- Inserts para REPORTS
INSERT INTO reports (
    report_state,
    state_reason,
    report_reason_id,
    plot_id,
    description,
    created_datetime,
    created_user,
    last_updated_datetime,
    last_updated_user
)
VALUES
    (1, 'Descripción inicial del estado', 1, 1, 'Reporte sobre lote 1', '2024-10-06 10:30:00', 1, '2024-10-06 12:00:00', 1),
    (2, 'Problema resuelto satisfactoriamente', 2, 2, 'Reporte sobre lote 2', '2024-09-30 09:15:00', 2, '2024-10-01 14:20:00', 2),
    (2, 'Reporte Abierto', 2, 3, 'Reporte sobre lote 3', '2024-09-10 09:15:00', 2, '2024-10-01 14:20:00', 2),
    (4, 'Se rechaza el reporte por falta de motivo para ampliar el caso', 2, 4, 'Reporte sobre lote 4', '2024-09-30 09:15:00', 2, '2024-10-01 14:20:00', 2),
    (4, 'Se rechaza el reporte por falta de pruebas documentales', 1, 5, 'Informe de inspección en lote 5', '2024-01-29 10:20:00', 3, '2024-09-30 11:30:00', 3),
    (3, 'El reporte se aprueba para análisis adicional', 2, 6, 'Revisión preliminar en lote 6', '2024-01-28 09:00:00', 4, '2024-09-29 09:50:00', 4),
    (4, 'Caso cerrado sin hallazgos concluyentes', 3, 7, 'Reporte de finalización lote 7', '2024-01-27 14:45:00', 5, '2024-09-28 13:15:00', 5),
    (2, 'Se requiere información adicional para evaluar el caso', 4, 8, 'Revisión inicial lote 8', '2024-02-26 11:35:00', 6, '2024-09-27 14:55:00', 6),
    (1, 'Reporte en proceso de revisión inicial', 5, 9, 'Investigación preliminar en lote 9', '2024-02-25 08:10:00', 7, '2024-09-26 09:40:00', 7),
    (3, 'El caso fue aprobado para revisión final', 1, 10, 'Informe de situación lote 10', '2024-02-24 13:55:00', 1, '2024-09-25 15:05:00', 1),
    (2, 'Caso concluido con hallazgos parciales', 2, 11, 'Evaluación final lote 11', '2024-09-23 12:20:00', 2, '2024-09-24 10:00:00', 2),
    (4, 'Se rechaza el reporte por insuficiencia de datos', 3, 12, 'Reporte de anomalías en lote 12', '2024-02-22 15:30:00', 3, '2024-09-23 12:30:00', 3),
    (2, 'Información incompleta, se requiere seguimiento', 4, 13, 'Reporte inicial lote 13', '2024-02-21 10:50:00', 4, '2024-09-22 09:45:00', 4),
    (1, 'Caso nuevo pendiente de revisión', 5, 14, 'Informe inicial lote 14', '2024-03-20 16:40:00', 5, '2024-09-21 08:30:00', 5),
    (4, 'Caso cerrado tras la inspección satisfactoria', 1, 15, 'Informe de conclusión lote 15', '2024-03-19 09:55:00', 6, '2024-09-20 15:25:00', 6),
    (3, 'Reporte aprobado para revisión', 2, 16, 'Investigación en lote 16', '2024-03-18 14:25:00', 7, '2024-09-19 10:10:00', 7),
    (4, 'Reporte rechazado por insuficiencia de pruebas', 3, 17, 'Revisión lote 17', '2024-03-17 11:30:00', 1, '2024-09-18 08:55:00', 1),
    (2, 'Se requiere seguimiento de datos adicionales', 4, 18, 'Informe preliminar lote 18', '2024-03-16 07:45:00', 2, '2024-09-17 12:35:00', 2),
    (1, 'Reporte en revisión inicial', 5, 19, 'Nuevo caso en lote 19', '2024-03-15 18:40:00', 3, '2024-09-16 09:00:00', 3),
    (3, 'Caso aprobado para análisis extendido', 1, 20, 'Reporte sobre lote 20', '2024-03-14 10:15:00', 4, '2024-09-15 14:20:00', 4),
    (1, 'Reporte cerrado sin resultados concluyentes', 2, 21, 'Seguimiento final en lote 21', '2024-03-13 09:05:00', 5, '2024-09-14 13:40:00', 5),
    (4, 'El reporte fue rechazado por datos inconsistentes', 3, 22, 'Inspección fallida lote 22', '2024-03-12 13:15:00', 6, '2024-09-13 16:45:00', 6),
    (2, 'Datos adicionales requeridos para proceder', 4, 23, 'Análisis preliminar lote 23', '2024-03-11 07:30:00', 7, '2024-09-12 08:15:00', 7),
    (1, 'Reporte registrado y en espera de evaluación', 5, 24, 'Nuevo caso en lote 24', '2024-03-10 17:10:00', 1, '2024-09-11 10:50:00', 1),
    (4, 'Se rechaza el reporte por falta de información', 1, 25, 'Inspección de lote 25', '2024-03-21 11:00:00', 1, '2024-10-22 10:00:00', 1),
    (3, 'En revisión por el comité', 2, 26, 'Informe de auditoría lote 26', '2024-06-22 14:30:00', 2, '2024-10-23 12:15:00', 2),
    (2, 'Requiere seguimiento adicional', 3, 27, 'Evaluación de riesgo en lote 27', '2024-06-23 09:50:00', 3, '2024-10-24 11:30:00', 3),
    (1, 'Aprobado para inspección', 4, 28, 'Revisión de condiciones en lote 28', '2024-06-24 10:15:00', 4, '2024-10-25 09:45:00', 4),
    (0, 'Cerrado con observaciones', 5, 29, 'Cierre de evaluación lote 29', '2024-06-25 13:45:00', 5, '2024-10-26 10:20:00', 5),
    (4, 'Se rechaza por incumplimiento de normas', 1, 30, 'Revisión de normas en lote 30', '2024-06-26 08:00:00', 6, '2024-10-27 12:10:00', 6),
    (1, 'Descripción inicial del estado', 1, 1, 'Reporte sobre lote 1', '2023-10-06 10:30:00', 1, '2023-10-06 12:00:00', 1),
    (2, 'Problema resuelto satisfactoriamente', 2, 2, 'Reporte sobre lote 2', '2023-09-30 09:15:00', 2, '2023-10-01 14:20:00', 2),
    (2, 'Reporte Abierto', 2, 3, 'Reporte sobre lote 3', '2023-09-10 09:15:00', 2, '2023-10-01 14:20:00', 2),
    (4, 'Se rechaza el reporte por falta de motivo para ampliar el caso', 2, 4, 'Reporte sobre lote 4', '2023-09-30 09:15:00', 2, '2023-10-01 14:20:00', 2),
    (4, 'Se rechaza el reporte por falta de pruebas documentales', 1, 5, 'Informe de inspección en lote 5', '2023-01-29 10:20:00', 3, '2023-09-30 11:30:00', 3),
    (3, 'El reporte se aprueba para análisis adicional', 2, 6, 'Revisión preliminar en lote 6', '2023-01-28 09:00:00', 4, '2023-09-29 09:50:00', 4),
    (4, 'Caso cerrado sin hallazgos concluyentes', 3, 7, 'Reporte de finalización lote 7', '2023-01-27 14:45:00', 5, '2023-09-28 13:15:00', 5),
    (2, 'Se requiere información adicional para evaluar el caso', 4, 8, 'Revisión inicial lote 8', '2023-02-26 11:35:00', 6, '2023-09-27 14:55:00', 6),
    (1, 'Reporte en proceso de revisión inicial', 5, 9, 'Investigación preliminar en lote 9', '2023-02-25 08:10:00', 7, '2023-09-26 09:40:00', 7),
    (3, 'El caso fue aprobado para revisión final', 1, 10, 'Informe de situación lote 10', '2023-02-24 13:55:00', 1, '2023-09-25 15:05:00', 1),
    (2, 'Caso concluido con hallazgos parciales', 2, 11, 'Evaluación final lote 11', '2023-09-23 12:20:00', 2, '2023-09-24 10:00:00', 2),
    (4, 'Se rechaza el reporte por insuficiencia de datos', 3, 12, 'Reporte de anomalías en lote 12', '2023-02-22 15:30:00', 3, '2023-09-23 12:30:00', 3),
    (2, 'Información incompleta, se requiere seguimiento', 4, 13, 'Reporte inicial lote 13', '2023-02-21 10:50:00', 4, '2023-09-22 09:45:00', 4),
    (1, 'Caso nuevo pendiente de revisión', 5, 14, 'Informe inicial lote 14', '2023-03-20 16:40:00', 5, '2023-09-21 08:30:00', 5),
    (4, 'Caso cerrado tras la inspección satisfactoria', 1, 15, 'Informe de conclusión lote 15', '2023-03-19 09:55:00', 6, '2023-09-20 15:25:00', 6),
    (3, 'Reporte aprobado para revisión', 2, 16, 'Investigación en lote 16', '2023-03-18 14:25:00', 7, '2023-09-19 10:10:00', 7),
    (4, 'Reporte rechazado por insuficiencia de pruebas', 3, 17, 'Revisión lote 17', '2023-03-17 11:30:00', 1, '2023-09-18 08:55:00', 1),
    (2, 'Se requiere seguimiento de datos adicionales', 4, 18, 'Informe preliminar lote 18', '2023-03-16 07:45:00', 2, '2023-09-17 12:35:00', 2),
    (1, 'Reporte en revisión inicial', 5, 19, 'Nuevo caso en lote 19', '2023-03-15 18:40:00', 3, '2023-09-16 09:00:00', 3),
    (3, 'Caso aprobado para análisis extendido', 1, 20, 'Reporte sobre lote 20', '2023-03-14 10:15:00', 4, '2023-09-15 14:20:00', 4),
    (1, 'Reporte cerrado sin resultados concluyentes', 2, 21, 'Seguimiento final en lote 21', '2024-11-13 09:05:00', 5, '2024-11-14 13:40:00', 5),
    (4, 'El reporte fue rechazado por datos inconsistentes', 3, 22, 'Inspección fallida lote 22', '2024-11-12 13:15:00', 6, '2024-11-13 16:45:00', 6),
    (2, 'Datos adicionales requeridos para proceder', 4, 23, 'Análisis preliminar lote 23', '2024-11-11 07:30:00', 7, '2024-11-12 08:15:00', 7),
    (1, 'Reporte registrado y en espera de evaluación', 5, 24, 'Nuevo caso en lote 24', '2024-11-10 17:10:00', 1, '2024-11-11 10:50:00', 1),
    (4, 'Se rechaza el reporte por falta de información', 1, 25, 'Inspección de lote 25', '2024-11-13 11:00:00', 1, '2024-10-18 10:00:00', 1),
    (3, 'En revisión por el comité', 2, 26, 'Informe de auditoría lote 26', '2024-11-16 14:30:00', 2, '2024-10-16 12:15:00', 2),
    (2, 'Requiere seguimiento adicional', 3, 27, 'Evaluación de riesgo en lote 27', '2024-11-19 09:50:00', 3, '2024-11-19 11:30:00', 3),
    (1, 'Aprobado para inspección', 4, 28, 'Revisión de condiciones en lote 28', '2024-11-18 10:15:00', 4, '2043-11-19 09:45:00', 4),
    (0, 'Cerrado con observaciones', 5, 29, 'Cierre de evaluación lote 29', '2024-11-10 13:45:00', 5, '2024-11-11 10:20:00', 5),
    (4, 'Se rechaza por incumplimiento de normas', 1, 30, 'Revisión de normas en lote 30', '2024-11-05 08:00:00', 6, '2024-11-10 12:10:00', 6);

-- Inserts para FINES
INSERT INTO fines (fine_state, state_reason, report_id, discharge_date, amount,
                   created_datetime, created_user, last_updated_datetime, last_updated_user)
VALUES
    (0, 'Falta de pago de cuota. Nota: El pago debe realizarse antes de la fecha de vencimiento.', 21, '2023-10-01', 150.00, '2023-04-12 10:15:00', 1, '2023-08-12 10:15:00', 1),
    (1, 'Multa por estacionamiento indebido. Nota: Asegúrese de estacionar en las áreas designadas.', 22, '2023-10-02', 100.00, '2023-04-01 14:45:00', 2, '2023-09-01 14:45:00', 2),
    (2, 'Infracción por ruidos molestos. Nota: Respete los horarios de silencio.', 23, '2023-10-03', 250.00, '2023-04-18 09:30:00', 3, '2023-09-18 09:30:00', 3),
    (3, 'Uso indebido de instalaciones. Nota: Siga las normas de uso de las instalaciones.', 24, '2023-10-04', 300.00, '2023-04-03 16:20:00', 4, '2023-10-03 16:20:00', 4),
    (0, 'No cumplir con horarios de uso', 25, '2023-10-05', 180.00, '2023-05-29 08:00:00', 5, '2023-08-29 08:00:00', 5),
    (1, 'Daños a propiedad común. Nota: Los daños deben ser reparados a la brevedad.', 26, '2023-10-06', 220.00, '2023-04-22 13:50:00', 6, '2023-09-22 13:50:00', 6),
    (2, 'Incumplimiento de normas de seguridad. Nota: Cumpla con todas las normas de seguridad.', 27, '2023-10-07', 130.00, '2023-04-11 11:25:00', 7, '2023-09-11 11:25:00', 7),
    (3, 'Falta de mantenimiento en áreas comunes. Nota: Mantenga las áreas comunes en buen estado.', 28, '2023-10-08', 200.00, '2023-07-05 17:45:00', 8, '2023-10-05 17:45:00', 8),
    (0, 'Exceso en el consumo de agua', 29, '2023-10-09', 160.00, '2023-06-20 07:55:00', 1, '2023-08-20 07:55:00', 1),
    (1, 'Incidente de vandalismo. Nota: El vandalismo será reportado a las autoridades.', 30, '2023-10-10', 400.00, '2023-06-29 19:00:00', 2, '2023-09-29 19:00:00', 2),
    (0, 'Falta de pago de cuota. Nota: El pago debe realizarse antes de la fecha de vencimiento.', 1, '2024-10-01', 150.00, '2024-04-12 10:15:00', 1, '2024-08-12 10:15:00', 1),
    (1, 'Multa por estacionamiento indebido. Nota: Asegúrese de estacionar en las áreas designadas.', 2, '2024-10-02', 100.00, '2024-04-01 14:45:00', 2, '2024-09-01 14:45:00', 2),
    (2, 'Infracción por ruidos molestos. Nota: Respete los horarios de silencio.', 3, '2024-10-03', 250.00, '2024-04-18 09:30:00', 3, '2024-09-18 09:30:00', 3),
    (3, 'Uso indebido de instalaciones. Nota: Siga las normas de uso de las instalaciones.', 4, '2024-10-04', 300.00, '2024-04-03 16:20:00', 4, '2024-10-03 16:20:00', 4),
    (0, 'No cumplir con horarios de uso', 5, '2024-10-05', 180.00, '2024-05-29 08:00:00', 5, '2024-08-29 08:00:00', 5),
    (1, 'Daños a propiedad común. Nota: Los daños deben ser reparados a la brevedad.', 6, '2024-10-06', 220.00, '2024-04-22 13:50:00', 6, '2024-09-22 13:50:00', 6),
    (2, 'Incumplimiento de normas de seguridad. Nota: Cumpla con todas las normas de seguridad.', 7, '2024-10-07', 130.00, '2024-04-11 11:25:00', 7, '2024-09-11 11:25:00', 7),
    (3, 'Falta de mantenimiento en áreas comunes. Nota: Mantenga las áreas comunes en buen estado.', 8, '2024-10-08', 200.00, '2024-07-05 17:45:00', 8, '2024-10-05 17:45:00', 8),
    (0, 'Exceso en el consumo de agua', 9, '2024-10-09', 160.00, '2024-06-20 07:55:00', 1, '2024-08-20 07:55:00', 1),
    (1, 'Incidente de vandalismo. Nota: El vandalismo será reportado a las autoridades.', 10, '2024-10-10', 400.00, '2024-06-29 19:00:00', 2, '2024-09-29 19:00:00', 2),
    (2, 'Uso inadecuado de zonas recreativas', 11, '2024-10-11', 90.00, '2024-07-01 12:10:00', 3, '2024-10-01 12:10:00', 3),
    (3, 'Infracción por mal estacionamiento', 12, '2024-10-12', 75.00, '2024-07-12 15:40:00', 4, '2024-10-12 15:40:00', 4),
    (0, 'Queja por desorden en áreas comunes', 13, '2024-10-13', 120.00, '2024-07-05 06:30:00', 5, '2024-09-05 06:30:00', 5),
    (1, 'Desperfecto en el equipo de gimnasio', 14, '2024-10-14', 300.00, '2024-09-04 18:20:00', 6, '2024-10-04 18:20:00', 6),
    (2, 'Incumplimiento de normas de convivencia', 15, '2024-10-15', 110.00, '2024-10-15 13:15:00', 7, '2024-09-15 13:15:00', 7),
    (3, 'Mala disposición de residuos', 16, '2024-10-16', 80.00, '2024-10-15 21:35:00', 8, '2024-10-15 21:35:00', 8),
    (0, 'Problemas de ruido en horas no permitidas', 17, '2024-10-17', 160.00, '2024-10-17 10:05:00', 1, '2024-08-17 10:05:00', 1),
    (1, 'Mal uso de las instalaciones deportivas', 18, '2024-10-18', 95.00, '2024-10-27 16:00:00', 2, '2024-09-27 16:00:00', 2),
    (2, 'Retención indebida de documentos', 19, '2024-10-19', 210.00, '2024-10-06 08:45:00', 3, '2024-10-06 08:45:00', 3),
    (3, 'Falta de respeto a las normas establecidas', 20, '2024-10-20', 190.00, '2024-10-05 12:00:00', 4, '2024-11-05 12:00:00', 4),
    (0, 'Falta de pago de cuota. Nota: El pago debe realizarse antes de la fecha de vencimiento.', 31, '2024-11-01', 150.00, '2024-05-12 10:15:00', 1, '2024-09-12 10:15:00', 1),
    (1, 'Multa por estacionamiento indebido. Nota: Asegúrese de estacionar en las áreas designadas.', 32, '2024-11-02', 100.00, '2024-05-01 14:45:00', 2, '2024-10-01 14:45:00', 2),
    (2, 'Infracción por ruidos molestos. Nota: Respete los horarios de silencio.', 33, '2024-11-03', 250.00, '2024-05-18 09:30:00', 3, '2024-10-18 09:30:00', 3),
    (3, 'Uso indebido de instalaciones. Nota: Siga las normas de uso de las instalaciones.', 34, '2024-11-04', 300.00, '2024-05-03 16:20:00', 4, '2024-11-03 16:20:00', 4),
    (0, 'No cumplir con horarios de uso', 35, '2024-11-05', 180.00, '2024-06-29 08:00:00', 5, '2024-09-29 08:00:00', 5),
    (1, 'Daños a propiedad común. Nota: Los daños deben ser reparados a la brevedad.', 36, '2024-11-06', 220.00, '2024-05-22 13:50:00', 6, '2024-10-22 13:50:00', 6),
    (2, 'Incumplimiento de normas de seguridad. Nota: Cumpla con todas las normas de seguridad.', 37, '2024-11-07', 130.00, '2024-05-11 11:25:00', 7, '2024-10-11 11:25:00', 7),
    (3, 'Falta de mantenimiento en áreas comunes. Nota: Mantenga las áreas comunes en buen estado.', 38, '2024-11-08', 200.00, '2024-08-05 17:45:00', 8, '2024-11-05 17:45:00', 8),
    (0, 'Exceso en el consumo de agua', 39, '2024-11-09', 160.00, '2024-07-20 07:55:00', 1, '2024-09-20 07:55:00', 1),
    (1, 'Incidente de vandalismo. Nota: El vandalismo será reportado a las autoridades.', 40, '2024-11-10', 400.00, '2024-07-29 19:00:00', 2, '2024-10-29 19:00:00', 2),
    (0, 'Falta de pago de cuota. Nota: El pago debe realizarse antes de la fecha de vencimiento.', 41, '2023-11-01', 150.00, '2023-05-12 10:15:00', 1, '2023-09-12 10:15:00', 1),
    (1, 'Multa por estacionamiento indebido. Nota: Asegúrese de estacionar en las áreas designadas.', 42, '2023-11-02', 100.00, '2023-05-01 14:45:00', 2, '2023-10-01 14:45:00', 2),
    (2, 'Infracción por ruidos molestos. Nota: Respete los horarios de silencio.', 43, '2023-11-03', 250.00, '2023-05-18 09:30:00', 3, '2023-10-18 09:30:00', 3),
    (3, 'Uso indebido de instalaciones. Nota: Siga las normas de uso de las instalaciones.', 44, '2023-11-04', 300.00, '2023-05-03 16:20:00', 4, '2023-11-03 16:20:00', 4),
    (0, 'No cumplir con horarios de uso', 45, '2023-11-05', 180.00, '2023-06-29 08:00:00', 5, '2023-09-29 08:00:00', 5),
    (1, 'Daños a propiedad común. Nota: Los daños deben ser reparados a la brevedad.', 46, '2023-11-06', 220.00, '2023-05-22 13:50:00', 6, '2023-10-22 13:50:00', 6),
    (2, 'Incumplimiento de normas de seguridad. Nota: Cumpla con todas las normas de seguridad.', 47, '2023-11-07', 130.00, '2023-05-11 11:25:00', 7, '2023-10-11 11:25:00', 7),
    (3, 'Falta de mantenimiento en áreas comunes. Nota: Mantenga las áreas comunes en buen estado.', 48, '2023-11-08', 200.00, '2023-08-05 17:45:00', 8, '2023-11-05 17:45:00', 8),
    (0, 'Exceso en el consumo de agua', 49, '2023-11-09', 160.00, '2023-07-20 07:55:00', 1, '2023-09-20 07:55:00', 1),
    (1, 'Incidente de vandalismo. Nota: El vandalismo será reportado a las autoridades.', 50, '2023-11-10', 400.00, '2023-07-29 19:00:00', 2, '2023-10-29 19:00:00', 2);

-- Inserts para DISCLAIMERS
INSERT INTO disclaimers (fine_id, disclaimer, created_datetime,
                         created_user, last_updated_datetime, last_updated_user)
VALUES
    (1, 'El pago debe realizarse antes de la fecha de vencimiento.', '2024-04-12 10:15:00', 1, '2024-08-12 10:15:00', 1),
    (2, 'Asegúrese de estacionar en las áreas designadas.', '2024-04-01 14:45:00', 2, '2024-09-01 14:45:00', 2),
    (3, 'Respete los horarios de silencio.', '2024-04-18 09:30:00', 3, '2024-09-18 09:30:00', 3),
    (4, 'Siga las normas de uso de las instalaciones.', '2024-04-03 16:20:00', 4, '2024-10-03 16:20:00', 4),
    (10, 'El vandalismo será reportado a las autoridades.', '2024-06-29 19:00:00', 2, '2024-09-29 19:00:00', 2);

-- Inserts para WARNINGS
INSERT INTO warnings (
    report_id,
    active,
    created_datetime,
    created_user,
    last_updated_datetime,
    last_updated_user
)
VALUES
    (1, true, '2024-10-06 08:30:00', 1, '2024-10-06 09:45:00', 2),
    (2, true, '2024-09-30 10:15:00', 2, '2024-09-30 11:20:00', 3),
    (3, false, '2024-09-15 14:20:00', 3, '2024-09-15 15:30:00', 1),
    (4, true, '2024-09-20 09:00:00', 4, '2024-09-20 10:45:00', 4),
    (5, true, '2024-08-25 11:30:00', 1, '2024-08-25 13:15:00', 2),
    (6, false, '2024-08-10 15:45:00', 2, '2024-08-10 16:50:00', 3),
    (7, true, '2024-07-05 13:20:00', 3, '2024-07-05 14:30:00', 1),
    (8, false, '2024-06-28 10:00:00', 4, '2024-06-28 11:45:00', 4),
    (9, true, '2024-06-15 16:30:00', 1, '2024-06-15 17:40:00', 2),
    (10, true, '2024-05-20 09:45:00', 2, '2024-05-20 10:50:00', 3),
    (11, false, '2024-05-10 14:15:00', 3, '2024-05-10 15:25:00', 1),
    (12, true, '2024-04-25 11:00:00', 4, '2024-04-25 12:30:00', 4),
    (13, true, '2024-04-15 08:30:00', 1, '2024-04-15 09:45:00', 2),
    (14, false, '2024-03-30 13:45:00', 2, '2024-03-30 14:50:00', 3),
    (15, true, '2024-03-15 10:20:00', 3, '2024-03-15 11:30:00', 1);

-- ///////////////////////////////// TABLAS AUDITH /////////////////////////////////////

-- Tabla de auditoría para REPORT_REASONS
-- CREATE TABLE IF NOT EXISTS REPORT_REASONS_AUDIT
-- (
--     id
--     INT
--     PRIMARY
--     KEY
--     AUTO_INCREMENT,
--     report_reason
--     VARCHAR
-- (
--     1000
-- ),
--     base_amount DOUBLE,
--     created_datetime DATETIME,
--     created_user INT,
--     last_updated_datetime DATETIME,
--     last_updated_user INT
--     );
--
-- -- Tabla de auditoría para REPORTS
-- CREATE TABLE IF NOT EXISTS REPORTS_AUDIT
-- (
--     id
--     INT
--     PRIMARY
--     KEY
--     AUTO_INCREMENT,
--     report_state
--     ENUM
-- (
--     'PENDING',
--     'OPEN',
--     'CLOSED',
--     'REJECTED',
--     'ENDED'
-- ) NOT NULL,
--     report_reason_id INT,
--     plot_id INT,
--     description VARCHAR
-- (
--     1000
-- ),
--     created_datetime DATETIME,
--     created_user INT,
--     last_updated_datetime DATETIME,
--     last_updated_user INT,
--     state_reason VARCHAR
-- (
--     1000
-- )
--     );
--
-- -- Tabla de auditoría para FINES
-- CREATE TABLE IF NOT EXISTS FINES_AUDIT
-- (
--     id
--     INT
--     PRIMARY
--     KEY
--     AUTO_INCREMENT,
--     fine_state
--     ENUM
-- (
--     'PENDING',
--     'PAYMENT_PAYMENT',
--     'PAYED',
--     'APPEALED',
--     'ACQUITTED'
-- ) NOT NULL,
--     report_id INT,
--     discharge_date DATE,
--     amount DOUBLE,
--     created_datetime DATETIME,
--     created_user INT,
--     last_updated_datetime DATETIME,
--     last_updated_user INT,
--     state_reason VARCHAR
-- (
--     1000
-- )
--     );
--
-- -- Tabla de auditoría para DISCLAIMERS
-- CREATE TABLE IF NOT EXISTS DISCLAIMERS_AUDIT
-- (
--     id
--     INT
--     PRIMARY
--     KEY
--     AUTO_INCREMENT,
--     disclaimer
--     VARCHAR
-- (
--     1000
-- ),
--     created_datetime DATETIME,
--     created_user INT,
--     last_updated_datetime DATETIME,
--     last_updated_user INT
--     );
--
-- -- Tabla de auditoría para WARNINGS
-- CREATE TABLE IF NOT EXISTS WARNINGS_AUDIT
-- (
--     id
--     INT
--     PRIMARY
--     KEY
--     AUTO_INCREMENT,
--     report_id
--     INT,
--     active
--     BOOLEAN,
--     created_datetime
--     DATETIME,
--     created_user
--     INT,
--     last_updated_datetime
--     DATETIME,
--     last_updated_user
--     INT
-- );

-- ///////////////////// TRIGGERS ////////////////////

-- Trigger para la tabla REPORT_REASONS
-- DELIMITER
-- //
-- CREATE TRIGGER after_insert_report_reasons
--     AFTER INSERT
--     ON REPORT_REASONS
--     FOR EACH ROW
-- BEGIN
--     INSERT INTO REPORT_REASON_AUDIT (report_reason, created_datetime, created_user, last_updated_datetime,
--                                      last_updated_user)
--     VALUES (NEW.report_reason, NEW.created_datetime, NEW.created_user, NEW.last_updated_datetime,
--             NEW.last_updated_user);
-- END;
-- //
-- DELIMITER ;
--
-- -- Trigger para la tabla REPORTS
-- DELIMITER
-- //
-- CREATE TRIGGER after_insert_reports
--     AFTER INSERT
--     ON REPORTS
--     FOR EACH ROW
-- BEGIN
--     INSERT INTO REPORTS_AUDIT (report_state, report_reason_id, plot_id, description, created_datetime, created_user,
--                                last_updated_datetime, last_updated_user, state_reason)
--     VALUES (NEW.report_state, NEW.report_reason_id, NEW.plot_id, NEW.description, NEW.created_datetime,
--             NEW.created_user, NEW.last_updated_datetime, NEW.last_updated_user, NEW.state_reason);
-- END;
-- //
-- DELIMITER ;
--
-- -- Trigger para la tabla FINES
-- DELIMITER
-- //
-- CREATE TRIGGER after_insert_fines
--     AFTER INSERT
--     ON FINES
--     FOR EACH ROW
-- BEGIN
--     INSERT INTO FINES_AUDIT (fine_state, report_id, discharge_date, amount, created_datetime, created_user,
--                              last_updated_datetime, last_updated_user, state_reason)
--     VALUES (NEW.fine_state, NEW.report_id, NEW.discharge_date, NEW.amount, NEW.created_datetime, NEW.created_user,
--             NEW.last_updated_datetime, NEW.last_updated_user, NEW.state_reason);
-- END;
-- //
-- DELIMITER ;
--
-- -- Trigger para la tabla DISCLAIMERS
-- DELIMITER
-- //
-- CREATE TRIGGER after_insert_disclaimers
--     AFTER INSERT
--     ON DISCLAIMERS
--     FOR EACH ROW
-- BEGIN
--     INSERT INTO DISCLAIMERS_AUDIT (disclaimer, created_datetime, created_user, last_updated_datetime, last_updated_user)
--     VALUES (NEW.disclaimer, NEW.created_datetime, NEW.created_user, NEW.last_updated_datetime, NEW.last_updated_user);
-- END;
-- //
-- DELIMITER ;
--
-- -- Trigger para la tabla WARNINGS
-- DELIMITER
-- //
-- CREATE TRIGGER after_insert_warnings
--     AFTER INSERT
--     ON WARNINGS
--     FOR EACH ROW
-- BEGIN
--     INSERT INTO WARNINGS_AUDIT (report_id, active, created_datetime, created_user, last_updated_datetime,
--                                 last_updated_user)
--     VALUES (NEW.report_id, NEW.active, NEW.created_datetime, NEW.created_user, NEW.last_updated_datetime,
--             NEW.last_updated_user);
-- END;
-- //
-- DELIMITER ;

