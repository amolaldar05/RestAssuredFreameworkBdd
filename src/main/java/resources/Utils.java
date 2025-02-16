package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
    public static RequestSpecification req;

    public RequestSpecification requestSpecification(ContentType contentType) throws IOException {
        // Get the path for the log file in the resources folder
        File logFile = new File(System.getProperty("user.dir") + "/logging.txt");

        // If the log file exists, delete it before each run to start fresh
        if (logFile.exists()) {
            logFile.delete();
        }

        // Open file in append mode to ensure logs are added during the run (no overwrite during the run)
        PrintStream log = new PrintStream(new FileOutputStream(logFile, true));
        log.println("\n===== New Request =====\n"); // Add a separator before each request

        RequestSpecBuilder specBuilder = new RequestSpecBuilder()
            .setBaseUri(getGlobalValues("baseURL"))
            .addFilter(RequestLoggingFilter.logRequestTo(log))
            .addFilter(ResponseLoggingFilter.logResponseTo(log));

        // Dynamically set Content-Type based on the request type
        if (contentType.equals(ContentType.MULTIPART)) {
            specBuilder.setContentType("multipart/form-data"); // Explicitly define multipart
        } else {
            specBuilder.setContentType(contentType);
        }

        req = specBuilder.build();
        return req;
    }

    // Method to fetch values from the global properties file
    public String getGlobalValues(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/java/resources/global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    // Method to extract a value from the JSON response using the provided key
    public String getJsonPath(Response response, String key) {
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }
}
