# First evolution of the database

# --- !Ups

CREATE TABLE artist
(
	id integer,
	username character varying(20) NOT NULL UNIQUE,
	password character varying(50) NOT NULL,
	forename character varying(71) NOT NULL,
	surname character varying(71) NOT NULL,
	CONSTRAINT PK_ARTIST PRIMARY KEY (id)
);
CREATE TABLE song
(
	id integer ,
	artist_id integer,
	title character varying(60),
	uri character varying (100),
	CONSTRAINT PK_SONG PRIMARY KEY (id),
	CONSTRAINT FK_SONG_ARTIST FOREIGN KEY (artist_id) REFERENCES artist(id)
);

# --- !Downs

DROP TABLE IF EXISTS song, artist CASCADE;