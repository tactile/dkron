
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

/**
 * This class is created to map joined record (job_info + callback url + auth) to jacksonBind
 */
public class JobModel implements Serializable {
    String name;
    String service;
    String schedule;
    String type;
    Integer retries;
    Boolean concurrency;
    @JsonRawValue
    JsonNode metadata;
    
    String url;
    String auth;
    
    public JobModel(String name) {
        this.name = name;
    }
    
    public JobModel() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getService() {
        return service;
    }
    
    public void setService(String service) {
        this.service = service;
    }
    
    public String getSchedule() {
        return schedule;
    }
    
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    
    public Integer getRetries() {
        return retries;
    }
    
    public void setRetries(Integer retries) {
        this.retries = retries;
    }
    
    public Boolean getConcurrency() {
        return concurrency;
    }
    
    public void setConcurrency(Boolean concurrency) {
        this.concurrency = concurrency;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getAuth() {
        return auth;
    }
    
    public void setAuth(String auth) {
        this.auth = auth;
    }
    
    public JsonNode getMetadata () {
        return metadata;
    }
    
    public void setMetadata (JsonNode metadata) {
        this.metadata = metadata;
    }
    
    public String getType () {
        return type;
    }
    
    public void setType (String type) {
        this.type = type;
    }
}
