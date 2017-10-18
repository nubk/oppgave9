package controller;

import io.javalin.Context;
import model.Exercise;

import java.util.Arrays;
import java.util.List;

public class ExerciseController {

    private static List<Exercise> exercises = Arrays.asList(
            new Exercise("Exercise 1", "Description 1", "Print 'Hello World'", "Hello World"),
            new Exercise("Exercise 2", "Description 2", "Print 'Hello Dorld'", "Hello Dorld"),
            new Exercise("Exercise 3", "Description 3", "Print 'Hello Corld'", "Hello Corld")
    );

    public static void getAllExercises(Context ctx) {
        ctx.json(exercises);
    }

    public static void getExercise(Context ctx) {
        ctx.json(exercises);
    }

}