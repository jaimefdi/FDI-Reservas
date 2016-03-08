INSERT INTO User (USER_ID, username, email, password, enabled, accountExpired, accountLocked, credentialsExpired) VALUES (1, 'root', 'root@ucm.es', '$2a$10$//HU8yTgOoLrZX6z9cgXhuR/IJki.BtS/ZM4Pr8s/zBDCFIT.I3P6', true, false, false, false);
INSERT INTO User (USER_ID, username, email, password, enabled, accountExpired, accountLocked, credentialsExpired) VALUES (2, 'user', 'user@ucm.es', '$2a$10$qspi.NK1570DsUrvDmxETekRTqpk/ZY2hmI3XCMER.RWPVlPAfpYK', true, false, false, false);

INSERT INTO UserRole (user, role) VALUES(1, 'ROLE_USER');
INSERT INTO UserRole (user, role) VALUES(1, 'ROLE_ADMIN');
INSERT INTO UserRole (user, role) VALUES(2, 'ROLE_USER');

INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(1,'Facultad de Informática');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(2,'Facultad de Bellas Artes');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(3,'Facultad de Ciencias Biológicas');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(4,'Facultad de Ciencias de la Documentación');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(5,'Facultad de Ciencias de la Información');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(6,'Facultad de Ciencias Económicas y Empresariales');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(7,'Facultad de Ciencias Físicas');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(8,'Facultad de Ciencias Geológicas');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(9,'Facultad de Ciencias Matemáticas');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(10,'Facultad de Ciencias Políticas y Sociología');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(11,'Facultad de Ciencias Químicas');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(12,'Facultad de Comercio y Turismo');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(13,'Facultad de Derecho');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(14,'Facultad de Educación');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(15,'Facultad de Enfermería, Fisioterapia y Podología');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(16,'Facultad de Estudios Estadísticos');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(17,'Facultad de Farmacia');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(18,'Facultad de Filología');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(19,'Facultad de Filosofía');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(20,'Facultad de Geografía e Historia');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(21,'Facultad de Medicina');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(22,'Facultad de Odontología');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(23,'Facultad de Óptica y Optometría');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(24,'Facultad de Psicología');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(25,'Facultad de Trabajo Social');
INSERT INTO Facultad(FACULTAD_ID, nombreFacultad) VALUES(26,'Facultad de Veterinaria');

INSERT INTO UserFacultades(user,FACULTAD_ID) VALUES(2,1);

INSERT INTO Edificio(EDIFICIO_ID, nombre_edificio, FACULTAD_ID) VALUES(1,'FDI',1);
INSERT INTO Edificio(EDIFICIO_ID, nombre_edificio, FACULTAD_ID) VALUES(2,'Multiusos',1);

INSERT INTO Espacio(ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(1,'Sala de Grados',70,true,true,1,1);
INSERT INTO Espacio(ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(2,'Sala de Conferencias',180,true,true,1,1);
INSERT INTO Espacio(ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(3,'Sala de Reuniones',20,true,true,1,1);
INSERT INTO Espacio(ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(4,'Aula 1',80,true,true,1,0);
INSERT INTO Espacio(ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(5,'Aula 2',80,true,true,1,0);
INSERT INTO Espacio(ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(6,'Aula 3',80,true,true,1,0);
INSERT INTO Espacio(ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(7,'Aula 4',80,true,true,1,0);
INSERT INTO Espacio(ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(8,'Aula 5',80,true,true,1,0);
INSERT INTO Espacio(ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(9,'Lab. 1',50,true,true,1,2);
INSERT INTO Espacio(ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(10,'Aula 1280',80,true,true,2,0);
INSERT INTO Espacio(ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(11,'Aula 1290',80,true,true,2,0);

INSERT INTO Reserva(RESERVA_ID, asunto, comienzo, fin, estado, username, ESPACIO_ID) VALUES(1,'Charla Intel','2016-03-16 12:30:00','2016-03-16 14:30:00',true,'user',1);
INSERT INTO Reserva(RESERVA_ID, asunto, comienzo, fin, estado, username, ESPACIO_ID) VALUES(2,'Reunion TFG','2016-03-16 14:30:00','2016-03-16 16:30:00',false,'user',3);
INSERT INTO Reserva(RESERVA_ID, asunto, comienzo, fin, estado, username, ESPACIO_ID) VALUES(3,'Rvision examen','2016-03-18 14:30:00','2016-03-18 16:30:00',true,'user',4);
INSERT INTO Reserva(RESERVA_ID, asunto, comienzo, fin, estado, username, ESPACIO_ID) VALUES(4,'Tutoria','2016-03-17 14:30:00','2016-03-17 16:30:00',true,'user',3);
INSERT INTO Reserva(RESERVA_ID, asunto, comienzo, fin, estado, username, ESPACIO_ID) VALUES(5,'Clase de repaso','2016-03-23 14:30:00','2016-03-23 16:30:00',true,'user',5);
INSERT INTO Reserva(RESERVA_ID, asunto, comienzo, fin, estado, username, ESPACIO_ID) VALUES(6,'PL','2016-03-07 09:00:00','2016-03-07 11:00:00',true,'user',6);
INSERT INTO Reserva(RESERVA_ID, asunto, comienzo, fin, estado, username, ESPACIO_ID) VALUES(7,'PL','2016-03-09 09:00:00','2016-03-09 11:00:00',true,'user',6);
INSERT INTO Reserva(RESERVA_ID, asunto, comienzo, fin, estado, username, ESPACIO_ID) VALUES(8,'MAR','2016-03-09 11:00:00','2016-03-09 12:00:00',true,'user',6);
INSERT INTO Reserva(RESERVA_ID, asunto, comienzo, fin, estado, username, ESPACIO_ID) VALUES(9,'DVI','2016-03-09 14:00:00','2016-03-09 16:00:00',true,'user',6);


