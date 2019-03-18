create table `selection` (
  id integer auto_increment primary key not null,
  product_id integer not null,
  order_id integer not null,
  quantity integer not null,
  purchase_price real not null,
  foreign key(order_id) references `order`(id),
  foreign key(product_id) references product(id),
  unique(order_id,product_id)
);
