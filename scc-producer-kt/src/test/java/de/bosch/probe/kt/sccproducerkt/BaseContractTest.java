package de.bosch.probe.kt.sccproducerkt;

import static org.mockito.Mockito.when;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SccProducerKtApplication.class)
public abstract class BaseContractTest {

  @MockBean
  GreetingService greetingService;
  @Autowired
  private WebApplicationContext context;

  Greeting expected = new Greeting("john", "hello", LocalDateTime.parse("2018-12-12T10:45:55"));
  Greeting expected1 = new Greeting("jane", "Hi", LocalDateTime.parse("2018-12-12T11:45:55"));
  Greeting expected2 = new Greeting("ken", "Hallo", LocalDateTime.parse("2018-12-12T12:45:55"));

  @Before
  public void setup() {
    when(greetingService.all()).thenReturn(Arrays.asList(expected, expected1, expected2));
    when(greetingService.sayHello(expected.getName())).thenReturn(expected);

    RestAssuredMockMvc.webAppContextSetup(context);
  }
}
