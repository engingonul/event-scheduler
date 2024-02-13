package com.mycompany.eventscheduler.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRequest {
    @NotBlank(message = "Name must be provided")
    private String name;

    @NotBlank(message = "Duration must be provided")
    @Pattern(regexp = "^(\\d+min|lightning)$",   message = "Duration must be in minutes or lightning. ex. 45min or lightning")
    private String duration;
}
