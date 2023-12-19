package models;



public class Employee {
    private String interviewDate;
    private String month;
    private String team;
    private String panelName;
    private String round;
    private String skill;
    private String time;
    private String candidateCurrentLoc;
    private String candidatePreferredLoc;
    private String candidateName;


    public Employee(String date, String month, String team, String panelName, String round,
                    String skill, String time, String candidateCurrentLoc, String candidatePreferredLoc,
                    String candidateName) {
        this.interviewDate = date;
        this.month = month;
        this.team = team;
        this.panelName = panelName;
        this.round = round;
        this.skill = skill;
        this.time = time;
        this.candidateCurrentLoc = candidateCurrentLoc;
        this.candidatePreferredLoc = candidatePreferredLoc;
        this.candidateName = candidateName;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(String date) {
        this.interviewDate = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCandidateCurrentLoc() {
        return candidateCurrentLoc;
    }

    public void setCandidateCurrentLoc(String candidateCurrentLoc) {
        this.candidateCurrentLoc = candidateCurrentLoc;
    }

    public String getCandidatePreferredLoc() {
        return candidatePreferredLoc;
    }

    public void setCandidatePreferredLoc(String candidatePreferredLoc) {
        this.candidatePreferredLoc = candidatePreferredLoc;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{" +
                "date='" + interviewDate + '\'' +
                ", month='" + month + '\'' +
                ", team='" + team + '\'' +
                ", panelName='" + panelName + '\'' +
                ", round='" + round + '\'' +
                ", skill='" + skill + '\'' +
                ", time='" + time + '\'' +
                ", candidateCurrentLoc='" + candidateCurrentLoc + '\'' +
                ", candidatePreferredLoc='" + candidatePreferredLoc + '\'' +
                ", candidateName='" + candidateName + '\'' +
                '}';
    }



}
