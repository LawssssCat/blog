package org.example.service;

import com.example.thrift.UserService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;
import org.example.service.processor.UserServiceImpl;

public class NonblockingService {
    public static void main(String[] args) {
        try (TNonblockingServerSocket socket = new TNonblockingServerSocket(19090)){
            UserService.Processor<UserServiceImpl> processor = new UserService.Processor<>(new UserServiceImpl());
            TBinaryProtocol.Factory protocolFactory = new TBinaryProtocol.Factory();
            TFramedTransport.Factory transportFactory = new TFramedTransport.Factory(); // [!code highlight]
            // server
            TNonblockingServer.Args serverArgs = new TNonblockingServer.Args(socket);
            serverArgs.processor(processor);
            serverArgs.protocolFactory(protocolFactory);
            serverArgs.transportFactory(transportFactory);
            TNonblockingServer server = new TNonblockingServer(serverArgs);
            server.serve();
        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }
    }
}
