/**
 * class performs task that is to done on Dkron start
 * Tasks
 * 1. Fetch all job info from helper service and call Dkro api to save in Dkron db
 * 2.
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import static Config.DkronConfig.*;

public class Startup
{
    private String jwtToken ="";
    private final String AMBaseUrl = getArchmaesterUrl();
    private final String DkronUrl = getDkronUrl();
    private final String GET_JOB_URI = AMBaseUrl + "/jobs";
    private final String AUTHENTICATE_URI = AMBaseUrl + "/authenticate";
    private final String REGISTER_URI = AMBaseUrl + "/services";
    private final String DKRON_JOB_URI = DkronUrl + "/jobs";
    private static final Logger logger = LoggerFactory.getLogger(Startup.class);
    
    public static void main(String args[]) throws IOException {
        Startup startup = new Startup();
        String authPayload = startup.getAuthPayload ();
        startup.authenticate (authPayload);
        startup.fetchJobs();
    }
    private String getAuthPayload() throws IOException {
        String client_id = getClientId();
        String secret = getSecret();
        String payload = "{\"clientId\":\"" + client_id +"\",\"clientSecret\":\""+secret+"\"}";
        return payload;
    }
    private void authenticate(String payload) throws IOException {
        URL authURL = new URL(AUTHENTICATE_URI);
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) authURL.openConnection();
        //call register api and pass the payload to authenticate to get JWT token


        HttpURLConnection con = (HttpURLConnection) authURL.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);


        try (OutputStream os = con.getOutputStream()) {
            byte[] input = payload.getBytes("utf-8");
            os.write (input, 0, input.length);
        }
        StringBuilder response = new StringBuilder ();
        try(BufferedReader br = new BufferedReader (
                new InputStreamReader(con.getInputStream (), "utf-8"))) {
            String responseLine = null;
            while((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim ());
            }
        }
        StringTokenizer st = new StringTokenizer(response.toString (), ":");
        String input1 = st.nextToken();
        String input2 = st.nextToken();
        jwtToken = "";
        for(int i=1;i<input2.length() -2;i++)
            jwtToken = jwtToken + input2.charAt (i);
    }
    private void fetchJobs() throws IOException
    {
        //URL urlForGetRequest = new URL("http://host.docker.internal:8080/jobs");
        URL urlForGetRequest = new URL(GET_JOB_URI);
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();

        conection.setRequestProperty("Content-Type","application/json");
        conection.setRequestMethod("GET");
        System.out.print (jwtToken);
        conection.setRequestProperty("Authorization","Bearer "+jwtToken);

        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            }
            in .close();
            System.out.println("JSON String Result " + response.toString() +"\n\n");
            //pass this response to Dkron similar to that I did in Kingslayer side
            addJobs2Dkron(response.toString());

            //response will contain List of job configuration, each element in List will have a job configuration
            //job conf -- job name, schedule, retries and concurrency
        }
        else {
            logger.warn("GET NOT WORKED");
        }
    }

    private void addSingleJob (JobModel jobModel) throws IOException {

        String payload = "{\"name\": \"" + jobModel.name +
                "\", \"schedule\": \"" + jobModel.schedule +
                "\", \"concurrency\": \"allow\",";
        payload += "\"executor\": \"shell\", \"executor_config\": { \"command\": \"java hello\"}}";
        //here we need to pass callback url and auth token as argument to java scheduler command

        URL dkronURL = new URL(DKRON_JOB_URI);

        HttpURLConnection con = (HttpURLConnection) dkronURL.openConnection ();
        con.setRequestMethod ("POST");
        con.setRequestProperty ("Content-Type", "application/json; utf-8");
        con.setRequestProperty ("Accept", "application/json");
        con.setDoOutput (true);

        try (OutputStream os = con.getOutputStream ()) {
            byte[] input = payload.getBytes (StandardCharsets.UTF_8);
            os.write (input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader (
                new InputStreamReader (con.getInputStream (), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder ();
            String responseLine = null;
            while ((responseLine = br.readLine ()) != null) {
                response.append (responseLine.trim ());
            }
        }
    }
    private void addJobs2Dkron(String response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JobModel[] jobModels = mapper.readValue(response, JobModel[].class);
        List<JobModel> jobModelList = Arrays.asList(mapper.readValue(response, JobModel[].class));
        System.out.println("\nJSON array to List of objects");
        for(int i = 0; i < jobModelList.size(); i++)
        {
            addSingleJob(jobModelList.get(i));
        }
    }
}