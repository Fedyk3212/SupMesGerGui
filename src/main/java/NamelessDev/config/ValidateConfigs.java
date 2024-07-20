package NamelessDev.config;

import NamelessDev.useful.Logger;

import java.util.HashMap;
import java.util.Objects;


public class ValidateConfigs {
    public static void validate() {
        try {
            Configs configFile = new Configs();
            HashMap<String, Object> configs = configFile.readAll();
            HashMap<String, Object> defaultConfigs = Configs.defaultProperties;
            for (String key : defaultConfigs.keySet()) {
                if (!configs.containsKey(key)) {
                    configFile.addNewParameter(key, (String) defaultConfigs.get(key));
                }
            }
            Logger.Log(ValidateConfigs.class, "Configs validated");
        }catch (Exception e){
            Logger.Log(ValidateConfigs.class, "Failed to validate configs");
        }

    }
}
