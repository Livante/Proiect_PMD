#include <SoftwareSerial.h>

SoftwareSerial bluetooth(19,18); /*Rx Tx */

int led=10;
int c;
void setup() {
//  pinMode(led,OUTPUT);
//  Serial.begin(9600);
//  blue.begin(9600);
Serial.begin(9600);
  //while (!Serial) ; // wait for serial port to connect. Needed for native USB
  Serial.println("--START--");
  
  bluetooth.begin(115200); // The Bluetooth Mate defaults to115200bps
  bluetooth.print("A"); // Print three times individually
  bluetooth.print("$");
  bluetooth.print("$"); // Enter command mode
  delay(100);
 bluetooth.println("U,9600,N"); // Temporarily Change the baudrate to 9600, no parity
  // 115200 can be too fast at times for NewSoftSerial to relay the data reliably
  bluetooth.begin(9600); // Start bluetooth serial at 9600

}

void loop() {

  
  if (Serial.available()){
    bluetooth.write(Serial.read());
   
  }

  if (bluetooth.available()){
    c = bluetooth.read();
    Serial.write(c);
    if(c == 48){ digitalWrite(led,HIGH);}
    if(c == 49) {digitalWrite(led,LOW); }
  }
}
