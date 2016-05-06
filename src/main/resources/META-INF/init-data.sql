INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(1,'Facultad de InformÃ¡tica','www.informatica.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(2,'Facultad de Bellas Artes','www.bellasartes.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(3,'Facultad de Ciencias BiolÃ³gicas','www.biologicas.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(4,'Facultad de Ciencias de la DocumentaciÃ³n','www.documentacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(5,'Facultad de Ciencias de la InformaciÃ³n','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(6,'Facultad de Ciencias EconÃ³micas y Empresariales','Campus de Somosaguas', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(7,'Facultad de Ciencias FÃ­sicas','Plaza Ciencias, 1', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(8,'Facultad de Ciencias GeolÃ³gicas','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(9,'Facultad de Ciencias MatemÃ¡ticas','Plaza Ciencias, 3', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(10,'Facultad de Ciencias PolÃ­ticas y SociologÃ­a','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(11,'Facultad de Ciencias QuÃ­micas','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(12,'Facultad de Comercio y Turismo','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(13,'Facultad de Derecho','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(14,'Facultad de EducaciÃ³n','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(15,'Facultad de EnfermerÃ­a, Fisioterapia y PodologÃ­a','C/ JosÃ© Antonio Novais, 12, false');
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(16,'Facultad de Estudios EstadÃ­sticos','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(17,'Facultad de Farmacia','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(18,'Facultad de FilologÃ­a','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(19,'Facultad de FilosofÃ­a','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(20,'Facultad de GeografÃ­a e Historia','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(21,'Facultad de Medicina','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(22,'Facultad de OdontologÃ­a','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(23,'Facultad de Ã“ptica y OptometrÃ­a','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(24,'Facultad de PsicologÃ­a','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(25,'Facultad de Trabajo Social','C/ JosÃ© Antonio Novais, 12', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(26,'Facultad de Veterinaria','C/ JosÃ© Antonio Novais, 12', false);

INSERT INTO User (UserId, username, email, password, enabled, accountExpired, accountLocked, credentialsExpired, FacultadId) VALUES (1, 'root', 'root@ucm.es', '$2a$10$//HU8yTgOoLrZX6z9cgXhuR/IJki.BtS/ZM4Pr8s/zBDCFIT.I3P6', true, false, false, false, 2);
INSERT INTO User (UserId, username, email, password, enabled, accountExpired, accountLocked, credentialsExpired, FacultadId) VALUES (2, 'user', 'user@ucm.es', '$2a$10$qspi.NK1570DsUrvDmxETekRTqpk/ZY2hmI3XCMER.RWPVlPAfpYK', true, false, false, false, 1);
INSERT INTO User (UserId, username, email, password, enabled, accountExpired, accountLocked, credentialsExpired, FacultadId) VALUES (3, 'secre', 'secre@ucm.es', '$2a$10$fHAYSqISyxuhwcmWb3m1geDcMn5aGuXM.vvsN74v7JHwiUEYudgB6', true, false, false, false, 1);

INSERT INTO UserRole (user, role) VALUES(1, 'ROLE_USER');
INSERT INTO UserRole (user, role) VALUES(1, 'ROLE_ADMIN');
INSERT INTO UserRole (user, role) VALUES(2, 'ROLE_USER');
INSERT INTO UserRole (user, role) VALUES(3, 'ROLE_USER');
INSERT INTO UserRole (user, role) VALUES(3, 'ROLE_GESTOR');

INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId, deleted) VALUES(1,'FDI','C/ del Prof. JosÃ© G! Santesmases, 9',1, false);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId, deleted) VALUES(2,'Multiusos','C/ del Prof. Aranguren, s/n',1, false);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId, deleted) VALUES(3,'Edificio de Bellas Artes','C/ Pintor el Greco, 2',2, false);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId, deleted) VALUES(4,'Edificio de Ciencias BiolÃ³gicas','C/ JosÃ© Antonio Novais, 12',3, false);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId, deleted) VALUES(5,'Edificio de la DocumentaciÃ³n','C/ SantÃ­sima Trinidad, 37',4, false);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId, deleted) VALUES(6,'Edificio de la InformaciÃ³n','Av. Complutense, s/n',5, false);

INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, deleted) VALUES(1,'Sala de Grados',70,true,true,1,1, false);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, deleted) VALUES(2,'Sala de Conferencias',180,true,true,1,1, false);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, deleted) VALUES(3,'Sala de Reuniones',20,true,true,1,1, false);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, deleted) VALUES(4,'Aula 1',80,true,true,1,0, false);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, deleted) VALUES(5,'Aula 2',80,true,true,1,0, false);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, deleted) VALUES(6,'Aula 3',80,true,true,1,0, false);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, deleted) VALUES(7,'Aula 4',80,true,true,1,0, false);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, deleted) VALUES(8,'Aula 5',80,true,true,1,0, false);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, deleted) VALUES(9,'Lab. 1',50,true,true,1,2, false);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, deleted) VALUES(10,'Aula 1280',80,true,true,2,0, false);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, deleted) VALUES(11,'Aula 1290',80,true,true,2,0, false);

INSERT INTO GrupoReserva(GrupoReservaId, nombreGrupo, descripcion, UserId) VALUES(1,'GII-1ÂºA','Lo que sea',2);
INSERT INTO GrupoReserva(GrupoReservaId, nombreGrupo, descripcion, UserId) VALUES(2,'GII-1ÂºB','Lo que sea',2);
INSERT INTO GrupoReserva(GrupoReservaId, nombreGrupo, descripcion, UserId) VALUES(3,'GII-1ÂºC','Lo que sea',1);
INSERT INTO GrupoReserva(GrupoReservaId, nombreGrupo, descripcion, UserId) VALUES(4,'GII-1ÂºD','Lo que sea',1);
INSERT INTO GrupoReserva(GrupoReservaId, nombreGrupo, descripcion, UserId) VALUES(5,'GII-1ÂºE','Lo que sea',2);
INSERT INTO GrupoReserva(GrupoReservaId, nombreGrupo, descripcion, UserId) VALUES(6,'GII-1ÂºF','Lo que sea',2);

INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(1,'Charla Intel','2016-03-16 12:30:00','2016-03-16 14:30:00',0,2,1,1);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(2,'Reunion TFG','2016-03-16 14:30:00','2016-03-16 16:30:00',2,1,3,1);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(3,'Revision examen','2016-03-18 14:30:00','2016-03-18 16:30:00',0,2,4,1);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(4,'Tutoria','2016-03-17 14:30:00','2016-03-17 16:30:00',1,2,3,2);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(5,'Clase de repaso','2016-03-23 14:30:00','2016-03-23 16:30:00',1,2,5,2);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(6,'PL','2016-03-07 09:00:00','2016-03-07 11:00:00',0,2,6,2);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(7,'PL','2016-03-09 09:00:00','2016-03-09 11:00:00',0,1,6,1);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(8,'MAR','2016-03-09 11:00:00','2016-03-09 12:00:00',0,1,6,2);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(9,'DVI','2016-03-09 14:00:00','2016-03-09 16:00:00',0,1,1,1);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(10,'Charla ELP','2016-03-24 15:30:00','2016-03-24 17:00:00',1,1,1,1);

