package ui.data;


import exceptions.FacultyNotFoundException;

// represents the paths of faculty courses
//References: BCS-Degree Navigator (https://github.com/def-not-ys/BCS-Degree-Navigator)
public class CoursePath {
    public CoursePath() {

    }

    // returns the http path of the faculty
    public String findPath(String faculty) {
        try {
            return findFacultyDataPath(faculty);
        } catch (FacultyNotFoundException e) {
            return "error";
        }
    }

    //REQUIRES: faculty must be one of "CPSC", "MATH", "STAT", "PSYC", "ECON", "COMM"
    // EFFECTS: returns the data path for this faculty
    public String findFacultyDataPath(String faculty) throws FacultyNotFoundException {
        switch (faculty) {
            case "CPSC":
                return "./data/CPSC-raw-data.txt";
            case "MATH":
                return "./data/MATH-raw-data.txt";
            case "STAT":
                return "./data/STAT-raw-data.txt";
            case "PSYC":
                return "./data/PSYC-raw-data.txt";
            case "ECON":
                return "./data/ECON-raw-data.txt";
            case "COMM":
                return "./data/COMM-raw-data.txt";
            default:
                throw new FacultyNotFoundException();
        }
    }
}
