package io.openliberty.sample.jakarta.jsonb;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.json.JsonArray;
import javax.json.JsonObject;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;


public class DemoJsonB {

    public static void main(String[] args) {
        serializeJson();
    }

    public static void serializeJson() {
        Developer dev = new Developer("John Doe",
            LocalDate.of(2019, 9, 7), 
            new String[] {"Python", "C"},
            "MongoDB"
        );
        Jsonb jsonb = JsonbBuilder.create();
        String jsonDev = jsonb.toJson(dev);
        System.out.println(jsonDev);
    }

}
