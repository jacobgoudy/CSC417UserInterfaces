<?php

/*
retrieve and clear the value of a session key.
the value must be a scalar
*/

function smarty_function_session_get_flash($params, $smarty) {
   if (empty($params['var'])) {
        trigger_error("assign: missing 'var' parameter");
        return;
    }

   $session = $smarty->getTemplateVars('session');
   $var = $params['var'];
   $value = $session->$var;
   unset($session->$var);
   return $value;
}
