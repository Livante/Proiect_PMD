#include <Keypad.h>
#include <LiquidCrystal.h> 
//tastatura
const byte ROWS = 4; //four rows
const byte COLS = 3; //three columns
char keys[ROWS][COLS] = {
  {'1','2','3'},
  {'4','5','6'},
  {'7','8','9'},
  {'*','0','#'}
};

byte rowPins[ROWS] = {5, 4, 3, 2}; //connect to the row pinouts of the keypad
byte colPins[COLS] = {8, 7, 6}; //connect to the column pinouts of the keypad

Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );

const byte passwordSize=4;
int i=-1;
char password[passwordSize]={0, 0, 0, 0};

//Yala

int relay2=13;

//LCD
int Contrast=150;
LiquidCrystal lcd(22, 23, 24, 25, 26, 27);  



void setup(){
  Serial.begin(9600);
  
  //Yala
  pinMode(relay2,OUTPUT);
  
  //LCD
      analogWrite(A0,Contrast);
     lcd.begin(16, 2);
}
  
void loop(){
  char key = keypad.getKey();
  digitalWrite(relay2,HIGH);
  if (key){
    Serial.println(key);
    i=i+1;
    password[i]=key;
    if(i==3){
      for(int j=0; j<passwordSize;j++){
        Serial.print(password[j]);
        if(password[0]=='1' && password[1]=='1' && password[2]=='1' && password[3]=='1'){
          digitalWrite(relay2,LOW);
          lcd.setCursor(0, 0);
          lcd.print("ACCESS GRANTED");
          delay(3000);
          
          digitalWrite(relay2,HIGH);
          delay(3000); 
           password[0]='6';
        }
        else
        {  
          lcd.setCursor(0, 1);
          lcd.print("ACCESS DENIED");
          delay(2000);
          
        }
      }
      Serial.println();
      i=-1;
    }
  }
  
}


