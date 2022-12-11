
CREATE TABLE client (
                        id_client         INTEGER NOT NULL,
                        firstname         VARCHAR2 (40 BYTE),
                        lastname          VARCHAR2 (40 BYTE),
                        email             VARCHAR2 (50 BYTE),
                        phone             VARCHAR2 (20 BYTE),
                        ticket_id         INTEGER NOT NULL,
                        quantity          INTEGER
);

ALTER TABLE client ADD CONSTRAINT client_pk PRIMARY KEY ( id_client );

CREATE TABLE sectors(
                        ID_Sectors      INTEGER NOT NULL,
                        Event_id        INTEGER NOT NULL,
                        Seats_id        INTEGER NOT NULL
);

ALTER TABLE sectors ADD CONSTRAINT sectors_pk PRIMARY KEY ( id_sectors );

CREATE TABLE distributor (
                             id_profile    INTEGER NOT NULL,
                             firstname     VARCHAR2 (40 BYTE),
                             lastname      VARCHAR2 (40 BYTE),
                             email         VARCHAR2 (50 BYTE),
                             phone         VARCHAR2 (20 BYTE),
                             rating        NUMBER,
                             salary        NUMBER
);

ALTER TABLE distributor ADD CONSTRAINT distributor_pk PRIMARY KEY ( id_profile );

CREATE TABLE seats (
                       id_seats          INTEGER NOT NULL,
                       type              VARCHAR2 (40 BYTE),
                       amount            INTEGER,
                       sold              INTEGER,
                       price             NUMBER,
                       ticketperclient   INTEGER
);

ALTER TABLE seats ADD CONSTRAINT seats_pk PRIMARY KEY ( id_seats );


CREATE TABLE event (
                       id_event             INTEGER NOT NULL,
                       name                 VARCHAR2 (50 BYTE),
                       address              VARCHAR2 (60 BYTE),
                       datetime             DATE,
                       description          VARCHAR2 (100 BYTE),
                       organiser_id         INTEGER NOT NULL
);

ALTER TABLE event ADD CONSTRAINT events_pkv2 PRIMARY KEY ( id_event );


CREATE TABLE organiser (
                           id_profile INTEGER NOT NULL,
                           firstname  VARCHAR2 (40 BYTE),
                           lastname   VARCHAR2 (40 BYTE),
                           email      VARCHAR2 (50 BYTE),
                           phone      VARCHAR2 (20 BYTE)
);

ALTER TABLE organiser ADD CONSTRAINT organiser_pk PRIMARY KEY ( id_profile );

CREATE TABLE profiles (
                          id_profile     INTEGER NOT NULL,
                          username       VARCHAR2 (30 BYTE),
                          password       VARCHAR2 (50 BYTE),
                          role_id INTEGER NOT NULL
);

ALTER TABLE profiles ADD CONSTRAINT profiles_pk PRIMARY KEY ( id_profile );

CREATE TABLE roles (
                       id_role INTEGER NOT NULL,
                       role    VARCHAR2(30 BYTE)
);

ALTER TABLE roles ADD CONSTRAINT roles_pk PRIMARY KEY ( id_role );

CREATE TABLE tickets (
                         id_ticket                   INTEGER NOT NULL,
                         sectors_id                  INTEGER NOT NULL,
                         ticketsold                  INTEGER,
                         distributor_id              INTEGER NOT NULL,
                         rate                        INTEGER
);

ALTER TABLE tickets ADD CONSTRAINT events_pk PRIMARY KEY ( id_ticket );

ALTER TABLE client
    ADD CONSTRAINT client_tickets_fk FOREIGN KEY ( ticket_id )
        REFERENCES tickets ( id_ticket );

ALTER TABLE distributor
    ADD CONSTRAINT distributor_profiles_fk FOREIGN KEY ( id_profile )
        REFERENCES profiles ( id_profile );

ALTER TABLE tickets
    ADD CONSTRAINT events_distributor_fk FOREIGN KEY ( distributor_id )
        REFERENCES distributor ( id_profile );

ALTER TABLE event
    ADD CONSTRAINT events_organizer_fk FOREIGN KEY ( organiser_id )
        REFERENCES organiser ( id_profile );

ALTER TABLE organiser
    ADD CONSTRAINT organiser_profiles_fk FOREIGN KEY ( id_profile )
        REFERENCES profiles ( id_profile );

ALTER TABLE profiles
    ADD CONSTRAINT profiles_roles_fk FOREIGN KEY ( role_id )
        REFERENCES roles ( id_role );

ALTER TABLE tickets
    ADD CONSTRAINT tickets_sectors_fk FOREIGN KEY ( sectors_id )
        REFERENCES sectors ( id_sectors );

ALTER TABLE sectors
    ADD CONSTRAINT sectors_event_fk FOREIGN KEY ( event_id )
        REFERENCES event ( id_event );

ALTER TABLE sectors
    ADD CONSTRAINT sectors_seats_fk FOREIGN KEY ( seats_id )
        REFERENCES seats ( id_seats );

CREATE SEQUENCE roles_sequence;

CREATE OR REPLACE TRIGGER roles_on_insert
    BEFORE INSERT ON roles
    FOR EACH ROW
BEGIN
    SELECT roles_sequence.nextval
    INTO :new.id_role
    FROM dual;
END;

INSERT INTO ROLES(ROLE) VALUES('админ');
INSERT INTO ROLES(ROLE) VALUES('организатор');
INSERT INTO ROLES(ROLE) VALUES('разпространител');

CREATE SEQUENCE profiles_sequence;

INSERT INTO PROFILES(ID_PROFILE,USERNAME,PASSWORD,ROLE_ID) VALUES(1,'admin','f24f62eeb789199b9b2e467df3b1876b',1);

CREATE SEQUENCE client_sequence;

CREATE SEQUENCE event_sequence;

CREATE SEQUENCE tickets_sequence;

CREATE SEQUENCE seats_sequence;

CREATE SEQUENCE sectors_sequence;

ALTER SYSTEM SET open_cursors = 65530 SCOPE=BOTH;