package ui.data;

import model.Course;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

// represents a file reader that reads course data from ubc academic calendar
// Reference: https://stackoverflow.com/questions/9611570/how-do-i-load-a-local-html-file-into-jsoup
//           BCS-Degree Navigator (https://github.com/def-not-ys/BCS-Degree-Navigator)

public class CourseReader {
    HashMap<String, Course> courses;

    // EFFECTS: constructs a web scraper
    public CourseReader(String path) throws IOException {
        courses = new HashMap<>();
        readCourses(path);
    }

    // REQUIRES: valid url, website format as UBC course description
    // MODIFIES: this
    // EFFECTS: create a list of courses from given course website url
    private void readCourses(String path) throws IOException {
        File file = new File(path);
        Document doc = Jsoup.parse(file, null);
        Elements titles = doc.body().getElementsByTag("dt");
        Elements descriptions = doc.body().getElementsByTag("dd");
        int n = titles.size();
        for (int i = 0; i < n; i++) {
            String[] str = titles.get(i).text().split(" ");
            String req = "";
            String fn = str[0];
            String cnum = str[1];
            String cid = fn + cnum;
            String cnm = "";
            for (int k = 3; k < str.length; k++) {
                cnm = cnm + str[k] + " ";
            }
            Course course = new Course(cnm, "Description not available", fn, cid, req);
            if (descriptions.size() >= i) {
                course.setCourseDescription(descriptions.get(i).text());
            }
            courses.put(cid, course);
        }
    }


    public HashMap<String, Course> getCourses() {
        return courses;
    }

}

