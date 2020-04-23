package com.grokonez.jwtauthentication;

import com.grokonez.jwtauthentication.message.request.SignUpForm;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

@RunWith(Parameterized.class)
public class SignupTestController {
  static Set<String> role = new HashSet<>();
  static Set<String> user = new HashSet<>();
  static Set<String> admin = new HashSet<>();
  static Set<String> pm = new HashSet<>();
  static Set<String> adminuserm = new HashSet<>();
  static Set<String> adminpm = new HashSet<>();
  static Set<String> pmadmin = new HashSet<>();
  static Set<String> pmuser = new HashSet<>();
  String name, username, password, email, tenant;

  public SignupTestController(
      String name,
      String username,
      String password,
      String email,
      Set<String> role,
      String tenant) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.email = email;
    this.tenant = tenant;
    this.role = role;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    user.add("user");
    admin.add("admin");
    pm.add("pm");
    adminuserm.add("admin");
    adminuserm.add("user");
    adminpm.add("admin");
    adminpm.add("pm");
    pmadmin.add("admin");
    pmadmin.add("pm");
    pmuser.add("pm");
    pmuser.add("user");
    user.add(" ");

    return Arrays.asList(
        new Object[][] {
          // test for name
          {"mythili", "mythili", "password12", "mythili@gmail.com", user, "india"},
          {"1234567", "mythyi", "password12", "mythyi@gmail.com", pmuser, "  india"},
          {" mythi", "mythi", "password12", "mythi@gmail.com", adminpm, "china"},
          // testcase for username
          {"trigger", " trigger", "password12", "trigger@gmail.com", adminpm, "chnnai"},
          {"militt", "12345", "password12", "militt@gmail.com", adminpm, "india"},
          // testcase for email
          {"shlowloads", " shlowloads", "password12", "1234567@gmail.com", adminpm, "chnnai"},
          // testcase for password
          {
            "passwordtest12",
            " passwordtest12",
            "1234566  ",
            "passwordtest12@gmail.com",
            pmuser,
            "china"
          },
        });
  }

  @Before
  public void restoredb() throws IOException, InterruptedException {
  }

 @Test
  public void validtSignupAction() throws IOException, JSONException {
    SignUpForm signUpForm = BaseTest.createSignUpDto(name, username, password, email, role, tenant);
    JSONObject jsonObject = BaseTest.userSignup(signUpForm);
    Assert.assertEquals("User registered successfully!", jsonObject.getString("message"));
  }
}
