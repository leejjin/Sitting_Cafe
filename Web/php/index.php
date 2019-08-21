<?php
 
  $connect=mysqlii_connect("localhost","threepig","sungshin95","threepig");
	//if($connect) echo 'db connected';
  $num=$_GET['num'];
  
    $sql = "INSERT INTO ESP8266 VALUES($num)";
	$result=mysqli_query($connect,$sql);
	mysqli_close($connect);
   ?>
