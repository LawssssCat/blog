package org.example.thrift.user.thrift;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;
import org.example.thrift.user.UserService;
import org.example.thrift.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class ThriftServer {
    @Value("${thrift.server.port}")
    private int thriftServerPort;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @PostConstruct
    public void startThriftServer() {
        UserService.Processor<UserServiceImpl> userServiceProcessor = new UserService.Processor<>(userServiceImpl);
        try (TServerSocket socket = new TServerSocket(thriftServerPort)){
            TServer.Args serverArgs = new TServer.Args(socket);
            serverArgs.processor(userServiceProcessor);
            serverArgs.protocolFactory(new TBinaryProtocol.Factory());
            serverArgs.transportFactory(new TTransportFactory());
            // server
            TSimpleServer server = new TSimpleServer(serverArgs);
            log.info("TServer - Start serving...");
            server.serve();
        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }
    }
}
