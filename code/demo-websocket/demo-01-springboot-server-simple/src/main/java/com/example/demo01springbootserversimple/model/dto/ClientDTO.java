package com.example.demo01springbootserversimple.model.dto;

import lombok.Data;

import javax.websocket.Session;

@Data
public class ClientDTO {
    private String clientId;

    private Session session;
}
