package com.grokonez.jwtauthentication.Message;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.grokonez.jwtauthentication.message.request.SignUpForm;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BaseTest {

  public static <T> T retrieveResourceFromResponse(HttpResponse response, Class<T> clazz)
      throws IOException {

    String jsonFromResponse = EntityUtils.toString(response.getEntity());
    ObjectMapper mapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper.readValue(jsonFromResponse, clazz);
  }

  public static String login(String username, String password, String tenant)
      throws IOException, JSONException {
    HttpUriRequest request =
        RequestBuilder.create("POST")
            .setUri("http://localhost:8085/api/auth/signin")
            .setEntity(
                new StringEntity(
                    String.format(
                        "{\n"
                            + "\n"
                            + "\t\"username\": \"%s\",\n"
                            + "\t\"password\": \"%s\",\n"
                            + "\t\"tenant\": \"%s\"\n"
                            + "}\n",
                        username, password, tenant),
                    ContentType.APPLICATION_JSON))
            .build();
    HttpResponse response = HttpClientBuilder.create().build().execute(request);

    HttpEntity entity = response.getEntity();
    String responseString = EntityUtils.toString(entity, "UTF-8");
    JSONObject jsonObj = new JSONObject(responseString);
    String tokem = jsonObj.getString("accessToken");
    return tokem;
  }

  public static SignUpForm createSignUpDto(
      String name,
      String username,
      String password,
      String email,
      Set<String> role,
      String tenant) {

    SignUpForm signUpForm = new SignUpForm();
    signUpForm.setName(name);
    signUpForm.setUsername(username);
    signUpForm.setPassword(password);
    signUpForm.setEmail(email);
    signUpForm.setRole(role);
    signUpForm.setTenant(tenant);

    return signUpForm;
  }

  public static JSONObject userSignup(SignUpForm signUpForm) throws IOException, JSONException {
    Gson gson = new Gson();
    String json = gson.toJson(signUpForm);

    HttpUriRequest request =
        RequestBuilder.create("POST")
            .setUri("http://localhost:8085/api/auth/signup")
            .setEntity(new StringEntity(json, ContentType.APPLICATION_JSON))
            .build();

    HttpResponse response = HttpClientBuilder.create().build().execute(request);

    HttpEntity entity = response.getEntity();
    String responseString = EntityUtils.toString(entity, "UTF-8");
    JSONObject jsonObj = new JSONObject(responseString);

    return jsonObj;
  }


  public static boolean restoreDB(String dbName, String dbUserName, String dbPassword, String source) throws IOException, InterruptedException {
    String[] executeCmd =
        new String[] {
          "C:\\xampp\\mysql\\bin\\mysql.exe ",
          "--user=" + dbUserName,
          "--password=" + dbPassword,
          dbName,
          "-e",
          " source " + source
        };
    Process runtimeProcess;
    try {
      runtimeProcess = Runtime.getRuntime().exec(executeCmd);
      int processComplete = runtimeProcess.waitFor();
      if (processComplete == 0) {
        System.out.println("Backup restored successfully");
        return true;
      }
      else {
        System.out.println("Could not restore the backup");
    }
    }
   catch (Exception ex) {
    ex.printStackTrace();
  }
        return false;
}


}
