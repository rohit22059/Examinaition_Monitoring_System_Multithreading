## Examination Monitoring System
	
	It is desktop based application for conducting examination in a schools, universities or colleges.
	This project is created by using Java-Swing with use of Socket-Programming and mutlithreading concept.

## Requirements and installation

	1- Database - MySQL
	2- JDK(Java Development Kit and Java-8 should be installed
	3- Systems should be connected with LAN/Ethernet cable.

## How can you run the project
	
	1- Unzip the folder

	2- Restore MySQL database using command(In Ubuntu)
		
		$ mysql -u username -p database_name < backup_name.sql

	3- Go to path "ClientInterface/src/clientinterface/"
	
	4- Open "ClientMainScreent.java" file and change the localhost ip address with server ip address.

	5- Then clean and build the ClientInterface project.

	6- You will get two jar files.

	7- One for examinationmonitoringsystem in "examinationmonitoringsystem/dist/" folder.

	8- Second for ClientInterface in "ClientInterface/dist/" folder.

	9- Give permission to both jar file using command (In Ubuntu)

		$ sudo chmod +x <jar_file_name>

	10- Now run "examinationmonitoringsystem.jar" in server side.

	11- Run "ClientInterface.jar" at client side in different systems.

## Samples

I have attached few screenshot in project.Please let me know if you have any issue regarding the steps.
	
