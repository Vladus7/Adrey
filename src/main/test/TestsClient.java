import com.model.User;
import com.model.UserDto;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class TestsClient {
    public static final int HTTP_CREATED = 201;
    private RestClient client = new RestClient();

    @Test
    public void givenCorrectObject_whenCorrectJsonRequest_thenResponseCodeCreated() {
       UserDto user = new UserDto();

        UserDto response = client.getJsonEmployee(1);

        assertEquals(response, user);
    }
}
