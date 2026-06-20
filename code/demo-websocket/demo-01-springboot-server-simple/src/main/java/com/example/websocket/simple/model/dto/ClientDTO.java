package com.example.websocket.simple.model.dto;

import lombok.Data;

import javax.websocket.Session;

@Data
public class ClientDTO {
    private String clientId;

    private Session session;
}
