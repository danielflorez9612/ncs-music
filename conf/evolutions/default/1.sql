# First evolution of the database

# --- !Ups

CREATE TABLE account
(
	id serial,
	username character varying(20) NOT NULL,
	password character varying(50) NOT NULL,
	CONSTRAINT PK_ACCOUNT PRIMARY KEY (id),
	CONSTRAINT UQ_ACCOUNT UNIQUE (username)
);
CREATE TABLE artist
(
	id serial,
	forename character varying(71) NOT NULL,
	surname character varying(71) NOT NULL,
	account_id integer,
	CONSTRAINT PK_ARTIST PRIMARY KEY (id),
	CONSTRAINT FK_ARTIST_ACCOUNT FOREIGN KEY (account_id) REFERENCES account(id)
);
CREATE TABLE song
(
	id serial,
	artist_id integer,
	title character varying(60),
	uri character varying (100),
	CONSTRAINT PK_SONG PRIMARY KEY (id),
	CONSTRAINT FK_SONG_ARTIST FOREIGN KEY (artist_id) REFERENCES artist(id)
);

# --- !Downs

DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS artist;
DROP TABLE IF EXISTS song;