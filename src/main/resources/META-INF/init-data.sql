INSERT INTO USER (USER_ID, username, email, password, enabled, accountExpired, accountLocked, credentialsExpired) VALUES (1, 'root', 'root@ucm.es', '$2a$10$//HU8yTgOoLrZX6z9cgXhuR/IJki.BtS/ZM4Pr8s/zBDCFIT.I3P6', true, false, false, false);
INSERT INTO USER (USER_ID, username, email, password, enabled, accountExpired, accountLocked, credentialsExpired) VALUES (2, 'user', 'user@ucm.es', '$2a$10$qspi.NK1570DsUrvDmxETekRTqpk/ZY2hmI3XCMER.RWPVlPAfpYK', true, false, false, false);

INSERT INTO USERROLE (user, role) VALUES(1, 'ROLE_USER');
INSERT INTO USERROLE (user, role) VALUES(1, 'ROLE_ADMIN');
INSERT INTO USERROLE (user, role) VALUES(2, 'ROLE_USER');

INSERT INTO EDIFICIO(EDIFICIO_ID, nombre_edificio) VALUES(1,'Facultad de Informática');
INSERT INTO EDIFICIO(EDIFICIO_ID, nombre_edificio) VALUES(2,'Multiusos');

INSERT INTO ESPACIO (ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(1,'Sala de Grados',70,true,true,1,1);
INSERT INTO ESPACIO (ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(2,'Sala de Conferencias',180,true,true,1,1);
INSERT INTO ESPACIO (ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(3,'Sala de Reuniones',20,true,true,1,1);
INSERT INTO ESPACIO (ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(4,'Aula 1',80,true,true,1,0);
INSERT INTO ESPACIO (ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(5,'Aula 1280',80,true,true,2,0);
INSERT INTO ESPACIO (ESPACIO_ID, nombre_espacio, capacidad, microfono, proyector, EDIFICIO_ID, tipoEspacio) VALUES(6,'Aula 1290',80,true,true,2,0);

INSERT INTO RESERVA (RESERVA_ID, asunto, fecha_ini, fecha_fin, estado, username, ESPACIO_ID) VALUES(1,'Charla Intel','2016-02-16 12:30:00','2016-02-16 14:30:00',true,'user',1);
INSERT INTO RESERVA (RESERVA_ID, asunto, fecha_ini, fecha_fin, estado, username, ESPACIO_ID) VALUES(2,'Reunion TFG','2016-02-16 14:30:00','2016-02-16 16:30:00',false,'user',3);
INSERT INTO RESERVA (RESERVA_ID, asunto, fecha_ini, fecha_fin, estado, username, ESPACIO_ID) VALUES(3,'Rvision examen','2016-02-18 14:30:00','2016-02-18 16:30:00',true,'user',4);
INSERT INTO RESERVA (RESERVA_ID, asunto, fecha_ini, fecha_fin, estado, username, ESPACIO_ID) VALUES(4,'Tutoria','2016-02-17 14:30:00','2016-02-17 16:30:00',true,'user',3);
INSERT INTO RESERVA (RESERVA_ID, asunto, fecha_ini, fecha_fin, estado, username, ESPACIO_ID) VALUES(5,'Clase de repaso','2016-02-23 14:30:00','2016-02-23 16:30:00',true,'user',5);


