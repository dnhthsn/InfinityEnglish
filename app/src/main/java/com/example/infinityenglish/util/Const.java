package com.example.infinityenglish.util;

public interface Const {
    interface Database {
        String user = "Users";
        String name = "name";
        String password = "password";
        String email = "email";
        String avatar = "avatar";
        String phone = "phone";
        String address = "address";
        String gender = "gender";
        String id = "id";
        String title = "title";
        String content = "content";
        String notes = "notes";
    }

    interface Sender {
        String name = "name";
        String password = "password";
        String phone = "phone";
        String gender = "gender";
        String address = "address";
        String SHARED_PREFERENCES = "dataLogin";
        String users = "users";
        String word = "word";
        String noteId = "id";
        String noteTitle = "title";
        String noteContent = "content";
        String loginState = "loginState";
    }

    interface Error {
        String name = "Your name has to be at least 3 characters...";
        String password = "Your password have to be at least 8 characters...";
        String phone = "Please write your phone...";
        String address = "Please write your address...";
        String email = "Please write your email exactly...";
        String information = "Wrong information";
        String avatar = "Please choose your avatar...";
        String wrongPhone = "You have input wrong phone number...";
        String existed = "existed";
        String notexisted = "Not existed";
        String notmatch = "Not match with password";
        String noData = "No data found...";
        String network = "Network problem...";
        String login = "You have to login to backup data !!!";
    }

    interface Regex {
        String emailRegex = "^(.+)@(.+)$";
    }

    interface Success {
        String created = "Created success fully";
        String update = "Profile info update successfully";
        String deleted = "Delete successfully";
    }
}
