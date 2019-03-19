<?php
/* Smarty version 3.1.33, created on 2019-03-18 20:51:06
  from 'C:\GitRepositories\CSC417UserInterfaces\WebStore\templates\links.tpl' */

/* @var Smarty_Internal_Template $_smarty_tpl */
if ($_smarty_tpl->_decodeProperties($_smarty_tpl, array (
  'version' => '3.1.33',
  'unifunc' => 'content_5c903cfa7f28f2_31377358',
  'has_nocache_code' => false,
  'file_dependency' => 
  array (
    '4072624811f89a6b9da61a91191b847435e48285' => 
    array (
      0 => 'C:\\GitRepositories\\CSC417UserInterfaces\\WebStore\\templates\\links.tpl',
      1 => 1551393563,
      2 => 'file',
    ),
  ),
  'includes' => 
  array (
  ),
),false)) {
function content_5c903cfa7f28f2_31377358 (Smarty_Internal_Template $_smarty_tpl) {
?><li class="nav-link"><a href="cart.php">Cart</a></li>

<?php if ($_smarty_tpl->tpl_vars['session']->value->login) {?>
  <li class="nav-link"><a href="logout.php">Logout</a></li>
<?php } else { ?>
  <li class="nav-link"><a href="login.php">Login</a></li>
<?php }
}
}
