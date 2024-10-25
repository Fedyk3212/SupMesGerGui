package feodorbal.localisation;

import java.util.HashMap;

public class Localies {
    static HashMap<String, String> code_country = new HashMap<>();
    public Localies() {
        code_country.put("en_EN", "English");
        code_country.put("ru_RU", "Русский");
    }
}
