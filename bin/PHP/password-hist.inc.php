<?php

$code = $_POST['code'];
$codedb = $_POST['codedb'];


if ($code=="123456"){
    header("Location: http://localhost:63342/Proiect_PMD/WEB/manager-page.html?_ijt=q5v32dcvnk6p1mjnf86j9laibc");
}
elseif($code=="024680"){
 //   header("Location: http://localhost:63342/Proiect_PMD/PHP/historyEven.inc.php?_ijt=g4jkrm4ooe5g837c9dunrc9uld");

}
elseif ($codedb=="654321"){
    header("Location: http://localhost:63342/Proiect_PMD/WEB/Admin%20page.html?_ijt=5a3fhbq0jfp7kdh1e7hlhko4rn");
}
else{
    echo $code;
//    header("Location: http://localhost:63342/Proiect_PMD/WEB/Main%20menu.html?_ijt=bj1ifn0j1hdonpa089dbfhjr13");
}


?>





