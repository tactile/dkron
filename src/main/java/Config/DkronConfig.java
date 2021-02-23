package Config;

import java.util.Map;

public class DkronConfig extends Config{
    
    private static Map<String, Object> map;
    
    static {
        map = loadConfig("config.yml");
    }
    
    public static String getDkronUrl() {
        return map.get("dkronUrl").toString();
    }
    public static String getArchmaesterUrl(){
        return map.get("archmaesterUrl").toString();
    }
    
    public static String getClientId(){
        return map.get("client_id").toString();
    }
    public static String getSecret(){
        return map.get("secret").toString();
    }
    public static String getDkronTags(){
        return map.get("dkronTags").toString();
    }
}
