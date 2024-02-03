import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.time.LocalDateTime;

import static java.lang.System.exit;
import static java.lang.System.in;

public class HallBooking {
    private static String bookingHistory = "";
    private static int bookingCounter = 1;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW="\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_MAGENTA = "\u001B[35m";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        showC();

        System.out.println(ANSI_CYAN+"=".repeat(30)+ANSI_MAGENTA+"    Welcome to CSTAD HALL BOOKING   "+ANSI_CYAN+"=".repeat(30)+ANSI_RESET);
        int length = getValidNumber(ANSI_RESET+"Enter the rows of the hall    : ");
        int width = getValidNumber("Enter the columns of the hall : ");
        char[][] morningHall = buildHall(length, width, "Morning");
        char[][] afternoonHall = buildHall(length, width, "Afternoon");
        char[][] nightHall = buildHall(length, width, "Night");
        char option;
        do {
            displayMenu();
            option = getValidOption(">>>>>>> Choose an option (A-F): ");
            switch (option) {
                case 'A':
                    handleBookingMenu(morningHall, afternoonHall, nightHall);
                    break;

                case 'B':
                    viewHall(morningHall, afternoonHall, nightHall);
                    break;
                case 'C':
                    showTime();
                    break;
                case 'D':
                    resetBookingHistory(morningHall, afternoonHall, nightHall);
                    break;
                case 'E':
                    viewBookingHistory();
                    break;


            }
        } while (option !='F');

        scanner.close();
    }

    private static void displayMenu() {

        System.out.println(ANSI_GREEN+"=".repeat(20)+ANSI_GREEN+"=".repeat(20)+ ANSI_YELLOW+"    Main Men    " +ANSI_GREEN +"=".repeat(20)+ANSI_GREEN+"=".repeat(20)+ANSI_RESET);
        System.out.println("""
        """ + ANSI_YELLOW + "<A> "+ANSI_CYAN+"Booking\n"+ANSI_RESET+ """
        """ + ANSI_YELLOW + "<B> "+ANSI_CYAN+"View Hall\n"+ANSI_RESET+ """
        """ + ANSI_YELLOW + "<C> "+ANSI_CYAN+"Show Time\n"+ANSI_RESET+ """
        """ + ANSI_YELLOW + "<D> "+ANSI_CYAN+"View Booking History\n"+ANSI_RESET+ """
        """ + ANSI_YELLOW + "<F> "+ANSI_CYAN+"Exit\n"+ANSI_RESET);



        System.out.println("=".repeat(96));
    }
    private static void showC(){
        System.out.println(ANSI_CYAN+"""
                                
                                
                 ██████╗███████╗████████╗ █████╗ ██████╗     ██╗  ██╗ █████╗ ██╗     ██╗         ██████╗  ██████╗  ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗\s
                ██╔════╝██╔════╝╚══██╔══╝██╔══██╗██╔══██╗    ██║  ██║██╔══██╗██║     ██║         ██╔══██╗██╔═══██╗██╔═══██╗██║ ██╔╝██║████╗  ██║██╔════╝\s
                ██║     ███████╗   ██║   ███████║██║  ██║    ███████║███████║██║     ██║         ██████╔╝██║   ██║██║   ██║█████╔╝ ██║██╔██╗ ██║██║  ███╗
                ██║     ╚════██║   ██║   ██╔══██║██║  ██║    ██╔══██║██╔══██║██║     ██║         ██╔══██╗██║   ██║██║   ██║██╔═██╗ ██║██║╚██╗██║██║   ██║
                ╚██████╗███████║   ██║   ██║  ██║██████╔╝    ██║  ██║██║  ██║███████╗███████╗    ██████╔╝╚██████╔╝╚██████╔╝██║  ██╗██║██║ ╚████║╚██████╔╝
                 ╚═════╝╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝    ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝\s
                                                                                                                                                        \s
                                
                """+ANSI_RESET);
    }
    private static int getValidNumber(String message) {
        Scanner scanner = new Scanner(System.in);
        int number;

        do {
            System.out.print(message);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.print(message);
                scanner.next(); // consume the invalid input
            }
            number = scanner.nextInt();
        } while (number <= 0);

