CREATE OR REPLACE VIEW Orders_meds_category AS
select pharmacy_database.pharmacies.code, pharmacy_database.pharmacies.pharmacy_name, pharmacy_database.pharmacies.cities_city_code, pharmacy_database.orders_meds.meds_id_medicine, pharmacy_database.orders_meds.id_order, pharmacy_database.orders.shipping_date, pharmacy_database.orders_meds.medicine_name, pharmacy_database.orders_meds.medicine_quantity
from (( pharmacy_database.orders
inner join pharmacy_database.pharmacies on pharmacy_database.orders.pharmacies_id_pharmacy = pharmacy_database.pharmacies.id_pharmacy)
inner join pharmacy_database.orders_meds on pharmacy_database.orders.id_order = pharmacy_database.orders_meds.id_order);