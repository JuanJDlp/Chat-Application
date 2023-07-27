package com.arkjj.Services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.arkjj.model.User;
import com.google.gson.Gson;

public class UserService {
    private List<User> connectedUsers;
    private static Gson gson = new Gson();

    public UserService() {

    }

    public List<User> getConnectedUsers() {
        fetchAllCOnectedUsers();
        return connectedUsers;
    }

    public void setConnectedUsers(List<User> connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    private void fetchAllCOnectedUsers() {
        String apiURL = "http://localhost:8080/sessions/findAll";
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(apiURL))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpResponse<String> response = httpClient.send(getRequest,
                    HttpResponse.BodyHandlers.ofString());

            User[] userArray = gson.fromJson(response.body(), User[].class);
            // Convert the array to a List if needed
            this.connectedUsers = Arrays.asList(userArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<User> getUser(String username) {
        for (User user : connectedUsers) {
            if (user.getUsername().equals(username)) {
                return Optional.ofNullable(user);
            }
        }
        return null;
    }

    public String getUserIDByName(String username) {
        for (User user : connectedUsers) {
            if (user.getUsername().equals(username)) {
                return user.getId();
            }
        }
        return null;
    }
}
