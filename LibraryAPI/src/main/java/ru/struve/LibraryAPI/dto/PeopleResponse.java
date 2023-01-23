package ru.struve.LibraryAPI.dto;

import ru.struve.LibraryAPI.models.Book;

import java.util.List;

public class PeopleResponse {
    private List<PeopleDTO> people;
    private String status;

    public PeopleResponse(List<PeopleDTO> people){
        this.people = people;
    }

    public List<PeopleDTO> getPeople() {
        return people;
    }

    public void setPeople(List<PeopleDTO> people) {
        this.people = people;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

