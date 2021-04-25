package pharmacy;
import java.sql.*;
import java.util.Scanner;

public class MySQLConnection {

	
	public static void main(String[] args) {
		String url="jdbc:mysql://localhost:3306/pharmacy_database";
		String user="root";
		String password="familiabodin";
		Connection conn=null;
		PharmacyAPI api=new PharmacyAPI();
		api.GetCities(conn,url,user,password);
		api.GetPharmacies(conn, url, user, password);
		api.GetCategories(conn, url, user, password);
		api.GetMeds(conn, url, user, password);
		api.GetOrders(conn, url, user, password);
		
		System.out.println("Type the city:");
		try (Scanner scanner = new Scanner(System.in)) {
			String inputString = scanner.nextLine();
			api.GetPharmciesFilteredByCity(conn, url, user, password, inputString);
		}
		api.GetPharmaciesFilteredByMedicine(conn, url, user, password, "Paracetamol");
	}
}
