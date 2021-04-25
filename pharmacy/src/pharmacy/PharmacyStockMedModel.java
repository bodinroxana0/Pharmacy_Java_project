package pharmacy;

public class PharmacyStockMedModel {
	public String Code;
	public String PharmacyName;
	public String CitiesCityCode;
	public String MedicineName;
	public double Quantity;
	public PharmacyStockMedModel() {
		
	}
	public PharmacyStockMedModel(String CitiesCityCode, String MedicineName, double TotalQuantity) {
		this.CitiesCityCode=CitiesCityCode;
		this.MedicineName=MedicineName;
		this.Quantity=TotalQuantity;
	}
	public PharmacyStockMedModel(String Code, String PharmacyName, String CitiesCityCode, String MedicineName, double Quantity) {
		this.Code=Code;
		this.PharmacyName=PharmacyName;
		this.CitiesCityCode=CitiesCityCode;
		this.MedicineName=MedicineName;
		this.Quantity=Quantity;
	}
	
}
