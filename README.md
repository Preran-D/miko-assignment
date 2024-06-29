# Simple Assembly Language Executor

This is a simple Spring Boot application that simulates a basic assembly language interpreter. It provides REST endpoints to execute assembly language statements and programs, and persists the results in a MySQL database.

## Features

- Execute individual assembly language statements (`MV`, `ADD`, `SHOW`, `CLEAR`)
- Execute a sequence of assembly language instructions
- Persist the results of executed programs in a MySQL database

## Endpoints

### POST /api/assembly/statement

### POST /api/assembly/execute

Executes a single assembly language statement and returns the result.

**Request Body:**
```json
{
    "statement": "ADD REG3,#500"
}
```

**Response**
```json
{
    "result": "Added 500 to REG3"
}
```
POST /api/assembly/execute

Executes a sequence of assembly language instructions and returns the final state of all registers.

```json
{
    "programText": "MV REG1,#3000\nMV REG2,#3000\nADD REG1,REG2\nADD REG1,#600\nSHOW REG"
}
```
**Response**
```json
{
    "id": 1,
    "programText": "MV REG1,#3000\nMV REG2,#3000\nADD REG1,REG2\nADD REG1,#600\nSHOW REG",
    "result": "REG1: 6600\nREG2: 3000\nREG3: 500"
}
```
## Commands
* MV REG, #value: Moves the constant value to the register REG.
* ADD REG1, REG2: Adds the value in REG2 to REG1 and stores the result in REG1.
* ADD REG, #value: Adds the constant value to the register REG.
* SHOW REG: Displays the value of all registers.
* CLEAR: Resets all registers.

## Postman Collection
You can use the provided Postman collection to test the API endpoints. Import the collection into Postman to get started quickly.

Download Postman Collection file ( Simple Assembly Language.postman_collection )
