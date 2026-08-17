<?php
header('Content-Type: application/json');

$conexion = new mysqli("localhost", "root", "", "psiconova");

if ($conexion->connect_error) {
    echo json_encode(["status" => "error", "message" => "Error de conexión"]);
    exit();
}

$id_usuario = $_POST['id_usuario'] ?? '';
$accion = $_POST['accion'] ?? ''; // 'aprobar' o 'rechazar'

if (empty($id_usuario) || empty($accion)) {
    echo json_encode(["status" => "error", "message" => "Datos incompletos"]);
    exit();
}

$estado = ($accion === 'aprobar') ? 1 : 0;

// Actualizar el estado de aprobación en la tabla usuarios
$stmt = $conexion->prepare("UPDATE usuarios SET aprobado = ? WHERE id = ?");
$stmt->bind_param("ii", $estado, $id_usuario);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Estado actualizado correctamente"]);
} else {
    echo json_encode(["status" => "error", "message" => "Error al actualizar el estado"]);
}

$stmt->close();
$conexion->close();
?>