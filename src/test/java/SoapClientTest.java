//import auto_soap.com.resources.soap.*;
//import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
//import org.junit.Before;
//import org.junit.Test;
//
//import javax.xml.datatype.DatatypeConfigurationException;
//import javax.xml.datatype.DatatypeFactory;
//import javax.xml.datatype.XMLGregorianCalendar;
//
//public class SoapClientTest {
//    private String soapServiceUrl = "http://localhost:8080/soap/UserWebSoap";
//    private JaxWsProxyFactoryBean factoryBean;
//    private UserWebSoap webserviceSEI;
//    private XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar("2009-05-07T17:05:45.678Z");
//
//    public SoapClientTest() throws DatatypeConfigurationException {
//    }
//
//    @Before
//    public void initClient() {
//        factoryBean = new JaxWsProxyFactoryBean();
//        factoryBean.setServiceClass(UserWebSoap.class);
//        factoryBean.setAddress(soapServiceUrl);
//        webserviceSEI = (UserWebSoap) factoryBean.create();
//    }
//
//    @Test
//    public void addUserOk() throws UserWasNotDeleted_Exception {
//        webserviceSEI.createUser(create());
//        long id = getUserIdByLogin("anton");
//        assertEquals(webserviceSEI.getUser(id).getEmail(), "andrey@as.com");
//        webserviceSEI.deleteUser(id);
//    }
//
//    @Test(expected = UserWasNotCreated_Exception.class)
//    public void addUserError() throws UserWasNotCreated_Exception {
//        EditUserDto userDto = create();
//        userDto.setLogin("1234");
//        userDto.setPassword("34");
//        webserviceSEI.createUser(userDto);
//    }
//
//    @Test
//    public void editUserOk() throws UserWasNotDeleted_Exception, UserWasNotChanged_Exception, UserWasNotCreated_Exception {
//        EditUserDto userDto = create();
//        webserviceSEI.createUser(create());
//        long id = getUserIdByLogin("anton");
//        userDto.setPassword("");
//        userDto.setConfirmPassword("");
//        userDto.setId(id);
//        userDto.setEmail("tom@as.com");
//        webserviceSEI.updateUser(userDto);
//        assertEquals(webserviceSEI.getUser(id).getEmail(), "tom@as.com");
//        webserviceSEI.deleteUser(id);
//    }
//
//    @Test(expected = UserWasNotChanged_Exception.class)
//    public void editUserError() throws UserWasNotChanged_Exception {
//        EditUserDto editUserDto = create();
//        editUserDto.setLogin("");
//        editUserDto.setConfirmPassword("34");
//        webserviceSEI.updateUser(editUserDto);
//    }
//
//
//    @Test(expected = UserWasNotDeleted_Exception.class)
//    public void deleteUserError() throws UserWasNotDeleted_Exception {
//        webserviceSEI.deleteUser(100);
//    }
//
//    @Test
//    public void getUser() {
//        assertEquals(webserviceSEI.getUser(1).getEmail(),
//                "user@ad.com");
//    }
//
//    @Test
//    public void getUsers() {
//        assertEquals(webserviceSEI.getUsers().size(), 2);
//    }
//
//    private long getUserIdByLogin(String login) {
//        for (UserRowDto user : webserviceSEI.getUsers()) {
//            if (login.equals(user.getLogin())) {
//                return user.getId();
//            }
//        }
//        return 0L;
//    }
//
//    private EditUserDto create() {
//        EditUserDto userDto = new EditUserDto();
//        userDto.setLogin("anton");
//        userDto.setPassword("1234");
//        userDto.setConfirmPassword("1234");
//        userDto.setEmail("andrey@as.com");
//        userDto.setFirstName("Asdf");
//        userDto.setLastName("asdf");
//        userDto.setBirthday(date);
//        userDto.setCaptcha("0000");
//        userDto.setRole("User");
//        return userDto;
//    }
//}
