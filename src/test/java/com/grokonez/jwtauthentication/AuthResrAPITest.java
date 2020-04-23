package com.grokonez.jwtauthentication;

import com.grokonez.jwtauthentication.message.request.SignUpForm;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AuthResrAPITest extends BaseTest {

  @Before
  public void restoredb() throws IOException, InterruptedException {
    restoreDB("testdb", "root", "mysql", "C:\\freshdb.sql");
  }



  @Test
  public void  signUp_test() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("pm");
   SignUpForm signUpForm = createSignUpDto("trigg1er","trigg1er","p1assword12","trigg1er@gmail.com",user,"chi1na");
   JSONObject jsonObject = userSignup(signUpForm);
    Assert.assertEquals("User registered successfully!", jsonObject.getString("message"));


  }

  //testing the requirements *NO DUPLICATES
  @Test
  public void  noDuplicateUsernameExistsTest() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("pm");
    SignUpForm signUpForm = createSignUpDto("trigger2","trigger2","password12","trigger2@gmail.com",user,"chi1na");
    JSONObject jsonObject = userSignup(signUpForm);
    SignUpForm signUpForm2 = createSignUpDto("trigger2","trigger2","password12","trigger2@gmail.com",user,"chi1na");
    JSONObject jsonObject2 = userSignup(signUpForm2);


    Assert.assertEquals("Fail -> Username is already taken!", jsonObject2.getString("message"));
  }


  //testing the requirements *NO DUPLICATES Email
  @Test
  public void  noDuplicateEmailExistsTest() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("pm");
    SignUpForm signUpForm = createSignUpDto("vamsi","vamsi","password12","trigg1er@gmail.com",user,"chi1na");
    JSONObject jsonObject = userSignup(signUpForm);

    SignUpForm signUpForm2 = createSignUpDto("vamsi","vamsi2","password12","trigg1er@gmail.com",user,"chi1na");
    JSONObject jsonObject2 = userSignup(signUpForm);
    Assert.assertEquals("Fail -> Email is already in use!", jsonObject2.getString("message"));
  }

  @Test
  public void  multipleRoleAssignTest() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("pm");
    user.add("admin");
    SignUpForm signUpForm = createSignUpDto("aradhna","aradhna","password12","aradhna@gmail.com",user,"chi1na");
    JSONObject jsonObject = userSignup(signUpForm);
    Assert.assertEquals("User registered successfully!", jsonObject.getString("message"));
  }

  @Test
  public void  differentRoleAssigned() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("principle");
    SignUpForm signUpForm = createSignUpDto("shivala","shivala","password12","shivala@gmail.com",user,"chi1na");
    JSONObject jsonObject = userSignup(signUpForm);
    Assert.assertEquals("Fail! -> Cause: User Role not find.", jsonObject.getString("message"));
  }


  @Test
  public void  noArguementEmptyTest() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("pm");
    SignUpForm signUpForm = createSignUpDto("","neeya","password12","neeya@gmail.com",user,"chi1na");
    JSONObject jsonObject = userSignup(signUpForm);
    Assert.assertEquals(400, HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  public void  noRoleAssiged() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    user.add("");
    SignUpForm signUpForm = createSignUpDto("biya","biya","password12","biya@gmail.com",user,"chi1na");
    JSONObject jsonObject = userSignup(signUpForm);
     Assert.assertEquals("User registered successfully!", jsonObject.getString("message"));
  }

  @Test
  public void  validatingIntValuesforString() throws IOException,JSONException{
    Set<String> user = new HashSet<>();
    SignUpForm signUpForm = createSignUpDto("21345","21345","password12","arunalaya@gmail.com",user,"chi1na");
    JSONObject jsonObject = userSignup(signUpForm);
    Assert.assertEquals("User registered successfully!", jsonObject.getString("message"));
  }

  @Test
  public void  validatingSpaces() throws IOException,JSONException {
    Set<String> user = new HashSet<>();
    user.add("          ");
    SignUpForm signUpForm = createSignUpDto("     ","      ","     ","      ",user ,"     ");
    JSONObject jsonObject = userSignup(signUpForm);
    Assert.assertEquals(400, HttpStatus.SC_BAD_REQUEST);
  }


}
