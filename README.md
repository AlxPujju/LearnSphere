### LearnSphere - A Secure E-Learning Platform

**LearnSphere** is a robust and secure e-learning platform designed to provide a comprehensive and safe environment for managing educational content. Built on the Java Spring Boot framework, this project emphasizes security, data integrity, and a clear, role-based access control system.

---

### Key Features

* **Secure User Management:** The platform provides a secure registration and login system. It defines distinct user roles—**Student**, **Instructor**, and **Admin**—each with specific permissions.
* **Role-Based Access Control (RBAC):** A finely-tuned RBAC system governs all platform interactions. This ensures that users can only perform actions (e.g., creating, updating, or deleting content) that are authorized by their assigned role.
* **Course & Content Management:**
    * Courses can be created and organized hierarchically into modules and sub-modules.
    * CRUD operations for courses are fully implemented and secured by RBAC.
    * Deleting a course automatically cascades and removes all associated content.
* **Encrypted Document Management:**
    * All documents uploaded to the platform are stored securely using encryption.
    * Only authorized users (enrolled students, instructors, and admins) can download and access decrypted files.
    * **Versioning:** The system supports document versioning, allowing instructors to upload new versions without overwriting older ones. Users can view the history and revert to a previous version if needed.
* **Student Progress Tracking:**
    * Students can enroll in courses and track their progress by marking sub-modules as complete.
    * The platform calculates and displays a student's completion percentage for each course.
* **System Auditing & Reporting:**
    * An automatic audit trail logs all significant events, such as new user sign-ups and content modifications.
    * Admins have access to a summary report of key platform metrics.
* **Security & Robustness:**
    * Sensitive data is automatically masked in API responses to enhance privacy.
    * API rate limiting is implemented to protect the platform from abuse and ensure system stability.
    * A background process handles the periodic re-encryption of older documents to maintain up-to-date security standards.
* **Comprehensive Testing:** The project includes a robust suite of JUnit tests to ensure the reliability and correctness of the application logic.

---

### Technology Stack

* **Backend:** Java 17+, Spring Boot 3.x
* **Security:** Spring Security, JWT (JSON Web Tokens)
* **Database:** PostgreSQL / MySQL
* **API Documentation:** SpringDoc
* **Data Persistence:** JPA (Java Persistence API) with Hibernate
* **Testing:** JUnit

---

### Getting Started

#### Prerequisites

* Java Development Kit (JDK) 17 or higher
* Maven 3.x
* A running instance of PostgreSQL or MySQL
* A text editor or IDE (e.g., IntelliJ IDEA, VS Code)

#### Building and Running the Project

1.  **Clone the repository:**
    ```bash
    git clone [your-repository-url]
    cd learn-sphere
    ```
2.  **Configure the database:**
    * Open `src/main/resources/application.properties` or `application.yml`.
    * Update the database connection properties with your credentials:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/learnsphere_db
        spring.datasource.username=your_username
        spring.datasource.password=your_password
        spring.jpa.hibernate.ddl-auto=update
        ```
3.  **Build the project:**
    ```bash
    mvn clean install
    ```
4.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```
5.  **Access the API Documentation:**
    * Once the application is running, you can access the interactive API documentation (Swagger) at:
        `http://localhost:8080/swagger-ui.html`

---

### Contact

For any questions or support, please reach out to the project maintainers.
