<?php


$hist = $_POST['hist'];

$base="../History_";
$extension=".html";
$rez=$base . $hist . $extension;



header("Location: $rez");


?>




