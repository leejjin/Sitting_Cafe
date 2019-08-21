<?php

header("Content-Type: text/html; charset=UTF-8"); 

$db_name="threepig";
//parse_str( html_entity_decode( $_SERVER['QUERY_STRING']) , $out);


  $connect=mysqli_connect("localhost","threepig","sungshin95",$db_name);
  if (mysqli_connect_errno()){

echo"error:".mysqli_connect_error();

exit;

}


	//if($connect) echo 'db connected';
  $numIn=$_GET["numIn"];
  $numOut=$_GET["numOut"];
  $cafeID=$_GET["cafeID"];
//echo "DB:\"$db_name\" connected. numIn is $numIn numOut is $numOut cafeID is $cafeID";

	$sql="INSERT INTO ESPtable(numIn,numOut,cafeID) VALUES($numIn,$numOut,$cafeID)";
	
	$result=mysqli_query($connect,$sql);
	
	$sel="select * from region";
	
	$selResult=mysqli_query($connect,$sel);



 ?>





<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>앉는 카페</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="homeStyle.css">
</head>

<body>

	<section class="row center">
		<br> <br> 
		<div class="col-2 col-s-2">　</div>
		<img id="mainLogo" class="col-8 col-s-8" src="Logo.png"> 
		<br><br>
		<form class="form-inline" action="/search/index.php" method="post">

			<input type="text" class="form-control" name="name" id="search"
				placeholder="대학 명을 입력하세요. (예: ‘성신’ or ‘성신여자대학교’)">
			<input type="image" id="button" src="search.svg">
		</form>

	</section>
</body>
</html>