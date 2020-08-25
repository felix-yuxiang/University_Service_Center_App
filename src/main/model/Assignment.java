package model;

import exceptions.NegativeValueException;
import exceptions.OutGradeBoundException;
import exceptions.OutWeightBoundException;
import org.json.simple.JSONObject;

// It represents an assignment with component, weight, grading status, grade and feedback,
// It also includes the name of teaching assistant who grade the assignment.
public class Assignment {
    private String component;
    private double weight;
    private boolean gradingStatus;
    private double grade;
    private String feedback;
    private String teachingAssistantName;

    // Construct an assignment comprising component, weight, grading status, grades, feedback.
    public Assignment(String component, double weight, String teachingAssistantName) {
        this.component = component;
        this.weight = weight;
        this.teachingAssistantName = teachingAssistantName;
        grade = 0;
        gradingStatus = false;
        feedback = "";
    }

    //EFFECTS: If the assignment is graded, return TA's feedback.
    //         Otherwise, print that the assignment is currently being graded.
    public String printGradingStatus() {
        String str;
        if (gradingStatus) {
            str = "Your assignment grade is " + grade + "%\n"
                    + "Your assignment is graded by " + teachingAssistantName + ".\n Here is my feedback: "
                    + feedback + "\n Feel free to contact me if you have any issue regarding to this assignment!";

            return str;
        }
        str = "Your assignment grading is in process, please wait patiently.";
        return str;
    }

    //MODIFIES: this
    //EFFECTS: create an assignment with component and weight.
    //         if weight is less than 0, throw a Negative value exception
    //         if weight is bigger than 1, throw an out of weight bound exception
    public void createAssignment(String component, double weight, String name)
            throws NegativeValueException,
            OutWeightBoundException {
        if (weight < 0) {
            throw new NegativeValueException();
        } else if (weight > 1) {
            throw new OutWeightBoundException();
        } else {
            this.component = component;
            this.weight = weight;
            this.teachingAssistantName = name;
        }
    }

    //MODIFIES: this
    //EFFECTS: grade an assignment with corresponding score and weight.
    //         if grade is less than 0, throw a Negative value exception
    //         if grade surpasses 100, throw an out of grade bound exception
    public void gradeAssignment(double grade, String feedback) throws NegativeValueException,
            OutGradeBoundException {
        if (grade < 0) {
            throw new NegativeValueException();
        } else if (grade > 100) {
            throw new OutGradeBoundException();
        } else {
            gradingStatus = true;
            this.grade = grade;
            this.feedback = feedback;
        }
    }

    //EFFECTS: get the component entry
    public String getComponent() {
        return component;
    }

    //MODIFIES: this
    //EFFECTS: set the component entry of this assignment.
    public void setComponent(String component) {
        this.component = component;
    }

    //EFFECTS: return the weight of this assignment.
    public double getWeight() {
        return weight;
    }

    //REQUIRES: Weight is not negative and less than 1.
    //MODIFIES: this
    //EFFECTS: set the weight of this assignment.
    public void setWeight(double weight) {
        this.weight = weight;
    }

    //EFFECTS: return the grading status.
    public boolean isGradingStatus() {
        return gradingStatus;
    }


    //MODIFIES: this
    //EFFECTS: set the grading status of this assignment.
    public void setGradingStatus(boolean gradingStatus) {
        this.gradingStatus = gradingStatus;
    }

    //EFFECTS: get the grade of this assignment.
    public double getGrade() {
        return grade;
    }

    //REQUIRES: grade is not negative and less than 1.
    //MODIFIES: this
    //EFFECTS: change the grade of this assignment.
    public void setGrade(double grade) {
        this.grade = grade;
    }

    //EFFECTS: return the TA feedback of the assignment.
    public String getFeedback() {
        return feedback;
    }

    //MODIFIES: this
    //EFFECTS: give the feedback of this assignment.
    public void giveFeedback(String feedback) {
        this.feedback = feedback;
    }

    //EFFECTS: return the name of the TA who grade your assignment.
    public String getTeachingAssistantName() {
        return teachingAssistantName;
    }

    //MODIFIES: this
    //EFFECTS: set the name of TA to grade your assignment.
    public void setTeachingAssistantName(String teachingAssistantName) {
        this.teachingAssistantName = teachingAssistantName;
    }

    //MODIFIES: json object.
    //EFFECTS: return a JSON object with all the assignment detail to write in.
    @SuppressWarnings("unchecked")
    public JSONObject assignmentDetails() {
        JSONObject assignment = new JSONObject();

        assignment.put("Component", component);
        assignment.put("Weight", weight);
        assignment.put("Grading Status", gradingStatus);
        assignment.put("Grade",grade);
        assignment.put("Feedback", feedback);
        assignment.put("Teaching assistant", teachingAssistantName);

        return assignment;
    }

    @Override
    public String toString() {
        return this.component;
    }
}
