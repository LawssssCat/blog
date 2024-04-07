package org.example.thrift.user.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.example.thrift.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceProvider {
    @Value("${thrift.service.user-host}")
    private String userServiceHost;
    @Value("${thrift.service.user-port}")
    private int userServicePort;

    final static private int SERVICE_TIMEOUT = 3000;

    public UserService.Client getUserService() throws TTransportException {
        try (TSocket socket = new TSocket(userServiceHost, userServicePort, SERVICE_TIMEOUT)) {
            socket.open();
            TBinaryProtocol protocol = new TBinaryProtocol(socket);
            return new UserService.Client(protocol);
        }
    }
}
