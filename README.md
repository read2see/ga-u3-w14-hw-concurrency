# Concurrency | Employee Salary Processing

Spring Boot application that reads employee records from a CSV file, applies concurrent salary calculations with Java concurrency primitives, and presents results in a web UI.

## Prerequisites

- **Java 17** (see `java.version` in `pom.xml`)
- **Maven 3.6+**

## Project setup
1. **Clone the repository** (or extract the project) and open a terminal in the project root.

2. **Build the project**

   ```bash  
   ./mvnw clean package   ```  
   On Windows:  
  
   ```cmd  
   mvnw.cmd clean package  
   ```  
3. **Run the application**

   ```bash  
   ./mvnw spring-boot:run   ```  
4. **Open the app** in a browser: [http://localhost:8080](http://localhost:8080)

   The home page loads employee data from the configured CSV, runs the concurrent raise pipeline, and renders the table.

### Configuration

| Property | Description |
|----------|-------------|
| `app.csv.file` | Classpath location of the default employee CSV (default: `data/test_employees.csv`). Set in `src/main/resources/application.properties`. |

Place CSV files under `src/main/resources/` (e.g. `data/my_employees.csv`) and point `app.csv.file` at them, or extend the code to accept uploads/external paths if needed.

### Dependencies (high level)

- Spring Boot Web MVC, Thymeleaf, Validation
- OpenCSV for parsing
- Lombok (optional, for model boilerplate)

---

## CSV file format

The default dataset is `src/main/resources/data/test_employees.csv`. Each row is parsed as:

| Column (index) | Field | Notes |
|----------------|--------|--------|
| 0 | *(ignored in model)* | Numeric id in sample data |
| 1 | Name | Employee name |
| 2 | Current salary | Parsed as `BigDecimal` |
| 3 | Joined date | `yyyy-MM-dd` or `M/d/yyyy` |
| 4 | Role | `Director`, `Manager`, or `Employee` (case-insensitive) |
| 5 | Project completion | Decimal fraction in \([0,1]\) (e.g. `0.8` = 80%) |

Implementation: `com.ga.concurrency.util.CSVProcessor` uses OpenCSV and maps rows into `Employee` entities.

---

## Implementation Report

This project implements a concurrent Java application for processing employee salary data from a CSV file. The system reads employee records, applies defined business rules to calculate salary increases, and processes the data efficiently using multithreading and concurrency control techniques.

---

## System Design

The application follows a modular structure:

* **CSVProcessor** handles reading and parsing CSV data into employee objects.
* **Employee** represents the data model, storing attributes such as salary, role, join date, and performance metrics.
* **EmployeeService** contains the core logic for salary calculation and manages concurrent processing.

This separation ensures clarity, maintainability, and logical organization of responsibilities.

---

## Concurrency Implementation

Concurrency is implemented using Java’s Executor framework. A fixed thread pool processes employees in parallel, with each employee handled as an independent task.

To ensure safe and controlled execution:

* A **Semaphore** limits the number of concurrent tasks.
* **Synchronized blocks** ensure thread-safe updates to employee salary data.
* **AtomicInteger** provides thread-safe tracking of processed results.

The executor is properly shut down and awaited to guarantee that all tasks complete before results are returned.