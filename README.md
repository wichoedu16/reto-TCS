# Sistema Bancario - Microservicios

Proyecto de dos microservicios para gestión de clientes, cuentas y movimientos bancarios.

## Arquitectura

El sistema está compuesto por:

- **ms-cliente-persona** (puerto 8081): Gestiona clientes y personas
- **ms-cuenta-movimiento** (puerto 8082): Gestiona cuentas, movimientos y reportes

La comunicación entre microservicios es asíncrona mediante RabbitMQ. Cuando se crea o actualiza un cliente, se publica un evento que `ms-cuenta-movimiento` consume para mantener una caché local con los datos del cliente (patrón Event-Carried State Transfer).

## Tecnologías

- Java 17
- Spring Boot 3.3.10
- Spring Data JPA
- MySQL 8.0
- RabbitMQ
- MapStruct
- Lombok
- Docker & Docker Compose

## Levantar el proyecto

```bash
docker-compose up --build
```

Esto levanta:
- MySQL en el puerto 3306
- RabbitMQ en el puerto 5672 (management UI en 15672)
- ms-cliente-persona en el puerto 8081
- ms-cuenta-movimiento en el puerto 8082

## Endpoints principales

### Clientes (ms-cliente-persona)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /clientes | Listar todos |
| GET | /clientes/{id} | Obtener por ID |
| POST | /clientes | Crear cliente |
| PUT | /clientes/{id} | Actualizar cliente |
| DELETE | /clientes/{id} | Eliminar cliente |

### Cuentas (ms-cuenta-movimiento)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /cuentas | Listar todas |
| GET | /cuentas/{id} | Obtener por ID |
| POST | /cuentas | Crear cuenta |
| PUT | /cuentas/{id} | Actualizar cuenta |
| DELETE | /cuentas/{id} | Eliminar cuenta |

### Movimientos (ms-cuenta-movimiento)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /movimientos | Listar todos |
| POST | /movimientos | Registrar movimiento |

### Reportes (ms-cuenta-movimiento)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /reportes | Estado de cuenta por cliente y rango de fechas |

Ejemplo:
```
GET /reportes?clienteId=CLI001&fechaInicio=2024-01-01&fechaFin=2024-12-31
```

## Pruebas

Cada microservicio tiene pruebas unitarias e integración:

```bash
# Desde la raíz de cada microservicio
./mvnw test
```

## Colección Postman

En la carpeta `postman/` está la colección con todos los endpoints listos para probar.
