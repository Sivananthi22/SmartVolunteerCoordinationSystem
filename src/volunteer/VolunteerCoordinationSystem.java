package volunteer;

import java.util.Scanner;

/*
 Volunteer Coordination System
 This class acts as the main controller of the system.
 It connects all data structures:
 - Linked List (stores volunteers)
 - Queue (handles priority-based assignment)
 - Stack (undo functionality)
 It also handles user interaction using menu-driven approach.
*/
public class VolunteerCoordinationSystem {

    // Linked List to store all volunteers permanently
    private final VolunteerLinkedList volunteerList = new VolunteerLinkedList();

    // Separate queues for priority handling (High => Medium => Low)
    private VolunteerQueue highPriorityQueue = new VolunteerQueue();
    private VolunteerQueue mediumPriorityQueue = new VolunteerQueue();
    private VolunteerQueue lowPriorityQueue = new VolunteerQueue();

    // Stack to store actions for undo functionality
    private final ActionStack actionStack = new ActionStack();

    // Scanner object to read user input
    private final Scanner scanner = new Scanner(System.in);

    /*
     Method: startSystem()
     Description:
     Displays menu repeatedly and processes user choices.
     Uses loop until user selects Exit.
    */
    public void startSystem() {
        int choice=0;
        do {
            // Display menu options
            System.out.println("\n==== Smart Volunteer Coordination System ====");
            System.out.println("1. Register Volunteer");
            System.out.println("2. Assign Task to Volunteer");
            System.out.println("3. Remove Volunteer");
            System.out.println("4. Search Volunteer");
            System.out.println("5. Display All Volunteers");
            System.out.println("6. Undo Last Operation");
            System.out.println("7. Sort Volunteers by Priority");
            System.out.println("8. Display Volunteers by Priority");
            System.out.println("9. Exit");
            System.out.println("Enter your choice: ");

            // Handle invalid input safely
            try {
                choice = scanner.nextInt(); // read integer choice
                scanner.nextLine(); // clear buffer
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a number.");
                scanner.nextLine(); // clear wrong input
                continue; // restart loop
            }

            // Perform operation based on user choice
            switch (choice) {
                case 1:
                    System.out.println("\n========== Register Volunteer ==========");
                    registerVolunteer();
                    break;
                case 2:
                    System.out.println("\n========== Assigning Task to Volunteer ==========");
                    assignTask();
                    break;
                case 3:
                    System.out.println("\n========== Removing Volunteer ==========");
                    removeVolunteer();
                    break;
                case 4:
                    System.out.println("\n========== Searching Volunteer ==========");
                    searchVolunteer();
                    break;
                case 5:
                    displayVolunteers();
                    break;
                case 6:
                    undoOperation();
                    break;
                case 7:
                    volunteerList.sortByPriority();
                    rebuildQueues();
                    System.out.println("Volunteers have been successfully sorted based on priority.");
                    System.out.println("\n===== Sorted Volunteer List =====");
                    volunteerList.displayVolunteers();
                    break;
                case 8:
                    displayByPriority();
                    break;
                case 9:
                    System.out.println("Exiting system... ");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 9); // loop until exit
    }

    /*

 Method: registerVolunteer()
 Description:
 This method registers a new volunteer into the system.
 It performs:
 - ID validation (must be positive and unique)
 - Name validation (must not be empty)
 - Priority validation (must be High, Medium, or Low)
 After validation:
 - Volunteer is added to linked list
 - Volunteer is added to correct priority queue
*/
    public void registerVolunteer() {
        int id;
        // ID VALIDATION
        while (true) {
            try {
                System.out.print("Enter Volunteer ID: ");
                id = scanner.nextInt(); // read ID
                scanner.nextLine(); // clear buffer

                // Check if ID is positive
                if (id <= 0) {
                    System.out.println("ID must be a positive number.");
                    continue;
                }

                // Check if ID already exists
                if (volunteerList.searchById(id) != null) {
                    System.out.println("ID already exists! Enter a different ID.");
                    continue;
                }

                break; // valid ID

            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // clear invalid input
            }
        }

        // NAME VALIDATION
        String name;
        while (true) {
            System.out.print("Enter Volunteer Name: ");
            name = scanner.nextLine().trim();

            // Check if empty
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
                continue;
            }

            // Check if only letters and spaces
            if (!name.matches("[a-zA-Z ]+")) {
                System.out.println("Name should not contain numbers or symbols.");
                continue;
            }

            break; // valid name
        }
        // PRIORITY VALIDATION
        String priority;
        while (true) {
            System.out.print("Enter Priority Level (High / Medium / Low): ");
            priority = scanner.nextLine().trim();
            // Check if valid priority
            if (priority.equalsIgnoreCase("High") ||
                    priority.equalsIgnoreCase("Medium") ||
                    priority.equalsIgnoreCase("Low")) {
                break; // valid priority
            }
            System.out.println("Invalid input! Please enter High, Medium, or Low.");
        }
        // CREATE VOLUNTEER
        Volunteer volunteer = new Volunteer(id, name, priority);
        // Add to linked list
        volunteerList.addVolunteer(volunteer);
        // ADD TO CORRECT QUEUE
        if (priority.equalsIgnoreCase("High")) {
            highPriorityQueue.enqueue(volunteer);
        } else if (priority.equalsIgnoreCase("Medium")) {
            mediumPriorityQueue.enqueue(volunteer);
        } else {
            lowPriorityQueue.enqueue(volunteer);
        }
        // SUCCESS MESSAGE
        System.out.println("Volunteer registered successfully!");
        volunteer.displayVolunteer();
    }

