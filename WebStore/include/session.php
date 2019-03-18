<?php
session_start();

define( "_SECRET_", "-set-this-to-make-sessions-secure-" );
define( "_HASH_", md5(__DIR__ . _SECRET_) );
define( "_KEY_", "sess_" . _HASH_ );

class Session {
  public function __set($name,$value) {
    $_SESSION[_KEY_][$name] = $value;
  }
  public function & __get($name) {
    return $_SESSION[_KEY_][$name];
  }
  public function __toString() {
    return isset($_SESSION[_KEY_]) ? print_r($_SESSION[_KEY_],true) : "null";
  }
  public function __isset($name) {
    return isset($_SESSION[_KEY_][$name]);
  }
  public function __unset($name) {
    unset($_SESSION[_KEY_][$name]);
  }
  public function unsetAll() {
    unset($_SESSION[_KEY_]);
  }
}

$session = new Session();

