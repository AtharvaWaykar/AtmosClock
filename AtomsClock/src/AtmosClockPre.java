
import java.awt.Color;
import java.io.IOException;

public class AtmosClockPre  {

	private static Color [] level = null;
	private static double [][] data = null;
	private static Color[] raining = null;

	public static void atmos(String city) throws IOException {



		level = new Color[48];
		raining = new Color[48];




		data = AtmosClockData.getData(city);


		double [] pointSystem = new double[48];

		for (int x = 0; x < 48; x++) {

			pointSystem[x] = (data[0][x] + humid(data[1][x]) + data [2][x]);

			if (data[3][x] > .30) {
				raining[x] = Color.MAGENTA;
			} else if(data[3][x] > .10) {
				raining[x] = Color.BLUE;
			} else if(data[3][x] > 0.0) {
				raining[x] = Color.CYAN;
			} else if (data[3][x] == 0.0) {
				raining[x] = Color.GRAY;
			}
		}

		for (int x = 0; x < 48; x++) {

			if (pointSystem[x] < 30) {
				level[x] = Color.MAGENTA;
			}

			else if ((pointSystem[x] >= 30) && (pointSystem[x] < 50)) {
				level[x] = Color.BLUE;
			}

			else if ((pointSystem[x] >= 50) && (pointSystem[x] < 65)) {
				level[x] = Color.CYAN;
			}

			else if ((pointSystem[x] >= 65) && (pointSystem[x] < 75)) {
				level[x] = Color.YELLOW;
			}

			else if ((pointSystem[x] >= 75) && (pointSystem[x] < 85)) {
				level[x] = Color.ORANGE;
			}

			else if (pointSystem[x] >= 85) {
				level[x] = Color.RED;
			}

		}
		//	testing	
		//		for (int j = 0; j < 48; j++) {
		//			System.out.print(j +level[j]+ " ");
		//			System.out.print( getRaining(j)+ "1 ");
		//		}



	}

	public static double humid(double humidity) {

		double humidityAdjusted = 0;


		humidityAdjusted = (((humidity/100) - .50) * 10);


		return humidityAdjusted;
	}


	public static Color getColor(int n) {
		return level[n];
	}

	public static Color getRaining(int index) {

		return raining[index];
	}

	public static void update() {

	}




}
