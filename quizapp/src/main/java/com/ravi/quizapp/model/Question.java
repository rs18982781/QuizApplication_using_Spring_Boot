package com.ravi.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.annotation.processing.Generated;
import java.lang.reflect.Type;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String difficultylevel;
    private String rightAnswer;
    private String category;

}
