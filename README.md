# Java Library Management System

**Course:** Programming in Java  
**Academic Year:** 2026-27

## Overview
A command-line Library Management System created to demonstrate the Java topics in the supplied course syllabus: Java fundamentals, OOP, inheritance, polymorphism, exception handling, multithreading, collections, I/O streams, and JDBC.

## Features
- Add and list books
- Add and list members
- Issue and return books
- Custom exception handling
- Save/load records using file I/O
- SQLite database using JDBC
- Background statistics using a Java thread
- JUnit tests
- Command-line execution without a GUI

## Requirements
- JDK 17 or later
- Maven 3.8 or later

Check:
```bash
java -version
mvn -version
```

## Run

### macOS/Linux
```bash
chmod +x run.sh
./run.sh
```

### Windows
```bat
run.bat
```

### Manual
```bash
mvn clean package
mvn exec:java
```

### Tests
```bash
mvn test
```

## Project Structure
```text
Java-Library-Management-System/
├── README.md
├── requirements.txt
├── pom.xml
├── .gitignore
├── run.sh
├── run.bat
├── PROJECT_REPORT.md
├── docs/
│   └── methodology.md
├── data/
├── results/
└── src/
    ├── main/java/com/yityarthi/library/
    │   ├── Main.java
    │   ├── Library.java
    │   ├── LibraryItem.java
    │   ├── Book.java
    │   ├── Person.java
    │   ├── Member.java
    │   ├── Persistable.java
    │   ├── LibraryException.java
    │   ├── BookNotAvailableException.java
    │   ├── DatabaseManager.java
    │   └── StatisticsTask.java
    └── test/java/com/yityarthi/library/
        └── LibraryTest.java
```

## Syllabus Mapping

### Unit 1 - Java Introduction
Variables, data types, operators, input/output, `if`, `switch`, loops, methods and program structure.

### Unit 2 - Java Object-Oriented Programming
Classes, objects, constructors, strings, access modifiers, `this`, inheritance, overriding, abstract classes, interfaces, polymorphism and encapsulation.

### Unit 3 - Java Exception Handling and Multithreading
`try-catch`, `throw`, custom exceptions, thread creation, execution and `join()`.

### Unit 4 - Java List and I/O Streams
`ArrayList`, collections, strings, arrays concepts, `BufferedReader`, `BufferedWriter`, file input/output and persistence.

### Unit 5 - Database Applications with JDBC
JDBC connection, SQLite driver, SQL table creation, prepared statements, insertion, selection and result processing.

## Database
SQLite is accessed through JDBC. The application automatically creates `data/library.db`; no separate database server is required.

## Student Details
Name: Anshuman Modgil
Registration Number: 24BAC10041
