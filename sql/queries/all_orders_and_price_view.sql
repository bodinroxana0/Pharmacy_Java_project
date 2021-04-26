create or replace view All_orders_and_price as
select All_orders.id_order, All_orders.code, All_orders.pharmacy_name, All_orders.shipping_date, pharmacy_database.meds.medicine_name, All_orders.medicine_quantity, pharmacy_database.meds.price
from All_orders
inner join pharmacy_database.meds on pharmacy_database.meds.id_medicine=All_orders.meds_id_medicine
group by All_orders.id_order;