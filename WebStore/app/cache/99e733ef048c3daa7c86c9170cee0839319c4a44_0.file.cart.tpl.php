<?php
/* Smarty version 3.1.33, created on 2019-03-18 20:51:19
  from 'C:\GitRepositories\CSC417UserInterfaces\WebStore\templates\cart.tpl' */

/* @var Smarty_Internal_Template $_smarty_tpl */
if ($_smarty_tpl->_decodeProperties($_smarty_tpl, array (
  'version' => '3.1.33',
  'unifunc' => 'content_5c903d079c04b4_34749798',
  'has_nocache_code' => false,
  'file_dependency' => 
  array (
    '99e733ef048c3daa7c86c9170cee0839319c4a44' => 
    array (
      0 => 'C:\\GitRepositories\\CSC417UserInterfaces\\WebStore\\templates\\cart.tpl',
      1 => 1551397416,
      2 => 'file',
    ),
  ),
  'includes' => 
  array (
  ),
),false)) {
function content_5c903d079c04b4_34749798 (Smarty_Internal_Template $_smarty_tpl) {
$_smarty_tpl->_loadInheritance();
$_smarty_tpl->inheritance->init($_smarty_tpl, true);
?>


<?php 
$_smarty_tpl->inheritance->instanceBlock($_smarty_tpl, 'Block_11338107305c903d079b4f81_21235943', "localstyle");
?>


<?php 
$_smarty_tpl->inheritance->instanceBlock($_smarty_tpl, 'Block_111780855c903d079b5ef8_92360625', "content");
?>

<?php $_smarty_tpl->inheritance->endChild($_smarty_tpl, "layout.tpl");
}
/* {block "localstyle"} */
class Block_11338107305c903d079b4f81_21235943 extends Smarty_Internal_Block
{
public $subBlocks = array (
  'localstyle' => 
  array (
    0 => 'Block_11338107305c903d079b4f81_21235943',
  ),
);
public function callBlock(Smarty_Internal_Template $_smarty_tpl) {
?>

  <style>
  </style>
<?php
}
}
/* {/block "localstyle"} */
/* {block "content"} */
class Block_111780855c903d079b5ef8_92360625 extends Smarty_Internal_Block
{
public $subBlocks = array (
  'content' => 
  array (
    0 => 'Block_111780855c903d079b5ef8_92360625',
  ),
);
public function callBlock(Smarty_Internal_Template $_smarty_tpl) {
?>


<h2>My Cart</h2>

<?php echo "\$session = ".((string)$_smarty_tpl->tpl_vars['session']->value);?>


<pre>
$cart_info =
<?php echo var_export($_smarty_tpl->tpl_vars['cart_info']->value,true);?>


total = <?php echo $_smarty_tpl->tpl_vars['total_price']->value;?>

</pre>

<?php
}
}
/* {/block "content"} */
}
