<?php
include_once '../PHP/dbhr.inc.php';

$badgeId = $_POST['badgeId'];

$sql = "DELETE FROM room WHERE badgeId=$badgeId;";
mysqli_query($connr, $sql);
if(!$php_errormsg){
    echo "Added successfully";
}
header("Location: http://localhost:63342/Proiect_PMD/WEB/Admin%20page.html?_ijt=5a3fhbq0jfp7kdh1e7hlhko4rn");

//header("Location: ../index.php?add=success");
?>
