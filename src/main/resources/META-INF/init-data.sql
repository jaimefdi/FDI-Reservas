INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(1,'Facultad de Informática','informatica.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(2,'Facultad de Bellas Artes','bellasartes.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(3,'Facultad de Ciencias Biológicas','biologicas.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(4,'Facultad de Ciencias de la Documentación','documentacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(5,'Facultad de Ciencias de la Información','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(6,'Facultad de Ciencias Económicas y Empresariales','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(7,'Facultad de Ciencias Físicas','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(8,'Facultad de Ciencias Geológicas','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(9,'Facultad de Ciencias Matemáticas','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(10,'Facultad de Ciencias Políticas y Sociología','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(11,'Facultad de Ciencias Químicas','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(12,'Facultad de Comercio y Turismo','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(13,'Facultad de Derecho','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(14,'Facultad de Educación','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(15,'Facultad de Enfermería, Fisioterapia y Podología','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(16,'Facultad de Estudios Estadísticos','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(17,'Facultad de Farmacia','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(18,'Facultad de Filología','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(19,'Facultad de Filosofía','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(20,'Facultad de Geografía e Historia','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(21,'Facultad de Medicina','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(22,'Facultad de Odontología','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(23,'Facultad de Óptica y Optometría','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(24,'Facultad de Psicología','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(25,'Facultad de Trabajo Social','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(26,'Facultad de Veterinaria','www.ccinformacion.ucm.es', false);
INSERT INTO Facultad(FacultadId, nombreFacultad, webFacultad, deleted) VALUES(27,'Sin asignar','www.ucm.es', false);

INSERT INTO Attachment(ImagenId, description, attachmentUrl, storageKey) VALUES(1, 'Facultad de informatica', '/img/FDI.jpg', '/img/FDI-1.jpg');
INSERT INTO Attachment(ImagenId, description, attachmentUrl, storageKey) VALUES(2, 'Edificio Basico', '/img/casa.jpg', '/img/casa-2.jpg');
INSERT INTO Attachment(ImagenId, description, attachmentUrl, storageKey) VALUES(3, 'Facultad de Ciencias Biológicas', '/img/biologicas.jpg', '/img/biologicas*3.jpg');
INSERT INTO Attachment(ImagenId, description, attachmentUrl, storageKey) VALUES(4, 'Facultad de Bellas Artes', '/img/bellas artes.jpg', '/img/bellas artes-4.jpg');
INSERT INTO Attachment(ImagenId, description, attachmentUrl, storageKey) VALUES(5, 'Facultad de Ciencias de la Documentación', '/img/documentacion.jpg', '/img/documentacion-5.jpg');
INSERT INTO Attachment(ImagenId, description, attachmentUrl, storageKey) VALUES(6, 'Edificio multiusos', '/img/multiusos.jpg', '/img/multiusos-6.jpg');
INSERT INTO Attachment(ImagenId, description, attachmentUrl, storageKey) VALUES(7, 'Facultad de Ciencias de la Información', '/img/cc_informacion.jpg', '/img/cc_informacion-7.jpg');
INSERT INTO Attachment(ImagenId, description, attachmentUrl, storageKey) VALUES(8, 'Usuario 1', '/img/root.png', '/img/root-8.png');
INSERT INTO Attachment(ImagenId, description, attachmentUrl, storageKey) VALUES(9, 'Usuario 2', '/img/user.png', '/img/user-9.png');
INSERT INTO Attachment(ImagenId, description, attachmentUrl, storageKey) VALUES(10, 'Usuario 3', '/img/secre.png', '/img/secre-10.png');

INSERT INTO User (UserId, username, email, password, enabled, accountExpired, accountLocked, credentialsExpired, FacultadId, ImagenId) VALUES (1, 'root', 'root@ucm.es', '$2a$10$//HU8yTgOoLrZX6z9cgXhuR/IJki.BtS/ZM4Pr8s/zBDCFIT.I3P6', true, false, false, false, 2, 8);
INSERT INTO User (UserId, username, email, password, enabled, accountExpired, accountLocked, credentialsExpired, FacultadId, ImagenId) VALUES (2, 'user', 'user@ucm.es', '$2a$10$qspi.NK1570DsUrvDmxETekRTqpk/ZY2hmI3XCMER.RWPVlPAfpYK', true, false, false, false, 1, 9);
INSERT INTO User (UserId, username, email, password, enabled, accountExpired, accountLocked, credentialsExpired, FacultadId, ImagenId) VALUES (3, 'secre', 'secre@ucm.es', '$2a$10$fHAYSqISyxuhwcmWb3m1geDcMn5aGuXM.vvsN74v7JHwiUEYudgB6', true, false, false, false, 1, 10);

INSERT INTO UserRole (user, role) VALUES(1, 'ROLE_USER');
INSERT INTO UserRole (user, role) VALUES(1, 'ROLE_ADMIN');
INSERT INTO UserRole (user, role) VALUES(2, 'ROLE_USER');
INSERT INTO UserRole (user, role) VALUES(3, 'ROLE_USER');
INSERT INTO UserRole (user, role) VALUES(3, 'ROLE_GESTOR');



INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId, deleted, ImagenId) VALUES(1,'FDI','C/ del Prof. José G! Santesmases, 9',1, false,1);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId, deleted, ImagenId) VALUES(2,'Multiusos','C/ del Prof. Aranguren, s/n',1, false,6);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId, deleted, ImagenId) VALUES(3,'Edificio de Bellas Artes','C/ Pintor el Greco, 2',2, false,4);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId, deleted, ImagenId) VALUES(4,'Edificio de Ciencias Biológicas','C/ José Antonio Novais, 12',3, false,3);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId, deleted, ImagenId) VALUES(5,'Edificio de la Documentación','C/ Santísima Trinidad, 37',4, false,5);
INSERT INTO Edificio(EdificioId, nombreEdificio, direccion, FacultadId, deleted, ImagenId) VALUES(6,'Edificio de la Información','Av. Complutense, s/n',5, false,7);

INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, tipoAutorizacion, horasAutorizacion, deleted, ImagenId) VALUES(1,'Sala de Grados',70,true,true,1,1,1,2, false, 2);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, tipoAutorizacion, horasAutorizacion, deleted, ImagenId) VALUES(2,'Sala de Conferencias',180,true,true,1,1,0,0, false, 2);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, tipoAutorizacion, horasAutorizacion, deleted, ImagenId) VALUES(3,'Sala de Reuniones',20,true,true,1,1,0,0, false, 2);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, tipoAutorizacion, horasAutorizacion, deleted, ImagenId) VALUES(4,'Aula 1',80,true,true,1,0,0,0, false, 2);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, tipoAutorizacion, horasAutorizacion, deleted, ImagenId) VALUES(5,'Aula 2',80,true,true,1,0,0,0, false, 2);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, tipoAutorizacion, horasAutorizacion, deleted, ImagenId) VALUES(6,'Aula 3',80,true,true,1,0,0,0, false, 2);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, tipoAutorizacion, horasAutorizacion, deleted, ImagenId) VALUES(7,'Aula 4',80,true,true,1,0,0,0, false, 2);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, tipoAutorizacion, horasAutorizacion, deleted, ImagenId) VALUES(8,'Aula 5',80,true,true,1,0,0,0, false, 2);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, tipoAutorizacion, horasAutorizacion, deleted, ImagenId) VALUES(9,'Lab. 1',50,true,true,1,2,0,0, false, 2);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, tipoAutorizacion, horasAutorizacion, deleted, ImagenId) VALUES(10,'Aula 1280',80,true,true,2,0,0,0, false, 2);
INSERT INTO Espacio(EspacioId, nombreEspacio, capacidad, microfono, proyector, EdificioId, tipoEspacio, tipoAutorizacion, horasAutorizacion, deleted, ImagenId) VALUES(11,'Aula 1290',80,true,true,2,0,0,0, false, 2);

