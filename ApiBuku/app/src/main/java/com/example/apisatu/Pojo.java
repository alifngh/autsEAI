package com.example.apisatu;
import com.google.gson.annotations.SerializedName;
public class Pojo {

    @SerializedName("name")
    private String name;

    @SerializedName("alpha2Code")
    private String alpha2code;

    @SerializedName("alpha3Code")
    private String alpha3code;

    @SerializedName("capital")
    private String capital;

    @SerializedName("region")
    private String region;

    @SerializedName("subregion")
    private String subregion;

    @SerializedName("population")
    private String population;


    public String getName() {
        return name;
    }

    public String getAlpha2code() {
        return alpha2code;
    }

    public String getAlpha3code() {
        return alpha3code;
    }

    public String getCapital() {
        return capital;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public String getPopulation() {
        return population;
    }
}