        return number;
    }
    private static char getValidOption(String message) {
        Scanner scanner = new Scanner(System.in);
        char option;

        do {
            System.out.print(message);
            option = scanner.nextLine().trim().toUpperCase().charAt(0);
        } while (!isValidOption(option));

        return option;
    }

    private static boolean isValidOption(char option) {
        return option >= 'A' && option <= 'F';
    }

    private static char[][] buildHall(int length, int width, String session) {
        char[][] hall = new char[length][width];

        // Initialize the hall with empty spaces
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                hall[i][j] = ' ';
            }
        }

        // Add borders to the hall
        for (int i = 0; i < length; i++) {
            hall[i][0] = '|';
            hall[i][width - 1] = '|';
        }

        for (int j = 0; j < width; j++) {
            hall[0][j] = '-';
            hall[length - 1][j] = '-';
        }

        // Add session information to the hall (convert to uppercase)
        int rowIndex = length / 2;
        int startIndex = Math.max((width - session.length()) / 2, 0);

        for (int i = 0; i < session.length(); i++) {
            if (startIndex + i < width) {
                hall[rowIndex][startIndex + i] = Character.toUpperCase(session.charAt(i));
            }
        }

        return hall;
    }

    private static void viewHall(char[][] morningHall, char[][] afternoonHall, char[][] nightHall) {
        System.out.println(ANSI_CYAN+"#HALL MORNING"+ANSI_RESET);
        displayHall(morningHall);

        System.out.println(ANSI_CYAN+"#HALL AFTERNOON"+ANSI_RESET);
        displayHall(afternoonHall);

        System.out.println(ANSI_CYAN+"#HALL NIGHT"+ANSI_RESET);
        displayHall(nightHall);
    }

    // Function to display the hall for a specific session
    private static void displayHall(char[][] hall) {
        for (int i = 0; i < hall.length; i++) {
            for (int j = 0; j < hall[i].length; j++) {
                char rowChar = (char) ('A' + i);
                int colNum = j + 1;
                String status = getStatusSymbol(hall[i][j]);
                System.out.print(String.format("#[%s-%d :: %s]\t", rowChar, colNum, status));
            }
            System.out.println();
        }
        System.out.println();
    }


    private static String getStatusSymbol(char seatStatus) {
        if (seatStatus == ' ') {
            return   "AV" ; // Available, displayed in green
        } else if (seatStatus == 'B') {
            return  ANSI_RED + "BO" + ANSI_RESET; // Booked, displayed in red
        } else {
            return "AV"; // Treat any other status as available
        }
    }
    private static void handleBookingMenu(char[][] morningHall, char[][] afternoonHall, char[][] nightHall) {
        Scanner scanner = new Scanner(System.in);
        char hallOption;
        String studentId = getStudentId();
        System.out.println();
        showTime();
        do {
            hallOption = getHallOption(">>>>>>> Choose time (M/A/N) for booking OR enter (F) to go back to the main menu) : ");

            switch (hallOption) {
                case 'M':
                    bookSeats(morningHall, "MORNING", studentId);
                    break;
                case 'A':
                    bookSeats(afternoonHall, "AFTERNOON", studentId);
                    break;
                case 'N':
                    bookSeats(nightHall, "NIGHT", studentId);
                    break;
                default:
                    System.out.println("Invalid option. Please choose M, A, E, or F.");
            }
        } while (hallOption != 'F');
    }
    private static void bookSeats(char[][] hall, String session,String studentId) {
        Scanner scanner = new Scanner(System.in);
        boolean atLeastOneSeatBooked = false; // Initialize to false
        String newBookingEntry;

        while (true) {
            System.out.println(ANSI_CYAN+"HALL "+ session+ANSI_RESET);

            System.out.println("_".repeat(96));
            displayHall(hall);
            System.out.println("_".repeat(96));
            System.out.println("#INSTRUCTION");
            System.out.println("#Single    : C-1");
            System.out.println("#Mulitple  : C-1,C-2");
            System.out.println();
            System.out.print(">Please Select Available  Seat and for done [Done]:");

            String input = scanner.nextLine().toUpperCase().trim();

            if (input.equals("DONE")) {
                break;
            }

            String[] seatNumbers = input.split(",");
            for (String seatNumber : seatNumbers) {
                if (isValidSeat(seatNumber)) {
                    int[] seatCoordinates = getSeatCoordinates(seatNumber);
                    int rowIndex = seatCoordinates[0];
                    int colIndex = seatCoordinates[1];

                    if (isSeatAvailable(hall, rowIndex, colIndex)) {
                        // Display the confirmation prompt
                        char confirmation = getConfirmation(">Are you sure you want to book seat " + seatNumber + "? (Y/N): ");

                        if (confirmation == 'Y') {
                            bookSeat(hall, rowIndex, colIndex);

                            System.out.println(">Seat " +" [" +seatNumber+"]" + " booked successfully for " + session + " by Student ID: " + studentId);
                            System.out.println();
                            atLeastOneSeatBooked = true;

                            // Record the booking and get the new entry
                            newBookingEntry = recordBooking(session, seatNumber, studentId);
                            System.out.println("Show Booking Information");
                            System.out.println("=".repeat(96));
                            System.out.println("#NO            #SEATS          #HALL          #Stu.ID          #Created Date");
                            System.out.println(newBookingEntry);
                            System.out.println("=".repeat(96));
                            // Print the new entry to the console
//                            System.out.println("New Booking Entry: " + newBookingEntry);
                        } else {
                            System.out.println("Booking canceled for seat " + seatNumber);
                        }
                    } else {
                        System.out.println("Invalid or already booked seat: " + seatNumber);
                    }
                } else {
                    System.out.println("Invalid seat format: " + seatNumber);
                }
            }

            if (!atLeastOneSeatBooked) {
                System.out.println("All entered seats are already booked or the booking was canceled.");
            }
        }
    }

    private static char getConfirmation(String message) {
        Scanner scanner = new Scanner(System.in);
        char confirmation;

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.length() == 1 && (input.equals("Y") || input.equals("N"))) {
                confirmation = input.charAt(0);
                break;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }

        return confirmation;
    }
    private static boolean isValidSeat(String seat) {
        // Validate seat format using the correct regular expression
        return seat.matches("[A-Ea-e]-[1-9]\\d*");
    }
    private static boolean isSeatAvailable(char[][] hall, int rowIndex, int colIndex) {
        return rowIndex >= 0 && rowIndex < hall.length && colIndex >= 0 && colIndex < hall[0].length
                && getStatusSymbol(hall[rowIndex][colIndex]).equals("AV");
    }


    private static char getHallOption(String message) {
        Scanner scanner = new Scanner(System.in);
        char option;

        String regex = "[manfMANF]";
        Pattern pattern = Pattern.compile(regex);

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.length() == 1 && pattern.matcher(input).matches()) {
                option = input.charAt(0);
                break;
            } else {
                System.out.println("Invalid option. Please choose M, A, or E.");
            }
        }

        return option;
    }
    private static boolean isValidOption(String option) {
    return option.matches("[MAE]") || option.equals("F");
}
    private static void bookSeat(char[][] hall, int rowIndex, int colIndex) {
        hall[rowIndex][colIndex] = 'B'; // 'B' represents a booked seat
    }
    private static int[] getSeatCoordinates(String seat) {
        char rowChar = seat.charAt(0);
        int colNum = Integer.parseInt(seat.substring(2));
        int rowIndex = rowChar - 'A';
        int colIndex = colNum - 1;

        return new int[]{rowIndex, colIndex};
    }
    private static void showTime(){

        System.out.println("#Daily Show Time CSTAD HALL");
        System.out.println("=".repeat(96));
        System.out.println("#"+ANSI_YELLOW+"M"+ANSI_RESET+ ")  MORNING    : 09:30am - 12:30pm");
        System.out.println("#"+ANSI_YELLOW+"A"+ANSI_RESET+ ")  AFTERNOON  : 03:00pm - 05:30pm");
        System.out.println("#"+ANSI_YELLOW+"N"+ANSI_RESET+ ")   NIGHT     : 07:00pm - 09:30pm");

        System.out.println();
    }
    private static void viewBookingHistory() {
        System.out.println("Booking History:");
        System.out.println(ANSI_MAGENTA+"=".repeat(96)+ANSI_RESET);
        System.out.println(ANSI_CYAN+"#NO            #SEATS          #HALL          #Stu.ID          #Created Date"+ANSI_RESET);
        System.out.println(ANSI_YELLOW+bookingHistory+ANSI_RESET);
        System.out.println("=".repeat(96));
    }

    private static String recordBooking(String session, String seatNumber,String studentId) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        String bookingEntry = String.format("%-16d%-16s%-15s%-16s%s",
                bookingCounter++, seatNumber, session, studentId, formattedDateTime) + LINE_SEPARATOR;

        bookingHistory += bookingEntry;

        return bookingEntry;
    }
    private static void resetBookingHistory(char[][] morningHall, char[][] afternoonHall, char[][] nightHall) {
        Scanner scanner = new Scanner(System.in);

        char confirmation = getConfirmation("Are you sure you want to reset the booking history and booked seats? (Y/N): ");

        if (confirmation == 'Y') {
            resetBookedSeats(morningHall);
            resetBookedSeats(afternoonHall);
            resetBookedSeats(nightHall);

            bookingHistory = ""; // Reset the booking history
            bookingCounter = 1; // Reset the booking counter
            System.out.println("Booking history and booked seats have been reset.");
        } else {
            System.out.println("Reset canceled. Booking history and booked seats remain unchanged.");
        }
    }

    private static void resetBookedSeats(char[][] hall) {
        for (int i = 0; i < hall.length; i++) {
            for (int j = 0; j < hall[i].length; j++) {
                if (hall[i][j] == 'B') {
                    hall[i][j] = ' '; // Reset booked seats to available
                }
            }
        }
    }
    private static String getStudentId() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">>>>>>>Enter Student ID: ");

        return scanner.nextLine().trim();

    }

}



