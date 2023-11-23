SMALL FINANCE APPLICATION

Overview        
Small Finance is a financial application designed for users interested in saving and investing in our bank. The application includes features such as Fixed Deposit, Recurring Deposit, Loans, InterBank transactions, and a savings account. It operates as a role-based system with two main roles: User and Manager.

User Role: Users can perform actions like Fixed Deposit, transactions, Recurring Deposit, and apply for loans.


Manager Role: Managers are responsible for verifying users and approving loans.

The primary objective of this project is to implement the application in four different frameworks for performance comparison. A JMX script is used to run tests on the four frameworks, and the metrics are exposed in Prometheus and Grafana. Performance is compared based on these metrics.

Requirements
 1. Java 17  or newer
 2. jmeter
 3. Preferred IDE (IntelliJ is used in this project)
 4. Postman

Installation Instructions
1. You can import the project as a maven application to your favorite IDE. I made my tests by using intellij.
   mvn spring-boot:run

To Run application
    1. Build using maven goal (or by using maven wrapper): mvn clean package and execute the resulting artifact as follows java -jar smallfinance.jar
    2. Run as a Docker container.
       Clone the repository.
       cd to project root directory.
       docker build -t Smallfinance .
       docker run --expose 8080 -p 8080:8080 Smallfinance


To test from postman

  1. Generate a token
     
      <img width="643" alt="image" src="https://github.com/pavan-thoughtclan/Smallfinance/assets/139839952/5b263bec-d49b-4f7e-8ede-a94276d709f1">

  3. Create an account
     
      Use the token to create an account.
     
      <img width="638" alt="image" src="https://github.com/pavan-thoughtclan/Smallfinance/assets/139839952/3e37110c-54a9-41db-9612-c913b6cda5a7">



Testing in Jmeter
  1. Download jmeter from apache - https://jmeter.apache.org/download_jmeter.cgi
  2. Start the jmeter application by going to apache-jmeter -> bin -> jmeter.bat (or) ApacheJmeter.jar
  3. Import the jmx script to the application by File -> open -> file.jmx
  4. check the results in aggregate report of the application



 







