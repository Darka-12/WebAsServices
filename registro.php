<?php
header('Content-Type: application/json');

$conexion = new mysqli("localhost", "root", "", "psiconova");

if ($conexion->connect_error) {
    echo json_encode(["status" => "error", "message" => "Error de conexión con la base de datos"]);
    exit();
}

$nombre = $_POST['nombre'] ?? '';
$email = $_POST['email'] ?? '';
$password = $_POST['password'] ?? '';
$rol = $_POST['rol'] ?? 'cliente'; // 'cliente' o 'psicologo'

// Campos adicionales opcionales si se registra como psicólogo
$cedula = $_POST['cedula_profesional'] ?? null;
$especialidad = $_POST['especialidad'] ?? null;
$precio = $_POST['precio_sesion'] ?? null;

if (empty($nombre) || empty($email) || empty($password)) {
    echo json_encode(["status" => "error", "message" => "Por favor llena los campos obligatorios"]);
    exit();
}

// Verificar si el correo ya existe
$checkEmail = $conexion->prepare("SELECT id FROM usuarios WHERE email = ?");
$checkEmail->bind_param("s", $email);
$checkEmail->execute();
if ($checkEmail->get_result()->num_rows > 0) {
    echo json_encode(["status" => "error", "message" => "El correo electrónico ya está registrado"]);
    exit();
}

// Insertar nuevo usuario
$stmt = $conexion->prepare("INSERT INTO usuarios (nombre, email, password, rol, cedula_profesional, especialidad, precio_sesion) VALUES (?, ?, ?, ?, ?, ?, ?)");
$stmt->bind_param("ssssssd", $nombre, $email, $password, $rol, $cedula, $especialidad, $precio);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Usuario registrado correctamente"]);
} else {
    echo json_encode(["status" => "error", "message" => "Error al registrar el usuario"]);
}

$stmt->close();
$conexion->close();
?>