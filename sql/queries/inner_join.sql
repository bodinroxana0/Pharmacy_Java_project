select pharmacy_database.meds.medicine_name, pharmacy_database.categories.category_name, pharmacy_database.meds.price
from (( pharmacy_database.meds_has_categories
inner join pharmacy_database.meds on pharmacy_database.meds_has_categories.meds_id_medicine = pharmacy_database.meds.id_medicine)
inner join pharmacy_database.categories on pharmacy_database.meds_has_categories.categories_id_categories = pharmacy_database.categories.id_categories); 