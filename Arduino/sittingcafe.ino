
#include <ESP8266WiFi.h>

// 아두이노에 연결할 wifi 이름과 패스워드 
const char* ssid     = "iPhone";
const char* password = "34567890";

const int enterDetectPin = 4;  // 첫 번째 근접센서 -> 들어오는 사람 감지
const int outDetectPin = 12;   // 두 번째 근접센서 -> 나가는 사람 감지

int enterCount = 0;  // 들어오는 사람 count
int outCount = 0;   // 나가는 사람 count
int total = 100;    // 카페 총 좌석 수

const char* host = "sittingcafe.com";   //홈페이지 호스트
const char* streamId   = "threepig";    //id
const char* privateKey = "sungshin95";  //패스워드

void setup() {

  Serial.begin(115200);
  delay(10);

  // 와이파이 네트워크와 연결 시작
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

int value = 0;
int uid=3400;

void loop() {
  ++value;
  int noEnterDetect = digitalRead(enterDetectPin); // 상태 읽어오기 (들어오는 사람)
  int noOutDetect = digitalRead(outDetectPin); // 상태 읽어오기 (나가는 사람)

 if (!noEnterDetect)
  {
   uid++;
   enterCount++;

   delay(3000);
   Serial.print("enterCount: ");
   Serial.println(enterCount);
   Serial.print("outCount: ");
   Serial.println(outCount);       
  }

// ------------------------------------------------ //


//----------------- 나가는 것 감지 ----------------- //
  else if (!noOutDetect)
  {
  uid++;
  ++outCount;

  if(outCount<0) outCount=0;
    
  delay(3000);
  Serial.print("enterCount: ");
  Serial.println(enterCount);
  Serial.print("outCount: ");
  Serial.println(outCount);   
  }

  int numIn=enterCount;
  int numOut=outCount;
  int cafeID=100; 

  Serial.print("connecting to ");
  Serial.println(host);

  // Use WiFiClient class to create TCP connections
  WiFiClient client;
  const int httpPort = 80;
  
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }

  // We now create a URI for the request 
  String url = "/search/index.php?numIn=";
  url+=numIn;
  url+="&numOut=";
  url+=numOut;
  url+="&cafeID=";
  url+=cafeID;
  url+="&uid=";
  url+=uid;
  Serial.print("Requesting URL: ");
  Serial.println(url);

  // This will send the request to the server
  client.print(String("GET ") + url + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" +
               "Connection: close\r\n\r\n");
  unsigned long timeout = millis();
  while (client.available() == 0) {
    if (millis() - timeout > 5000) {
      Serial.println(">>> Client Timeout !");
      client.stop();
      return;
    }
  }
  Serial.println("closing connection");
}