    /*
     Method: assignTask()
     Description:
     Assigns task to next volunteer based on priority: High => Medium => Low
    */
    public void assignTask() {
        Volunteer volunteer = null;
        // Select volunteer based on priority queues
        if (!highPriorityQueue.isEmpty()) {
            volunteer = highPriorityQueue.dequeue();
        } else if (!mediumPriorityQueue.isEmpty()) {
            volunteer = mediumPriorityQueue.dequeue();
        } else if (!lowPriorityQueue.isEmpty()) {
            volunteer = lowPriorityQueue.dequeue();
        } else {
            System.out.println("No volunteers available.");
            return;
        }
        System.out.print("Enter Task to Assign: ");
        String task = scanner.nextLine();

        // Assign task using setters
        volunteer.setTaskAssigned(task);
        volunteer.setAssigned(true);

        // Save action for undo
        actionStack.push(new Action(Action.ASSIGN, volunteer));

        System.out.println("\n===== Task Assignment Successful =====");
        System.out.println("The task has been assigned to the following volunteer:");
        volunteer.displayVolunteer();
    }

    /*
     Method: removeVolunteer()
     Description:
     Removes volunteer from system and stores action for undo
    */
    public void removeVolunteer() {

        try {
            System.out.print("Enter Volunteer ID to remove: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // clear buffer
            // Search volunteer
            Volunteer volunteer = volunteerList.searchById(id);
            if (volunteer == null) {
                System.out.println("Volunteer not found!");
                return;
            }
            // Remove from linked list
            boolean removed = volunteerList.removeVolunteer(id);
            if (removed) {
                // Save for undo
                actionStack.push(new Action(Action.REMOVE, volunteer));
                System.out.println("Volunteer with ID " + id + " has been removed successfully.");
            } else {
                System.out.println("Error removing volunteer.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input!");
            scanner.nextLine();
        }
    }

    /*
 Method: searchVolunteer()
 Description:
 Allows user to search volunteer by ID or Name.
 Improvements:
 - Handles invalid input safely using try-catch
 - Prevents program crashes
 - Displays meaningful messages when volunteer is not found
*/
    public void searchVolunteer() {

        System.out.println("Search Volunteer By:");
        System.out.println("1. ID");
        System.out.println("2. Name");

        int choice;
        // HANDLE MENU INPUT SAFELY
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter 1 or 2.");
            scanner.nextLine(); // clear wrong input
            return;
        }
        // SEARCH BY ID
        if (choice == 1) {
            System.out.print("Enter Volunteer ID: ");
            int id;
            try {
                id = scanner.nextInt();
                scanner.nextLine(); // clear buffer
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a numeric ID.");
                scanner.nextLine(); // clear wrong input
                return;
            }
            // Search volunteer in linked list
            Volunteer volunteer = volunteerList.searchById(id);
            // Check if found
            if (volunteer != null) {
                System.out.println("\n===== Volunteer Found =====");
                volunteer.displayVolunteer();
            } else {
                System.out.println("There is no volunteer with this ID.");
            }
        }
        // SEARCH BY NAME
        else if (choice == 2) {
            System.out.print("Enter Volunteer Name: ");
            String name = scanner.nextLine().trim();
            // Check empty input
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }
            System.out.println("\n===== Matching Volunteers =====");
            volunteerList.searchByName(name);
        }

        // INVALID MENU CHOICE
        else {
            System.out.println("Invalid choice. Please select 1 or 2.");
        }
    }

