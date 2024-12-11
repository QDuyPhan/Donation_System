package com.donation.donation_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableScheduling
public class DonationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonationSystemApplication.class, args);
//        openHomePage();
    }


}
