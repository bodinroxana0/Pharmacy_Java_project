select city_code, medicine_name, sum(quantity) as total_quantity from All_pharmacies_with_stock
where medicine_name="Paracetamol" and city_code="B"