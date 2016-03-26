const int pingPin = 11;
unsigned int duration, inches;
unsigned int measure1, measure2, measure3;
unsigned int distanceReading;
unsigned int counter;
const int threshHold = 67;
const int outputPin = 8;

void setup() {
  Serial.begin(9600);
  pinMode(outputPin, OUTPUT);
  counter = 0;
}
void loop() {
  pinMode(pingPin, OUTPUT);          // Set pin to OUTPUT
  digitalWrite(pingPin, LOW);        // Ensure pin is low
  delayMicroseconds(2);
  digitalWrite(pingPin, HIGH);       // Start ranging
  delayMicroseconds(5);              // with 5 microsecond burst
  digitalWrite(pingPin, LOW);        // End ranging
  pinMode(pingPin, INPUT);           // Set pin to INPUT
  duration = pulseIn(pingPin, HIGH); // Read echo pulse
  distanceReading = duration / 74 / 2; // Convert to inches
  if (counter == 0) {
    measure1 = inches;
    measure2 = inches;
    measure3 = inches;
    inches = distanceReading;
  } else {
    if (abs(distanceReading - inches) < 4) {
      measure3 = measure2;
      measure2 = measure1;
      measure1 = inches;
      inches = distanceReading;            
    }
  } 
 
  Serial.println(inches);            // Display result
 
  if(inches <= threshHold){
    digitalWrite(outputPin,HIGH);  
  }else{
    digitalWrite(outputPin,LOW);
    }
  delay(100);                     // Short delay
}
