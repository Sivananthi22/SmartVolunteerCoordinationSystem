package volunteer;

import java.util.Scanner;

/*
 This class controls the entire Volunteer Coordination System.
 It connects all data structures and handles user interaction.
 */
public class VolunteerCoordinationSystem {

    // Linked List to store all volunteers
    VolunteerLinkedList volunteerList = new VolunteerLinkedList();

    // Queues for different priority levels
    VolunteerQueue highPriorityQueue = new VolunteerQueue();
    VolunteerQueue mediumPriorityQueue = new VolunteerQueue();
    VolunteerQueue lowPriorityQueue = new VolunteerQueue();

    // Stack to store actions for undo functionality
    ActionStack actionStack = new ActionStack();

    // Scanner for user input
    Scanner scanner = new Scanner(System.in);

    /*
     Method to start the system
     Displays menu and processes user choices
     */
    public void startSystem() {

        int choice;

        do {

            System.out.println("\n==== Smart Volunteer Coordination System ====");
            System.out.println("1. Register Volunteer");
            System.out.println("2. Assign Task to Volunteer");
            System.out.println("3. Remove Volunteer");
            System.out.println("4. Search Volunteer");
            System.out.println("5. Display All Volunteers");
            System.out.println("6. Undo Last Operation");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    registerVolunteer();
                    break;

                case 2:
                    assignTask();
                    break;

                case 3:
                    removeVolunteer();
                    break;

                case 4:
                    searchVolunteer();
                    break;

                case 5:
                    volunteerList.displayVolunteers();
                    break;

                case 6:
                    undoOperation();
                    break;

                case 7:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 7);
    }

    /*
     Method to register a new volunteer
     */
    public void registerVolunteer() {

        System.out.print("Enter Volunteer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        // Prevent duplicate volunteer IDs
        if (volunteerList.searchById(id) != null) {
            System.out.println("Volunteer ID already exists!");
            return;
        }

        System.out.print("Enter Volunteer Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Priority Level (High / Medium / Low): ");
        String priority = scanner.nextLine();

        // Create volunteer object
        Volunteer volunteer = new Volunteer(id, name, priority);

        // Add volunteer to linked list
        volunteerList.addVolunteer(volunteer);

        // Add volunteer to correct priority queue
        if (priority.equalsIgnoreCase("High")) {

            highPriorityQueue.enqueue(volunteer);

        } else if (priority.equalsIgnoreCase("Medium")) {

            mediumPriorityQueue.enqueue(volunteer);

        } else {

            lowPriorityQueue.enqueue(volunteer);

        }

        System.out.println("Volunteer registered successfully!");
    }

    /*
     Method to assign a task to the next volunteer in queue
     */
    public void assignTask() {

        Volunteer volunteer = null;

        // Priority assignment
        if (!highPriorityQueue.isEmpty()) {

            volunteer = highPriorityQueue.dequeue();

        } else if (!mediumPriorityQueue.isEmpty()) {

            volunteer = mediumPriorityQueue.dequeue();

        } else if (!lowPriorityQueue.isEmpty()) {

            volunteer = lowPriorityQueue.dequeue();

        } else {

            System.out.println("No volunteers available for assignment.");
            return;
        }

        scanner.nextLine();

        System.out.print("Enter Task to Assign: ");
        String task = scanner.nextLine();

        volunteer.taskAssigned = task;
        volunteer.assigned = true;

        // Save action for undo
        actionStack.push(new Action("ASSIGN", volunteer));

        System.out.println("Task assigned to volunteer: " + volunteer.name);
    }

    /*
     Method to remove a volunteer
     */
    public void removeVolunteer() {

        System.out.print("Enter Volunteer ID to remove: ");
        int id = scanner.nextInt();

        // Search volunteer first
        Volunteer volunteer = volunteerList.searchById(id);

        if (volunteer == null) {

            System.out.println("Volunteer not found!");
            return;
        }

        // Remove from linked list
        boolean removed = volunteerList.removeVolunteer(id);

        if (removed) {

            // Save action for undo
            actionStack.push(new Action("REMOVE", volunteer));

            System.out.println("Volunteer removed successfully.");

        } else {

            System.out.println("Error removing volunteer.");
        }
    }

    /*
     Method to search volunteers by ID or Name
     */
    public void searchVolunteer() {

        System.out.println("Search Volunteer By:");
        System.out.println("1. ID");
        System.out.println("2. Name");

        int choice = scanner.nextInt();

        if (choice == 1) {

            System.out.print("Enter Volunteer ID: ");
            int id = scanner.nextInt();

            Volunteer volunteer = volunteerList.searchById(id);

            if (volunteer != null) {

                volunteer.displayVolunteer();

            } else {

                System.out.println("Volunteer not found.");
            }

        } else if (choice == 2) {

            scanner.nextLine();

            System.out.print("Enter Volunteer Name: ");
            String name = scanner.nextLine();

            volunteerList.searchByName(name);

        } else {

            System.out.println("Invalid choice.");
        }
    }

    /*
     Undo the last system action
     */
    public void undoOperation() {

        if (actionStack.isEmpty()) {

            System.out.println("No actions to undo.");
            return;
        }

        Action lastAction = actionStack.pop();

        if (lastAction.actionType.equals("ASSIGN")) {

            lastAction.volunteer.assigned = false;
            lastAction.volunteer.taskAssigned = "";

            System.out.println("Assignment undone for volunteer: "
                    + lastAction.volunteer.name);
        }

        else if (lastAction.actionType.equals("REMOVE")) {

            volunteerList.addVolunteer(lastAction.volunteer);

            System.out.println("Volunteer restoration successful: "
                    + lastAction.volunteer.name);
        }
    }
}