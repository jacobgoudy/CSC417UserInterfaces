<pre>
<?php
chdir(__DIR__);
require_once '../include/db.php';

echo "\n---- database = ", DBProps::which, "\n\n";

$create_order = ['user', 'category', 'product', 'order', 'selection'];
$drop_order = array_reverse($create_order);

foreach ($drop_order as $table) {
  $sql = "drop table if exists `$table`";
  echo "$sql\n";
  R::exec($sql);
}

echo "\n";

foreach ($create_order as $table) {
  $filename = sprintf("%s-%s.sql", $table, DBProps::which);
  $sql = file_get_contents("tables/$filename");
  echo "$sql\n";
  R::exec($sql);
}
