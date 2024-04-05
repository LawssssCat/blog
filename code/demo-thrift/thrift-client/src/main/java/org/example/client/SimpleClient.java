package org.example.client;

import com.example.thrift.User;
import com.example.thrift.UserService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class SimpleClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 19090;
        try (TTransport socket = new TSocket(host, port); // 传输层，阻塞io
        ) {
            socket.open();
            TProtocol protocol = new TBinaryProtocol(socket); // 协议层，二进制编码格式
            UserService.Client client = new UserService.Client(protocol);
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
