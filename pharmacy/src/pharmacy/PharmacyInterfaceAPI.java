package pharmacy;
import java.sql.*;
import java.util.List;

public interface PharmacyInterfaceAPI {
	public void GetCities(Connection conn, String url, String user, String password);
	public List<PharmacyModel> GetPharmacies(Connection conn, String url, String user, String password);
	public List<CategoryModel> GetCategories(Connection conn, String url, String user, String password);
	public void GetMeds(Connection conn, String url, String user, String password);
	public List<OrderModel> GetOrders(Connection conn, String url, String user, String password);
	public List<PharmacyModel> GetPharmciesFilteredByCity(Connection conn, String url, String user, String password, String city);
	public List<PharmacyStockMedModel> GetPharmaciesFilteredByMedicine(Connection conn, String url, String user, String password, String medicine);
	public List<PharmacyStockMedModel> GetPharmaciesFilteredByMedicineAndCity(Connection conn, String url, String user, String password, String medicine, String city);
	public List<OrderPharmacyMonthModel> GetOrdersFilteredByPharmacyAndMonth(Connection conn, String url, String user, String password, String pharmacy, String month);
	public List<OrderPharmacyMonthModel> GetOrdersFilteredByPharmacyCategoryAndYear(Connection conn, String url, String user, String password, String pharmacy, String category, String year);
	public List<OrderPharmacyMonthModel> GetTheBiggestOrder(Connection conn, String url, String user, String password, String year);
	
}
