package pharmacy;

public class OrderPharmacyMonthModel {
	public String Code;
	public String PharmacyName;
	public String ShippingDate;
	public int NumberOrders;
	public double TotalSum;
	public double AvgPriceOrder;
	public String CategoryName;
	
	public OrderPharmacyMonthModel() {
		
	}
	
	public OrderPharmacyMonthModel(String Code, String PharmacyName, String ShippingDate, double TotalSum) {
		this.Code=Code;
		this.PharmacyName=PharmacyName;
		this.ShippingDate=ShippingDate;
		this.TotalSum=TotalSum;
	}
	
	public OrderPharmacyMonthModel(String Code, String PharmacyName, String ShippingDate, int NumberOrders, String CategoryName) {
		this.Code=Code;
		this.PharmacyName=PharmacyName;
		this.ShippingDate=ShippingDate;
		this.NumberOrders=NumberOrders;
		this.CategoryName=CategoryName;
	}
	
	public OrderPharmacyMonthModel(String Code, String PharmacyName, String ShippingDate, int NumberOrders, double TotalSum, double AvgPriceOrder) {
		this.Code=Code;
		this.PharmacyName=PharmacyName;
		this.ShippingDate=ShippingDate;
		this.NumberOrders=NumberOrders;
		this.TotalSum=TotalSum;
		this.AvgPriceOrder=AvgPriceOrder;
	}
	
}
