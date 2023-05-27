
# Shape

Aplikacja REST API do dodawania i edycji różnych typów figur. Głównym warunkiem było stworzenie aplikacji otwartej na rozbudowę o nowe typy figur i zamkniętej na modyfikacje obecnych klas (zasada Open/Close).
Encje zapisywane są do bazy My SQL. Każda zmiana parametrów figury również zapisywana jest w bazie. Baza danych zarządzana przy pomocy Liquibase.
Możliwość filtrowania figur po parametrach wykonano przy pomocy JPA Specification. 
Dostęp do poszczególnych endpointów zabezpieczony Spring Security dla użytkowników z odpowiednią rolą. Logowanie z użyciem JWT Token.
Testy integracyjne napisane z użyciem MockMvc.

## Technology stack:
* Java
* Spring Boot
* Spring Data JPA
* Spring Security
* JWT token
* Hibernate
* MySQL
* Maven
* Liquibase

## API Reference

#### Get all users

```http
  GET /users
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `page`      | `integer` | **Optional**. Number of page |
| `pageSize`  | `integer` | **Optional**. Size of page |

#### Add user

```http
  POST /users
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | **Required**. User's name |
| `surname`      | `string` | **Required**. User's surrname |
| `login`      | `string` | **Required**. User's login |
| `password`      | `string` | **Required**. User's password |
| `role`      | `string` | **Required**. User's role (ADMIN/CREATOR) |

#### Token

```http
  POST /auth
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username`      | `string` | **Required**. User's login/username |
| `password`      | `string` | **Required**. User's password |

#### Refresh-token

```http
  GET /auth/refresh-token
```

#### Add shape

```http
  POST /shapes
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `type`      | `string` | **Required**. Shape's type (SQUARE/CIRCLE/RECTANGLE) |
| `parameters`      | `List <Big decimals>` | **Required**. Shape's parameters |

#### Get shapes

```http
  GET /shapes
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `page`      | `integer` | **Optional**. Number of page |
| `pageSize`  | `integer` | **Optional**. Size of page |
| `type`      | `string` | **Optional**. Shape's type |
| `createdBy`      | `string` | **Optional**. Shape's creator |
| `createdAtFrom`      | `string` | **Optional**. Date from |
| `createdAtTo`      | `string` | **Optional**. Date to |
| `areaFrom`      | `string` | **Optional**. Min shape's area |
| `areaTo`      | `string` | **Optional**. Max shape's area |
| `perimeterFrom`      | `string` | **Optional**. Min shape's perimeter |
| `perimeterTo`      | `string` | **Optional**. Max shape's perimeter |

#### Update shape

```http
  PUT /shapes/${id}
```

#### Get changes

```http
  GET /shapes/${id}/changes
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Shape's id |

## System requirments

...
  
## Installation

...
