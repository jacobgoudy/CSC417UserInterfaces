<?php

require_once __DIR__ . "/rb.php";

class DBProps {
  const which = "mysql"; // "mysql" or "sqlite"

  public static function getProps() {
    switch (self::which) {
      case "mysql":
        return [
            'url' => "mysql:host=127.0.0.1;dbname=store",
            'username' => 'guest',
            'password' => '',
        ];
      case "sqlite":
        $dir = __DIR__."/database";
        $database = "$dir/db.sqlite";
        if (!is_dir($dir)) {
          mkdir($dir);
          chmod($dir, 0777);
        }
        if (!is_file($database)) {
          touch($database);
          chmod($database, 0666);
        }
        return [
            'url' => "sqlite:$database",
            'username' => null,
            'password' => null
        ];
    }
  }
  
  public static function init() {
    $props = self::getProps();
    R::setup($props['url'], $props['username'], $props['password']);
    R::freeze(true);
    return $props;
  }
}

// put in this include file so that it is guaranteed to be done only once
DBProps::init();
