package uz.code.todo;

public class Service {
    static Scanner scanner = new Scanner();
    static JDBC jdbc = new JDBC();

    static ToDo toDo = new ToDo();

    public void start() {
        while (true) {

            System.out.print("""
                    1.Create
                    2.Active task list
                    3.Finished task list
                    4.Update (by id)
                    5.Delete (by id)
                    6.Mark as done
                    0.Exit
                    """);
            String option = scanner.printString("?: ");
            if (option.equals("0")) {
                System.out.println("Exited");
                break;
            } else if (option.trim().isEmpty()) {
                System.out.println("Choose option:");
            } else {
                switch (option) {
                    case "1" -> createTask();
                    case "2" -> listOfActiveTask();
                    case "3" -> listofFinishedTask();
                    case "4" -> update();
                    case "5" -> delete();
                    case "6" -> markAsDone();
                }
            }
        }
    }

    //==================================================================================================================
    private static void createTask() {

        String title = scanner.printString("Write a name for task : ");
        String content = scanner.printString("Write a description for task : ");

        toDo.setTitle(title);
        toDo.setContent(content);

        jdbc.createTask(toDo);


    }

    private static void listOfActiveTask() {

        int count = 0;
        for (ToDo listTask : jdbc.getListTasks()) {
            if (listTask.getStatus().equals(TaskStatus.ACTIVE)) {
                System.out.println(listTask);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("List of the active tasks is empty");
        }


    }

    private static void listofFinishedTask() {

        int count = 0;
        for (ToDo listTask : jdbc.getListTasks()) {
            if (listTask.getStatus().equals(TaskStatus.DONE)) {
                System.out.println(listTask);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("List of the finished tasks is empty");
        }
    }

    private static void update() {

        String id = scanner.printString("Enter ID of the task to edit : ");

        boolean bool=false;
        for (ToDo listTask : jdbc.getListTasks()) {
            if (listTask.getId().equals(Integer.parseInt(id))) {
                String title = scanner.printString("Enter a new name for task : ");
                String content = scanner.printString("Enter a new description for task : ");

                toDo.setTitle(title);
                toDo.setContent(content);

                jdbc.update(Integer.parseInt(id), toDo);
                bool = true;
                break;
            }
        }
        if(bool){
            System.out.println("Task is edited successfully♻️♻️♻️♻️♻️");
        }else {
            System.out.println("Task has not found with this ID👀👀👀👀");
        }



    }

    private static void delete() {

        String id = scanner.printString("Enter ID of the task to edit : ");

        boolean bool=false;
        for (ToDo listTask : jdbc.getListTasks()) {
            if (listTask.getId().equals(Integer.parseInt(id))) {
                jdbc.delete(Integer.parseInt(id));
                bool = true;
                break;
            }
        }
        if(bool){
            System.out.println("Task is deleted successfully❌❌❌❌❌");
        }else {
            System.out.println("Task has not found with this ID👀👀👀👀");
        }


    }

    private static void markAsDone() {
        String id = scanner.printString("Enter ID of the task to mark as done : ");

        boolean bool=false;
        for (ToDo listTask : jdbc.getListTasks()) {
            if (listTask.getId().equals(Integer.parseInt(id))) {
                jdbc.markAsDone(Integer.parseInt(id));
                bool = true;
                break;
            }
        }

        if(bool){
            System.out.println("Task has done successfully✅✅✅✅✅✅");
        }else {
            System.out.println("Task has not found with this ID👀👀👀👀");
        }


    }

    //==================================================================================================================

}
