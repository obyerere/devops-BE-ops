package com.grokonez.jwtauthentication;

import com.google.gson.Gson;
import com.grokonez.jwtauthentication.message.request.SignUpForm;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TestBootUp {

    @Test
    public void testLucky() throws IOException, JSONException {
        Set<String> user = new HashSet<>();
        user.add("pm");
        SignUpForm signUpForm = createSignUpDto("trigg1er","trigg1er","p1assword12","trigg1er@gmail.com",user,"chi1na");
        JSONObject jsonObject = userSignup(signUpForm);
        Assert.assertEquals("User registered successfully!", jsonObject.getString("message"));
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

    public  JSONObject userSignup(SignUpForm signUpForm) throws IOException, JSONException {
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
}
