<?php
ob_start();
header('Content-Type: application/json; charset=utf-8');

include 'conexion.php';

$email = $_GET['email'] ?? '';

if (empty($email)) {
    ob_clean();
    echo json_encode([
        "status" => "error",
        "message" => "El email es requerido",
        "user" => null
    ]);
    exit();
}

// Consulta ajustada a la estructura real de la tabla 'usuarios'
$stmt = $conn->prepare("SELECT id, nombre, email, rol, cedula_profesional, especialidad, precio_sesion, descripcion FROM usuarios WHERE email = ?");

if (!$stmt) {
    ob_clean();
    echo json_encode([
        "status" => "error",
        "message" => "Error en la consulta SQL: " . $conn->error,
        "user" => null
    ]);
    exit();
}

$stmt->bind_param("s", $email);
$stmt->execute();
$result = $stmt->get_result();

if ($user = $result->fetch_assoc()) {
    // Estructurar el objeto para la app mapeando los nombres requeridos
    $perfil = [
        "id" => (int)$user['id'],
        "nombre" => $user['nombre'],
        "email" => $user['email'],
        "rol" => $user['rol'],
        "cedula" => $user['cedula_profesional'],
        "especialidad" => $user['especialidad'],
        "precio" => $user['precio_sesion'] !== null ? (float)$user['precio_sesion'] : 0.0,
        "descripcion" => $user['descripcion'],
        "foto_url" => null // Se envía null al no existir en la base de datos
    ];
    
    ob_clean();
    echo json_encode([
        "status" => "success",
        "message" => "Perfil obtenido correctamente",
        "user" => $perfil
    ]);
} else {
    ob_clean();
    echo json_encode([
        "status" => "error",
        "message" => "Usuario no encontrado",
        "user" => null
    ]);
}

$stmt->close();
$conn->close();
exit();
?>