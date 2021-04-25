CREATE OR REPLACE VIEW All_pharmacies_with_stock  AS
select pharmacy_database.pharmacies.code, pharmacy_database.pharmacies.pharmacy_name, pharmacy_database.pharmacies.cities_city_code as city_code, pharmacy_database.meds.medicine_name,pharmacy_database.pharmacies_stock.quantity
from (( pharmacy_database.pharmacies_stock
inner join pharmacy_database.pharmacies on pharmacy_database.pharmacies_stock.pharmacies_id_pharmacy = pharmacy_database.pharmacies.id_pharmacy)
inner join pharmacy_database.meds on pharmacy_database.pharmacies_stock.meds_id_medicine = pharmacy_database.meds.id_medicine); 