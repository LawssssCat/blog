package org.example.main;

import org.example.svc.Service;
// import org.example.svc.impl.ServiceImpl; // ❗
import java.util.ServiceLoader; // ❗

public class Main {
	public static void main(String[] args) {
		// Service service = new ServiceImpl(); // ❗
		Service service = ServiceLoader
            .load(Service.class)
            .findFirst()
            .orElseThrow(); // ❗
		service.use();
	}
}