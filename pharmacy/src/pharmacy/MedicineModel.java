package pharmacy;

public class MedicineModel {
	public int IdMedicine;
	public String MedicineName;
	public float Price;
	public MedicineModel() {
		
	}
	public MedicineModel(int IdMedicine, String MedicineName, float Price){
		this.IdMedicine=IdMedicine;
		this.MedicineName=MedicineName;
		this.Price=Price;
	}
}
