<li class="nav-link"><a href="cart.php">Cart</a></li>

{if $session->login}
  <li class="nav-link"><a href="logout.php">Logout</a></li>
{else}
  <li class="nav-link"><a href="login.php">Login</a></li>
{/if}
