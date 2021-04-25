package pharmacy;

public class OrderModel {
	public int IdOrder;
	public String Code;
	public String PharmacyName;
	public String ShippingDate;
	public String MedicineName;
	public int MedicineQuantity;
	public double Price;
	
	public OrderModel() {
		
	}
	
	public OrderModel(int IdOrder, String Code, String PharmacyName, String ShippingDate, String MedicineName, int MedicineQuantity, double Price) {
		this.IdOrder=IdOrder;
		this.Code=Code;
		this.PharmacyName=PharmacyName;
		this.ShippingDate=ShippingDate;
		this.MedicineName=MedicineName;
		this.MedicineQuantity=MedicineQuantity;
		this.Price=Price;
	}
}
