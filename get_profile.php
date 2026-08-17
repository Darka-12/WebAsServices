<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include 'db_connection.php'; // Tu conexión a MySQL

$email = $_GET['email'] ?? null;

if ($email) {
    $stmt = $conn->prepare("SELECT id, nombre, email, foto_url, rol FROM usuarios WHERE email = ?");
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($user = $result->fetch_assoc()) {
        echo json_encode(["status" => "success", "user" => $user]);
    } else {
        echo json_encode(["status" => "error", "message" => "Usuario no encontrado"]);
    }
}
?>