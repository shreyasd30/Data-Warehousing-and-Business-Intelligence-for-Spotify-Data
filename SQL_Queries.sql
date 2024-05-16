-- Database: spotify_datawarehouse
CREATE DATABASE spotify_datawarehouse WITH
OWNER = postgres
 
 ENCODING = 'UTF8' LC_COLLATE = 'C' LC_CTYPE = 'C' LOCALE_PROVIDER = 'libc' TABLESPACE = pg_default CONNECTION LIMIT = -1 IS_TEMPLATE = False;
-- SCHEMA: spotify_dw
-- DROP SCHEMA IF EXISTS spotify_dw ;
CREATE SCHEMA IF NOT EXISTS spotify_dw AUTHORIZATION postgres;
-- Table: spotify_dw.albums
-- DROP TABLE IF EXISTS spotify_dw.albums;
CREATE TABLE IF NOT EXISTS spotify_dw.albums (
album_id character varying COLLATE pg_catalog."default" NOT NULL, album_name character varying COLLATE pg_catalog."default", album_type character varying COLLATE pg_catalog."default", release_date_id integer,
popularity integer,
CONSTRAINT albums_pkey PRIMARY KEY (album_id),
CONSTRAINT albums_release_date_id_fkey FOREIGN KEY (release_date_id)
REFERENCES spotify_dw."time" (time_id) MATCH SIMPLE ON UPDATE NO ACTION
ON DELETE NO ACTION
NOT VALID
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS spotify_dw.albums OWNER to postgres;
-- Table: spotify_dw.artists
-- DROP TABLE IF EXISTS spotify_dw.artists;
CREATE TABLE IF NOT EXISTS spotify_dw.artists (
artists_id character varying COLLATE pg_catalog."default" NOT NULL, artists_name character varying COLLATE pg_catalog."default", popularity integer,
followers bigint,

 CONSTRAINT artists_pkey PRIMARY KEY (artists_id) )
TABLESPACE pg_default;
ALTER TABLE IF EXISTS spotify_dw.artists OWNER to postgres;
-- Table: spotify_dw.audio_features
-- DROP TABLE IF EXISTS spotify_dw.audio_features;
CREATE TABLE IF NOT EXISTS spotify_dw.audio_features (
audio_feature_id character varying COLLATE pg_catalog."default" NOT NULL, acousticness numeric,
danceability numeric,
energy numeric,
instrumentalness numeric, key integer,
liveness numeric, loudness numeric,
mode integer,
speechiness numeric,
tempo numeric,
time_signature integer,
valence numeric,
CONSTRAINT audio_features_pkey PRIMARY KEY (audio_feature_id)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS spotify_dw.audio_features OWNER to postgres;
-- Table: spotify_dw.genres_type
-- DROP TABLE IF EXISTS spotify_dw.genres_type; CREATE TABLE IF NOT EXISTS spotify_dw.genres_type (
genre_id integer NOT NULL,
genres character varying COLLATE pg_catalog."default", CONSTRAINT "Genres_type_pkey" PRIMARY KEY (genre_id)
)

 TABLESPACE pg_default;
ALTER TABLE IF EXISTS spotify_dw.genres_type
OWNER to postgres;
-- Table: spotify_dw.time
-- DROP TABLE IF EXISTS spotify_dw."time";
CREATE TABLE IF NOT EXISTS spotify_dw."time" (
time_id integer NOT NULL,
date date,
day_of_week character varying COLLATE pg_catalog."default", week_number integer,
month character varying COLLATE pg_catalog."default", quarter integer,
year integer,
datetime timestamp with time zone,
CONSTRAINT time_pkey PRIMARY KEY (time_id)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS spotify_dw."time" OWNER to postgres;
-- Table: spotify_dw.tracks_info
-- DROP TABLE IF EXISTS spotify_dw.tracks_info;
CREATE TABLE IF NOT EXISTS spotify_dw.tracks_info (
track_id character varying COLLATE pg_catalog."default" NOT NULL, artist_id character varying COLLATE pg_catalog."default",
album_id character varying COLLATE pg_catalog."default",
genre_id integer,
audio_feature_id character varying COLLATE pg_catalog."default", release_date_id integer,
track_name character varying COLLATE pg_catalog."default", track_total_duration bigint,
track_popularity integer,
explicit integer,
track_number integer,
disc_number integer,
CONSTRAINT tracks_info_pkey PRIMARY KEY (track_id), CONSTRAINT tracks_info_album_id_fkey FOREIGN KEY (album_id)
REFERENCES spotify_dw.albums (album_id) MATCH SIMPLE

ON UPDATE NO ACTION ON DELETE NO ACTION NOT VALID,
CONSTRAINT tracks_info_artist_id_fkey FOREIGN KEY (artist_id) REFERENCES spotify_dw.artists (artists_id) MATCH SIMPLE ON UPDATE NO ACTION
ON DELETE NO ACTION
NOT VALID,
CONSTRAINT tracks_info_audio_feature_id_fkey FOREIGN KEY (audio_feature_id)
REFERENCES spotify_dw.audio_features (audio_feature_id) MATCH SIMPLE ON UPDATE NO ACTION
ON DELETE NO ACTION
NOT VALID,
CONSTRAINT tracks_info_genre_id_fkey FOREIGN KEY (genre_id) REFERENCES spotify_dw.genres_type (genre_id) MATCH SIMPLE ON UPDATE NO ACTION
ON DELETE NO ACTION
NOT VALID,
CONSTRAINT tracks_info_release_date_id_fkey FOREIGN KEY (release_date_id)
REFERENCES spotify_dw."time" (time_id) MATCH SIMPLE ON UPDATE NO ACTION
ON DELETE NO ACTION
NOT VALID
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS spotify_dw.tracks_info OWNER to postgres;
