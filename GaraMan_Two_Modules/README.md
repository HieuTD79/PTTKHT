# GaraMan – 2 module code

Stack: Java 17, JSP, Jakarta Servlet 6, JDBC, MySQL 8, Maven, Tomcat 10.1+.

## Run
1. Create database and tables using `database/garaman.sql`.
2. Edit database credentials in `src/main/java/vn/garaman/config/DB.java`.
3. Build: `mvn clean package`.
4. Deploy `target/GaraMan.war` to Tomcat 10.1+.
5. Open `/GaraMan/gdDatLichHen.jsp` for online appointment.
6. Open `/GaraMan/gdNhanXe.jsp` for vehicle reception.

## Session requirement
The reception module requires a logged-in sales employee id in session:
`session.setAttribute("nhanVienId", <id in tblNhanVien>)`.
For quick local testing, you may temporarily set it after login; do not hard-code it in production.

## Important assumptions
- Appointment slot is 60 minutes; a slot is unavailable when another non-cancelled appointment exists at the same date and time.
- `tblLichHen` stores customer contact/vehicle details directly to preserve the document's appointment workflow.
- Employee `viTri` is `NVBanHang` or `NVKyThuat`.
- Prices and stock are read from the database, never trusted from browser input.
- The source document does not fully specify every SQL column. The SQL in this package is a consistent implementation schema for the entities and relationships it describes.
