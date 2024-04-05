from thrift.transport import TSocket, TTransport
from thrift.protocol import TBinaryProtocol

from thrift_api.user import UserService

host = "localhost"
port = 19090

# 传输层
socket = TSocket.TSocket(host, port)
socket.setTimeout(600)
# Buffering is critical. Raw sockets are very slow
socket = TTransport.TBufferedTransport(socket)

# 协议层
protocol = TBinaryProtocol.TBinaryProtocol(socket)

# Create a client to use the protocol encoder
client = UserService.Client(protocol)

# Connect!
socket.open()

user = client.getById(1)
print(user)
