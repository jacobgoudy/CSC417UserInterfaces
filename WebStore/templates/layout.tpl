<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>  
      {$page_title|default: basename(dirname($smarty.server.PHP_SELF))}
    </title>

    <link rel="stylesheet" 
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"  
          crossorigin="anonymous" />
    <link href="css/layout.css" rel="stylesheet" />

  {block name="localstyle"}{/block}
</head>
<body>     
  <header>
    <div>
      <img class="img-fluid" src="img/header.png" />
      <span class='login'>{$session->login->name|default}</span>
    </div>

    <nav class="navbar navbar-dark bg-info navbar-expand-sm">

      <a class="navbar-brand" href=".">Home</a>

      <button class="navbar-toggler navbar-toggler-icon" type="button" 
              data-toggle="collapse" data-target="#navbar1">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbar1">
        <ul class="navbar-nav mr-auto">
          {include file='links.tpl'}
        </ul>
      </div>
    </nav>    

  </header>

  <section class="container-fluid">{block name="content"}{/block}</section>

  <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>  

  {block name="localscript"}{/block}
</body>
</html>
