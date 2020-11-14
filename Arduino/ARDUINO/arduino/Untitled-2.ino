#include <Keypad.h>

int red_light_pin= 11;
int green_light_pin = 10;
int blue_light_pin = 9;

const byte ROWS = 4; //four rows
const byte COLS = 3; //three columns
char keys[ROWS][COLS] = {
  {'1','2','3'},
  {'4','5','6'},
  {'7','8','9'},
  {'*','0','#'}
};

byte rowPins[ROWS] = {2, 3, 4, 5}; //connect to the row pinouts of the keypad
byte colPins[COLS] = {6, 7, 8}; //connect to the column pinouts of the keypad


int password=0;
int keyNum;
Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );

void setup(){
  Serial.begin(9600);
  pinMode(red_light_pin, OUTPUT);
  pinMode(green_light_pin, OUTPUT);
  pinMode(blue_light_pin, OUTPUT);
}

void loop(){
  char key = keypad.getKey();
  if(key){
    Serial.println(keyNum);
    keyNum = convertToInt(key);
    password=password*10+keyNum;
    if(password<1000){
      RGB_color(0,0,255);
    }else
    if(password<100){
      RGB_color(0,125,125);
    }else
    if(password<10){
      RGB_color(125,125,125);
    }
    
    if(password>999 && password<10000){
      if(password==1111){
        RGB_color(0,255,0);
      }
      else{
        RGB_color(255,0,0);
      }
      delay(500);
      initNum();
    }else if(password>=10000){
      RGB_color(255,0,0);
      delay(500);
      initNum();
    }
  }
}
  void RGB_color(int red_light_value, int green_light_value, int blue_light_value)
 {
  analogWrite(red_light_pin, red_light_value);
  analogWrite(green_light_pin, green_light_value);
  analogWrite(blue_light_pin, blue_light_value);
}
  
  void initNum(){
   password=0;
   Serial.print("INIT");
  }

  int convertToInt(char key){
    Serial.print("MATA");

    if(key=='0'){
      return 0;
    }
    if(key=='1'){
      return 1;
    }
    if(key=='2'){
      return 2;
    }
    if(key=='3'){
      return 3;
    }
    if(key=='4'){
      return 4;
    }
    if(key=='5'){
      return 5;
    }
    if(key=='6'){
      return 6;
    }
    if(key=='7'){
      return 7;
    }
    if(key=='8'){
      return 8;
    }
    if(key=='9'){
      return 9;
    }
    return 11;  
  }