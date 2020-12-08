import java.io.Serializable;

/**
 * This class is created to map joined record (job_info + callbackurl + auth) to jacksonBind
 */
public class JobModel implements Serializable {
    String  name;
    String  service;
    String  schedule;
    Integer retries;
    Boolean concurrency;

    String url;
    String auth;

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
}

