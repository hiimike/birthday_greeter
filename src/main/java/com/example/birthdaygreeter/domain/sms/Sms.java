package com.example.birthdaygreeter.domain.sms;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Sms {

    public String subject;
    public String body;

    @Override
    public String toString() {
        return "Sms{" +
                "subject='" + subject + '\'' +
                ", body=" + body +
                '}';
    }
}
