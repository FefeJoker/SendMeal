package ar.com.portlander.sendmeal.persistance;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ar.com.portlander.sendmeal.model.Plato;

public class Converters {
    @TypeConverter
    public static List<Plato> toPlatoList(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromPlatoList(List<Plato> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static LatLng toLatLng(String value) {
        String[] split = value.split(";");
        LatLng result = new LatLng(Double.valueOf(split[0]), Double.valueOf(split[1]));
        return result;
    }

    @TypeConverter
    public static String fromLatLng(LatLng list) {
        if(list == null)    return null;
        String result = Double.valueOf(list.latitude).toString() + ";" + Double.valueOf(list.longitude).toString();
        return result;
    }
}
