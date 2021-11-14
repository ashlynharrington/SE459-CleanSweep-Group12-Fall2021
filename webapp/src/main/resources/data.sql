DROP TABLE IF EXISTS cleanSweepUsers;

CREATE TABLE cleanSweepUsers (
    clean_Sweep_Id BIGINT AUTO_INCREMENT  PRIMARY KEY,
    first_name VARCHAR(40) NOT NULL,
    last_name VARCHAR (40) NOT NULL,
    email VARCHAR (250) NOT NULL,
    password VARCHAR(250) NOT NULL
);

INSERT INTO cleanSweepUsers (first_name,last_name,email,password) VALUES ('Michael','Scott','mc@dundermifflen.com','office');
