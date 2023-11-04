package ru.fso13.jmsmockandstubtools.api;


import java.time.Duration;

public record Queue(String name, JmsType type, String comment, Duration delay, String stubMessage, String replyTo) {
}