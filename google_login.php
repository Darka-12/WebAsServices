<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");

// 1. Obtener los datos enviados por Retrofit / Android
$data = json_decode(file_get_contents("php://input"), true);
$idToken = $data['id_token'] ?? $_POST['id_token'] ?? null;

if (!$idToken) {
    echo json_encode([
        "status" => "error",
        "message" => "No se recibió el idToken en el servidor PHP"
    ]);
    exit;
}

// 2. Verificar el token directamente con los servidores de Google
$googleUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" . urlencode($idToken);
$response = file_get_contents($googleUrl);

if ($response === FALSE) {
    echo json_encode([
        "status" => "error",
        "message" => "Token inválido o expirado"
    ]);
    exit;
}

$payload = json_decode($response, true);

// 3. Validar que el token pertenece a tu Web Client ID (cliente-A)
$webClientId = "334805993407-iq5kvm6dc3nmqmmrhuig333fkfu0lde8.apps.googleusercontent.com";

if (isset($payload['aud']) && $payload['aud'] === $webClientId) {
    $email = $payload['email'];
    $nombre = $payload['name'] ?? '';

    // AQUÍ REALIZAS LA LÓGICA DE TU BASE DE DATOS:
    // - Buscar si el correo existe en la BD.
    // - Si no existe, registrarlo.
    // - Retornar el rol del usuario.

    echo json_encode([
        "status" => "success",
        "message" => "Autenticación exitosa con Google",
        "usuario" => [
            "email" => $email,
            "nombre" => $nombre,
            "rol" => "cliente" // O el rol extraído de tu base de datos
        ]
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "El token no fue emitido para este Web Client ID"
    ]);
}

?>