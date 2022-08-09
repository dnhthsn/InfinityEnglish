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
        String notexisted = "Not existed";
        String notmatch = "Not match with password";
        String noData = "No data found...";
        String network = "Network problem...";
        String login = "You have to login to backup data !!!";
        String profileClick = "You have to login to see your profile";
    }

    interface Regex {
        String emailRegex = "^(.+)@(.+)$";
        String randomWord = "[\"\\[\\]]";
    }

    interface Success {
        String created = "Created success fully";
        String update = "Profile info update successfully";
        String deleted = "Delete successfully";
    }

    interface webUrl {
        String lesson1 = "file:///android_asset/Lesson1.html";
        String lesson2 = "file:///android_asset/Lesson2.html";
        String lesson3 = "file:///android_asset/Lesson3.html";
        String lesson4 = "file:///android_asset/Lesson4.html";
        String lesson5 = "file:///android_asset/Lesson5.html";
        String lesson6 = "file:///android_asset/Lesson6.html";
        String lesson7 = "file:///android_asset/Lesson7.html";
        String lesson8 = "file:///android_asset/Lesson8.html";
        String lesson9 = "file:///android_asset/Lesson9.html";
        String lesson10 = "file:///android_asset/Lesson10.html";
        String lesson11 = "file:///android_asset/Lesson11.html";
        String lesson12 = "file:///android_asset/Lesson12.html";
        String lesson13 = "file:///android_asset/Lesson13.html";
        String lesson14 = "file:///android_asset/Lesson14.html";
        String lesson15 = "file:///android_asset/Lesson15.html";
    }
}
