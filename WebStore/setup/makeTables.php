<?php
chdir(__DIR__);
echo "\n============= createTables\n";
require_once "createTables.php"; 

echo "\n============= populateTables\n";
require_once "populateTables.php";
