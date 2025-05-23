package com.example.demo.application.dto;

import lombok.Data;
import java.util.List;

@Data
public class SteamPlayerResponse {
    private Response response;

    @Data
    public static class Response {
        private List<Player> players;
    }

    @Data
    public static class Player {
        private String steamid;
        private String personaname;
        private String avatarfull;
    }
}
