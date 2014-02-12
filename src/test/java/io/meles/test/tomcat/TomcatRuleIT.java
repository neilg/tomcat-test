package io.meles.test.tomcat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;

public class TomcatRuleIT {

    @Rule
    public TomcatRule tomcatRule = new TomcatRule(8071);

    @Test
    public void ruleRunsWithoutError() throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8071/").openConnection();
        connection.getResponseCode(); // this method call will throw an exception if it can't connect to a server
    }
}
