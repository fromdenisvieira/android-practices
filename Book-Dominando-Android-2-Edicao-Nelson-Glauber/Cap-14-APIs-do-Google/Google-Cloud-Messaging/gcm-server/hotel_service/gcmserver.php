<?php

$acao = $_POST['acao'];
$servername = "localhost";
$username = "root"
$password = "password"

$conn = newsqli($servername, $username, $password);
if($conn -> connect_error){
  die("Connection failed: ".$conn->connect_error);
}
$sql = "CREATE DATABASE IF NOT EXISTS hotel_db";
if(!$conn->query($sql) === TRUE){
  echo "Erro ao criar banco de dados: ".$conn->error;
}

$sql = "CREATE TABLE IF NOT EXISTS hotel_db.Dispositivo(registration_id VARCHAR(200) PRIMARY KEY)";
if($conn->query($sql) === FALSE){
  echo "Erro ao criar tabela: ".$conn->error;
}

if($acao == "registrar"){
  $stmt = $conn->prepare("INSERT INTO hotel_db.Dispositivo(registration_id) VALUES (?)");
  $json = json_decode(file_get_contents('php://input'));
  $registrationId = $_POST['regId'];
  $stmt->execute();
  $stmt->close();
}else if($acao == "enviar"){
  $jsonArray = array();
  $sql = "SELECT registration_id FROM hotel_db.Dispositivo";
  $result = $conn->query($sql);
  if ($result && $result->num_rows > 0){
    while($row = $result->fetch_assoc()){
      $jsonArray[] = $row["registration_id"];
    }
  }
  $mensagem = $_POST["mensagem"];
  $url = "https://gcm-http.googleapis.com/gcm/send";
  $apiKey = "SUA_API_KEY_AQUI";
  $ch = curl_init($url);
  $jsonData = array(
    "registration_ids" => $jsonArray,
    "data" => array(
      "mensagem" => $mensagem
    )
  );
  $jsonDataEncoded = json_encode($jsonData);
  curl.setopt($ch,CURLOPT_POST,1);
  curl.setopt($ch,CURLOPT_POSTFIELDS,$jsonDataEncoded);
  curl.setopt($ch,CURLOPT_HTTPHEADER),array.("Content-type: application/json","Authorization: key". $apikey));

  $result= curl_exec($ch);
}
$conn->close();
 ?>
