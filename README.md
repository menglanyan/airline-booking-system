# ✈️ Airline Booking System — Fullstack (Spring Boot + React + MySQL)

A comprehensive airline management platform enabling **flight booking, scheduling, and role-based operations** for customers, pilots, and administrators. Includes automated email notifications for user register and booking.    
The backend is built with **Spring Boot** for scalability and security, while the frontend (React) offers an intuitive and responsive interface — all powered by **MySQL** and **JWT authentication**.

---

## 🧠 Project Overview

### 🧩 Backend — Powered by Spring Boot

**🔹 Database Architecture**  
Designed a relational database in **MySQL** with structured entities for **Users**, **Roles**, **Flights**, **Bookings**, and **Airports**.    
Established **one-to-many** and **many-to-one** relationships to maintain data integrity and ensure efficient lookups for flight and booking datasets.

**🔹 RESTful API Development**  
Developed modular REST endpoints for handling **authentication, user management, bookings, and flight operations**.  
Used a standardized response structure (`Response<T>`) across all endpoints for better API consistency.

**🔹 Security and Authentication**  
Implemented **Spring Security** with **JWT** for stateless authentication.  
Each user is authenticated via token-based and role-based access control restricting access to sensitive endpoints.

**🔹 Role-Based Access Control**  
Three main roles are defined:
- **Customer:** Can search and book flights.
- **Pilot:** Can view assigned flights and update flight status.
- **Admin:** Has full access to manage users, flights, airports, and bookings.

**🔹 Email Notifications**  
Integrated **JavaMailSender** to deliver automated transactional emails, such as:
- Welcome emails after registration
- Booking confirmations

---

### 🎨 Frontend — Built with React

**🔹 Role-Based User Interface**  
Designed an intuitive and responsive interface for different user roles:
- **Customer**: Search and book flights, manage bookings, update personal profile
- **Pilot**: View assigned flights and update flight statuses
- **Admin**: Manage all flights, airports, users, and assign pilot roles

**🔹 Real-Time Flight Search**  
Integrated API-based flight filtering by **airport**, **status**, **date**, and **time**, allowing customers to easily find available flights

**🔹 API Integration**  
Seamlessly connected to the Spring Boot backend using **Axios** for secure data transactions
Implemented interceptors to attach JWT tokens for authorized requests

**🔹 Authentication & Security**
Implemented **JWT-based authentication** with automatic token management and logout on expiration  
Restricted route access through **React Router DOM** and role-specific guards

**🔹 Modern Design**
Developed dynamic dashboards that adapt to each user’s role and permissions
Responsive layout with reusable React components for naviagtion bar, footer, and message display
Integrated notification and toast messages for user experience

---

## 🚀 Features

### 👤 User & Role Management
- Secure user registration and login via **JWT**
- Role-based permissions (**Customer**, **Admin**, **Pilot**)
- Admin can register users to assign roles
- Secure password hashing with **Spring Security**

### ✈️ Flight Management
- Create, update, and delete flights
- Assign pilots to flights
- Filter flights by airport, date, and status
- Public endpoints for flight browsing (unauthenticated)

### 🧾 Booking Management
- Customers can **book** and **view** their bookings
- Unique booking reference automatically generated
- Admins can access and update all bookings
- Linked flight and passenger details per booking

### 👨‍✈️ Pilot Dashboard
- Pilots can view their assigned flights only
- Flights are ordered by departure time
- Access restricted via role validation

### 📨 Email Notifications
- Welcome and booking confirmation emails
- Modular service class for easy extension

---

## 🧱 Backend Overview

### 🛠️ Tech Stack
| Layer | Technology |
|-------|-------------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3 |
| **Security** | Spring Security + JWT |
| **ORM** | Hibernate / JPA |
| **Database** | MySQL |
| **Build Tool** | Maven |
| **Mail Service** | JavaMailSender |
| **Testing** | JUnit 5, Mockito |
| **Docs** | Swagger UI, Postman |

### 📂 Core Components

#### Entities
- `User` → defines user data and assigned roles
- `Role` → defines authorization levels (CUSTOMER, ADMIN, PILOT)
- `Flight` → stores flight info, assigned pilot, and schedule
- `Booking` → links user and flight with booking details
- `Airport` → stores departure and arrival data
- `Passenger` → stores passengers info and links with booking details
- `EmailNotification` → defines email notification setting

#### Controllers
- `AuthController` → handles user registration and login with JWT authentication
- `FlightController` → manages flight creation, updates, status changes, and search operations
- `BookingController` → processes customer bookings and booking retrieval
- `AirportController` → provides endpoints to fetch and manage airport data
- `UserController` → manages user details and roles
- `RoleController` → handles system role retrieval and user-role assignments

#### Security Configuration
- **JWT Authentication Filter** validates tokens on each request for stateless session management
- **Custom Access Handlers** manage authentication and authorization errors with consistent responses
- Role-Based Authorization enforces access control through `@PreAuthorize` and endpoint-specific permissions
- Password Encryption ensures secure storage using BCryptPasswordEncoder

### 🧪 API Docs
- Swagger UI:  
  `<BASE_URL>/swagger-ui/index.html`  
  Local(dev): `http://localhost:<PORT>/swagger-ui/index.html`
- Postman Collection: `/docs/postman/airline_booking_api.postman_collection.json`

---

### 🔗 Frontend Repository
**👉 [airline-booking-system-frontend](https://github.com/menglanyan/airline-booking-system-frontend)**