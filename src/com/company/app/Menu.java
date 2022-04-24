package com.company.app;

import com.company.user.User;
import com.company.user.UserService;

import java.util.Scanner;

public class Menu {
    public static void menu()
    {
        UserService userService = UserService.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("🏥 Welcome to Sirona centre 🏥");
        String option;
        do {
            System.out.println("1. Enter user from keyboard");
            System.out.println("2. Connect to user");
            System.out.println("3. Find user");
            System.out.println("0. Exit");

            option = scanner.nextLine();

            switch (option)
            {
                case "1":{
                    userService.readUser(scanner);
                    break;
                }
                case "2": {
                    userService.connect(scanner);
                    break;
                }
                case "3": {
                    System.out.println("1. Find by username");
                    System.out.println("2. Find by email");
                    String type = scanner.nextLine();
                    switch (type)
                    {
                        case "1": {
                            System.out.println("Enter username: ");
                            String username = scanner.nextLine();
                            User user = userService.findByUsername(username);
                            if (user == null)
                                System.out.println("No user found.");
                            else {
                                System.out.println(user);
                                System.out.println("Delete user? (Y/N)");
                                String deleteQuestion = scanner.nextLine();
                                if (deleteQuestion.equals("Y") || deleteQuestion.equals("y"))
                                    userService.deleteUser(user);
                            }
                            break;
                        }
                        case "2": {
                            System.out.println("Enter email: ");
                            String email = scanner.nextLine();
                            User user = userService.findByEmail(email);
                            if (user == null)
                                System.out.println("No user found.");
                            else {
                                System.out.println(user);
                                System.out.println("Delete user? (Y/N)");
                                String deleteQuestion = scanner.nextLine();
                                if (deleteQuestion.equals("Y") || deleteQuestion.equals("y"))
                                    userService.deleteUser(user);
                            }
                            break;
                        }
                        default:
                            System.out.println("Invalid option");
                    }
                    break;
                }
            }
        }while (!option.equals("0"));
    }
}
