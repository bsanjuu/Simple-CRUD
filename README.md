# Employee Management System

A simple CRUD (Create, Read, Update, Delete) application built with Spring Boot backend and Angular frontend, using H2 in-memory database.

## Architecture

- **Backend**: Spring Boot with REST APIs
- **Frontend**: Angular with TypeScript
- **Database**: H2 in-memory database
- **Development**: Two separate apps (backend:8080, frontend:4200)
- **Production**: Single JAR deployment

## Prerequisites

- Java 17+
- Node.js 18+
- Maven 3.6+
- Angular CLI
- IntelliJ IDEA (recommended)

## Project Structure

```
employee-management/
├── backend/                    # Spring Boot application
│   ├── src/main/java/
│   │   └── com/bsanju/simplecrud/
│   │       ├── entity/
│   │       │   └── Employee.java
│   │       ├── repository/
│   │       │   └── EmployeeRepository.java
│   │       ├── service/
│   │       │   └── EmployeeService.java
│   │       ├── controller/
│   │       │   └── EmployeeController.java
│   │       ├── config/
│   │       │   └── WebConfig.java
│   │       └── SimpleCrudApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
│
└── frontend/                   # Angular application
    ├── src/app/
    │   ├── services/
    │   │   └── employee.service.ts
    │   ├── employee.ts
    │   ├── app.ts
    │   ├── app.html
    │   ├── app.css
    │   └── app.config.ts
    ├── package.json
    └── angular.json
```

## Backend Setup (Spring Boot)

### 1. Dependencies (pom.xml)

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

### 2. Configuration (application.properties)

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

### 3. CORS Configuration

```java
@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}
```

### 4. Run Backend

```bash
cd backend
mvn spring-boot:run
```

**Endpoints:**
- API Base: `http://localhost:8080/employees`
- H2 Console: `http://localhost:8080/h2-console`
- GET All: `http://localhost:8080/employees/all`
- POST Create: `http://localhost:8080/employees`

## Frontend Setup (Angular)

### 1. Create Angular Project

```bash
npm install -g @angular/cli
ng new frontend
cd frontend
```

### 2. Install Dependencies

```bash
npm install
```

### 3. Key Files Structure

**Employee Model (employee.ts):**
```typescript
export interface Employee {
  id?: number;
  name: string;
  age: number;
  department: string;
}
```

**Employee Service (services/employee.service.ts):**
```typescript
@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private baseUrl = 'http://localhost:8080/employees';
  
  constructor(private http: HttpClient) {}
  
  getAllEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.baseUrl}/all`);
  }
  
  createEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.baseUrl, employee);
  }
}
```

### 4. Run Frontend

```bash
ng serve -o
```

**Access:** `http://localhost:4200`

## Development Workflow

### Separate Development (Recommended for Dev)

1. **Start Backend:**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

2. **Start Frontend:**
   ```bash
   cd frontend  
   ng serve -o
   ```

3. **Access:**
    - Frontend: `http://localhost:4200`
    - Backend API: `http://localhost:8080/employees`
    - H2 Console: `http://localhost:8080/h2-console`

## Production Deployment (Single JAR)

### 1. Build Angular for Production

```bash
cd frontend
ng build --configuration production
```

### 2. Copy Angular Build to Spring Boot

```bash
cp -r frontend/dist/frontend/* backend/src/main/resources/static/
```

### 3. Add Angular Router Support

Add this controller to handle Angular routing:

```java
@Controller
public class AngularForwardController {
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/index.html";
    }
}
```

### 4. Build and Run Single JAR

```bash
cd backend
mvn clean package
java -jar target/simplecrud-0.0.1-SNAPSHOT.jar
```

**Access:** `http://localhost:8080` (serves both UI and API)

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/employees/all` | Get all employees |
| POST | `/employees` | Create new employee |
| GET | `/employees/{id}` | Get employee by ID |
| PUT | `/employees/{id}` | Update employee |
| DELETE | `/employees/{id}` | Delete employee |

## Sample Request/Response

### Create Employee (POST /employees)

**Request:**
```json
{
  "name": "John Doe",
  "age": 30,
  "department": "IT"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "age": 30,
  "department": "IT"
}
```

## Database Access

**H2 Console:** `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## Troubleshooting

### Common Issues

1. **CORS Errors:** Make sure WebConfig is properly configured
2. **Port Conflicts:** Ensure ports 8080 and 4200 are available
3. **Angular Build Issues:** Check Node.js and Angular CLI versions
4. **Database Connection:** Verify H2 configuration in application.properties

### Testing the API

Use Postman or curl:

```bash
# Get all employees
curl http://localhost:8080/employees/all

# Create employee
curl -X POST http://localhost:8080/employees \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane Doe","age":25,"department":"HR"}'
```

## Features

-  Employee CRUD operations
- H2 in-memory database
-  REST API with Spring Boot
-  Angular frontend with forms and tables
-  CORS configuration
-  Single JAR deployment option
-  Responsive UI with basic styling

## Tech Stack

- **Backend:** Spring Boot 3.3.3, Spring Data JPA, H2 Database
- **Frontend:** Angular 17+, TypeScript, RxJS
- **Build Tools:** Maven, Angular CLI
- **Java Version:** 17+
- **Node Version:** 18+

## Contributing

1. Fork the project
2. Create feature branch (`git checkout -b feature/new-feature`)
3. Commit changes (`git commit -am 'Add new feature'`)
4. Push to branch (`git push origin feature/new-feature`)
5. Create Pull Request

## License

This project is open source and available under the [MIT License](LICENSE).