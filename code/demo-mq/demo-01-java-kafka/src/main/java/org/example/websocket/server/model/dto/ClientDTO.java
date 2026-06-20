package org.example.websocket.server.model.dto;

import jakarta.websocket.Session;
import lombok.Data;

@Data
public class ClientDTO {
    private String clientId;

    private Session session;
}
