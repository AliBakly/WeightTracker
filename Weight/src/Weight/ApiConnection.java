package Weight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import com.google.gson.Gson;

public class ApiConnection {
	private HttpClient client;
	private HttpRequest request;
	private Result result;
	//private HttpResponse<String> response;
	public ApiConnection() {
		client = null;
		request = null;
		result = null;

	}
	
	private void connect(String word) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("ApiKey.properties"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String key = props.getProperty("key");
		
		client = HttpClient.newHttpClient();
		request = HttpRequest.newBuilder()
            .GET()
            .uri(
            URI.create("https://api.nal.usda.gov/fdc/v1/foods/search?query=" + word + "&pageSize=10&pageNumber=1&dataType=Survey%20(FNDDS)&api_key=" + key)
            ).build();
	
		HttpResponse<String> response = null;
		try {
			response = client.send(request,HttpResponse.BodyHandlers.ofString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         result = new Gson().fromJson(response.body(), Result.class);
	}
	
	public Result search(String word) {
		connect(word);
		return result;
	}
	
	public HashMap<String, Integer> getNameAndIdMap(){
		HashMap<String, Integer> map = new HashMap<>();
		for(SearchResultFood srf : result.foods) {
			map.put(srf.description, srf.fdcId);
		}
		return map;
	}
	
	public ArrayList<String> getNameList(){
		ArrayList<String> list = new ArrayList<>();
		for(SearchResultFood srf : result.foods) {
			list.add(srf.description);
		}
		return list;
	}
	
	public ArrayList<String> getPortionList(int id){
		ArrayList<String> list = new ArrayList<>();
		for(SearchResultFood srf : result.foods) {
			if(srf.fdcId == id) {
				for(measure m: srf.foodMeasures) {
					list.add(m.disseminationText);
				}
			}
			
		}
		return list;
	}
	
	//Hashmap with food id as key and an arraylist of map entries with nutrition info as value.
	public HashMap<Integer, ArrayList<Map.Entry<String, Double>>> getIdPortionMap(){
		HashMap<Integer, ArrayList<Map.Entry<String, Double>>> map = new HashMap<>();
		
		for(SearchResultFood srf : result.foods) {
			ArrayList<Map.Entry<String, Double>> list = new ArrayList<>();
			for(measure m : srf.foodMeasures) {
				list.add(new AbstractMap.SimpleEntry(m.disseminationText, m.gramWeight));
			}
			map.put(srf.fdcId, list);
		}
		return map;
	}
	
	public double[] getNutritionValues(int id){
		double[] nutrition = new double[4];
		nutrition[0] = getNutritionValue(id, "Energy");
		nutrition[1] = getNutritionValue(id, "Protein");
		nutrition[2] = getNutritionValue(id, "Carbohydrate, by difference");
		nutrition[3] = getNutritionValue(id, "Total lipid (fat)");
		return nutrition;
	}
	
	private double getNutritionValue(int id, String nutrient) {
		for(SearchResultFood srf : result.foods) {
			if(srf.fdcId == id) {
				for(AbridgedFoodNutrient afd: srf.foodNutrients) {
					if(afd.nutrientName.equals(nutrient)) {
						return afd.value;
					}
				}
			}
			
		}
		return -1;
	}
	
}









