# **VMS**
## **Vehicle Maintenance & Service Management System** 


## **Table of Contents**
- [Project Overview](#project-overview)
- [Installation & Setup](#installation--setup)
- [Project Objectives](#project-objectives)
- [Features & Functionalities](#features--functionalities)
- [Technical Stack](#technical-stack)
---

## **📌 Project Overview**
The **Vehicle Maintenance & Service Management System** is a **JavaFX-based** application designed to help automotive service centers manage:  
* Customer appointments  
* Service history  
* Payments & invoices  
* Inventory tracking

The system integrates **JavaFX**, **Oracle DB**, and **Hibernate** for database interaction, while **PL/SQL stored procedures & triggers** optimize transactions.

---

## **🚀 Installation & Setup**

### **Prerequisites:**
🔹 **Java JDK 21+** (Ensure JavaFX is installed)  
🔹 **Apache Maven** (for dependency management)  
🔹 **Oracle 23** (Ensure the database is running)  
🔹 **Oracle JDBC Driver** (Download from Oracle's official site)  
🔹 **Hibernate ORM**

### **📆 Install Dependencies (if using Maven)**
```sh
mvn clean install
```

---
## **⚙️ Features & Functionalities**

### **👤 User Roles:**
- **Sales Representative:** Manages customers, schedules services, processes payments.
- **Admin:** Full control over users, inventory, services, and reports.

### **🛠 Core Modules:**
#### 1. **Customer & Vehicle Management**
🔹 Add/Edit customer & vehicle details.  
🔹 Retrieve vehicle service history.

#### 2. **Service & Appointment Scheduling**
🔹 Schedule maintenance services (detailing, PPF, repairs).  
🔹 Assign mechanics to tasks.

#### 3. **Invoicing & Payment Processing**
🔹 Generate **PDF invoices** automatically.  
🔹 Track and process payments securely.

#### 4. **Inventory Management (Admin Only)**
🔹 Track parts & consumables.  
🔹 **Low-stock alerts** via PL/SQL triggers.

#### 5. **Reports & Dashboard**
🔹 Monthly **service reports & revenue tracking**.  
🔹 Analyze trends in vehicle servicing.

#### 6. **Authentication & Security**
🔹 **Role-based access control** (Admin vs Sales Rep).  
🔹 **Audit logs** for tracking database updates.

---

## **⚙️ Technical Stack**

| **Component** | **Technology**                     |  
|-------------|------------------------------------|  
| **Frontend (UI)** | JavaFX                             |  
| **Backend (ORM Layer)** | Hibernate                          |  
| **Database** | Oracle DB (PL/SQL, JDBC, Hibernate) |
| **File Handling** | PDF Reports & Invoices (iText)     |  

---
