package com.robohorse.robopojogenerator.utils

import com.google.common.io.CharStreams
import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert
import java.io.IOException
import java.io.InputStreamReader

class JsonReader {

    fun read(name: String): JSONObject? {
        var json: JSONObject? = null
        try {
            json = JSONObject(readFile(name))
        } catch (e: IOException) {
            Assert.fail("$name not found or incorrect")
        } catch (e: NullPointerException) {
            Assert.fail("$name not found or incorrect")
        } catch (e: JSONException) {
            Assert.fail("$name not found or incorrect")
        }
        Assert.assertNotNull("JSON is empty", json)
        return json
    }

    private fun readFile(name: String): String {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(name)
        return CharStreams.toString(InputStreamReader(inputStream, "UTF-8"))
    }
}
