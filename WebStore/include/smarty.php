<?php
require_once(__DIR__ . "/libs/Smarty.class.php");
$smarty = new Smarty();
$smarty->template_dir = realpath(__DIR__ . "/../templates");
$smarty->compile_dir = realpath(__DIR__ . "/../app/cache");
$smarty->addPluginsDir(__DIR__ . '/myplugins');

require_once __DIR__ . "/session.php";

$smarty->assign('session', new Session());
