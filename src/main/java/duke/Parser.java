package duke;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Parser {

    private TaskList tasks;

    /**
     * Class constructor specifying the TaskList.
     * @param tasks
     */
    public Parser(TaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Parses the specified String as required.
     * @param str the String to be parsed.
     * @throws DukeException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void parse(String str) throws DukeException, IOException, ClassNotFoundException {

        ArrayList<Task> store = this.tasks.getList();

        if (str.equals("blah")) {
            throw new DukeException("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
        }

        while (str != null) {
            if (str.equals("list")) {
                int counter = 1;
                System.out.println("-------------------------");
                for (Task task : store) {
                    if (task.getDescription() != null) {
                        System.out.println(counter + ". [" + task.getType()
                                + "][" + task.getStatusIcon() + "] " + task.getDescription());
                        counter++;
                    }
                }
                System.out.println("-------------------------");
                break;
            } else if (str.contains("done")) {
                if (str.length() == 4) {
                    throw new DukeException("\u2639 OOPS!!! Please state what task has been completed.");
                }
                int num = Integer.parseInt(str.substring(5)) - 1;
                Task curr = store.get(num);
                curr.markAsDone();
                System.out.println("-------------------------");
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  [" + curr.getStatusIcon() + "] " + curr.getDescription());
                System.out.println("-------------------------");
                break;
            } else if (str.contains("todo")) {
                try {
                    if (str.length() == 4) {
                        throw new DukeException("\u2639 OOPS!!! The description of a todo cannot be empty.");
                    }
                    ToDos curr = new ToDos(str.substring(5));
                    store.add(curr);
                    System.out.println("-------------------------");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  [" + curr.getType() + "][" + curr.getStatusIcon() + "] "
                            + curr.getDescription());
                    System.out.println("Now you have " + store.size() + " tasks in the list.");
                    System.out.println("-------------------------");
                    Path relativePath = Paths.get("duke.txt");
                    Path absolutePath = relativePath.toAbsolutePath();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("" + absolutePath));
                    writer.append('\n');
                    StringBuilder result = new StringBuilder("");
                    for (Task task : store) {
                        if (task.getDescription() != null) {
                            result.append("[" + task.getType() + "]"
                                    + "[" + task.getStatusIcon() + "] " + task.getDescription());
                            result.append("\n");
                        }
                    }
                    writer.append(result);
                    writer.close();
                    break;
                } catch (IOException e) {
                    File yourFile = new File("duke.txt");
                    yourFile.createNewFile();
                    FileOutputStream oFile = new FileOutputStream(yourFile, false);
                }
            } else if (str.contains("deadline")) {
                try {
                    Deadlines curr = new Deadlines(str.substring(9));
                    String description = curr.getDescription();
                    int index = description.indexOf("/") + 4;
                    curr.setDeadline(description.substring(index));
                    curr.setDateTime();
                    store.add(curr);
                    System.out.println("-------------------------");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  [" + curr.getType() + "][" + curr.getStatusIcon() + "] "
                            + curr.getDescription().substring(0, description.indexOf("/"))
                            + "(by: " + curr.getDeadline() + ")");
                    System.out.println("Now you have " + store.size() + " tasks in the list.");
                    System.out.println("-------------------------");
                    Path relativePath = Paths.get("duke.txt");
                    Path absolutePath = relativePath.toAbsolutePath();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("" + absolutePath));
                    writer.append('\n');
                    StringBuilder result = new StringBuilder("");
                    for (Task task : store) {
                        if (task.getDescription() != null) {
                            result.append("[" + task.getType() + "]"
                                    + "[" + task.getStatusIcon() + "] " + task.getDescription());
                            result.append("\n");
                        }
                    }
                    writer.append(result);
                    writer.close();
                    break;
                } catch (IOException e) {
                    File yourFile = new File("duke.txt");
                    yourFile.createNewFile();
                    break;
                }
            } else if (str.contains("event")) {
                try {
                    Events curr = new Events(str.substring(6));
                    String description = curr.getDescription();
                    int index = description.indexOf("/") + 4;
                    curr.setStart(description.substring(index));
                    curr.setDateTime();
                    store.add(curr);
                    System.out.println("-------------------------");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  [" + curr.getType() + "][" + curr.getStatusIcon() + "] "
                            + curr.getDescription().substring(0, description.indexOf("/"))
                            + "(at: " + curr.getStart() + ")");
                    System.out.println("Now you have " + store.size() + " tasks in the list.");
                    System.out.println("-------------------------");
                    Path relativePath = Paths.get("data/duke.txt");
                    Path absolutePath = relativePath.toAbsolutePath();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("" + absolutePath));
                    writer.append('\n');
                    StringBuilder result = new StringBuilder("");
                    for (Task task : store) {
                        if (task.getDescription() != null) {
                            result.append("[" + task.getType() + "]" + "[" + task.getStatusIcon()
                                    + "] |" + task.getDescription());
                            result.append("\n");
                        }
                    }
                    writer.append(result);
                    writer.close();
                    break;
                } catch (IOException e) {
                    File yourFile = new File("duke.txt");
                    yourFile.createNewFile();
                    break;
                }
            } else if (str.contains("delete")) {
                if (str.length() == 6) {
                    throw new DukeException("\u2639 OOPS!!! Please specify what task to delete.");
                }
                int num = Integer.parseInt(str.substring(7)) - 1;
                Task curr = store.get(num);
                store.remove(curr);
                System.out.println("-------------------------");
                System.out.println("Noted! I've removed this task:");
                System.out.println(curr);
                System.out.println("Now you have " + store.size() + " tasks in the list.");
                System.out.println("-------------------------");
                break;
            } else if (str.contains("find")) {
                TaskList tasks = new TaskList(store);
                int index = str.indexOf(" ") + 1;
                String query = str.substring(index);
                tasks.find(query);
                break;
            } else if (!str.equals("bye")) {
                System.out.println("-------------------------");
                System.out.println("added: " + str);
                System.out.println("-------------------------");
                store.add(new Task(str));
                break;
            } else {
                break;
            }
        }
    }

    /**
     * Returns the result string after parsing.
     * @param str
     * @return the result string
     * @throws DukeException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String parseStr(String str) throws DukeException, IOException, ClassNotFoundException {

        ArrayList<Task> store = this.tasks.getList();
        String result = "";

        if (str.equals("blah")) {
            throw new DukeException("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
        }

        while (str != null) {
            if (str.equals("list")) {
                int counter = 1;
                for (Task task : store) {
                    if (task.getDescription() != null) {
                        result = result + counter + ". [" + task.getType()
                                + "][" + task.getStatusIcon() + "] " + task.getDescription() + "\n";
                        counter++;
                    }
                }
                return result;
            } else if (str.contains("done")) {
                if (str.length() == 4) {
                    throw new DukeException("\u2639 OOPS!!! Please state what task has been completed.");
                }
                int num = Integer.parseInt(str.substring(5)) - 1;
                Task curr = store.get(num);
                curr.markAsDone();
                result = result + "Nice! I've marked this task as done:\n";
                result = result + "  [" + curr.getStatusIcon() + "] " + curr.getDescription() + "\n";
                return result;
            } else if (str.contains("todo")) {
                try {
                    if (str.length() == 4) {
                        throw new DukeException("\u2639 OOPS!!! The description of a todo cannot be empty.");
                    }
                    ToDos curr = new ToDos(str.substring(5));
                    store.add(curr);
                    result = result + "Got it. I've added this task:\n";
                    result = result + "  [" + curr.getType() + "][" + curr.getStatusIcon() + "] "
                            + curr.getDescription() + "\n";
                    result = result + "Now you have " + store.size() + " tasks in the list.\n";
                    Path relativePath = Paths.get("duke.txt");
                    Path absolutePath = relativePath.toAbsolutePath();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("" + absolutePath));
                    writer.append('\n');
                    StringBuilder res = new StringBuilder("");
                    for (Task task : store) {
                        if (task.getDescription() != null) {
                            res.append("[" + task.getType() + "]"
                                    + "[" + task.getStatusIcon() + "] " + task.getDescription());
                            res.append("\n");
                        }
                    }
                    writer.append(res);
                    writer.close();
                    return result;
                } catch (IOException e) {
                    File yourFile = new File("duke.txt");
                    yourFile.createNewFile();
                }
            } else if (str.contains("deadline")) {
                try {
                    Deadlines curr = new Deadlines(str.substring(9));
                    String description = curr.getDescription();
                    int index = description.indexOf("/") + 4;
                    curr.setDeadline(description.substring(index));
                    curr.setDateTime();
                    store.add(curr);
                    result = result + "Got it. I've added this task:\n";
                    result = result + "  [" + curr.getType() + "][" + curr.getStatusIcon() + "] "
                            + curr.getDescription().substring(0, description.indexOf("/"))
                            + "(by: " + curr.getDeadline() + ")" + "\n";
                    result = result + "Now you have " + store.size() + " tasks in the list.\n";
                    Path relativePath = Paths.get("duke.txt");
                    Path absolutePath = relativePath.toAbsolutePath();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("" + absolutePath));
                    writer.append('\n');
                    StringBuilder res = new StringBuilder("");
                    for (Task task : store) {
                        if (task.getDescription() != null) {
                            res.append("[" + task.getType() + "]"
                                    + "[" + task.getStatusIcon() + "] " + task.getDescription());
                            res.append("\n");
                        }
                    }
                    writer.append(res);
                    writer.close();
                    return result;
                } catch (IOException e) {
                    File yourFile = new File("duke.txt");
                    yourFile.createNewFile();
                    break;
                }
            } else if (str.contains("event")) {
                try {
                    Events curr = new Events(str.substring(6));
                    String description = curr.getDescription();
                    int index = description.indexOf("/") + 4;
                    curr.setStart(description.substring(index));
                    curr.setDateTime();
                    store.add(curr);
                    result = result + "Got it. I've added this task:\n";
                    result = result + "  [" + curr.getType() + "][" + curr.getStatusIcon() + "] "
                            + curr.getDescription().substring(0, description.indexOf("/"))
                            + "(at: " + curr.getStart() + ")\n";
                    result = result + "Now you have " + store.size() + " tasks in the list.\n";
                    Path relativePath = Paths.get("data/duke.txt");
                    Path absolutePath = relativePath.toAbsolutePath();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("" + absolutePath));
                    writer.append('\n');
                    StringBuilder res = new StringBuilder("");
                    for (Task task : store) {
                        if (task.getDescription() != null) {
                            res.append("[" + task.getType() + "]" + "[" + task.getStatusIcon()
                                    + "] |" + task.getDescription());
                            res.append("\n");
                        }
                    }
                    writer.append(res);
                    writer.close();
                    return result;
                } catch (IOException e) {
                    File yourFile = new File("duke.txt");
                    yourFile.createNewFile();
                    break;
                }
            } else if (str.contains("delete")) {
                assert store.size() > 0 : "There are already no tasks!";
                if (str.length() == 6) {
                    throw new DukeException("\u2639 OOPS!!! Please specify what task to delete.");
                }
                int num = Integer.parseInt(str.substring(7)) - 1;
                Task curr = store.get(num);
                store.remove(curr);
                result = result + "Noted! I've removed this task:\n";
                result = result + curr.toString() + "\n";
                result = result + "Now you have " + store.size() + " tasks in the list.\n";
                return result;
            } else if (str.contains("find")) {
                TaskList tasks = new TaskList(store);
                int index = str.indexOf(" ") + 1;
                String query = str.substring(index);
                result = tasks.finder(query);
                return result;
            } else if (!str.equals("bye")) {
                result = result + "added: " + str + "\n";
                store.add(new Task(str));
                return result;
            } else {
                result = "error";
                return result;
            }
        }
        return "error found";
    }
}
