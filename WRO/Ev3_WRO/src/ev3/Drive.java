package ev3;

import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;

/*
 *  Zuständig fürs Fahren und Wenden
 */


/* wahrheisttabele
 * - L M R 
	 * 0 1 2 3
	 * 1|1 2 1 > Rechter Motor
	 * 2|1 2 2 > Linker Motor
	 * 3|1 1 2 > Korektur Linker Motor
	 * 4|2 1 1 > Korektur Rechts Motor
	 * 5|1 1 1 > Panik !!
	 * 6|2 2 2 > X Kreuzung Grade
	 * 7|2 2 1 > T kreuzng Links || Ecke
	 * 8|1 2 2 > T kreuzung Rechts || Ecke
	 * 
* Color ist der Color sensor dabei gilt
* weiß = 1
* schwarz = 2
* rot = 3
* blau = 4
* gelb = 5
* grün = 6 
*/
/**
 * Drive ist für die Fahrt zustämdig
 * @author foeni
 * 
 */
public class Drive {
	
	static Color Color1 = new Color(SensorPort.S1);
	static Color Color2 = new Color(SensorPort.S2);
	static Color Color3 = new Color(SensorPort.S3);
	
	// Motor auswahl
	static final NXTRegulatedMotor MotorLeft = Motor.A; // Linker Motor
	static final NXTRegulatedMotor MotorRight = Motor.D; // Rechter Motor
	
	
	static final float motorStark = 80;		// geschwindigkeit des Motors
	private static final long turnTime = 1; // zeitliche versetzung für Dreheung
	private static final long turnDelay = 500; // scan verzögerung
	 
	private static int sens(int i) {
		
	int sensor = 0;
	if(i == 1){sensor = Color1.color();} // Links
	if(i == 2){sensor = Color2.color();} // Mitte
	if(i == 3){sensor = Color3.color();} // Rechts 

	return sensor;
	}
	
	 public static void panic() {
		 MotorLeft.forward();
		 MotorRight.forward();
		 Delay.msDelay(500);
		 MotorRight.stop();
		 MotorLeft.stop();
		 
		 while(sens(1) == 1){
			 
			 MotorLeft.backward();
			 MotorRight.backward();
			 Delay.msDelay(500);
			 MotorRight.stop();
			 MotorLeft.stop();
		 }
		 
	 }
	 public static void Curve(boolean left, boolean intersection,float pro_stark) {
			 MotorLeft.setSpeed(motorStark*pro_stark);
			 int sens2 = sens(2);
			if(left){
			do {
				 MotorLeft.forward();
				 Delay.msDelay(turnDelay);
				 MotorLeft.stop();
				 System.out.println(MotorLeft.getSpeed() + " " + MotorLeft.getMaxSpeed() );
				 
				 if(sens2 == 2 && !intersection) {
					 
					 Delay.msDelay(turnTime);
					 MotorLeft.stop();
					 break;
				}
				intersection = false;
			 } while(sens2 == 1);
			
			 Sound.beep();
			}
			else {
				do {
					 MotorRight.forward();
					 Delay.msDelay(turnDelay);
					 MotorRight.stop();
					 System.out.println(MotorRight.getSpeed() + " " + MotorRight.getMaxSpeed() );
					 
					 if(sens2 == 2) {
						 Delay.msDelay(turnTime);
						 MotorRight.stop();
						 break;
					}
				 } while(sens2 == 1);
				
				 Sound.beep();
			}
	}

	 public static void straight() {
	
		 int sensL = sens(1); // left
		 int sensM = sens(2); //mtte
		 int sensR = sens(3); // right
		 
		 MotorLeft.setSpeed(motorStark);
		 MotorRight.setSpeed(motorStark);
		 while(sensM == 2) {
			 MotorLeft.forward();
			 MotorRight.forward();
			 sensM = sens(2);
		 }
		 	MotorLeft.stop();
		 	MotorRight.stop();
		 if(sensL == 2) {
			 Curve(true,false,0.9f);
		 }
		 if(sensR == 2){
			 Curve(false,false,0.9f);
		 }
	 }	

}
