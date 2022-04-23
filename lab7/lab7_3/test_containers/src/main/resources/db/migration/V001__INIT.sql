CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    employee_name varchar(255) not null,
    phone_number integer not null,
    date_of_birth date
);

INSERT INTO employees (employee_name, phone_number, date_of_birth) VALUES('Joaquim', 123456789, '1990-12-2');