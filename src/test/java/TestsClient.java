import com.model.*;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TestsClient {
    private RestClient client = new RestClient();

    @Test
    public void registrationOk() {
        Response response = client.registration(
                new RegistrationUserDto(
                        "andrey",
                        "1234",
                        "1234",
                        "andrey@as.com",
                        "Asdf",
                        "asdf",
                        new Date(),
                        "0000"));
        assertEquals(response.getStatus(), 200);
        client.deleteUser((int)(client.getUserIdByLogin("andrey")));
    }

    @Test
    public void registrationError() {
        Response response = client.registration(
                new RegistrationUserDto(
                        "",
                        "1234",
                        "34",
                        "andrey@as.com",
                        "Asdf",
                        "asdf",
                        new Date(),
                        "0000"));
        assertEquals(response.getStatus(), 401);
        assertEquals(response.readEntity(HashMap.class).toString(), "{confirmPassword=Passwords are not equals, login=Login is short}");
    }

    @Test
    public void loginOk() {
        Response response = client.login(
                new LoginUserDto("admin", "admin"));
        assertEquals(response.getStatus(), 200);
    }

    @Test
    public void loginError() {
        Response response = client.login(
                new LoginUserDto("n", "admin"));
        assertEquals(response.getStatus(), 401);
        assertEquals(response.readEntity(HashMap.class).toString(), "{login=Login is not register}");
    }

    @Test
    public void addUserOk() {
        Response response = client.addUser(
                new EditUserDto(
                        null,
                        "anton",
                        "1234",
                        "1234",
                        "andrey@as.com",
                        "Asdf",
                        "asdf",
                        new Date(),
                        "0000",
                        "User"));
        assertEquals(response.getStatus(), 200);
        client.deleteUser((int)(client.getUserIdByLogin("anton")));
    }

    @Test
    public void addUserError() {
        Response response = client.addUser(
                new EditUserDto(
                        0L,
                        "",
                        "1234",
                        "34",
                        "andrey@as.com",
                        "Asdf",
                        "asdf",
                        new Date(),
                        "0000",
                        "User"));
        assertEquals(response.getStatus(), 409);
        assertEquals(response.readEntity(HashMap.class).toString(), "{confirmPassword=Passwords are not equals, login=Login is short}");
    }

    @Test
    public void editUserOk() {
        client.addUser(new EditUserDto(
                0L,
                "andrey",
                "1234",
                "1234",
                "andrey@as.com",
                "Asdf",
                "asdf",
                new Date(),
                "0000",
                "User"));
        Response response = client.editUser(
                new EditUserDto(
                        client.getUserIdByLogin("andrey"),
                        "andrey",
                        "",
                        "",
                        "tom@as.com",
                        "Asdf",
                        "asdf",
                        new Date(),
                        "0000",
                        "User"));
        assertEquals(response.getStatus(), 200);
        client.deleteUser((int)(client.getUserIdByLogin("andrey")));
    }

    @Test
    public void editUserError() {
        client.addUser(new EditUserDto(
                0L,
                "andrey",
                "1234",
                "1234",
                "andrey@as.com",
                "Asdf",
                "asdf",
                new Date(),
                "0000",
                "User"));
        Response response = client.editUser(
                new EditUserDto(
                        0L,
                        "",
                        "1234",
                        "34",
                        "andrey@as.com",
                        "Asdf",
                        "asdf",
                        new Date(),
                        "0000",
                        "User"));
        assertEquals(response.getStatus(), 409);
        assertEquals(response.readEntity(HashMap.class).toString(), "{confirmPassword=Passwords are not equals}");
        client.deleteUser((int)(client.getUserIdByLogin("andrey")));
    }


    @Test
    public void deleteUserError() {
        assertEquals(client.deleteUser(100).getStatus(), 409);
    }

    @Test
    public void getUser() {
        assertEquals(client.getUser(1).toString(),
                "UserDto{id=1, login='user', password='user', email='user@ad.com', firstName='user', lastName='user', birthday=Sat Jun 15 00:00:00 EEST 2013, role='User'}");
    }

    @Test
    public void getUsers() {
        assertEquals(client.getUsers().size(), 2);
    }
}
