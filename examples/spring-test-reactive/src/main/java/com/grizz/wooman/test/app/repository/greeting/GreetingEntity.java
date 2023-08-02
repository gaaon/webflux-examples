package com.grizz.wooman.test.app.repository.greeting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("GREETING")
public class GreetingEntity {
    @Id
    private Long id;
    private String greeting;
    private String who;

    public GreetingEntity(String greeting, String who) {
        this.greeting = greeting;
        this.who = who;
    }
}
