<?php
/* Smarty version 3.1.33, created on 2019-03-18 20:51:06
  from 'C:\GitRepositories\CSC417UserInterfaces\WebStore\templates\layout.tpl' */

/* @var Smarty_Internal_Template $_smarty_tpl */
if ($_smarty_tpl->_decodeProperties($_smarty_tpl, array (
  'version' => '3.1.33',
  'unifunc' => 'content_5c903cfa7e0362_90478261',
  'has_nocache_code' => false,
  'file_dependency' => 
  array (
    '5980e1f364529567397b7956426f30d1816e9beb' => 
    array (
      0 => 'C:\\GitRepositories\\CSC417UserInterfaces\\WebStore\\templates\\layout.tpl',
      1 => 1552063695,
      2 => 'file',
    ),
  ),
  'includes' => 
  array (
    'file:links.tpl' => 1,
  ),
),false)) {
function content_5c903cfa7e0362_90478261 (Smarty_Internal_Template $_smarty_tpl) {
$_smarty_tpl->_loadInheritance();
$_smarty_tpl->inheritance->init($_smarty_tpl, false);
?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>  
      <?php echo (($tmp = @$_smarty_tpl->tpl_vars['page_title']->value)===null||$tmp==='' ? basename(dirname($_SERVER['PHP_SELF'])) : $tmp);?>

    </title>

    <link rel="stylesheet" 
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"  
          crossorigin="anonymous" />
    <link href="css/layout.css" rel="stylesheet" />

  <?php 
$_smarty_tpl->inheritance->instanceBlock($_smarty_tpl, 'Block_15542036755c903cfa7d9b62_20928042', "localstyle");
?>

</head>
<body>     
  <header>
    <div>
      <img class="img-fluid" src="img/header.png" />
      <span class='login'><?php echo (($tmp = @$_smarty_tpl->tpl_vars['session']->value->login->name)===null||$tmp==='' ? '' : $tmp);?>
</span>
    </div>

    <nav class="navbar navbar-dark bg-info navbar-expand-sm">

      <a class="navbar-brand" href=".">Home</a>

      <button class="navbar-toggler navbar-toggler-icon" type="button" 
              data-toggle="collapse" data-target="#navbar1">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbar1">
        <ul class="navbar-nav mr-auto">
          <?php $_smarty_tpl->_subTemplateRender('file:links.tpl', $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, 0, $_smarty_tpl->cache_lifetime, array(), 0, false);
?>
        </ul>
      </div>
    </nav>    

  </header>

  <section class="container-fluid"><?php 
$_smarty_tpl->inheritance->instanceBlock($_smarty_tpl, 'Block_804637175c903cfa7df442_51313078', "content");
?>
</section>

  <?php echo '<script'; ?>
 src="https://code.jquery.com/jquery-1.12.4.min.js"><?php echo '</script'; ?>
>
  <?php echo '<script'; ?>
 src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"><?php echo '</script'; ?>
>
  <?php echo '<script'; ?>
 src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"><?php echo '</script'; ?>
>  

  <?php 
$_smarty_tpl->inheritance->instanceBlock($_smarty_tpl, 'Block_15694046665c903cfa7dfd54_16955598', "localscript");
?>

</body>
</html>
<?php }
/* {block "localstyle"} */
class Block_15542036755c903cfa7d9b62_20928042 extends Smarty_Internal_Block
{
public $subBlocks = array (
  'localstyle' => 
  array (
    0 => 'Block_15542036755c903cfa7d9b62_20928042',
  ),
);
public function callBlock(Smarty_Internal_Template $_smarty_tpl) {
}
}
/* {/block "localstyle"} */
/* {block "content"} */
class Block_804637175c903cfa7df442_51313078 extends Smarty_Internal_Block
{
public $subBlocks = array (
  'content' => 
  array (
    0 => 'Block_804637175c903cfa7df442_51313078',
  ),
);
public function callBlock(Smarty_Internal_Template $_smarty_tpl) {
}
}
/* {/block "content"} */
/* {block "localscript"} */
class Block_15694046665c903cfa7dfd54_16955598 extends Smarty_Internal_Block
{
public $subBlocks = array (
  'localscript' => 
  array (
    0 => 'Block_15694046665c903cfa7dfd54_16955598',
  ),
);
public function callBlock(Smarty_Internal_Template $_smarty_tpl) {
}
}
/* {/block "localscript"} */
}
