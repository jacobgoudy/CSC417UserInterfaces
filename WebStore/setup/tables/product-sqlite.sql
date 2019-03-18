create table `product` (
  id integer primary key not null,
  name text unique not null collate nocase,
  description text not null,
  price real not null,
  category_id integer not null, 
  photo_file text not null collate nocase,
  foreign key(category_id) references category(id)
);
