package ev3;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.SensorPort;

public class WroEv3 {
/*
 * A	Links   |	1 Links		| 
 * B	Arm		|	2 Mitte		|
 * C			|	3 Rechts	|
 * D	Rechts	|	4 Seite		|
*/
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		final EV3 ev3 = (EV3) BrickFinder.getLocal();

		TextLCD lcd = ev3.getTextLCD();
		Color color1 = new Color(SensorPort.S1);
		Color color2 = new Color(SensorPort.S2);
		Color color3 = new Color(SensorPort.S3);
		
		while(true) {
			Drive.Curve(true,1);
			
		}
		
		//color1.colorSensor.close();
		//color2.colorSensor.close();
		//color3.colorSensor.close();
		/*while(true) {
			Color1.color();
			color.colortset();
			//Drive.straight();
			Delay.msDelay(2*1000);
		}*/
		
	}
}
