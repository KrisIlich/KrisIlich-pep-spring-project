package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exceptions.ApiExceptions.BadRequestException;
import com.example.exceptions.ApiExceptions.DuplicateResourceException;
import com.example.exceptions.ApiExceptions.ExceptionKnownButOk;
import com.example.exceptions.ApiExceptions.UnauthorizedException;
import com.example.exceptions.ApiExceptions.ResourceNotFoundException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;
    
        @Autowired
        public MessageService(MessageRepository messageRepository, AccountRepository accountRepository)  {
            this.messageRepository = messageRepository;
            this.accountRepository = accountRepository;
    }

    public Message newMessage(Message message){
        if (message.getMessageText().trim().isBlank() || message.getMessageText() == null) {
            throw new BadRequestException("Message is blank");
        }
        if (message.getMessageText().length() > 255) {
            throw new BadRequestException("Message should be under 255 characters");
        }

        int postedId = message.getPostedBy();

        if (message.getPostedBy() == null || !accountRepository.existsById(postedId)) {
            throw new BadRequestException("Needs to be posted by a real person");
        }
        return messageRepository.save(message);
    }

    public List<Message> getMessages() {
        return (List<Message>)messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int messageId){
        return messageRepository.findById(messageId);
    }

    public int deleteMessageById(int messageId){
        if (messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    public int patchMessage(int messageId, String newMessageText) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new BadRequestException("Message not found"));
        
        if (newMessageText.trim().isBlank() || newMessageText == null) {
            throw new BadRequestException("Message is blank");
        }
        
        if ( newMessageText.length() > 255) {
            throw new BadRequestException("Message should be under 255 characters");
        }
       
        message.setMessageText(newMessageText);
        messageRepository.save(message);
        return 1;
    }

    public List<Message> getMessagesByUser(int accountId)  {
        return messageRepository.findByPostedBy(accountId);
    }

}
