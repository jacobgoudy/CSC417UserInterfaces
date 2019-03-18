<?php
require_once "include/smarty.php";
require_once "include/db.php";

//$session->unsetAll();  // failsafe

if (!isset($session->field)) {
  $session->field = 'name';
}

// convert category records to map from id to name
$category_recs = R::findAll('category', 'order by name');
$categories[0] = "-- ALL --";
foreach($category_recs as $rec) {
  $categories[$rec->id] = $rec->name;
}

$products = R::findAll('product');

$data = [
  'products' => $products,
  'categories' => $categories,
  'category_id' => null,
];
$smarty->assign($data);
$smarty->display("index.tpl");
