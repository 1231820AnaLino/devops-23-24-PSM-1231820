package basic_demo;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting()); }

}