<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "room";

// Create connection
$connr = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($connr->connect_error) {
    die("Connection failed: " . $connr->connect_error);
}
