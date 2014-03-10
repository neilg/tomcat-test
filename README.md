# Tomcat Test

Embed [Tomcat](http://tomcat.apache.org/) in your [JUnit](http://junit.org/) tests.

## Getting started

If you're using Maven add a dependency on tomcat-test

    <dependency>
        <groupId>io.meles.testing</groupId>
        <artifactId>tomcat-test</artifactId>
        <version>0.1-SNAPSHOT</version>
    </dependency>

If you want to use JSPs you also need

    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
        <version>7.0.52</version>
    </dependency>

Then in your test classes

    import static io.meles.test.tomcat.TomcatRule.*
    import static io.meles.test.tomcat.config.WebappBuilder.*

    import io.meles.test.tomcat.TomcatRule;

    @ClassRule
    public static TomcatRule tomcat = withTomcat()
                                            .run(webapp("dir/containing/webapp").at("/"))
                                            .onPort(9876);

    @Test
    public void shouldBehaveInSomeManner() {
        // ...
    }

If you don't want to bind tomcat to a specific port, for instance if you're running your tests on a build server

    @ClassRule
    public static TomcatRule tomcat = withTomcat()
                                            .run(webapp("dir/containing/webapp").at("/"))
                                            .onFreePort();

    @Test
    public void shouldBehaveInSomeManner() {
        int portTomcatIsListeningOn = tomcat.getLocalPort();
        // ...
    }
