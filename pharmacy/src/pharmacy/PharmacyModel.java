package pharmacy;

public class PharmacyModel {
	public int IdPharmacy;
	public String Code;
	public String PharmacyName;
	public String Adress;
	public String Phone;
	public String CitiesCityCode;
	public PharmacyModel() {
		
	}
	public PharmacyModel(int IdPharmacy, String Code, String PharmacyName, String Adress, String Phone, String CitiesCityCode) {
		this.IdPharmacy=IdPharmacy;
		this.Code=Code;
		this.PharmacyName=PharmacyName;
		this.Adress=Adress;
		this.Phone=Phone;
		this.CitiesCityCode=CitiesCityCode;
	}
	
}
