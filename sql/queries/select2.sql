select count(*) as count_orders, pharmacy_database.pharmacies.pharmacy_name, pharmacy_database.pharmacies.cities_city_code
from pharmacy_database.orders
inner join pharmacy_database.pharmacies on pharmacy_database.orders.pharmacies_id_pharmacy = pharmacy_database.pharmacies.id_pharmacy
where pharmacy_database.pharmacies.pharmacy_name="Farmacia Tei" AND pharmacy_database.orders.shipping_date like '%04%';