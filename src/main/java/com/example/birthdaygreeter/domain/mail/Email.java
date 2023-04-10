package com.example.birthdaygreeter.domain.mail;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Email {

    public String to;
    public String subject;
    public String body;

    @Override
    public String toString() {
        return "Email{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body=" + body +
                '}';
    }
}
