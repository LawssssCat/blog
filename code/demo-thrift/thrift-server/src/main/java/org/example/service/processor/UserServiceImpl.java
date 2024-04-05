package org.example.service.processor;

import com.example.thrift.User;
import com.example.thrift.UserService;
import org.apache.thrift.TException;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService.Iface {

    final private Map<Integer, User> userMap = new HashMap<>();
    {
        User user = new User();
        user.setId(1);
        user.setName("Steven");
        // user.age = 0
        userMap.put(user.getId(), user);
    }

    @Override
    public User getById(int id) throws TException {
        System.out.println(" ==== 远程调用 getById 方法 ===");
        return userMap.get(id);
    }

    @Override
    public boolean isExist(String name) throws TException {
        return userMap.values().stream().anyMatch(p -> p.getName().equals(name));
    }
}
