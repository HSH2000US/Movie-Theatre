import java.io.*;
import java.util.Scanner;

public class Main
{
    // Declare static variables for the array, the max, and the prices
    private static char [][] rowsCol;
    private static int maxRows;
    private static int maxColumns;
    final private static double ADULT = 10;
    final private static double CHILD = 5;
    final private static double SENIOR = 7.50;

    public static void main (String [] args) throws IOException
    {
        // Variable only used in main
        Scanner sc = new Scanner (System.in);
        maxRows = 0; maxColumns = 0;
        String filename;
        char seatsWanted = '0';
        int totalTickets = 0;
        int adultTotal = 0;
        int childTotal = 0;
        int seniorTotal = 0;
        char bestSeat;
        char wanted = '0';


        System.out.print("Enter the name of the file: ");
        filename = sc.nextLine();
        Scanner input = new Scanner(new File(filename));
        // start file
        while (input.hasNextLine())
        {
            String save = input.nextLine();
            if (!save.isEmpty())
                maxRows++;


        }
        rowsCol = new char [maxRows][];
        input.close();
        // close file and check
         input = new Scanner (new File(filename));
        for (int i = 0; i < maxRows; i++)
         {
             String row = input.nextLine();
             rowsCol [i] = row.toCharArray();
             // maxColumns = Math.max(maxColumns, rowsCol [i].length);

             if (maxColumns > rowsCol[i].length)
             {
                 maxColumns = maxColumns;
             }
             else if (rowsCol[i].length > maxColumns)
             {
                 maxColumns = rowsCol[i].length;
             }
         }

         String selection = "1";
        // while loop to keep going ask long as seats still want to be reserved
         while (!selection.equals("2" ) && !selection.equals("Exit"))
         {
             // keep going ask long as selection does not equal 2 or exit in string form
             System.out.print("1. Reserve Seats \n2. Exit: ");
             selection = sc.next();
             System.out.println();
             if(!selection.equals("2") && !selection.equals("Exit"))
             {
                 Display();
                 newDisplay();
                 System.out.print("Enter the row >>> ");
                 int row = sc.nextInt();
                 System.out.print("Enter a seat >>> ");
                 seatsWanted = Character.toUpperCase(sc.next().charAt(0));
                 System.out.print("How many adult tickets do you want >>> ");
                 int adult = sc.nextInt();
                 System.out.print("How many child tickets do you want >>> ");
                 int child = sc.nextInt();
                 System.out.print("How many senior tickets do you want >>> ");
                 int senior = sc.nextInt();
                 totalTickets = adult + senior + child;
                 System.out.println("You asked for " + adult + " adult seats " + child + " child seats " + senior + " senior seats at " + "row " + row + " & seat " + seatsWanted);
                 if (Availability(row, seatsWanted, totalTickets))
                 Reserve(row, seatsWanted, adult, senior, child);
                 else
                 {   // If seats cannot be reserved check for best Available and return
                     System.out.println("The seats are not available checking for the best available seats.");
                     bestSeat = bestAvail(row, totalTickets);

                     if (bestSeat != '#' && bestSeat != 'A' && bestSeat != 'C' && bestSeat !='S')
                     // If best seat is not occupied check if it can be used
                     {
                         // check for the best available seats or seat and then ask if the value is wanted
                         if (totalTickets == 1)
                         {
                             System.out.print(String.valueOf(row) + String.valueOf(bestSeat));
                         }
                         else
                         {

                             System.out.print(String.valueOf(row) + String.valueOf(bestSeat) + " - " + String.valueOf(row) + (char) (bestSeat + (totalTickets - 1)));
                         }
                         System.out.print(" Do you want the best available seats: ");
                         //check for best seat wanted
                         wanted = Character.toUpperCase(sc.next().charAt(0));
                         if (wanted == 'N')
                             System.out.println("no seats available");
                         else if(wanted == 'Y')
                         {
                             bestAvail(row, totalTickets);
                             Reserve(row, bestSeat, adult, senior, child);
                         }

                     }
                     else
                         System.out.println("no seats available");
                 }
                 // Display the final text file with A, C, S, ., to show which seat is of which category
                 Display();




             }

         }
         // Increase totals depending on the amount of type of seat from the Reserve method
        System.out.println("Total Seats: " + maxRows * maxColumns);

        for (int i = 0; i < maxRows; i++)
        {
            for (int j = 0; j < maxColumns; j++)
            {
                if (rowsCol[i][j] == 'A' )
                {
                    adultTotal++;
                }
                else if(rowsCol[i][j] == 'C' )
                {
                    childTotal++;
                }
                else if(rowsCol[i][j] == 'S' )
                {
                    seniorTotal++;
                }
            }
        }
        // display final totals
        totalTickets = adultTotal + seniorTotal + childTotal;
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Adult Tickets: " + adultTotal);
        System.out.println("Child Tickets: " + childTotal);
        System.out.println("Senior Tickets: " + seniorTotal);
        System.out.printf("Total Sales: $%.2f", ((seniorTotal * SENIOR) + (childTotal * CHILD) + (adultTotal * ADULT)));
        // Read the code into the file for adult, senior, and child
        BufferedWriter obj = new BufferedWriter(new FileWriter("A1.txt"));
        for (int i = 0; i < maxRows;i++)
        {
            for(int j = 0; j < maxColumns; j++)
            {
                obj.write(rowsCol[i][j]);
            }
            obj.write("\n");

        }
        obj.close();
    }
    // have to separate displays one for the letters and one with # to show seats that are or are not available
    public static void Display()
    {
        System.out.println("  " + "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(0, maxColumns));
        for (int i = 0; i < maxRows; i++)
        {
            System.out.println((i + 1) + " " + new String(rowsCol[i]));
        }
    }
    public static void newDisplay()
    {
        // Display rows and columns and with #'s and use periods for open seats

        System.out.println("  " + "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(0, maxColumns));
        for (int i = 0; i < maxRows; i++)
        {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < maxColumns; j++)
            {
                char c = rowsCol [i][j];
                if (c == '.')
                    sb.append('.');
                else
                    sb.append('#');
            }
            System.out.println((i + 1) + " " + sb.toString());
        }
    }

    public static void Reserve(int row, char seat, int adult, int senior, int child)
    {
        // Reserve seats in the array and then placing them as Adult, child, or senior using for loops for each one
        // Check for the amount for adult
        for (int i = 0; i < adult; i++)
        {
            // Ascii values
        rowsCol [row - 1][seat - 'A'] = 'A';
        seat++;
        }
        // check for amount for child
        for (int i = 0; i < child; i++)
        {
            rowsCol [row - 1][seat - 'A'] = 'C';
            seat++;
        }
        // check for amount for senior
        for (int i = 0; i < senior; i++)
        {
            rowsCol [row - 1][seat - 'A'] = 'S';
            seat++;
        }

    }
    public static boolean Availability(int row, char seat, int total)
    { // checking availability of array using the char numeric equivalent value
        for (int i = 0; i < total; i++)
        {
            if (rowsCol[row - 1][seat - 'A']  != '.')
            {
                return false;
            }
            seat++;
        }
        return true;
    }
    public static char bestAvail(int row, int quantity)
    {
        // best Available seat using char original value placed as # to check if it can be changed
        char bestSeat = '#';
        double bestDistance = rowsCol[row - 1].length;
        double currentDistance;
        for (int i = 0; i < rowsCol[row - 1].length - quantity; i++)
        {
            if(Availability(row, (char)(i + 'A'), quantity))
            {
                // Formula for checking for the best seat with regards to distance
                currentDistance = Math.abs((i + (quantity - 1)/2) - ((rowsCol[row - 1].length + 1) / 2));
                if(currentDistance < bestDistance)
                {
                    bestDistance = currentDistance;
                    bestSeat = (char)(i + 'A');
                }
            }
        }
          return bestSeat;


    }

}







