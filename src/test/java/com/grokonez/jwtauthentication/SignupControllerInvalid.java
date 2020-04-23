package com.grokonez.jwtauthentication;

import com.grokonez.jwtauthentication.message.request.SignUpForm;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@RunWith(Parameterized.class)
public class SignupControllerInvalid {

    String name ,username,password,email,tenant;
    static Set<String> role = new HashSet<>();
    static Set<String> user = new HashSet<>();
    static Set<String> admin = new HashSet<>();
    static Set<String> pm = new HashSet<>();
    static Set<String> adminuserm = new HashSet<>();
    static Set<String> adminpm = new HashSet<>();
    static Set<String> pmadmin = new HashSet<>();
    static Set<String> pmuser = new HashSet<>();

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
                        {"shallowloads", " shallowloads","password12", "shallowloads@@gmail.com", adminpm,"chnnai" } ,
                        {"", "mythili","password12","mythiliya@gmail.com", pmadmin,"india" } ,

                        {"     ", "mythyi","password12","mythyi@gmail.com", pmuser,"  india" } ,
                        {"my", "mythra","password12","mythra@gmail.com", user,"india" } ,
                        {"@myl", "myl","password12","myl@gmail.com", user,"india" } ,
                        //test for username
                        {"trigger12", " @trigger12","password12","trigger12@gmail.com", adminpm,"chnnai" } ,
                        {"trigger13", " 'trigger12","password12","trigger132@gmail.com", adminpm,"chnnai" } ,
                        {"trigger14", " $trigger12","password12","trigger32@gmail.com", adminpm,"chnnai" } ,
                        {"trigger", " trigger","password12","trigger@gmail.com", adminpm,"chnnai" } ,
                        {"trigger21", "","password12","trigger12@gmail.com", user,"india" } ,
                        {"myhili", "     ","password12","myhili@gmail.com", user,"india" } ,
                        {"mili", "mt","password12","mili@gmail.com",user ,"india" } ,
                        //test for email
                        {"shallowloads", " shallowloads","password12","shallowloads@@gmail.com", adminpm,"chnnai" } ,
                        {"shallowloads", " shallowloads","password12","''shallowloads@gmail.com", adminpm,"chnnai" } ,
                        {"shallowloads", " shallowloads","password12","shallowloadsgmail.com", adminpm,"chnnai" } ,
                        {"shallowloads", " shallowloads","password12","shallowloads@gmailcom", adminpm,"chnnai" } ,
                        {"shallowloads", " shallowloads","password12","@gmailcom", adminpm,"chnnai" } ,
                        {"shalloads", " shalloads","password12","      ", adminpm,"chnnai" } ,
                        {"shlowloads", " shlowloads","password12","$slowloads@gmail.com", adminpm,"chnnai" } ,
                        {"shlowlods", " shlowloas","password12","sg@gmail.com", adminuserm,"chnnai" } ,
                        {"shlolods", " shloloas", "password12", "  shloloas@gmail.com", adminuserm, "chnnai"},
                        //test for password
                        {"passwordtest12", " passwordtest12"," password122  ","passwordtest12@gmail.com", user,"china" } ,
                        {"passwordtest12", " passwordtest12"," @@@@@@ ","passwordtest12@gmail.com", user,"china" } ,
                        {"passwordtest12", " passwordtest12"," @werty ","passwordtest12@gmail.com", user,"china" } ,
                        {"passwordtest12", " passwordtest12"," $nnnnkk ","passwordtest12@gmail.com", user,"china" } ,
                        {"passwordtest12", " passwordtest12","'''' ","passwordtest12@gmail.com", user,"china" } ,
                        //test for role

                        {"kythili", "kythili","password12","kythili@gmail.com", adminpm,"india" } ,
                        //test for tenant

                        {"bthili", "bthili","password12","bthili@gmail.com", user,"     " } ,
                        {"chili", "chili","password12","chili@gmail.com", user," @d111   " } ,
                        {"lhili", "lhili","password12","lhili@gmail.com", user," " }

                });
    }

    public SignupControllerInvalid(String name, String username, String password, String email,Set<String> role, String tenant) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.tenant = tenant;
    }

    @Before
    public void restoredb() throws IOException, InterruptedException {
    }

    @Test
    public void invalidTestCaseSignup() throws IOException, JSONException {
        SignUpForm signUpForm = BaseTest.createSignUpDto(name,username,password,email,role,tenant);
        //JSONObject jsonObject = BaseTest.userSignup(signUpForm);
        Assert.assertEquals(400, HttpStatus.SC_BAD_REQUEST);
    }
}
