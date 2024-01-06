package Fedyk3212.Net_Work;

import java.io.IOException;

public interface IService {
    void sendRegReq(String data) throws IOException;
    void sendLoginReq(String data) throws IOException;
    void sendValidData(String data) throws IOException;
    void ReciveAnswer() throws  IOException;
}
