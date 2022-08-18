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
        String databaseLink = "https://test-b2d47-default-rtdb.asia-southeast1.firebasedatabase.app/";
        String storageLink = "gs://test-b2d47.appspot.com";
    }

    interface Sender {
        String name = "name";
        String password = "password";
        String phone = "phone";
        String gender = "gender";
        String address = "address";
        String sharePreferences = "dataLogin";
        String users = "users";
        String word = "word";
        String noteId = "id";
        String noteTitle = "title";
        String noteContent = "content";
        String loginState = "loginState";
        String searchQuery = "query";
        String randomWord = "randomWord";
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
        String notExisted = "Not existed";
        String notMatch = "Not match with password";
        String noData = "No data found...";
        String network = "Network problem...";
        String login = "You have to login to backup data !!!";
        String profileClick = "You have to login to see your profile";
        String audio = "Can't play this audio";
    }

    interface Regex {
        String emailRegex = "^(.+)@(.+)$";
        String randomWord = "[\"\\[\\]]";
    }

    interface Success {
        String created = "Created success fully";
        String uploaded = "Image uploaded";
        String update = "Profile info update successfully";
        String deleted = "Delete successfully";
    }

    interface webUrl {
        String lesson = "file:///android_asset/Lesson%d.html";
    }
}
