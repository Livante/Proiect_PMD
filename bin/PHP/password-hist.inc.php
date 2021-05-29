<?php


$code = $_POST['code'];
$codedb = $_POST['codedb'];


if ($code == "123456") {
    header("Location: http://localhost:63342/Proiect_PMD/PHP/history.inc.php?_ijt=6qqrmu7u9dbr62jrqq400vpg5h");
} elseif ($code == "024680") {
    header("Location: http://localhost:63342/Proiect_PMD/PHP/historyEven.inc.php?_ijt=aptdt1g5e2qk50ofvi5a1sok97");
} elseif ($code == "135791") {
    header("Location: http://localhost:63342/Proiect_PMD/PHP/historyOdd.inc.php?_ijt=htk06s91bhn0bfp5t0m5pajn85");
} elseif ($codedb == "654321") {
    header("Location: http://localhost:63342/Proiect_PMD/WEB/Admin%20page.html?_ijt=5a3fhbq0jfp7kdh1e7hlhko4rn");
} else {
    header("Location: http://localhost:63342/Proiect_PMD/WEB/Main%20menu.html?_ijt=bj1ifn0j1hdonpa089dbfhjr13");
}


?>




