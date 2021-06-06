import com.model.User;
import com.model.UserDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestClient {

    private static final String REST_URI
            = "http://localhost:8080/rest/users/";

    private Client client = ClientBuilder.newClient();

    public UserDto getJsonEmployee(int id) {
        return client
                .target(REST_URI)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(UserDto.class);
    }
}
