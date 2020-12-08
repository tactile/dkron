package Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public abstract class Config {
    protected static final Logger logger = LoggerFactory.getLogger(Config.class);
    public static final String DEV1 = "dev1";
    public static final String DEV2 = "dev2";
    public static final String STAGING = "staging";
    public static final String PRODUCTION = "production";
    public static final String DEVELOPMENT = "development";
    public static final String TEST = "test";
    
    public static Map<String, Object> loadConfig(String fileName) {
        Map<String, Object> config = null;
        String env = getEnv();
        if (null == env || "localhost".equalsIgnoreCase(env) ||
                "test".equalsIgnoreCase(env) || env.isEmpty()) env = DEVELOPMENT;
        InputStream in = null;
        
        try {
            in = new FileInputStream (new File (fileName));
        } catch (Exception e) {
            logger.error("Error loading config file " +  fileName + " from classpath.", e);
        }
        
        if (in != null) {
            config = (Map<String, Object>) ((Map<String, Object>) new Yaml().load(in)).get(env);
            logger.debug("Done initializing KS configuration.");
        }
        return config;
    }
    
    public static String getEnv () {
        String syncEnv = System.getenv ().get ("ENVIRONMENT");
        return syncEnv == null ? DEVELOPMENT : syncEnv;
    }
}
