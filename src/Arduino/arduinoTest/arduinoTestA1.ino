#include <Keypad.h>
#include <LiquidCrystal_I2C.h>
#include <Wire.h> 

#define NUMAR_SALA 1

int red_light_pin = 11;
int green_light_pin = 10;
int blue_light_pin = 9;

const byte ROWS = 4; //four rows
const byte COLS = 3; //three columns
char keys[ROWS][COLS] = { { '1', '2', '3' }, { '4', '5', '6' },
		{ '7', '8', '9' }, { '*', '0', '#' } };

byte rowPins[ROWS] = { 22, 23, 24, 25 }; //connect to the row pinouts of the keypad
byte colPins[COLS] = { 26, 27, 28 }; //connect to the column pinouts of the keypad

long password = NUMAR_SALA;
int keyNum;
Keypad keypad = Keypad(makeKeymap(keys), rowPins, colPins, ROWS, COLS);

LiquidCrystal_I2C lcd(0x27, 16, 2); //LCD

byte incomingByte = 0;

// YALA
int relay2 = 13;

void setup() {
	Serial.begin(9600);
	pinMode(red_light_pin, OUTPUT);
	pinMode(green_light_pin, OUTPUT);
	pinMode(blue_light_pin, OUTPUT);
	pinMode(relay2, OUTPUT);
	lcd.begin();

	lcd.backlight();
	lcd.print("Enter a code:");
	incomingByte = Serial.read();
}

void loop() {
	char key = keypad.getKey();
	digitalWrite(relay2, HIGH);
	if (key) {
		if (password > 9999 && password < 100000) {
			incomingByte = Serial.read();
			if (incomingByte == 1) {
                                controlYala(1);
				initNum();
			} else if (incomingByte == 2) {
				controlYala(2);
                                initNum();
  			} 
		} else if (password >= 100000) {
			controlYala(3);
                        initNum();
		}
		setLedState();
                keyNum = convertToInt(key);
         if(key!='#')
		password = password * 10 + keyNum;
		Serial.println(password);		
	}
}
void RGB_color(int red_light_value, int green_light_value,
		int blue_light_value) {
	analogWrite(red_light_pin, red_light_value);
	analogWrite(green_light_pin, green_light_value);
	analogWrite(blue_light_pin, blue_light_value);
}

void initNum() {
	password = NUMAR_SALA;
	lcd.begin();
	lcd.backlight();
	lcd.setCursor(0, 0);
	lcd.print("ENTER THE CODE!");
	lcd.setCursor(0, 1);
}

int convertToInt(char key) {
	if (key == '*') {
		return 10;
	}
	if (key == '#') {
		initNum();
		return 0;
	}
	return char(key) - 48;
}

void setLedState(){
	if (password < 100) {
		lcd.setCursor(0, 1);
		lcd.print("*");
		RGB_color(125, 125, 125);
	} else if (password < 1000) {
		lcd.setCursor(0, 1);
		lcd.print("**");
		RGB_color(0, 125, 125);
	} else if (password < 10000) {
		lcd.setCursor(0, 1);
		lcd.print("***");
		RGB_color(0, 0, 255);
	}
}

void controlYala(int grantedOrDenied){
	if(grantedOrDenied==1){
		RGB_color(0, 255, 0);
		lcd.begin();
		lcd.backlight();
		lcd.setCursor(0, 0);
		lcd.print("ACCESS GRANTED!");
		digitalWrite(relay2, LOW);
		lcd.print(incomingByte);
		delay(3000);
		digitalWrite(relay2, HIGH);
	}
	else if(grantedOrDenied==2){
		RGB_color(255, 0, 0);
		lcd.begin();
		lcd.backlight();
		lcd.setCursor(0, 0);
		lcd.print("ACCESS DENIED!");
		digitalWrite(relay2, HIGH);
		lcd.print(incomingByte);
		delay(3000);
		digitalWrite(relay2, HIGH);
	}
	else if(grantedOrDenied=3){
		RGB_color(255, 0, 0);
		delay(500);
		lcd.begin();
		lcd.backlight();
		lcd.setCursor(0, 0);
		lcd.print("ACCESS DENIED!");
		initNum();
	}
}
