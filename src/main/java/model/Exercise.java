package model;

import java.util.Map;

public class Exercise {

    public String id;
    public String title;
    public String description;
    public String instructions;
    public Map<String, String> testCode;

    //todo: how to store test-code and/or solution ???

    public Exercise(String title, String description, String instructions, Map<String, String> testCode) {
        this.id = title.toLowerCase().replaceAll(" ", "-"); // todo: reconsider
        this.title = title;
        this.description = description;
        this.instructions = instructions;
        this.testCode = testCode;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructions() {
        return instructions;
    }

    public Map<String, String> getTestCode() {
        return testCode;
    }

}
