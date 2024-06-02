# Projeto API REST para Reservas de Hotel na Nuvem

Este projeto consiste em uma API REST para gerenciamento de reservas de hotel, desenvolvida como parte do laboratório final do Bootcamp Java do Santander, oferecido pela DIO.

## Diagrama UML do Domínio da API

```mermaid
classDiagram
    class Room {
        - int id
        - int number
        - int floor
        - int qty_bed
        - int qty_bathroom
        - double priceDay
        - String describe
    }

    class User {
        <<abstract>>
        - int id
        - String email
        - String password
    }

    class Employee {
        - String name
        - String cpf
        - double salary
        - LocalDate date
     }

    class Client {
        - int id
        - String name
        - String cpf
        - LocalDate dateOfBirth
    }


   class  ReserveStatus {
        <<Enum>>
        FINALIZED
        RESERVED
        CANCELED
    }

    class Reserve {
        - int id
        - Client client
        - Employee employee
        - Room room
        - double price
        - LocalDate dateCheckIn
        - LocalDate dateCheckOut
        - ReserveStatus status
    }




User <|-- Employee
User <|-- Admin

ReserveStatus --> Reserve:status

Client "1" --*"0..*" Reserve
Employee "1" --*"0..*" Reserve
Room "1"--*"0..*" Reserve
```