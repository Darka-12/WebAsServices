<?php
header('Content-Type: application/json');

$conexion = new mysqli("localhost", "root", "", "psiconova");

if ($conexion->connect_error) {
    echo json_encode(["status" => "error", "message" => "Error de conexión"]);
    exit();
}

// Obtener datos enviados desde Android Studio
$email = $_POST['email'] ?? '';
$password = $_POST['password'] ?? '';

if (empty($email) || empty($password)) {
    echo json_encode(["status" => "error", "message" => "Campos vacíos"]);
    exit();
}

// Consultar el usuario
$stmt = $conexion->prepare("SELECT id, nombre, rol, password FROM usuarios WHERE email = ?");
$stmt->bind_param("s", $email);
$stmt->execute();
$resultado = $stmt->get_result();

if ($user = $resultado->fetch_assoc()) {
    // Si encriptaste la contraseña con password_hash(), aquí usas password_verify()
    // Para la prueba rápida con texto plano, comparamos directo:
    if ($password === $user['password']) {
        echo json_encode([
            "status" => "success",
            "id" => $user['id'],
            "nombre" => $user['nombre'],
            "rol" => $user['rol'] // 'cliente', 'psicologo' o 'admin'
        ]);
    } else {
        echo json_encode(["status" => "error", "message" => "Contraseña incorrecta"]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "El usuario no existe"]);
}

$stmt->close();
$conexion->close();
?>