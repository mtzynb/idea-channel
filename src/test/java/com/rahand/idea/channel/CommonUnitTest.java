package com.rahand.idea.channel;

import com.rahand.idea.channel.utils.Utils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by z.mohammadtabar on 28/08/2021.
 */
public class CommonUnitTest {

    @Test
    void jsonStringToMap() throws IOException {
        String json = "{\n" +
                "\"bank\" : \"SAMAN\",\n" +
                "\"body\" :{\n" +
                "\"customer_number\" : \"12345\",\n" +
                "\"deposit_number\" : \"192.168.1.23\"\n" +
                "}\n" +
                "\n" +
                "}";

        Map result = Utils.jsonStringToMap(json);
        System.out.println(result);
        System.out.println(result.get("body").toString());
    }

    @Test
    void base64Encoder() {
        String pass = "13774:dd7yv0owJDvLS15zg7Ah8F9iRwxIbtPjDW4nW6j0xg";

        System.out.println("pass = " + pass + " \nencryptedPass = " +
                Utils.encodeStringToBase64(pass));
    }

    @Test
    void base64Decoder() {
        String encryptedPass = "MTM3NzQ6ZGQ3eXYwb3dKRHZMUzE1emc3QWg4RjlpUnd4SWJ0UGpEVzRuVzZqMHhn";
        System.out.println("encryptedPass = " + encryptedPass + "\npass = " +
                Utils.decodeBase64ToString(encryptedPass));
    }


    @Test
    void getJsonValueBasedIgnoreCaseKey() throws JSONException {

        String json = "{\n" +
                "\"BanK\" : \"BOOMIR\",\n" +
                "\"body\" :{\n" +
                "    \"customer_number\" : \"12345\",\n" +
                "    \"deposit_number\" : \"192.168.1.11\"\n" +
                "}\n" +
                "\n" +
                "}";

//        JSONObject s = (JSONObject) Utils.getJsonValueByIgnoredCaseKey(json, "Body");
        String s2 = (String) Utils.getJsonValueByIgnoredCaseKey(Utils.getJsonValueByIgnoredCaseKey(json, "Body").toString(), "deposit_number");
        System.out.println(s2);

    }


}
