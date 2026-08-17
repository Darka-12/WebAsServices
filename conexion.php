<?php
$host = "localhost";
$user = "root";
$password = ""; // Contraseña por defecto en XAMPP
$database = "psiconova";

$conn = new mysqli($host, $user, $password, $database);

if ($conn->connect_error) {
    header('Content-Type: application/json; charset=utf-8');
    echo json_encode([
        "status" => "error",
        "message" => "Error de conexión a la base de datos"
    ]);
    exit();
}

$conn->set_charset("utf8mb4");
?>