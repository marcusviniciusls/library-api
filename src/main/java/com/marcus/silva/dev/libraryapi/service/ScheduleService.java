package com.marcus.silva.dev.libraryapi.service;

import com.marcus.silva.dev.libraryapi.dto.response.BookReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private static final String CRON_LATE_LOANS = "0 0 0 1/1 * ?";
    @Autowired private BookService bookService;
    @Autowired private EmailService emailService;
    @Value("${application.mail.lateloans.message}")
    private String message;

    @Scheduled(cron = CRON_LATE_LOANS)
    public void sendMailToLateLoans(){
        List<BookReturnResponse> listBookReturn = bookService.bookLaterAndLimitTenDays();
        List<String> emailList = listBookReturn.stream().map(book -> book.getEmail()).collect(Collectors.toList());

        emailService.sendEmail(message, emailList);
    }
}
