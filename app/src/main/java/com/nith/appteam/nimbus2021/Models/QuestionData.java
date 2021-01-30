package com.nith.appteam.nimbus2021.Models;

public class QuestionData {
    private String question;
    private String questionid;
    private String option_1;
    private String option_2;
    private String option_3;
    private String option_4;
    private String optionid_1;
    private String optionid_2;
    private String optionid_3;
    private String optionid_4;
    private int option_chosen;


    public QuestionData(String questionid, String question, String option_1, String option_2,
                        String option_3, String option_4,String optionid_1, String optionid_2, String optionid_3, String optionid_4) {
        this.question = question;
        this.questionid = questionid;
        this.option_1 = option_1;
        this.option_2 = option_2;
        this.option_3 = option_3;
        this.option_4 = option_4;
        this.optionid_1 = optionid_1;
        this.optionid_2 = optionid_2;
        this.optionid_3 = optionid_3;
        this.optionid_4 = optionid_4;
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

    public String getOptionid_1() {
        return optionid_1;
    }

    public void setOptionid_1(String optionid_1) {
        this.optionid_1 = optionid_1;
    }

    public String getOptionid_2() {
        return optionid_2;
    }

    public void setOptionid_2(String optionid_2) {
        this.optionid_2 = optionid_2;
    }

    public String getOptionid_3() {
        return optionid_3;
    }

    public void setOptionid_3(String optionid_3) {
        this.optionid_3 = optionid_3;
    }

    public String getOptionid_4() {
        return optionid_4;
    }

    public void setOptionid_4(String optionid_4) {
        this.optionid_4 = optionid_4;
    }
}
