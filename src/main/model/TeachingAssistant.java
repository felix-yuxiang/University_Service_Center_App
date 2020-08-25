package model;

import org.json.simple.JSONObject;

// It represents a teaching assistant with name and ID.
public class TeachingAssistant implements User {

    private String teachingAssistantName;
    private String teachingAssistantID;

    public TeachingAssistant(String teachingAssistantName, String teachingAssistantID) {
        this.teachingAssistantName = teachingAssistantName;
        this.teachingAssistantID = teachingAssistantID;
    }

    //MODIFIES: json object.
    //EFFECTS: return a JSON object with all teaching assistant detail to write in.
    @SuppressWarnings("unchecked")
    public JSONObject teachingAssistantDetails() {
        JSONObject ta = new JSONObject();

        ta.put("Name of the teaching assistant", teachingAssistantName);
        ta.put("Teaching assistant ID", teachingAssistantID);

        return ta;
    }


    //EFFECTS: return the name of this TA
    @Override
    public String getName() {
        return teachingAssistantName;
    }

    //MODIFIES: this
    //EFFECTS: set the name of this TA
    @Override
    public void setName(String name) {
        teachingAssistantName = name;
    }

    //EFFECTS: return the ID of this TA
    @Override
    public String getID() {
        return teachingAssistantID;
    }

    //MODIFIES: this
    //EFFECTS: set the ID of this TA
    @Override
    public void setID(String teachingAssistantID) {
        this.teachingAssistantID = teachingAssistantID;
    }


}
