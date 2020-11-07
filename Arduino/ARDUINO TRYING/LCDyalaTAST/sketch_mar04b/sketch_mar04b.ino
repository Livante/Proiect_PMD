#include <SoftwareSerial.h>

SoftwareSerial blue(19,18); /*Rx Tx */


int led=10;
int infra=7;
int c;
void setup() {
  pinMode(led,OUTPUT);
  Serial.begin(9600);
  blue.begin(9600);
  
  pinMode(infra,INPUT);
}

void loop() {

  
  if (Serial.available()){
    blue.write(Serial.read());
   
  }

 /*
  if (blue.available()){
    c = blue.read();
    Serial.write(c);
    if(c == 48){ digitalWrite(led,HIGH);}
    if(c == 49) {digitalWrite(led,LOW); }
  }
  */
  
  if (blue.available()){
    c = blue.read();
    Serial.write(c);
    if(infra==HIGH){ Serial.write("hotul");}
    if(infra==LOW) {Serial.write("t's okay"); }
  }
    
}

