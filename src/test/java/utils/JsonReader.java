package utils;

import com.google.common.io.CharStreams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by vadim on 02.10.16.
 */

public class JsonReader {

    public JSONObject read(String name) throws IOException, NullPointerException {
        JSONObject json = null;
        try {
            json = new JSONObject(readFile(name));
        } catch (IOException | NullPointerException | JSONException e) {
            fail(name + " not found or incorrect");
        }

        assertNotNull("JSON is empty", json);
        return json;
    }

    private String readFile(String name) throws IOException, NullPointerException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(name);
        return CharStreams.toString(new InputStreamReader(inputStream, "UTF-8"));
    }
}
