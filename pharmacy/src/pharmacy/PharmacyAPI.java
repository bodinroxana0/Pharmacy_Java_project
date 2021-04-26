package pharmacy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PharmacyAPI implements PharmacyInterfaceAPI {
	public void GetCities(Connection conn, String url, String user, String password) {
		try {
			conn=DriverManager.getConnection(url,user,password);
			if(conn!=null) {
				System.out.println("");
				System.out.println("Cities:");
				System.out.println("-----------------------------------------------------");
				String sql = "SELECT * FROM cities";
				 
				Statement statement = conn.createStatement();
				ResultSet result = statement.executeQuery(sql);
				
				while (result.next()){
					CityModel city=new CityModel(result.getString(1), result.getString(2));
				    System.out.printf("%s - %s \n", city.CityCode, city.CityName);
				}
			}
				
		}catch (SQLException ex) {
			System.out.println("An error occured. Maybe user/password is invalid");
			ex.printStackTrace();
		}finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
		}
	}
	public List<PharmacyModel> GetPharmacies(Connection conn, String url, String user, String password) {
		List<PharmacyModel> pharmacies=new ArrayList<PharmacyModel>();
		try {
			conn=DriverManager.getConnection(url,user,password);
			if(conn!=null) {
				String sql = "SELECT * FROM pharmacies";
				 
				Statement statement = conn.createStatement();
				ResultSet result = statement.executeQuery(sql);
				
				while (result.next()){
					PharmacyModel pharmacy = new PharmacyModel(result.getInt(1), result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6));
					pharmacies.add(pharmacy);
				   }
			}
				
		}catch (SQLException ex) {
			System.out.println("An error occured. Maybe user/password is invalid");
			ex.printStackTrace();
		}finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
		}
		return pharmacies;
	}
	public List<CategoryModel> GetCategories(Connection conn, String url, String user, String password) {
		List<CategoryModel> categories=new ArrayList<CategoryModel>();
		try {
			conn=DriverManager.getConnection(url,user,password);
			if(conn!=null) {
				String sql = "SELECT * FROM categories";
				 
				Statement statement = conn.createStatement();
				ResultSet result = statement.executeQuery(sql);
				
				while (result.next()){
					CategoryModel category=new CategoryModel(result.getInt(1), result.getString(2));
					categories.add(category);
				}
			}
				
		}catch (SQLException ex) {
			System.out.println("An error occured. Maybe user/password is invalid");
			ex.printStackTrace();
		}finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
		}
		return categories;
	}
	public void GetMeds(Connection conn, String url, String user, String password) {
		try {
			conn=DriverManager.getConnection(url,user,password);
			if(conn!=null) {
				String sql = "SELECT * FROM meds";
				 
				Statement statement = conn.createStatement();
				ResultSet result = statement.executeQuery(sql);
				
				while (result.next()){
					MedicineModel medicine=new MedicineModel(result.getInt(1), result.getString(2),result.getFloat(3));
				   }
			}
				
		}catch (SQLException ex) {
			System.out.println("An error occured. Maybe user/password is invalid");
			ex.printStackTrace();
		}finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
		}
	}
	public List<OrderModel> GetOrders(Connection conn, String url, String user, String password) {
		List<OrderModel> orders=new ArrayList<OrderModel>();
		try {
			conn=DriverManager.getConnection(url,user,password);
			if(conn!=null) {
				
				String sql = "SELECT * FROM All_orders_and_price";
				 
				Statement statement = conn.createStatement();
				ResultSet result = statement.executeQuery(sql);
				
				while (result.next()){
					OrderModel order = new OrderModel(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getInt(6), result.getDouble(7));
					orders.add(order);
				}
			}
				
		}catch (SQLException ex) {
			System.out.println("An error occured. Maybe user/password is invalid");
			ex.printStackTrace();
		}finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
		}
		return orders;
	}
	public List<PharmacyModel> GetPharmciesFilteredByCity(Connection conn, String url, String user, String password, String city) {
		List<PharmacyModel> pharmacies=new ArrayList<PharmacyModel>();
		try {
			conn=DriverManager.getConnection(url,user,password);
			if(conn!=null) {
				
				String sql = "SELECT * FROM pharmacies WHERE cities_city_code = ? ";
				 
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1,  city);
				ResultSet result = ps.executeQuery();
				
				while (result.next()){
					PharmacyModel pharmacy = new PharmacyModel(result.getInt(1), result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6));
					pharmacies.add(pharmacy);
				}
			}
				
		}catch (SQLException ex) {
			System.out.println("An error occured. Maybe user/password is invalid");
			ex.printStackTrace();
		}finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
		}
		return pharmacies;
	}
	public List<PharmacyStockMedModel> GetPharmaciesFilteredByMedicine(Connection conn, String url, String user, String password, String medicine) {
		List<PharmacyStockMedModel> pharmacies=new ArrayList<PharmacyStockMedModel>();
		try {
			conn=DriverManager.getConnection(url,user,password);
			if(conn!=null) {
				
				String sql = "SELECT * FROM All_pharmacies_with_stock WHERE medicine_name = ? ";
				 
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1,  medicine);
				ResultSet result = ps.executeQuery();
				
				while (result.next()){
					PharmacyStockMedModel pharmacy = new PharmacyStockMedModel(result.getString(1), result.getString(2),result.getString(3),result.getString(4),result.getDouble(5));
					pharmacies.add(pharmacy);
				}
			}
				
		}catch (SQLException ex) {
			System.out.println("An error occured. Maybe user/password is invalid");
			ex.printStackTrace();
		}finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
		}
		return pharmacies;
	}
	public List<PharmacyStockMedModel> GetPharmaciesFilteredByMedicineAndCity(Connection conn, String url, String user, String password, String medicine, String city) {
		List<PharmacyStockMedModel> pharmacies=new ArrayList<PharmacyStockMedModel>();
		try {
			conn=DriverManager.getConnection(url,user,password);
			if(conn!=null) {
				
				String sql = "SELECT city_code, medicine_name, sum(quantity) as total_quantity FROM All_pharmacies_with_stock WHERE medicine_name = ? AND city_code = ? ";
				 
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1,  medicine);
				ps.setString(2, city);
				ResultSet result = ps.executeQuery();
				
				while (result.next()){
					PharmacyStockMedModel pharmacy = new PharmacyStockMedModel(result.getString(1), result.getString(2), result.getDouble(3));
					pharmacies.add(pharmacy);
				}
			}
				
		}catch (SQLException ex) {
			System.out.println("An error occured. Maybe user/password is invalid");
			ex.printStackTrace();
		}finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
		}
		return pharmacies;
	}
	public List<OrderPharmacyMonthModel> GetOrdersFilteredByPharmacyAndMonth(Connection conn, String url, String user, String password, String pharmacy, String month){
		List<OrderPharmacyMonthModel> orders=new ArrayList<OrderPharmacyMonthModel>();
		try {
			conn=DriverManager.getConnection(url,user,password);
			if(conn!=null) {
				
				String sql = "SELECT code, pharmacy_name, shipping_date, count(id_order) AS number_orders, sum(price) AS total_sum, avg(price) AS average_order_price FROM All_orders_and_price WHERE pharmacy_name= ? AND shipping_date like ? ";
				 
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, pharmacy);
				ps.setString(2, "%"+month+"%");
				ResultSet result = ps.executeQuery();
				
				while (result.next()){
					OrderPharmacyMonthModel order = new OrderPharmacyMonthModel(result.getString(1), result.getString(2), result.getString(3), result.getInt(4), result.getDouble(5), result.getDouble(6));
					orders.add(order);
				}
			}
				
		}catch (SQLException ex) {
			System.out.println("An error occured. Maybe user/password is invalid");
			ex.printStackTrace();
		}finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
		}
		return orders;
	}
	public List<OrderPharmacyMonthModel> GetOrdersFilteredByPharmacyCategoryAndYear(Connection conn, String url, String user, String password, String pharmacy, String category, String year){
		List<OrderPharmacyMonthModel> orders=new ArrayList<OrderPharmacyMonthModel>();
		try {
			conn=DriverManager.getConnection(url,user,password);
			if(conn!=null) {
				
				String sql = "SELECT code, pharmacy_name, shipping_date, count(id_order) AS number_orders, category_name FROM Orders_meds_category2 WHERE  pharmacy_name = ? AND category_name= ? AND shipping_date LIKE ?";
				 
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, pharmacy);
				ps.setString(2, category);
				ps.setString(3, year+"%");
				ResultSet result = ps.executeQuery();
				
				while (result.next()){
					OrderPharmacyMonthModel order = new OrderPharmacyMonthModel(result.getString(1), result.getString(2), result.getString(3), result.getInt(4), result.getString(5));
					orders.add(order);
				}
			}
				
		}catch (SQLException ex) {
			System.out.println("An error occured. Maybe user/password is invalid");
			ex.printStackTrace();
		}finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
		}
		return orders;
	}
	public List<OrderPharmacyMonthModel> GetTheBiggestOrder(Connection conn, String url, String user, String password, String year) {
		List<OrderPharmacyMonthModel> orders=new ArrayList<OrderPharmacyMonthModel>();
		try {
			conn=DriverManager.getConnection(url,user,password);
			if(conn!=null) {
				
				String sql = "SELECT code, pharmacy_name, shipping_date, sum(price) AS total_sum FROM All_orders_and_price WHERE shipping_date LIKE ? GROUP BY pharmacy_name ORDER BY total_sum desc";
				 
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, year+"%");
				ResultSet result = ps.executeQuery();
				
				
				while (result.next()){
					OrderPharmacyMonthModel order = new OrderPharmacyMonthModel(result.getString(1), result.getString(2), result.getString(3), result.getDouble(4));
					orders.add(order);
				}
			}
				
		}catch (SQLException ex) {
			System.out.println("An error occured. Maybe user/password is invalid");
			ex.printStackTrace();
		}finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
		}
		return orders;
	}
}
