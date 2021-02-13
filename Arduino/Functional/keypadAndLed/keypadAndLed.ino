#include <Keypad.h>
#include <LiquidCrystal_I2C.h>
#include <Wire.h> 

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

byte rowPins[ROWS] = {22, 23, 24, 25}; //connect to the row pinouts of the keypad
byte colPins[COLS] = {26, 27, 28}; //connect to the column pinouts of the keypad


int password=0;
int keyNum;
Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );

LiquidCrystal_I2C lcd(0x27, 16, 2); //LCD

byte incomingByte=0;

// YALA
int relay2=13;
int javaRead=0;
void setup(){
  Serial.begin(9600);
  pinMode(red_light_pin, OUTPUT);
  pinMode(green_light_pin, OUTPUT);
  pinMode(blue_light_pin, OUTPUT);
  pinMode(relay2,OUTPUT);
  lcd.begin();

	// Turn on the blacklight and print a message.
	lcd.backlight();
  lcd.print("Enter a code:");
  incomingByte= Serial.read();
}

void loop(){
  char key = keypad.getKey();
  digitalWrite(relay2,HIGH);
  if(key){
    keyNum = convertToInt(key);
    
    password=password*10+keyNum;
//    Serial.print("PASSWORD: ");
    Serial.println(password);
    if(password>999 && password<10000 && javaRead==0){
      incomingByte= Serial.read();
      javaRead=javaRead+1;
    }

    if(password>999 && password<10000 && javaRead==1){
      incomingByte= Serial.read();
      if(incomingByte==1){
        lcd.setCursor(0,1);
        lcd.print("****");
        RGB_color(0,255,0);
        lcd.begin();
        lcd.backlight();
        lcd.setCursor(0,0);
//        lcd.print("ACCESS GRANTED!");
        digitalWrite(relay2,LOW);
        lcd.print(incomingByte);
        delay(3000);
        digitalWrite(relay2,HIGH);
        initNum();
      }
      else{
        RGB_color(255,0,0);
        lcd.begin();
        lcd.backlight();
        lcd.setCursor(0,0);
//        lcd.print("ACCESS DENIED!");
        lcd.print(incomingByte);
        delay(500);
        initNum();  
      }
    }
    else if(password>=10000){
       RGB_color(255,0,0);
       delay(500);
       lcd.begin();
       lcd.backlight();
       lcd.setCursor(0,0);
       lcd.print("ACCESS DENIED!");
       initNum();
     }
    

    if(password<10){
      lcd.setCursor(0,1); 
      lcd.print("*");
      RGB_color(125,125,125);
    }else
    if(password<100){
      lcd.setCursor(0,1);
      lcd.print("**");
      RGB_color(0,125,125);
    }else
    if(password<1000){
      lcd.setCursor(0,1);
      lcd.print("***");
      RGB_color(0,0,255);
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
//   Serial.print("INIT");
   lcd.begin();
    lcd.backlight();
    lcd.setCursor(0,0);
    lcd.print("ENTER THE CODE!");
    lcd.setCursor(0,1);
  }

  int convertToInt(char key){
    if(key=='*'){
     return 10; 
    }
    if(key=='#'){
      initNum();
      return 0;
    }
    return char(key)-48;  
  }
