package org.example.client;

import com.example.thrift.User;
import com.example.thrift.UserService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;

public class NonblockingClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 19090;
        try (TSocket socket = new TSocket(host, port)){
            TFramedTransport transport = new TFramedTransport(socket); // [!code highlight]
            TBinaryProtocol protocol = new TBinaryProtocol(transport);
            UserService.Client client = new UserService.Client(protocol);
            // Connect!
            socket.open();
            try {
                User user = client.getById(1);
                System.out.println("user = " + user);
            } catch (TException e) {
                throw new RuntimeException(e);
            }
        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }
    }
}
