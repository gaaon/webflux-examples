package com.campusgram.user.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendNotiMessage {
    private String targetUserId;
    private String message;
}
