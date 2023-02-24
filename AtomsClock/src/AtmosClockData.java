import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;


public class AtmosClockData {
	static double finalData[][] = new double [4][];
	static double [] temp = new double [48];
	static double [] hum = new double [48];
	static double [] sun = new double [48];
	static double [] per = new double [48];
	static String[] zone = new String [1];
	static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
	public static double [][] getData(String city) {

		try {
			
			String apiKey = "18641e7bfc3d4e78a03195018230402";

			String urlString = "http://api.weatherapi.com/v1/forecast.json?key=" + apiKey + "&q="+ city + "&days=2&aqi=no&alerts=no";

			URL url = new URL(urlString);


			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputLine;
			StringBuilder response = new StringBuilder();
			String responseHelp = "";

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			Calendar now =  Calendar.getInstance();
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.HOUR_OF_DAY, 0);
			for(int aa = 0 ; aa < response.length()-10; aa++) {

				//gets timezone
				if(response.substring(aa, aa+9).equals("\"tz_id\":\"")) {
					for(int bb =aa+9 ; bb<response.length(); bb++) {
						if(response.substring(bb, bb+1).equals(",")) {
							ClockGui.timeZone = TimeZone.getTimeZone(response.substring(aa+9,bb -1).toString());
							ClockGui.zone = ZoneId.of(response.substring(aa+9,bb -1).toString());
							break;
						}
						
					}
					break;
				}
				
			}
			for(int t = 0; t < 2; t++) {
				
				for (int a = 0 + (t * 24); a < 24 + (t * 24); a++) {
					for(int i = 0; i <response.length() ;i++) {
						if((response.substring(i, i+ 5).equals(sdf.format(now.getTime())))){	
							setDay(i,response, a);
							
							responseHelp = response.substring(i);
							break;
						}

					}
					now.add(Calendar.HOUR_OF_DAY, +1);
					response.setLength(0);
					response.append(responseHelp);

				}
				
			}

			finalData[0] = temp;
			finalData[1] = hum;
			finalData[2] = sun;
			finalData[3] = per;
						
//			testing
//			for(int i = 0; i < 4; i++) {
//				for (int j = 0; j < 48; j++) {
//					System.out.print(finalData[i][j]+ " ");
//				}
//				System.out.println(finalData[i].length);
//				System.out.println();
//				
			
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return finalData;
	}

	private static void setDay(int i, StringBuilder response, int index ) {
		for(int j= i ; j<response.length(); j++) {

			//gets precipitation amount
			if(response.substring(j, j+12).equals("\"precip_in\":")) {
				for(int k =j+12 ; k<response.length(); k++) {
					if(response.substring(k, k+1).equals(",")) {
						per[index] = Double.parseDouble(response.substring(j+12,k)) ;
						break;
					}
				}

			}


			//gets humidity
			if(response.substring(j, j+11).equals("\"humidity\":")) {
				for(int k =j+11 ; k<response.length(); k++) {
					if(response.substring(k, k+1).equals(",")) {
						hum[index] = Double.parseDouble(response.substring(j+11,k)) ;
						break;
					}
				}
			}

			//gets feel like temp in f
			if(response.substring(j, j+14).equals("\"feelslike_f\":")) {

				for(int k =j+14 ; k<response.length(); k++) {
					if(response.substring(k, k+1).equals(",")) {
						temp[index] = Double.parseDouble(response.substring(j+14,k)) ;
						break;

					}
				}

			}

			// gets sun exposure
			if(response.substring(j, j+5).equals("\"uv\":")) {
				for(int k =j+5 ; k<response.length(); k++) {
					if(response.substring(k, k+1).equals("}")) {
						sun[index] = Double.parseDouble(response.substring(j+5,k)) ;
						break;

					}

				}
				
				break;
			}
		}

	}
	


}
