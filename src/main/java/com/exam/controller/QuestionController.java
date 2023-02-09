package com.exam.controller;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;
    //add question
    @PostMapping("/")
    public ResponseEntity<Question> add(@RequestBody Question question){
        System.out.println("my issue :" +question);
        return   ResponseEntity.ok(this.questionService.addQuestion(question));
    }

    //update question
    @PutMapping("/")
    public ResponseEntity<Question> update(@RequestBody Question question){
        return   ResponseEntity.ok(this.questionService.updateQuestion(question));
    }

    //gett all question of any quiz
    @GetMapping("/quiz/{quizid}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("quizid") long quizid){
//        Quiz quiz=new Quiz();
//        quiz.setQid(quizid);
//        Set<Question> questionsOfQuiz=this.questionService.getQuestionOfQuiz(quiz);
//        return   ResponseEntity.ok(questionsOfQuiz);
        Quiz quiz=this.quizService.getQuiz(quizid);
        Set<Question> questions=quiz.getQuestions();
        List<Question> list=new ArrayList<>(questions);
        if (list.size()>Integer.parseInt(quiz.getNumberOfQuestions())){
            list=list.subList(0,Integer.parseInt((quiz.getNumberOfQuestions()+1)));
        }
        list.forEach((q)->{
            q.setAnswer("");
        });

        Collections.shuffle(list);
        return  ResponseEntity.ok(questions);
    }


    //gett all question of any quiz
    @GetMapping("/quiz/all/{quizid}")
    public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("quizid") long quizid){
        Quiz quiz=new Quiz();
        quiz.setQid(quizid);
        Set<Question> questionsOfQuiz=this.questionService.getQuestionOfQuiz(quiz);
        return   ResponseEntity.ok(questionsOfQuiz);
    }


    //get single question
    @GetMapping("/{questid}")
    public Question get(@PathVariable("questid") long questid){
        return this.questionService.getQuestion(questid);
    }

    //get single question
    @DeleteMapping ("/{questid}")
    public void delete(@PathVariable("questid") long questid){
         this.questionService.deleteQuestion(questid);
    }

    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions ){
        System.out.println(questions);
       double marksGot=0;
        int correctAnswers=0;
        int attempted=0;
        for(Question q:questions){
             Question question= this.questionService.get(q.getQuesId());
             if(question.getAnswer().equals((q.getGivenAnswer()))){
                 //correct
                 correctAnswers++;
                 double marksSingle=Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();
                 marksGot+=marksSingle;
             }
             if(q.getGivenAnswer()!=null){
                    attempted++;
             }
        }
        Map<String,Object> map=Map.of("marksGot",marksGot,"correctAnswer",correctAnswers,"attempted",attempted);
        return ResponseEntity.ok(map);
    }

}
