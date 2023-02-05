import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AtmosClockData {
	static double finalData[][] = new double [4][24];
	static double [] temp = new double [24];
	static double [] hum = new double [24];
	static double [] sun = new double [24];
	static double [] per = new double [24];
	static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	   public static double [][] getData(String city) {

		try {
			String apiKey = "18641e7bfc3d4e78a03195018230402";
			
			String urlString = "http://api.weatherapi.com/v1/forecast.json?key=" + apiKey + "&q="+ city + "&days=1&aqi=no&alerts=no";

			URL url = new URL(urlString);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			Calendar now =  Calendar.getInstance();
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.HOUR_OF_DAY, 0);
			
			for (int a = 0; a < 24; a++) {
				for(int i = 0; i <response.length() ;i++) {
					if((response.substring(i, i+ 5).equals(sdf.format(now.getTime())))){	
						setDay(i,response, a);
						break;
					}
					
				}
				now.add(Calendar.HOUR_OF_DAY, +1);
				
			}
			finalData[0] = temp;
			finalData[1] = hum;
			finalData[2] = sun;
			finalData[3] = per;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return finalData;
	}

	public static void setDay(int i, StringBuilder response, int index ) {
		for(int j= i ; j<response.length(); j++) {

			if(response.substring(j, j+9).equals("\"temp_f\":")) {

				for(int k =j+9 ; k<response.length(); k++) {
					if(response.substring(k, k+1).equals(",")) {
						temp[index] = Double.parseDouble(response.substring(j+9,k)) ;
						break;
					}
				}

			}

			if(response.substring(j, j+12).equals("\"precip_in\":")) {
				for(int k =j+12 ; k<response.length(); k++) {
					if(response.substring(k, k+1).equals(",")) {
						per[index] = Double.parseDouble(response.substring(j+12,k)) ;
						break;
					}
				}

			}

			if(response.substring(j, j+11).equals("\"humidity\":")) {
				for(int k =j+11 ; k<response.length(); k++) {
					if(response.substring(k, k+1).equals(",")) {
						hum[index] = Double.parseDouble(response.substring(j+11,k)) ;
						break;
					}
				}
			}

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
