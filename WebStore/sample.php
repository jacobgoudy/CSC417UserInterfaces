<?php
require_once "include/smarty.php";

$data = [
  'page_title' => 'Sample',
];
$smarty->assign($data);
$smarty->display("sample.tpl");
