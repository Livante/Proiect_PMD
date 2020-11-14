#include <Keypad.h>

int red_light_pin= 11;
int green_light_pin = 10;
int blue_light_pin = 9;

const byte ROWS = 4; //four rows
const byte COLS = 3; //three columns
int keys[ROWS][COLS] = {
  {1,2,3},
  {4,5,6},
  {7,8,9},
  {10,0,10}
};

byte rowPins[ROWS] = {2, 3, 4, 5}; //connect to the row pinouts of the keypad
byte colPins[COLS] = {6, 7, 8}; //connect to the column pinouts of the keypad


int password=0;

Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );

void setup(){
  Serial.begin(9600);
  pinMode(red_light_pin, OUTPUT);
  pinMode(green_light_pin, OUTPUT);
  pinMode(blue_light_pin, OUTPUT);
}

void loop(){
  init();
  int key = keypad.getKey();
  if (key){
    Serial.println(key);       
    if(password<1000){
      RGB_color(0,0,255);
    }else
    if(password<100){
      RGB_color(0,125,125);
    }else
    if(password<10){
      RGB_color(125,125,125);
    }
    password=password*10+key;
    if(password>999 && password<10000){
      if(password==1111){
        RGB_color(0,255,0);
      }
      else{
        RGB_color(255,0,0);
      }
      delay(500);
      init();
    }else{
      RGB_color(255,0,0);
      delay(500);
      init();
    }
  }
}
  void RGB_color(int red_light_value, int green_light_value, int blue_light_value)
 {
  analogWrite(red_light_pin, red_light_value);
  analogWrite(green_light_pin, green_light_value);
  analogWrite(blue_light_pin, blue_light_value);
}
  
  void init(){
    password=0;
    Serial.print("INIT");
  }

