package io.openliberty.sample.jakarta.jsonb;

import java.time.LocalDate;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

public class Developer {

    @JsonbProperty("name")
	private String name;

    @JsonbDateFormat("dd-MM-yyyy")
	private LocalDate birthday;

    @JsonbProperty("languages")
	private String[] languages;

    @JsonbTransient
    // @JsonbProperty("db")     // Will cause an exception
    public String favoriteDatabase;

    
    public Developer() {

    }  
    
    @JsonbCreator
    public Developer(
        @JsonbProperty("name") String name, 
        @JsonbDateFormat("dd-MM-yyyy") LocalDate birthday,
        @JsonbProperty("languages") String[] languages,
        String favoriteDatabase) {
        this.name = name;
        this.birthday = birthday;
        this.languages = languages;
        this.favoriteDatabase = favoriteDatabase;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String [] getLanguages() {
        return languages;
    }

    public String getFavoriteDatabase() {
        return favoriteDatabase;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setLanguages(String [] languages) {
        this.languages = languages;
    }

    public void setFavoriteDatabase(String favoriteDatabase) {
        this.favoriteDatabase = favoriteDatabase;
    }
}
