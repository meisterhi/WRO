package ev3;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class Color3 {

	
	public static int color() {
		/* 
		 * weiß = 1
		 * schwarz = 2
		 * rot = 3
		 * blau = 4
		 * gelb = 5
		 * grün = 6 
		 *
		 * Default werte | | mx = max | mn = min
		  
		final int[] white_mx = {21,21,21};
		final int[] white_mn = {19,19,19};
		
		final int[] black_mx = {1,1,1};
		final int[] black_mn = {0,0,0};
		
		final int[] red_mx = {20,4,3};
		final int[] red_mn = {13,4,3};
		
		final int[] blue_mx = {1,4,11};
		final int[] blue_mn = {0,4,7};
		
		final int[] yellow_mx = {10,6,3};
		final int[] yellow_mn = {9,5,1};
		
		final int[] green_mx = {1,3,2};
		final int[] green_mn = {0,2,1}; 
		 
		 */
		
		final float[] white_mx = {29,27,33};
		final float[] white_mn = {29,22,29};
		
		final float[] black_mx = {(float) 1.5,(float) 1.5,(float) 1.5};
		final float[] black_mn = {0,0,0};
		
		final float[] red_mx = {15,4,4};
		final float[] red_mn = {10,0,0};
		
		final float[] blue_mx = {3,5,17};
		final float[] blue_mn = {0,0,9};
		
		final float[] yellow_mx = {18,11,5};
		final float[] yellow_mn = {14,7,1};
		
		final float[] green_mx = {3,7,4};
		final float[] green_mn = {1,5,2};
		
		//---------------------------------------------------------//
		
		final EV3 ev3 = (EV3) BrickFinder.getLocal();	
		TextLCD lcd = ev3.getTextLCD();
		int color_r = 1;

		// Color sensor
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
		SensorMode color = colorSensor.getRGBMode();
		float[] colorSample = new float[color.sampleSize()];
		lcd.drawInt(colorSample.length, 0, 2);	
	
		color.fetchSample(colorSample, 0);
//		lcd.drawString(""+colorSample[0]*100,0,0);
//		lcd.drawString(""+colorSample[1]*100,0,1);
//		lcd.drawString(""+colorSample[2]*100,0,2);
		
		/* 
		 * berechnet aus den Messwerten die Farbe. es bei gilt
		 * weiß = 1
		 * schwarz = 2
		 * rot  = 3
		 * blau = 4
		 * gelb = 5
		 * grün = 6 
		 */
		
		 if(selector(colorSample,white_mn,white_mx))		{color_r = 1;}
		 else if(selector(colorSample,black_mn,black_mx)) 	{color_r = 2;}
		 else if(selector(colorSample,red_mn,red_mx)) 		{color_r = 3;}
		 else if(selector(colorSample,blue_mn,blue_mx)) 	{color_r = 4;}
		 else if(selector(colorSample,yellow_mn,yellow_mx))	{color_r = 5;}
		 else if(selector(colorSample,green_mn,green_mx)) 	{color_r = 6;}

		lcd.drawString("3:"+color_r,0,20);
		colorSensor.close();
		return color_r;
	}
		private static boolean selector(float[] scan, float[] min, float[] max) {
			boolean red = false;
			boolean green = false;
			boolean blue = false;
			boolean color_r = false;
			
			if(scan[0]*100 <= max[0] && scan[0]*100 >= min[0]) { red = true;}
			if(scan[1]*100 <= max[1] && scan[1]*100 >= min[1]) { green = true;}
			if(scan[2]*100 <= max[2] && scan[2]*100 >= min[2]) { blue = true;}
			
			if(red && green && blue) { color_r = true;}
			else { color_r = false;}
			
			return color_r;
		} 
}
