<pre>
  <?php
  chdir(__DIR__);
  require_once "../include/db.php";

  echo "\n---- database = ", DBProps::which, "\n";

  R::begin();  // start tranaction

  //==================================================

  echo "\n---- users\n";

  // name, email, password, for simplicity password = name
  $user_data = [
      ["john", "arachnid@oracle.com", "john"],
      ["kirsten", "buffalo@go.com", "kirsten"],
      ["bill", "digger@gmail.com", "bill"],
      ["mary", "elephant@wcupa.edu", "mary"],
      ["joan", "kangaroo@upenn.edu", "joan"],
      ["alice", "feline@yahoo.com", "alice"],
      ["carla", "badger@esu.edu", "carla"],
      ["dave", "warthog@temple.edu", "dave"],
  ];

  foreach ($user_data as $data) {
    list($username, $email, $password) = $data;
    $user = R::dispense('user');
    $user->name = $username;
    $user->email = $email;
    $user->password = hash('sha256', $password);
    $user->is_admin = false;
    try {
      $id = R::store($user);
      echo "$id: $username\n";
    }
    catch (Exception $ex) {
      echo $ex->getMessage(), "\n";
    }
  }

//==================================================

  echo "\n---- admins\n";

  foreach (['dave', 'carla'] as $name) {
    $user = R::findOne('user', 'name = ?', [$name]);
    $user->is_admin = true;
    R::store($user);
    echo "admin: $name\n";
  }

  require_once __DIR__ . "/product_data.php";

  //==================================================

  echo "\n---- categories\n\n";

  $category_names = [];

  foreach ($product_data as $data) {
    $category_names[$data['category']] = 1;
  }

  foreach (array_keys($category_names) as $name) {
    $category = R::dispense('category');
    $category->name = $name;
    $id = R::store($category);
    echo "$id: $name\n";
  }

  $photos_dir = __DIR__ . "/../img/products/";
  $descriptions_dir = "descriptions";

  //==================================================

  echo "\n---- products\n\n";

  foreach ($product_data as $data) {
    $name = $data['name'];
    $category_name = $data['category'];
    $price = $data['price'];
    $photo_file = $data['photo_file'];
    $description_file = $data['description_file'];

    $category = R::findOne('category', 'name = ?', [$category_name]);

    echo "product name: $name\n";

    $file = "$descriptions_dir/$description_file";
    if (file_exists($file)) {
      $description = file_get_contents($file);
    }
    else {
      echo "---- missing description, set to empty\n";
      $description = "";
    }

    $product = R::dispense('product');

    $product->photo_file = $photo_file;

    $product->name = $name;
    $product->category_id = $category->id;
    $product->price = $price;
    $product->description = $description;
    $id = R::store($product);
    echo "---- product added with id: $id\n";

    $products[$id] = $product;
  }

  //==================================================

  echo "\n---- orders\n";

  define("SECONDS_PER_DAY", 3600 * 24);

  function randTimeNdaysPast($days) {
    $timestamp = time() - $days * SECONDS_PER_DAY + rand(0, SECONDS_PER_DAY);
    return date("Y-m-d H:i:s", $timestamp);
  }

  function makeOrder($user, $ndays, $productQuant) {
    $order = R::dispense('order');
    $order->user_id = $user->id;
    $order->created_at = randTimeNdaysPast($ndays);
    echo "\nby $user->name on $order->created_at\n";
    foreach ($productQuant as $arr) {
      list($product, $quantity) = $arr;
      echo " #$product->id: $product->name ($quantity)\n";
      $order->link('selection', [
              'quantity' => $quantity,
              'purchase_price' => $product->price
              ]
          )->product = $product;
    }
    R::store($order);
  }

  $alice = R::findOne('user', 'name=?', ["alice"]);
  $john = R::findOne('user', 'name=?', ["john"]);
  $bill = R::findOne('user', 'name=?', ["bill"]);

  $ndays = 7;

  makeOrder($alice, $ndays--, [
      [$products[1], 2],
      [$products[5], 3],
  ]);

  makeOrder($alice, $ndays--, [
      [$products[12], 1],
      [$products[8], 4],
  ]);

  makeOrder($bill, $ndays--, [
      [$products[2], 5],
      [$products[6], 2],
  ]);

  makeOrder($alice, $ndays--, [
      [$products[3], 3],
      [$products[11], 1],
  ]);

  makeOrder($bill, $ndays--, [
      [$products[1], 3],
      [$products[3], 3],
      [$products[5], 1],
      [$products[6], 2],
  ]);

  R::commit();  // commit transaction

