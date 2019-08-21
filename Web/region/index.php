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

	$siDo=array();
	$gu=array();
	$dong=array();
	$i=0;
	while($r=mysqli_fetch_assoc($selResult)){
	$siDo[i]=$r[siDo];
	$gu[i]=$r[gu];
	$dong[i]=$r[dong];
	$i++;

}


	
	mysqli_close($connect);


 ?>
 