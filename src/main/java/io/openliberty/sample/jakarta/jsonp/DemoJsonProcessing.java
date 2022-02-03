package io.openliberty.sample.jakarta.jsonp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.json.*;
import javax.json.stream.JsonGenerator;

public class DemoJsonProcessing {
	
	public static void main(String[] args) {
		// process JSON object from a file
		writeJson(readJson("src/main/java/io/openliberty/sample/jakarta/jsonp/data.json"));
		// process a fresh new JSON object
		writeJson(buildJson());
		jsonPointer();
	}
	
	/**
	 * Reads from a JSON file
	 */
	public static JsonObject readJson(String filepath) {
		try {
			InputStream is = new FileInputStream(filepath);
			JsonReaderFactory factory = Json.createReaderFactory(null);;
			JsonReader jsonReader = factory.createReader(is);
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();
	        is.close();
			return jsonObject;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Writes a JSON object and prints it
	 */
	public static void writeJson(JsonObject jsonObject) {
		Map<String, Boolean> config = new HashMap<String, Boolean>();
		config.put(JsonGenerator.PRETTY_PRINTING, true);
		StringWriter sw = new StringWriter();
		
		// Use a factory to create multiple instances of writer
		JsonWriterFactory factory = Json.createWriterFactory(config);
		JsonWriter jsonWriter = factory.createWriter(sw);
		jsonWriter.writeObject(jsonObject);
		//jsonWriter.write(jsonObject);
		jsonWriter.close();
		System.out.println(sw);
		
	}
	
	public static void writeJsonWithoutFactory(JsonObject jsonObject) {
		Map<String, Boolean> config = new HashMap<String, Boolean>();
		config.put(JsonGenerator.PRETTY_PRINTING, true);
		StringWriter sw = new StringWriter();
		
		// Create a writer directly
		JsonWriter jsonWriter = Json.createWriter(sw);
		jsonWriter.writeObject(jsonObject);
		System.out.println(sw);
		
	}
	
	/**
	 * Builds a new JSON object
	 */
	public static JsonObject buildJson() {
		Map<String, Boolean> config = new HashMap<String, Boolean>();
		JsonBuilderFactory factory = Json.createBuilderFactory(config);
		
		JsonObject jsonObject = factory.createObjectBuilder()
				.add("name", "Jane Doe")
				.add("birthday", LocalDate.of(1995, 6, 12).toString())
				.add("languages", factory.createArrayBuilder()
						.add("Java")
						.add("Python"))
				.build();
		return jsonObject;
	}

	public static void jsonPointer() {
		String jsonString = "{\"name\":\"duke\",\"age\":42,\"skills\":[\"Java SE\", \"Java EE\"]}";
	
		JsonObject jsonObject = Json.createReader(new StringReader(jsonString)).readObject();

		JsonPointer arrayElementPointer = Json.createPointer("/skills/1");
		JsonPointer agePointer = Json.createPointer("/age");
		JsonPointer namePointer = Json.createPointer("/name");
		JsonPointer addressPointer = Json.createPointer("/address");
		JsonPointer tagsPointer = Json.createPointer("/tags");
	
		System.out.println("Get array element with pointer: " + arrayElementPointer.getValue(jsonObject).toString());
		System.out.println("Remove age with pointer: " + agePointer.remove(jsonObject));
		System.out.println("Replace name with pointer: " + namePointer.replace(jsonObject, Json.createValue("john")));
		System.out.println("Check address with pointer: " + addressPointer.containsValue(jsonObject));
		System.out.println("Add tags with pointer: " + tagsPointer.add(jsonObject, Json.createArrayBuilder().add("nice").build()));
	  }

}
