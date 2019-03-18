create table `order` (
  id integer auto_increment primary key not null,
  user_id integer not null,
  created_at datetime not null,
  foreign key(user_id) references user(id)
);
