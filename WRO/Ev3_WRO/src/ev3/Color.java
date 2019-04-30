package ev3;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class Color {
		
		private final float[] whiteMax = {29,27,33};
		private final float[] whiteMin = {29,22,29};
		
		private final float[] blackMax = {(float) 1.5,(float) 1.5,(float) 1.5};
		private final float[] blackMin = {0,0,0};
		
		private final float[] redMax = {15,4,4};
		private final float[] redMin = {10,0,0};
		
		private final float[] blueMax = {3,5,17};
		private final float[] blueMin = {0,0,9};
		
		private  final float[] yellowMax = {18,11,5};
		private  final float[] yellowMin = {14,7,1};
		
		private  final float[] greenMax = {3,7,4};
		private  final float[] greenMin = {1,5,2};
		
		/* 
		 * mx -> max || mn -> min
		 * weiß = 1
		 * schwarz = 2
		 * rot = 3
		 * blau = 4
		 * gelb = 5
		 * grün = 6 
		 *
		 */
		
		private int test2;
		//---------------------------------------------------------//

		private int colorResult = 0;
		public EV3ColorSensor colorSensor;
		SensorMode color;
		float[] colorSample;
		TextLCD lcd;
		
		Color(Port s1){
		System.out.println("const");
		final EV3 ev3 = (EV3) BrickFinder.getLocal();	
		lcd = ev3.getTextLCD();
		
		// Color sensor
		colorSensor = new EV3ColorSensor(s1); // SensorPort.S1
		color = colorSensor.getRGBMode();
		colorSample = new float[color.sampleSize()];

		color.fetchSample(colorSample, 0);
		lcd.drawString(""+colorSample[0]*100,0,2);
		lcd.drawString(""+colorSample[1]*100,0,3);
		lcd.drawString(""+colorSample[2]*100,0,4);
		}
		
		public void colortset() {
			
	
			color.fetchSample(colorSample, 0);
			lcd.drawString(""+colorSample[0]*100,0,2);
			lcd.drawString(""+colorSample[1]*100,0,3);
			lcd.drawString(""+colorSample[2]*100,0,4);
			
		

		}
	
	public int color() {
		color.fetchSample(colorSample, 0);
		 if(selector(colorSample,whiteMin,whiteMax))		{colorResult = 1;}
		 else if(selector(colorSample,blackMin,blackMax)) 	{colorResult = 2;}
		 else if(selector(colorSample,redMin,redMax)) 		{colorResult = 3;}
		 else if(selector(colorSample,blueMin,blueMax)) 	{colorResult = 4;}
		 else if(selector(colorSample,yellowMin,yellowMax))	{colorResult = 5;}
		 else if(selector(colorSample,greenMin,greenMax)) 	{colorResult = 6;}
		return colorResult;
		}
	
	private boolean selector(float[] scan, float[] min, float[] max) {
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
			
			/* 
			 * weiß = 1
			 * schwarz = 2
			 * rot = 3
			 * blau = 4
			 * gelb = 5
			 * grün = 6 
			 *
			 */
		} 
}
