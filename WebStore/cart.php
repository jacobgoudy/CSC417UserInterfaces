<?php
require_once "include/smarty.php";
require_once "include/db.php";

//$session->cart = null;  // failsafe

//echo $session;

if (!isset($session->cart)) {  // or, is_null($session->cart)
  $session->cart = [];
}
//
// for example, suppose we have added this to the cart: 

$session->cart[9] = 2; 

// process $session->cart, storing information into $cart_info

$cart_info = [];

// for example, we might generate:

$cart_info[9] = [ 
        'name' => 'HP Officejet Pro 6100 ePrinter',
        'category' => 'printer',
        'price' => 99.99, 
        'quantity' => 2
];
$total_price = 99.99 * 2;

$data = [
    'cart_info' => $cart_info,
    'total_price' => $total_price,
];
$smarty->assign($data);
$smarty->display("cart.tpl");
