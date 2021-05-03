package com.example.urlopy;

import java.time.LocalDate;
import java.sql.Date;

public class VacationDB implements Comparable<VacationDB> {

    private int vacationId;
    private int userId;
    private Date startDate;
    private Date endDate;
    private boolean accepted;

    public VacationDB(int userId, Date startDate, Date endDate, boolean accepted) {
        this.userId=userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accepted = accepted;
    }

    public VacationDB(int vacationId, int userId, Date startDate, Date endDate, boolean accepted) {
        this.vacationId = vacationId;
        this.userId=userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accepted = accepted;
    }

    @Override
    public int compareTo(VacationDB o) {
        return Integer.compare(this.vacationId, o.vacationId);
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}

