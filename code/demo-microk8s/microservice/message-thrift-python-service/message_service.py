# -*- coding: utf-8 -*-
# thrift 服务器
from thrift_api.message.api import MessageService
from thrift.transport import TSocket, TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer
class MessageServiceHandler(MessageService.Iface):
    def sendMobileMessage(self, mobile, message):
        print("sendMobileMessage, mobile:{}, message:{}", mobile, message)
        return True

    def sendEmailMessage(self, email, message):
        # import smtplib
        print("sendEmailMessage, email:{}, message:{}", email, message)
        return True

if __name__ == '__main__':
    # 1
    handler = MessageServiceHandler()
    processor = MessageService.Processor(handler)
    # 2
    host = "localhost"
    port = 19090
    socket = TSocket.TServerSocket(host, port)
    # 3
    transportFactory = TTransport.TTransportFactoryBase()
    # 4
    protocolFactory = TBinaryProtocol.TBinaryProtocolFactory()
    # server
    server = TServer.TSimpleServer(processor, socket, transportFactory, protocolFactory)
    print("python thrift server start...")
    server.serve()
    print("python thrift server exit.")
