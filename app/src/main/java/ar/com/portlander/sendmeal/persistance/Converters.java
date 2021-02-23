package ar.com.portlander.sendmeal.persistance;

import androidx.room.TypeConverter;

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
}
