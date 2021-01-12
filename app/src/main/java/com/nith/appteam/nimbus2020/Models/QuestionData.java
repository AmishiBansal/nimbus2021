package com.nith.appteam.nimbus2020.Models;

public class QuestionData {
    private String question;
    private String questionid;
    private String option_1;
    private String option_2;
    private String option_3;
    private String option_4;
    private int option_chosen;

    public QuestionData(String questionid, String question, String option_1, String option_2,
                        String option_3, String option_4) {
        this.question = question;
        this.questionid = questionid;
        this.option_1 = option_1;
        this.option_2 = option_2;
        this.option_3 = option_3;
        this.option_4 = option_4;
        this.option_chosen = 0;
    }

    public String getQuestion() {
        return question;
    }

    public String getQuestionid() {
        return questionid;
    }

    public String getOption_1() {
        return option_1;
    }

    public String getOption_2() {
        return option_2;
    }

    public String getOption_3() {
        return option_3;
    }

    public String getOption_4() {
        return option_4;
    }

    public int getOption_chosen() {
        return option_chosen;
    }

    public void setOption_chosen(int option_chosen) {
        this.option_chosen = option_chosen;
    }
}
