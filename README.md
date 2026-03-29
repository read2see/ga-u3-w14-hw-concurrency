# Concurrency | Employee Salary Processing

Spring Boot application that reads employee records from a CSV file, applies concurrent salary calculations with Java concurrency primitives, and presents results in a web UI.

## Prerequisites

- **Java 17** (see `java.version` in `pom.xml`)
- **Maven 3.6+**

## Project setup
*To Be Updated*
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

## Implementation report  (outcomes)

*To Be Updated*
---

