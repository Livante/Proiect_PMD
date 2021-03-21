<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "history";

// Create connection
$connh = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($connh->connect_error) {
    die("Connection failed: " . $connh->connect_error);
}