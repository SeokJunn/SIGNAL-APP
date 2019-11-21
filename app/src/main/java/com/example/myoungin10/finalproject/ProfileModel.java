package com.example.myoungin10.finalproject;

public class ProfileModel {
    public int profile;        // R.drawable.리소스 아이디
    public String name;         // 대화명
    public String chat;         // 마지막 대화 내용
    public long timestamp;   // 마지막 대화 내용

    public static ProfileModel newInstance(int profile, String name, String chat, long timestamp) {
        ProfileModel instance = new ProfileModel();
        instance.profile = profile;
        instance.name = name;
        instance.chat = chat;
        instance.timestamp = timestamp;

        return instance;
    }
}