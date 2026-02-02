CREATE DATABASE IF NOT EXISTS db_cliente
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS db_cuenta
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'banco'@'%' IDENTIFIED BY 'banco';

GRANT ALL PRIVILEGES ON db_cliente.* TO 'banco'@'%';
GRANT ALL PRIVILEGES ON db_cuenta.* TO 'banco'@'%';

FLUSH PRIVILEGES;
