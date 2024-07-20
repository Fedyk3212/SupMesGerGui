package NamelessDev.usersytem;

import NamelessDev.resource.exception.EthrenetException;

import java.io.IOException;

public interface IRegistration {
    void Show_registration_menu() throws IOException;
    void Send_registration_request() throws IOException;
    void Send_login_request() throws IOException, EthrenetException;
}
