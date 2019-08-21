<?php
  $host ='sittingcafe.com';
  $user='threepig';
  $pw='sungshin95';
  $dbName='threepig';

  $mysqli=new mysqli($host,$user,$pw,$dbName);

  if($mysqli) {
    echo "MySQL connected";
  }
  else {
    echo "failed to connect to MySQL";
  }
?>
