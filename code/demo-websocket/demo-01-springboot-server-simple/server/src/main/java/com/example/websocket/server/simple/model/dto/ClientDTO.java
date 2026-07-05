package com.example.websocket.server.simple.model.dto;

import lombok.Data;

import jakarta.websocket.Session;

@Data
public class ClientDTO {
    private String clientId;

    private Session session;
}
