package Fedyk3212.User_System;

import javax.swing.*;
import java.io.IOException;

public interface IRegistration {
    void Show_registration_menu();
    void Send_registration_request() throws IOException;
    void Send_login_request() throws IOException;
}
