package com.tub;

import java.util.concurrent.ConcurrentHashMap;

public class ChatStorage {

    public static final ConcurrentHashMap<String,ChatMessage> messages=new ConcurrentHashMap<>();

    private ChatStorage(){

    }
}
