{extends file="layout.tpl"}

{block name="localstyle"}
  <style>
  </style>
{/block}

{block name="content"}

<h2>My Cart</h2>

{"\$session = $session"}

<pre>
$cart_info =
{var_export($cart_info,true)}

total = {$total_price}
</pre>

{/block}
