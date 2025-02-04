CREATE TABLE CATEGORIA(
	ID UUID PRIMARY KEY,
	NOME VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE PRODUTO(
	ID 				UUID 			PRIMARY KEY,
	NOME 			VARCHAR(50) 	NOT NULL,
	PRECO 			DECIMAL(10,2) 	NOT NULL CHECK(PRECO>0),
	QUANTIDADE 		INTEGER 		NOT NULL,
	CATEGORIA_ID 	UUID 			NOT NULL,
	FOREIGN KEY(CATEGORIA_ID) REFERENCES CATEGORIA(ID)
); 