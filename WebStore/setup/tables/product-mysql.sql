create table `product` (
  id integer auto_increment primary key not null,
  name varchar(255) unique not null, 
  description text not null,
  price real not null,
  photo_file varchar(255) not null,
  category_id integer not null, 
  foreign key(category_id) references category(id)
);
