INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(1,'Facultad de Informática','www.informatica.ucm.es');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(2,'Facultad de Bellas Artes','www.bellasartes.ucm.es');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(3,'Facultad de Ciencias Biológicas','www.biologicas.ucm.es');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(4,'Facultad de Ciencias de la Documentación','www.documentacion.ucm.es');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(5,'Facultad de Ciencias de la Información','www.ccinformacion.ucm.es');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(6,'Facultad de Ciencias Económicas y Empresariales','Campus de Somosaguas');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(7,'Facultad de Ciencias Físicas','Plaza Ciencias, 1');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(8,'Facultad de Ciencias Geológicas','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(9,'Facultad de Ciencias Matemáticas','Plaza Ciencias, 3');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(10,'Facultad de Ciencias Políticas y Sociología','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(11,'Facultad de Ciencias Químicas','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(12,'Facultad de Comercio y Turismo','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(13,'Facultad de Derecho','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(14,'Facultad de Educación','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(15,'Facultad de Enfermería, Fisioterapia y Podología','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(16,'Facultad de Estudios Estadísticos','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(17,'Facultad de Farmacia','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(18,'Facultad de Filología','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(19,'Facultad de Filosofía','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(20,'Facultad de Geografía e Historia','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(21,'Facultad de Medicina','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(22,'Facultad de Odontología','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(23,'Facultad de Óptica y Optometría','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(24,'Facultad de Psicología','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(25,'Facultad de Trabajo Social','C/ José Antonio Novais, 12');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad) VALUES(26,'Facultad de Veterinaria','C/ José Antonio Novais, 12');

INSERT INTO User (UserId, username, email, password, enabled, accountExpired, accountLocked, credentialsExpired, FacultadId) VALUES (1, 'root', 'root@ucm.es', '$2a$10$//HU8yTgOoLrZX6z9cgXhuR/IJki.BtS/ZM4Pr8s/zBDCFIT.I3P6', true, false, false, false, 2);
INSERT INTO User (UserId, username, email, password, enabled, accountExpired, accountLocked, credentialsExpired, FacultadId) VALUES (2, 'user', 'user@ucm.es', '$2a$10$qspi.NK1570DsUrvDmxETekRTqpk/ZY2hmI3XCMER.RWPVlPAfpYK', true, false, false, false, 1);

INSERT INTO UserRole (user, role) VALUES(1, 'ROLE_USER');
INSERT INTO UserRole (user, role) VALUES(1, 'ROLE_ADMIN');
INSERT INTO UserRole (user, role) VALUES(2, 'ROLE_USER');

INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId) VALUES(1,'FDI','C/ del Prof. José G! Santesmases, 9',1);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId) VALUES(2,'Multiusos','C/ del Prof. Aranguren, s/n',1);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId) VALUES(3,'Edificio de Bellas Artes','C/ Pintor el Greco, 2',2);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId) VALUES(4,'Edificio de Ciencias Biológicas','C/ José Antonio Novais, 12',3);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId) VALUES(5,'Edificio de la Documentación','C/ Santísima Trinidad, 37',4);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId) VALUES(6,'Edificio de la Información','Av. Complutense, s/n',5);

INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio) VALUES(1,'Sala de Grados',70,true,true,1,1);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio) VALUES(2,'Sala de Conferencias',180,true,true,1,1);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio) VALUES(3,'Sala de Reuniones',20,true,true,1,1);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio) VALUES(4,'Aula 1',80,true,true,1,0);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio) VALUES(5,'Aula 2',80,true,true,1,0);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio) VALUES(6,'Aula 3',80,true,true,1,0);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio) VALUES(7,'Aula 4',80,true,true,1,0);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio) VALUES(8,'Aula 5',80,true,true,1,0);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio) VALUES(9,'Lab. 1',50,true,true,1,2);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio) VALUES(10,'Aula 1280',80,true,true,2,0);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio) VALUES(11,'Aula 1290',80,true,true,2,0);

INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estado, username, EspacioId) VALUES(1,'Charla Intel','2016-03-16 12:30:00','2016-03-16 14:30:00',true,'user',1);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estado, username, EspacioId) VALUES(2,'Reunion TFG','2016-03-16 14:30:00','2016-03-16 16:30:00',false,'user',3);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estado, username, EspacioId) VALUES(3,'Rvision examen','2016-03-18 14:30:00','2016-03-18 16:30:00',true,'user',4);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estado, username, EspacioId) VALUES(4,'Tutoria','2016-03-17 14:30:00','2016-03-17 16:30:00',true,'user',3);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estado, username, EspacioId) VALUES(5,'Clase de repaso','2016-03-23 14:30:00','2016-03-23 16:30:00',true,'user',5);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estado, username, EspacioId) VALUES(6,'PL','2016-03-07 09:00:00','2016-03-07 11:00:00',true,'user',6);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estado, username, EspacioId) VALUES(7,'PL','2016-03-09 09:00:00','2016-03-09 11:00:00',true,'user',6);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estado, username, EspacioId) VALUES(8,'MAR','2016-03-09 11:00:00','2016-03-09 12:00:00',true,'user',6);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estado, username, EspacioId) VALUES(9,'DVI','2016-03-09 14:00:00','2016-03-09 16:00:00',true,'user',6);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estado, username, EspacioId) VALUES(10,'Charla ELP','2016-03-24 15:30:00','2016-03-24 17:00:00',true,'user',1);

