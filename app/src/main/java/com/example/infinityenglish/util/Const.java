package com.example.infinityenglish.util;

public interface Const {
    interface Database {
        String video = "Videos";
        String admin = "Admins";
        String news = "News";
        String user = "Users";
        String name = "name";
        String password = "password";
        String phone = "phone";
        String address = "address";
        String gender = "gender";
        String image = "image";
        String videos = "video";
        String category = "category";
        String content = "content";
    }

    interface Sender {
        String name = "name";
        String password = "password";
        String phone = "phone";
        String gender = "gender";
        String address = "address";
        String videoName = "videoName";
        String videoImageUrl = "videoImageUrl";
        String videoUrl = "videoUrl";
        String newsTitle = "newsTitle";
        String newsImage = "newsImage";
        String contentUrl = "contentUrl";
        String url = "url";
        String SHARED_PREFERENCES = "dataLogin";
        String users = "users";
    }

    interface Error {
        String name = "Please write your name...";
        String password = "Please write your password...";
        String phone = "Please write your phone...";
        String address = "Please write your address...";
        String information = "Wrong information";
        String wrongPhone = "You have input wrong phone number...";
        String existed = "existed";
        String notexisted = "Not existed";
        String notmatch = "Not match with password";
        String noData = "No data found...";
    }

    interface Success {
        String created = "Created success fully";
        String update = "Profile info update successfully";
        String login = "Logged in Successfully";
    }
}
