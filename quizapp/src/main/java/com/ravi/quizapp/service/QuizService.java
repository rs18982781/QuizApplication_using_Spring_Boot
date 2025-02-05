package com.ravi.quizapp.service;

import com.ravi.quizapp.dao.QuestionDao;
import com.ravi.quizapp.dao.QuizDao;
import com.ravi.quizapp.model.Question;
import com.ravi.quizapp.model.QuestionWrapper;
import com.ravi.quizapp.model.Quiz;
import com.ravi.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions =questionDao.findRandomQuestionByCategory(category,numQ);

        Quiz quiz =new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return  new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestionId(Integer id) {

       Optional<Quiz> quiz= quizDao.findById(id);
       List<Question> questionsFromDB =quiz.get().getQuestions();
       List<QuestionWrapper> questionForUsers = new ArrayList<>();
       for(Question q: questionsFromDB){
           QuestionWrapper qw =new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
           questionForUsers.add(qw);

       }
       return new ResponseEntity<>(questionForUsers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz =quizDao.findById(id).get();
        List<Question> questions =quiz.getQuestions();
        int right =0;
        int i=0;
        for(Response response: responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;

            i++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
