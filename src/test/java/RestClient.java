import com.model.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class RestClient {
    private Client client = ClientBuilder.newClient();

    public Response login(LoginUserDto userDto) {
        return client.target("http://localhost:8080/rest/inter/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(userDto));
    }

    public Response registration(RegistrationUserDto userDto) {
        return client.target("http://localhost:8080/rest/inter/registration")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(userDto));
    }

    public List<UserRowDto> getUsers() {
        return client.target("http://localhost:8080/rest/users")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class).readEntity(new GenericType<List<UserRowDto>>() {
                });
    }

    public UserDto getUser(int id) {
        return client
                .target("http://localhost:8080/rest/users/")
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(UserDto.class);
    }

    public Response addUser(EditUserDto editUserDto) {
        return client.target("http://localhost:8080/rest/users/add")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(editUserDto));
    }

    public Response editUser(EditUserDto editUserDto) {
        return client.target("http://localhost:8080/rest/users/update")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(editUserDto));
    }

    public Response deleteUser(long id) {
        return client
                .target("http://localhost:8080/rest/users")
                .path(String.valueOf(id)).request().delete();
    }

    public long getUserIdByLogin(String login){
        for (UserRowDto user: getUsers()) {
            if(login.equals(user.getLogin())) {
                return user.getId();
            }
        }
        return 0L;
    }
}
