<?php
    include_once '../PHP/dbh.inc.php';

$function = $_POST['function'];
$badgeId = $_POST['badgeId'];

$sql = "INSERT INTO badge (function, badgeId) VALUES('$function','$badgeId');";
mysqli_query($conn, $sql);
if(!$php_errormsg){
    echo "Added successfully";
}
header("Location: http://localhost:63342/Proiect_PMD/WEB/Admin%20page.html?_ijt=5a3fhbq0jfp7kdh1e7hlhko4rn");

//header("Location: ../index.php?add=success");
?>
