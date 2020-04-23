package com.grokonez.jwtauthentication.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grokonez.jwtauthentication.message.request.SignUpForm;
import com.grokonez.jwtauthentication.message.response.JwtResponse;
import com.grokonez.jwtauthentication.model.Loginresponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.ws.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class AuthResrAPITest extends BaseTest {

  @Before
  public void restoredb() throws IOException, InterruptedException {
    BaseTest.restoreDB("testdb", "root", "mysql", "C:\\freshdb.sql");
  }



  @Test
  public void  signUp_test() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("pm");
   SignUpForm signUpForm = BaseTest.createSignUpDto("trigg1er","trigg1er","p1assword12","trigg1er@gmail.com",user,"chi1na");
   JSONObject jsonObject = BaseTest.userSignup(signUpForm);
    Assert.assertEquals("User registered successfully!", jsonObject.getString("message"));


  }

  //testing the requirements *NO DUPLICATES
  @Test
  public void  noDuplicateUsernameExistsTest() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("pm");
    SignUpForm signUpForm = BaseTest.createSignUpDto("trigger2","trigger2","password12","trigger2@gmail.com",user,"chi1na");
    JSONObject jsonObject = BaseTest.userSignup(signUpForm);
    SignUpForm signUpForm2 = BaseTest.createSignUpDto("trigger2","trigger2","password12","trigger2@gmail.com",user,"chi1na");
    JSONObject jsonObject2 = BaseTest.userSignup(signUpForm2);


    Assert.assertEquals("Fail -> Username is already taken!", jsonObject2.getString("message"));
  }


  //testing the requirements *NO DUPLICATES Email
  @Test
  public void  noDuplicateEmailExistsTest() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("pm");
    SignUpForm signUpForm = BaseTest.createSignUpDto("vamsi","vamsi","password12","trigg1er@gmail.com",user,"chi1na");
    JSONObject jsonObject = BaseTest.userSignup(signUpForm);

    SignUpForm signUpForm2 = BaseTest.createSignUpDto("vamsi","vamsi2","password12","trigg1er@gmail.com",user,"chi1na");
    JSONObject jsonObject2 = BaseTest.userSignup(signUpForm);
    Assert.assertEquals("Fail -> Email is already in use!", jsonObject2.getString("message"));
  }

  @Test
  public void  multipleRoleAssignTest() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("pm");
    user.add("admin");
    SignUpForm signUpForm = BaseTest.createSignUpDto("aradhna","aradhna","password12","aradhna@gmail.com",user,"chi1na");
    JSONObject jsonObject = BaseTest.userSignup(signUpForm);
    Assert.assertEquals("User registered successfully!", jsonObject.getString("message"));
  }

  @Test
  public void  differentRoleAssigned() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("principle");
    SignUpForm signUpForm = BaseTest.createSignUpDto("shivala","shivala","password12","shivala@gmail.com",user,"chi1na");
    JSONObject jsonObject = BaseTest.userSignup(signUpForm);
    Assert.assertEquals("Fail! -> Cause: User Role not find.", jsonObject.getString("message"));
  }


  @Test
  public void  noArguementEmptyTest() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("pm");
    SignUpForm signUpForm = BaseTest.createSignUpDto("","neeya","password12","neeya@gmail.com",user,"chi1na");
    JSONObject jsonObject = BaseTest.userSignup(signUpForm);
    Assert.assertEquals(400, HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  public void  noRoleAssiged() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("");
    SignUpForm signUpForm = BaseTest.createSignUpDto("biya","biya","password12","biya@gmail.com",user,"chi1na");
    JSONObject jsonObject = BaseTest.userSignup(signUpForm);
     Assert.assertEquals("User registered successfully!", jsonObject.getString("message"));
  }

  @Test
  public void  validatingIntValuesforString() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    SignUpForm signUpForm = BaseTest.createSignUpDto("21345","21345","password12","arunalaya@gmail.com",user,"chi1na");
    JSONObject jsonObject = BaseTest.userSignup(signUpForm);
    Assert.assertEquals("User registered successfully!", jsonObject.getString("message"));
  }

  @Test
  public void  validatingSpaces() throws IOException,JSONException {
    Set<String> user = new HashSet<>();
    user.add("          ");
    SignUpForm signUpForm = BaseTest.createSignUpDto("     ","      ","     ","      ",user ,"     ");
    JSONObject jsonObject = BaseTest.userSignup(signUpForm);
    Assert.assertEquals(400, HttpStatus.SC_BAD_REQUEST);
  }


}