INSERT INTO GrupoReserva(GrupoReservaId, nombreCorto, nombreLargo, UserId) VALUES(1,'GII-1ºA','Grado en Ing. Informática',2);
INSERT INTO GrupoReserva(GrupoReservaId, nombreCorto, nombreLargo, UserId) VALUES(2,'GII-1ºB','Lo que sea',2);
INSERT INTO GrupoReserva(GrupoReservaId, nombreCorto, nombreLargo, UserId) VALUES(3,'GII-1ºC','Lo que sea',1);
INSERT INTO GrupoReserva(GrupoReservaId, nombreCorto, nombreLargo, UserId) VALUES(4,'GII-1ºD','Lo que sea',1);
INSERT INTO GrupoReserva(GrupoReservaId, nombreCorto, nombreLargo, UserId) VALUES(5,'GII-1ºE','Lo que sea',2);
INSERT INTO GrupoReserva(GrupoReservaId, nombreCorto, nombreLargo, UserId) VALUES(6,'GII-1ºF','Lo que sea',2);

INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(1,'Charla Intel','2016-09-16 12:30:00','2016-09-16 14:30:00',0,2,1,1);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(2,'Reunion TFG','2016-09-16 14:30:00','2016-09-16 16:30:00',2,1,3,1);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(3,'Revision examen','2016-09-18 14:30:00','2016-09-18 16:30:00',0,2,4,1);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(4,'Tutoria','2016-09-17 14:30:00','2016-09-17 16:30:00',1,2,3,2);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(5,'Clase de repaso','2016-09-23 14:30:00','2016-09-23 16:30:00',1,2,5,2);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(6,'PL','2016-09-07 09:00:00','2016-09-07 11:00:00',0,2,6,2);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(7,'PL','2016-09-09 09:00:00','2016-09-09 11:00:00',0,1,6,1);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(8,'MAR','2016-09-09 11:00:00','2016-09-09 12:00:00',0,1,6,2);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(9,'DVI','2016-09-09 14:00:00','2016-09-09 16:00:00',0,1,1,1);
INSERT INTO Reserva(ReservaId, asunto, comienzo, fin, estadoReserva, UserId, EspacioId, GrupoReservaId) VALUES(10,'Charla ELP','2016-09-24 15:30:00','2016-09-24 17:00:00',1,1,1,1);


