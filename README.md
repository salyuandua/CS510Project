
A project for Marshall Health STSTEM  in spring 2017 
1. Aim: The primary purpose of this project is to give the opportunity to database Students to practice
what they learn with a real application.
2. Possible Practice Tools: Java, JDBC, MySQL, JavaScript, HTML
3. Group Policy: At most two learners can submit the project together if they implement the user
interface. The group policy will only be option for the learners who try to implement user interface
with databases (main program plus extra part of the projects). Each of them will get the same point
from the project although one of them is the only worker. Therefore, do not cry at the end of the
project. Choose your partner wisely. I recommend you to work in a team if you can equally code the
project. If not, you should work alone. The team submission will be evaluated accordingly comparing
to individual submission.
4. Time: START EARLY
5. Scenario: You need to simulate a pharmacy system with patient tracking. You basically simulate the
databases which are needed to operate a pharmacy, money exchange with bank and health tracking.
If you implement an user interface, the application must hormonally work with bank, pharmacy and
health tracking system databases.
Basically, a pharmacy has a number of medicines and each medicine has a price, multiple suppliers,
one manufacturer, type, multiple benefits, etc.
Moreover, patients must be registered to Health Tracking System with her/his health problems, doctor
information and health care providers, required medicines and their quantities or dosage, restrictions,
and other personal information like contact information, legal representative if any, emergency contact.
The medicine information and the required quantities will be updated automatically for every purchase
of medicine from the pharmacy. If the patient try to buy a medicine which has restriction for the patient,
the health tracking system warn pharmacy not to sell the medicine to the patient. Only the patient’s
doctor can update or modify the restriction of the patients. The patient, s/he can buy the medicines
from a pharmacy or the legal representative of the patient can buy the medicine for the patient.
The buyer of the medicine must have at least a bank account which will be hold in bank databases.
The buyer can have multiple bank accounts from different banks. The bank database also holds the
account of the pharmacy, and every money exchanges between pharmacy and the buyers increase or
decrease the balances of the accounts according to bought medicines. It is important to note the bank
can only transfer money from the buyer account to pharmacy account if the secret password is entered
by the buyer and it is verified by the bank database.
CS410/CS510/IS623 Project (MAX 50 POINTS WITH EXTRA 20 POINTS) Page 2 of 4
Furthermore, there are multiple users for bank, pharmacy, and health tacking databases. The administrative
users have all privileges to modify the databases. However, the patient can only see his
information from health tracking and bank databases.
6. Database Kinds and Data in Each Table: Pharmacy Database, Bank Database, and Health
Tracking System are the databases. Please insert suitable data in each table according your design.
Create all the relations with primary and foreign keys in MySQL and insert the data to tables in
MySQL.
7. Demo for Extra Credit Runners: The students who work for extra credit needs to show the demo
to TA, you need to have suitable data in each database. Therefore, please insert suitable data to each
table. Moreover, it should be possible to add a new patient, new medicine, new order,.... to databases
over the application. For example, the user interface which you design, must give options to the user to
select possible options such as add, insert, modify data, buy medicine... Please create one user interface
for all the options from the drop down menu or the other types which you prefer. User interface can
be in GUI or HTML or JS....
8. Critical Issues: You need to consider real life situations. “What happen if the medicine which is
desired does not exists”, “what happen there is no money in the account of the patient”, “what happen
the patient is not qualified to buy the medicine”, “what happen the patient returns the medicine back”,
“what happen if the user attacks the databases from the input form which you give”, “what happen
if the user put blank for the patient name when the insertion happening”, “what happen when the
new returned patient wants to buy same medicine which the patient should not get”, “what happen if
the user does not have rights to add data to database”, “who can see what information from database
(You should create different users and give options in the user interface to select user type)”, “what
happen if the patient need Vaccination Records”... BE CAREFUL WHEN YOU DESIGN YOUR
DATABASES AND RELATIONS. DO NOT MAKE IT TOO MUCH HARDER FOR YOURSELF
BUT CONSIDER SCENARIOS AS MUCH AS YOU CAN.
9. Constraints: If you can use available constraints in MySQL, you must use the constraints from it.
However, if there is no option to satisfy those constraints with MySQL (like “check” in MySQL does
not do anything.), you can check ”trigger” in MySQl to use it. For extra point runners, you can use
application based restrictions in Java or other languages for checking.
10. Submission Policy: You need to use Doxygen or Javadoc to report your programs for the extra point
runners. Both javadoc and doxygen create a Html report from Java code comments which help me to
look for your codes. All of you must write a report (according to the template) for your designs of all
databases in a nice graphical format with your assumptions (which we did in the class), and submit
your Java code, your SQL backup, user information with creation and permission of each user with
SQL queries. If you work as a group to complete the project, you must implement a user interface
which get information from users and do insertion deletion of records for administrative type users, and
extras which you think of. If the user is the patient, the patient type users can only buy the medicines,
deposit money to the account and register to health tracking system, and extras which you think of..
11. NOTE: There is only one project now for CS students. It is same for CS Undergraduate and CS
Graduate. Every students in CS must do this project not the IS project. You need to implement
JDBC and user interface to get extra 20 points. IF YOU DO NOT UNDERSTAND THE PROJECT,
SEND ME EMAIL OR STOP BY MY OFFICE.
12. Attachment: Report template, JDBC implementation examples in JAVA and the required jar file to
create connection to database.
