//package ui;
//
//import exceptions.NegativeValueException;
//import exceptions.OutGradeBoundException;
//import exceptions.OutWeightBoundException;
//import model.*;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Scanner;
//
//import static ui.UserApp.showLocalTime;
//
////Represents a teaching assistant console-based user interface with all the functions which a ta could invoke.
//public class TeachingAssistantConsole {
//    private final UserApp app;
//    private List<User> users;
//
//    public TeachingAssistantConsole(User ta, UserApp app) {
//        this.app = app;
//        teachingAssistantPanel(ta);
//    }
//
//    // EFFECTS: View all the students' ID who have registered this application.
//    public void teachingAssistantPanel(User user) {
//        users = app.getUsers();
//        Scanner input = new Scanner(System.in);
//        System.out.println("------------------------------------------------------");
//        System.out.println("Login time: " + showLocalTime());
//        System.out.println("Hello, " + user.getName() + "!");
//        System.out.println("You are assigned to create and grade assignments in CPSC210.");
//        System.out.println("Find this course in students' schedule.");
//        System.out.println("Enter the item number.");
//        System.out.println("1> View students                 2> Quit");
//        System.out.print(">>>");
//        int num = input.nextInt();
//        if (num == 1) {
//            printAllStudent(user);
//        } else if (num == 2) {
//            quit(user);
//        } else {
//            System.out.println("Please type in the correct number!");
//            teachingAssistantPanel(user);
//        }
//    }
//
//    //EFFECTS: QUIT helper method
//
//    public void quit(User user) {
//        System.out.println("Log out time: " + showLocalTime());
//        System.out.println("Goodbye, " + user.getName() + "!");
//        app.end();
//    }
//
//    //EFFECTS: print all students' ID in this application (registered students)
//
//    public void printAllStudent(User user) {
//        Scanner input = new Scanner(System.in);
//        boolean flag = false;
//        for (User member : users) {
//            if (!(member.getID().length() == 3)) {
//                System.out.println(member.getID());
//                flag = true;
//            }
//        }
//        if (!flag) {
//            System.out.println("NO students have registered on this application!");
//            teachingAssistantPanel(user);
//        } else {
//            System.out.println("Type in the ID of the student.");
//            String str = input.nextLine();
//            viewScheduleTA(user, str);
//        }
//    }
//
//    //EFFECTS: view the schedule of a student
//    public void viewScheduleTA(User user, String str) {
//        Scanner input = new Scanner(System.in);
//        User member;
//        for (User user1 : users) {
//            if (user1.getID().equals(str)) {
//                member = user1;
//                if (((Student) user1).getSchedule().getCourses().isEmpty()) {
//                    System.out.println("This student has not registered any course.");
//                    teachingAssistantPanel(user);
//                } else {
//                    for (Course course : ((Student) user1).getSchedule().getCourses()) {
//                        System.out.println(course.getCourseIDForGrade());
//                    }
//                    System.out.println("Enter the item number please.");
//                    System.out.println("1> Select course                     2> Back");
//                    System.out.print(">>>");
//                    int num = input.nextInt();
//                    distributeFunction2(num, member, user, str);
//                }
//            }
//        }
//        System.out.println("Please type in the correct ID above.");
//        teachingAssistantPanel(user);
//    }
//
//    //EFFECTS: use different number to call different methods  (after viewing student's schedule)
//    public void distributeFunction2(int num, User student, User user, String str) {
//        Scanner input = new Scanner(System.in);
//        if (num == 1) {
//            System.out.println("Which course do you want to select? Key in courseID.");
//            String courseID = input.nextLine();
//            for (Course course1 : ((Student) student).getSchedule().getCourses()) {
//                if (course1.getCourseIDForGrade().equals(courseID)) {
//                    createAssignmentPanel(course1, user);
//                }
//            }
//            System.out.println("Please key in the course within the list.");
//        }
//        viewScheduleTA(user, str);
//    }
//
//    //EFFECTS: assignment managing panel.
//    public void createAssignmentPanel(Course course, User user) {
//        Scanner input = new Scanner(System.in);
//        if (course.getAssignments().isEmpty()) {
//            System.out.println("You have to create an assignment first!");
//        } else {
//            for (Assignment assignment : course.getAssignments()) {
//                System.out.println("Component: " + assignment.getComponent());
//                System.out.println("Weight: " + assignment.getWeight());
//                System.out.println("Grade: " + assignment.getGrade());
//            }
//        }
//        System.out.println("-------------------------------------------------------------");
//        System.out.println("1> Create assignment");
//        System.out.println("2> Remove assignment");
//        System.out.println("3> Grade assignment");
//        System.out.println("4> Back to panel");
//        System.out.println("Enter the item number please.");
//        System.out.print(">>>");
//        int num = input.nextInt();
//        distributeFunction3(course, user, num);
//    }
//
//
//    //EFFECTS: use different number to call different methods  (after creating assignments)
//    public void distributeFunction3(Course course, User user, int num) {
//        Scanner input = new Scanner(System.in);
//        Scanner input1 = new Scanner(System.in);
//        if (num == 1) {
//            createAssignment(course, user);
//        } else if (num == 2) {
//            System.out.println("Which assignment do you want to remove? Key in component.");
//            String str = input.nextLine();
//            removeAssignment(course, user, str);
//        } else if (num == 3) {
//            System.out.println("Which assignment do you want to grade? Key in component.");
//            String str1 = input1.nextLine();
//            gradeAssignment(course, user, str1);
//        } else {
//            teachingAssistantPanel(user);
//        }
//    }
//
//
//    //MODIFIES: Assignment
//    //EFFECTS: create an assignment with component and weight for a student
//    public void createAssignment(Course course, User user) {
//        Assignment assignment = new Assignment("",0,"");
//        Scanner input = new Scanner(System.in);
//        Scanner input1 = new Scanner(System.in);
//        System.out.println("Key in the elements carefully in the order of 'component', 'weight'.");
//        String component = input.nextLine();
//        double weight = input1.nextDouble();
//        try {
//            assignment.createAssignment(component,weight,user.getName());
//        } catch (NegativeValueException e) {
//            System.err.println("Negative value is not acceptable for the weight of the assignment.");
//            createAssignment(course,user);
//        } catch (OutWeightBoundException e) {
//            System.err.println("An assignment should not weigh more than 1.");
//            createAssignment(course,user);
//        }
//        course.getAssignments().add(assignment);
//        System.out.println("You have create an assignment successfully!");
//        createAssignmentPanel(course, user);
//    }
//
//    //MODIFIES: Assignments
//    //EFFECTS: remove the assignment from the course.
//    //References: How to solve the concurrent modification exception in java?
//    // https://stackoverflow.com/questions/1921104/loop-on-list-with-remove
//    public void removeAssignment(Course course, User user, String str) {
//        boolean flag = false;
////        for (int i = 0; i < course.getAssignments().size(); i++) {
////            if (course.getAssignments().get(i).getComponent().equals(str)) {
////                course.getAssignments().remove(i);
////                flag = true;
////            }
////        }
//        for (Iterator<Assignment> iterator = course.getAssignments().iterator(); iterator.hasNext();) {
//            Assignment assignment = iterator.next();
//            if (assignment.getComponent().equals(str)) {
//                iterator.remove();
//                course.setGradingFinalStatus(false);
//                flag = true;
//            }
//        }
//        if (flag) {
//            System.out.println("You remove the assignment successfully!");
//        } else {
//            System.out.println("There is no assignment with this name. Try to remove again.");
//        }
//        createAssignmentPanel(course, user);
//    }
//
//
//
//    //MODIFIES: Assignment
//    //EFFECTS: grade an assignment, you can put grade and feedback in it.
//    public void gradeAssignment(Course course, User user, String str) {
//        Scanner input = new Scanner(System.in);
//        Scanner input1 = new Scanner(System.in);
//        for (Assignment assignment : course.getAssignments()) {
//            if (assignment.getComponent().equals(str)) {
//                System.out.println("Please type in the 'grade' and 'feedback' only in this specified order.");
//                double grade = input.nextDouble();
//                String feedback = input1.nextLine();
//                try {
//                    assignment.gradeAssignment(grade,feedback);
//                } catch (NegativeValueException e) {
//                    System.err.println("Negative value is not acceptable for the grade of the assignment.");
//                    gradeAssignment(course,user,str);
//                } catch (OutGradeBoundException e) {
//                    System.err.println("The grade should not be more than 100.0.");
//                    gradeAssignment(course,user,str);
//                }
//                System.out.println("You have finished grading this student");
//                createAssignmentPanel(course, user);
//            }
//        }
//        System.out.println("There is no assignment with this name. Try to remove again.");
//        createAssignmentPanel(course, user);
//    }
//}