    /*
    Method: undoOperation()
    Description:
    Reverses the last performed action using stack (LIFO).
    - If last action was ASSIGN:
        → Remove assigned task
        → Mark volunteer as unassigned
        → Re-add volunteer back into correct priority queue
    - If last action was REMOVE:
        → Add volunteer back into linked list
   */
    private void undoOperation() {
        if (actionStack.isEmpty()) {
            System.out.println("No actions to undo.");
            return;
        }
        Action lastAction = actionStack.pop();

        if (lastAction.getActionType().equals(Action.REMOVE)) {
            System.out.println("Undoing removal...");
        } else {
            System.out.println("Undoing assignment...");
        }

        // Undo assignment
        if (lastAction.getActionType().equals(Action.ASSIGN)) {

            Volunteer v = lastAction.getVolunteer();

            // Reset assignment details
            v.setAssigned(false);
            v.setTaskAssigned("Not Assigned");

            // Re-add volunteer into correct queue based on priority
            if (v.getPriorityLevel().equalsIgnoreCase("High")) {
                highPriorityQueue.enqueue(v);
            } else if (v.getPriorityLevel().equalsIgnoreCase("Medium")) {
                mediumPriorityQueue.enqueue(v);
            } else {

                lowPriorityQueue.enqueue(v);
            }
            System.out.println("\n===== Undo Successful =====");
            System.out.println("The last assignment has been undone.");
            System.out.println("Updated volunteer details:");
            v.displayVolunteer();
        }

        // Undo removal
        else if (lastAction.getActionType().equals(Action.REMOVE)) {

            Volunteer v = lastAction.getVolunteer();

            // Add back to linked list
            volunteerList.addVolunteer(v);

            // RESTORE previous state
            v.setTaskAssigned(lastAction.getPreviousTask());
            v.setAssigned(lastAction.getPreviousAssigned());

            // Also re-add to queue (important for assignment)
            if (!v.isAssigned()) {

                if (v.getPriorityLevel().equalsIgnoreCase("High")) {

                    highPriorityQueue.enqueue(v);

                } else if (v.getPriorityLevel().equalsIgnoreCase("Medium")) {

                    mediumPriorityQueue.enqueue(v);

                } else {

                    lowPriorityQueue.enqueue(v);
                }
            }

            System.out.println("\n===== Undo Successful =====");
            System.out.println("The last operation has been successfully reversed.");
            System.out.println("Undo performed: " + lastAction.getActionType());
            v.displayVolunteer();
        }
    }

    /*
     Method: rebuildQueues()
     Description:
     Rebuilds all priority queues after sorting
     Ensures correct assignment order
    */
    public void rebuildQueues() {
        // Reset queues
        highPriorityQueue = new VolunteerQueue();
        mediumPriorityQueue = new VolunteerQueue();
        lowPriorityQueue = new VolunteerQueue();

        Node temp = volunteerList.getHead();
        // Traverse linked list
        while (temp != null) {

            Volunteer v = temp.getVolunteer();

            // Only add unassigned volunteers
            if (!v.isAssigned()) {

                if (v.getPriorityLevel().equalsIgnoreCase("High")) {
                    highPriorityQueue.enqueue(v);
                } else if (v.getPriorityLevel().equalsIgnoreCase("Medium")) {
                    mediumPriorityQueue.enqueue(v);
                } else {
                    lowPriorityQueue.enqueue(v);
                }
            }

            temp = temp.getNext();
        }
    }

    /*
 Method: displayByPriority()
 Method: displayByPriority()
 Description:
 Displays volunteers filtered by a given priority level.
 Features:
 - Validates user input (High / Medium / Low)
 - Displays a heading based on selected priority
 - Traverses the linked list and shows matching volunteers
 - Handles empty list and no-match situations
*/
    private void displayByPriority() {
        System.out.print("Enter Priority (High / Medium / Low): ");
        String priority = scanner.nextLine().trim();
        // CHECK IF LIST IS EMPTY
        if (volunteerList.getHead() == null) {
            System.out.println("There are no volunteers currently registered in the system.");
            return;
        }

        // VALIDATE PRIORITY INPUT
        if (!priority.equalsIgnoreCase("High") &&
                !priority.equalsIgnoreCase("Medium") &&
                !priority.equalsIgnoreCase("Low")) {

            System.out.println("Invalid priority. Please enter High, Medium, or Low.");
            return;
        }

        // DISPLAY HEADING
        if (priority.equalsIgnoreCase("High")) {
            System.out.println("\n===== List of High Priority Volunteers =====");
        } else if (priority.equalsIgnoreCase("Medium")) {
            System.out.println("\n===== List of Medium Priority Volunteers =====");
        } else {
            System.out.println("\n===== List of Low Priority Volunteers =====");
        }

        // TRAVERSE LINKED LIST
        Node temp = volunteerList.getHead();
        boolean found = false;
        while (temp != null) {
            Volunteer v = temp.getVolunteer();
            // Compare priority
            if (v.getPriorityLevel().equalsIgnoreCase(priority)) {
                v.displayVolunteer();
                found = true;
            }
            temp = temp.getNext();
        }

        // NO MATCH FOUND
        if (!found) {
            System.out.println("No volunteers found with this priority.");
        }
    }

 //Method to display all volunteers
    public void displayVolunteers() {
        // Check if list is empty
        if (volunteerList.getHead() == null) {
            System.out.println("There are no volunteers currently registered in the system.");
            return;
        }
        System.out.println("\n===== List of All Registered Volunteers =====");
        // Display volunteers
        volunteerList.displayVolunteers();
    }
}
