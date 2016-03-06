package com.example.spanishgrammarapp.Data;


/**
 * Created by JAESEOKAN on 06/03/2016.
 */
public class QuestionData {

    private String questionText;
    private String choice_1;
    private String choice_2;
    private String choice_3;
    private String choice_4;
    private String choice_5;
    private String choice_6;
    private String correct_answer;
    private String created_at;


    //constructor
    public QuestionData()
    {
    }

    public QuestionData(String questionText, String choice_1, String choice_2,
                        String choice_3, String choice_4, String choice_5, String choice_6
    , String correct_answer)
    {
        this.questionText = questionText;
        this.choice_1 = choice_1;
        this.choice_2 = choice_2;
        this.choice_3 = choice_3;
        this.choice_4 = choice_4;
        this.choice_5 = choice_5;
        this.choice_6 = choice_6;
        this.correct_answer = correct_answer;
    }

    // setters

    public void setQuestionText(String questionText){
        this.questionText=questionText;
    }public void setChoice_1(String choice_1){
        this.choice_1=choice_1;
    }public void setChoice_2(String choice_2){
        this.choice_2=choice_2;
    }public void setChoice_3(String choice_3){
        this.choice_3=choice_3;
    }public void setChoice_4(String choice_4){
        this.choice_4=choice_4;
    }public void setChoice_5(String choice_5){
        this.choice_5=choice_5;
    }public void setChoice_6(String choice_6){
        this.choice_6=choice_6;
    }public void setCorrect_answer(String correct_answer){
        this.correct_answer=correct_answer;
    }public void setCreated_at(String created_at){
        this.created_at=created_at;
    }

    public String getQuestionText(){
        return questionText;
    }

    public String getChoice_1() {
        return choice_1;
    }

    public String getChoice_2() {
        return choice_2;
    }

    public String getChoice_3() {
        return choice_3;
    }

    public String getChoice_4() {
        return choice_4;
    }

    public String getChoice_5() {
        return choice_5;
    }

    public String getChoice_6() {
        return choice_6;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }
}
