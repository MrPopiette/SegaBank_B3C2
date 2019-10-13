-- ==================================================
-- filename : sql.sql
-- date 	: 09/10/2019 
-- projet	: SegaBank
-- ==================================================

create database SegaBank;
use SegaBank;

create table Agence(
	identifiant int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	code varchar(50) NOT NULL,
	numero_adresse varchar(10) NULL,
	adresse varchar(50) NULL,
	code_postal varchar(5) NOT NULL,
	ville varchar(50) NOT NULL
);

-- create or replace

create table Compte(
	identifiant int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	solde float NOT NULL,
	taux_interet float NULL,
	type_compte varchar(50) NOT NULL,
	id_agence int NOT NULL,
	decouvert_max int NULL
);

create table Operation(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	type_operation varchar(20) not null,
	date varchar(20) not null,
	id_agence varchar(20) not null,
	id_compte varchar(20) not null,
	montant varchar(20) not null,
	compte_status varchar(100) not null
);