select code, pharmacy_name, shipping_date, count(id_order) as number_orders, sum(price) as total_sum, avg(price) as average_order_price from All_orders_and_price
where pharmacy_name="Farmacia Tei" and shipping_date like '%04%'