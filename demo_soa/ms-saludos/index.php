<?php
// ms-saludos/index.php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *"); // Permitir al Frontend consultarlo

// 1. Llamamos al microservicio de usuarios por red
$url_usuarios = "http://localhost:8001";
$respuesta_cruda = @file_get_contents($url_usuarios);

if ($respuesta_cruda === FALSE) {
    http_response_code(500);
    echo json_encode(["mensaje" => "Error: El servicio de usuarios está caído."]);
    exit;
}

// 2. Procesamos el JSON que nos dio el otro servicio
$datos = json_decode($respuesta_cruda, true);
$nombre = $datos['data']['nombre'];

// 3. Devolvemos nuestro propio resultado
echo json_encode(["mensaje" => "¡Hola, $nombre! Este es tu primer microservicio."]);
?>