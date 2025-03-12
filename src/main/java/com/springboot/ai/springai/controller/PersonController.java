package com.springboot.ai.springai.controller;

import com.springboot.ai.springai.model.Person;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final ChatClient chatClient;

    public PersonController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping
    List<Person> findAll() {
        PromptTemplate pt = new PromptTemplate("""
                Return a current list of 10 famous persons if exists or generate a new list with random values.
                Each object should contain an auto-incremented id field.
                Do not include any explanations or additional text.
                """);

        return this.chatClient.prompt(pt.create())
                .call()
                .entity(new ParameterizedTypeReference<>() {});
    }

}
