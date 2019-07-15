package com.example.highschoolgrades;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final class GymnasiegemensammaKurser {
        public static final String[] ggCourses = {"SVE / SVA 1", "SVE / SVA 2", "SVE / SVA 3",
                "Engelska 5", "Engelska 6", "Historia 1b", "Idrott 1",
                "Matte 1", "Matte 2", "Matte 3", "Religion 1b", "Samhällskunskap 1b"};
        public static final double[] ggGrades = {20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0};
        public static final double[] ggPoints = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 50, 100};

    }

    public static final Course[] highSchoolSharedCourses = {
            new Course("SVE / SVA 1", 20.0, 100),
            new Course("SVE / SVA 2", 20.0, 100),
            new Course("SVE / SVA 3", 20.0, 100),
            new Course("Engelska 5", 20.0, 100),
            new Course("Engelska 6", 20.0, 100),
            new Course("Historia 1b", 20.0, 100),
            new Course("Idrott 1", 20.0, 100),
            new Course("Matte 1", 20.0, 100),
            new Course("Matte 2", 20.0, 100),
            new Course("Matte 3", 20.0, 100),
            new Course("Religion 1b", 20.0, 50),
            new Course("Samhällskunskap 1b", 20.0, 100),
    };

}
