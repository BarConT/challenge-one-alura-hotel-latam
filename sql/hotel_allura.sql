CREATE DATABASE hotel_alura_ar;
USE hotel_alura_ar;

CREATE TABLE reservas(
id INT NOT NULL AUTO_INCREMENT,
fecha_entrada DATE NOT NULL,
fecha_salida DATE NOT NULL,
valor VARCHAR(50),
forma_de_pago VARCHAR(50) NOT NULL,
PRIMARY KEY(id)
);
CREATE TABLE huespedes(
	id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    fecha_nac DATE NOT NULL,
    nacionalidad VARCHAR(50) NOT NULL,
    telefono VARCHAR(50) NOT NULL,
    id_reserva INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id_reserva) REFERENCES reservas(id) ON DELETE CASCADE
);
