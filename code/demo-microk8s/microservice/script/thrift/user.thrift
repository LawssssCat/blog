namespace java org.example.thrift.user

struct UserInfo {
    1:i32 id,
    2:string username,
    3:string password,
    4:string readName,
    5:string mobile,
    6:string email,
}

service UserService {
    UserInfo getUserById(1:i32 id);
    UserInfo getUserByUsername(1:string username);
    void registerUser(1:UserInfo userInfo);
}
