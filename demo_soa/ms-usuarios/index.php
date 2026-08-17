<?php
// ms-usuarios/index.php
header("Content-Type: application/json");

// Conexión a la base de datos (Exclusiva de este microservicio)
$host = '127.0.0.1';
$db   = 'ms_usuarios_db';
$user = 'root'; // Cambia esto por tu usuario de MariaDB
$pass = '';     // Cambia esto por tu contraseña de MariaDB

try {
    $pdo = new PDO("mysql:host=$host;dbname=$db", $user, $pass);
    $stmt = $pdo->query("SELECT nombre FROM usuarios WHERE id = 1");
    $usuario = $stmt->fetch(PDO::FETCH_ASSOC);

    // Entregamos el contrato en formato JSON
    echo json_encode(["status" => "success", "data" => $usuario]);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(["status" => "error", "message" => "Error de base de datos"]);
}
?>