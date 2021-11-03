CREATE TABLE ROLE
(
    roleId integer primary key,
    name   VARCHAR2(30) not null unique
);

CREATE TABLE CREW
(
    crewId integer primary key,
    name   varchar2(15) not null,
    roleId integer REFERENCES role (roleId) ON DELETE SET NULL
);

CREATE TABLE AIRPlANE
(
    airplaneId   integer primary key,
    serialNumber VARCHAR2(30) not null unique
);

CREATE TABLE AIRPLANE_CREW
(
    crewId     integer REFERENCES CREW (crewId) ON DELETE CASCADE,
    airplaneId integer references AIRPLANE (airplaneId) ON DELETE CASCADE
);

CREATE TABLE ROUTE
(
    routeId   integer primary key,
    departure VARCHAR2(30) not null,
    arrival   VARCHAR2(30) not null
);
CREATE TABLE AIRPLANE_ROUTE
(
    routeId    integer REFERENCES ROUTE (routeId) ON DELETE CASCADE,
    airplaneId integer references AIRPLANE (airplaneId) ON DELETE CASCADE
);

CREATE SEQUENCE seq_crew;
CREATE SEQUENCE seq_role;
CREATE SEQUENCE seq_airplane;
CREATE SEQUENCE seq_route;


INSERT INTO ROLE(roleId, name)
values (SEQ_ROLE.nextval, 'commander');
INSERT INTO ROLE
values (SEQ_ROLE.nextval, 'pilotOne');
INSERT INTO ROLE
values (SEQ_ROLE.nextval, 'pilotTwo');
INSERT INTO ROLE
values (SEQ_ROLE.nextval, 'stewardess');

INSERT INTO CREW(crewId, name, roleId)
VALUES (seq_crew.nextval, 'Alexey', 1);
INSERT INTO CREW
VALUES (seq_crew.nextval, 'Kirill', 2);
INSERT INTO CREW
VALUES (seq_crew.nextval, 'Vadim', 3);
INSERT INTO CREW
VALUES (seq_crew.nextval, 'Daria', 4);
INSERT INTO CREW
VALUES (seq_crew.nextval, 'Maria', 4);
INSERT INTO CREW
VALUES (seq_crew.nextval, 'Anastasia', 4);
INSERT INTO CREW
VALUES (seq_crew.nextval, 'Anzhela', 4);
INSERT INTO CREW
VALUES (seq_crew.nextval, 'Viktorya', 4);
INSERT INTO CREW
VALUES (seq_crew.nextval, 'Sashenka', 1);
INSERT INTO CREW
VALUES (seq_crew.nextval, 'Kolen''ka', 2);
INSERT INTO CREW
VALUES (seq_crew.nextval, 'Viten''ka', 3);

INSERT INTO Airplane(airplaneId, serialNumber)
VALUES (seq_airplane.nextval, 'N707JT');
INSERT INTO Airplane(airplaneId, serialNumber)
VALUES (seq_airplane.nextval, 'EW-001PB');


INSERT INTo airplane_crew(crewId, airplaneId)
values (1, 1);
INSERT INTo airplane_crew
values (2, 1);
INSERT INTo airplane_crew
values (6, 1);
INSERT INTo airplane_crew
values (4, 1);
INSERT INTo airplane_crew
values (9, 2);
INSERT INTo airplane_crew
values (10, 2);
INSERT INTo airplane_crew
values (11, 2);
INSERT INTo airplane_crew
values (7, 2);
INSERT INTo airplane_crew
values (8, 2);
INSERT INTo airplane_crew
values (5, 2);


INSERT INTO ROUTE(routeId, departure, arrival)
values (SEQ_ROUTE.nextval, 'MINSK', 'MOSKVA');
INSERT INTO ROUTE(routeId, departure, arrival)
values (SEQ_ROUTE.nextval, 'MOSKVA', 'MINSK');
INSERT INTO ROUTE(routeId, departure, arrival)
values (SEQ_ROUTE.nextval, 'TOKYO', 'Torronto');
INSERT INTO ROUTE(routeId, departure, arrival)
values (SEQ_ROUTE.nextval, 'Torronto', 'TOKYO');



INSERT INTo airplane_route(routeId, airplaneId)
values (1, 2);
INSERT INTo airplane_route(routeId, airplaneId)
values (2, 2);
INSERT INTo airplane_route(routeId, airplaneId)
values (3, 1);
INSERT INTo airplane_route(routeId, airplaneId)
values (4, 1);
commit;