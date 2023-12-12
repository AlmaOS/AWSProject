package com.project.RestAppAWS.services;

import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class SameIDException extends RuntimeException {
    private final String message;
    private final String details;
}
