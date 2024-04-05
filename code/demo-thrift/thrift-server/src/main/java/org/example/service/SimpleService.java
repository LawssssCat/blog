package org.example.service;

import com.example.thrift.UserService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.example.service.processor.UserServiceImpl;

public class SimpleService {
    public static void main(String[] args) {
        try (TServerSocket socket = new TServerSocket(19090); // 传输层
        ){
            TBinaryProtocol.Factory protocolFactory = new TBinaryProtocol.Factory(); // 协议层
            UserService.Processor<UserServiceImpl> processor = new UserService.Processor<>(new UserServiceImpl()); // 处理层

            // 设置服务器参数
            TServer.Args serverArgs = new TServer.Args(socket); // 传输层
            serverArgs.protocolFactory(protocolFactory); // 协议层
            serverArgs.processor(processor); // 处理层
            // 启动服务器 （单线程，一般用于测试）
            TSimpleServer server = new TSimpleServer(serverArgs);
            server.serve();
        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }
    }
}
