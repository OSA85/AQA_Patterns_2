package ru.netology;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo {
    private final String login;
    private final String password;
    private final String status;

//    public UserInfo(String login, String password, String status) {
//        this.login = login;
//        this.password = password;
//        this.status = status;
//    }

//    public String getLogin() {
//        return login;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public String getStatus() {
//        return status;
//    }
}
