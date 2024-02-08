package Fedyk3212.usersytem;

import Fedyk3212.resource.exception.EthrenetException;

import java.io.IOException;

public interface IRegistration {
    void Show_registration_menu();
    void Send_registration_request() throws IOException;
    void Send_login_request() throws IOException, EthrenetException;
}
