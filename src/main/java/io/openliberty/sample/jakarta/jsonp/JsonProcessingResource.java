package io.openliberty.sample.jakarta.jsonp;

import java.io.StringWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonPointer;
import jakarta.json.JsonWriter;
import jakarta.json.JsonWriterFactory;
import jakarta.json.stream.JsonGenerator;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/demo")
public class JsonProcessingResource {
	
	private static JsonObject jsonObject;
	
	/**
	 * Build a sample JsonObject
	 */
	@PUT
	@Path("/build")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject buildJsonObject() {
		Map<String, Boolean> config = new HashMap<String, Boolean>();
		JsonBuilderFactory factory = Json.createBuilderFactory(config);
		
		jsonObject = factory.createObjectBuilder()
				.add("name", "Jane Doe")
				.add("birthday", LocalDate.of(1995, 6, 12).toString())
				.add("languages", factory.createArrayBuilder()
						.add("Java")
						.add("Python")
						.add("HTML"))
				.build();
		return jsonObject;
	}
	
	/**
	 * List the JsonObject
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getJsonObject() {
		return jsonObject;
	}
	
	/**
	 * Write the JsonObject to the console
	 */
	@GET
	@Path("/write")
	public void writeJson() {
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
	
	/**
	 * Get the value of an attribute of the JsonObject
	 */
	@GET
	@Path("/list/{attribute}")
	public String readJsonObject(@PathParam("attribute") String attribute) {
		JsonPointer arrayElementPointer = Json.createPointer("/" + attribute);
		return arrayElementPointer.getValue(jsonObject).toString();
	}
	
	/**
	 * Get the language at the specified index
	 */
	@GET
	@Path("/language/{index}")
	public String readJsonObject(@PathParam("index") int index) {
		JsonPointer arrayElementPointer = Json.createPointer("/languages/" + index);
		return arrayElementPointer.getValue(jsonObject).toString();
	}
	
	/**
	 * Append a language to the end
	 */
	@POST
	@Path("/language/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject addToJsonObject(@PathParam("lang") String language) {
		JsonPointer langPointer = Json.createPointer("/languages/-");
		jsonObject = langPointer.add(jsonObject, Json.createValue(language));
		return jsonObject;
	}
}