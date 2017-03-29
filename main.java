import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class main {
  // Global Variables
  public static ArrayList<Ballot> list;
  public static ArrayList<Voter> _list;
  public static JButton button;
  public static JButton loginButt;
  public static JButton castButt;
  public static Ballot ballot;
  public static Voter voter;
  public static String tempID;
  public static int counter;

  public static void main(String[] args) throws IOException {
    // Temporary Variables
    int numBallots;
    String ballotIDIn;
    String ballotIn;
    String[] candidatesIn;
    String voterIDIn;
    String voterNameIn;
    boolean votedIn;

    ballot = new Ballot();
    voter = new Voter();
    Scanner inputScan = new Scanner(System.in);

    // user enters the ballot text file
    System.out.print("Enter File >>  ");
    String enterFile = inputScan.nextLine();

    // checks to see if the file entered by the user exists
    File file = new File(enterFile);
		while(!file.exists()){
				System.out.println("\nThis file does not exist. Please enter an appropriate file name.");
				System.out.print(" >>  ");
				enterFile = inputScan.nextLine();
				file = new File(enterFile);
		} // end while

    // opens the ballot file
    Scanner inputFile = new Scanner(file);
		list = new ArrayList<Ballot>();
    // reads in num of ballots
    numBallots = inputFile.nextInt();
    // reads in blank new line character
    inputFile.nextLine();
    // reads until end of file and puts into an ArrayList
    while(inputFile.hasNextLine()) {
      ballot = new Ballot();
      // reads line in a temporary string
      String temp = inputFile.nextLine();
      // creates a temp array and ignores the colon
      String[] tokens = temp.split(":");
      // sets first element of temp array to the ballot ID
      ballotIDIn = tokens[0];
      ballot.setBallotID(ballotIDIn);
      // sets second element of temp array to the ballet topic
      ballotIn = tokens[1];
      ballot.setBallot(ballotIn);
      // sets third element into a temp array and ignores commas
      String[] temp1 = tokens[2].split(",");
      // sets temp array equal to the candidates
      candidatesIn = temp1;
      ballot.setCandidates(candidatesIn);
      // adds ballot object into ArrayList
      list.add(ballot);
    } // end while
    // closes text file
    inputFile.close();

    //opens voter text file
    Scanner fileIn = new Scanner(new File("voters.txt"));
    _list = new ArrayList<Voter>();
    // reads until EOF and puts into ArrayList
    while(fileIn.hasNextLine()) {
      voter = new Voter();
      // reads line in a temporary string
      String _temp = fileIn.nextLine();
      // creates a temp array and ignores the colon
      String[] token = _temp.split(":");
      // sets first element of array to the voter ID
      voterIDIn = token[0];
      voter.setVoterID(voterIDIn);
      // sets second element of array to the voter name
      voterNameIn = token[1];
      voter.setVoterName(voterNameIn);
      // sets last element of array to voting
      votedIn = Boolean.parseBoolean(token[2]);
      voter.setHasVoted(votedIn);
      // adds voter object into ArrayList
      _list.add(voter);
    } // end while
    // closes the file
    fileIn.close();


    // determines size of window frame
    final int windowWidth = 1000;
    final int windowHeight = 350;
    //creates window and sets a title
    JFrame votingWindow = new JFrame();
    votingWindow.setTitle("E-Voting Ballot");
    // sets size of window
    votingWindow.setSize(windowWidth, windowHeight);
    // specifices when close button is clicked
    votingWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // adds a gridlayout to the frame
    votingWindow.setLayout(new GridLayout(1, (numBallots + 2)));

    // populates x amount of panels with x amount of ballot info
    for (int i = 0; i < numBallots; i++) {
      // creates a new JPanel
      JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 10));
      // creates a label corresponding to a specific element in ArrayList
      JLabel ballotLabel = new JLabel(list.get(i).getBallot());
      ballotLabel.setFont(ballotLabel.getFont().deriveFont(15.0f));
      // adds label to panel
      panel.add(ballotLabel);
      // creates buttons corresponding to specific ballot and adds to panel
      for (int m = 0; m < list.get(i).getCandidates().length; m++) {
        button = new JButton(list.get(i).getCandidates()[m]);
        button.setFont(button.getFont().deriveFont(14.0f));
        panel.add(button);
        button.setEnabled(false);
        button.addActionListener(new ButtonListener());
        //Add button to ArrayList
        list.get(i).setButtons(button);
      }
      // adds panel to window
      votingWindow.add(panel);
    }

    // creates two JPanels for login button and vote casting button
    JPanel login = new JPanel(new FlowLayout(FlowLayout.CENTER,0, 100));
    JPanel cast = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100));
    // names button and gives it functionality
    loginButt = new JButton("Login to Vote");
    loginButt.setFont(loginButt.getFont().deriveFont(18.0f));
    // adds an action listener to the login button
    loginButt.addActionListener(new ButtonListener());

    castButt = new JButton("Cast Your Vote");
    castButt.setFont(castButt.getFont().deriveFont(18.0f));
    castButt.setEnabled(false);
    castButt.addActionListener(new ButtonListener());

    // adds buttons to panels
    login.add(loginButt);
    cast.add(castButt);
    // adds panels to window
    votingWindow.add(login);
    votingWindow.add(cast);

    // displays window
    votingWindow.setVisible(true);

    printWriter();
  } // end main

  // has user enter their ID in order to vote
  public static class ButtonListener implements ActionListener {

     public void actionPerformed (ActionEvent e) {
       Component actionButt = (Component) e.getSource();

       // executes if user logs in
       if(actionButt == loginButt) {
         String input = JOptionPane.showInputDialog(null, "Enter your ID:",
          "Identification Verification", JOptionPane.PLAIN_MESSAGE);

          boolean flag = false;
          // iterates through for loop finding apporpriate ID
          for(int i = 0; i < _list.size(); i++) {
            if(input.equals(_list.get(i).getVoterID()) && !_list.get(i).getHasVoted()) {
              JOptionPane.showMessageDialog(null, "Welcome " + _list.get(i).getVoterName() + ". Please cast your vote.");
              flag = true;
              // puts user ID in a temporary variable
              tempID = input;
              break;
            }
            // DOES NOT WORK
            if(input.equals(_list.get(i).getVoterID()) && _list.get(i).getHasVoted()) {
              JOptionPane.showMessageDialog(null, input + ", you already voted!");
            }
          }
          // if user is not registered or input is invalid
          if(!flag) {
            JOptionPane.showMessageDialog(null, "ID NOT FOUND");
          } else {
            // enables all candidate buttons
            for (int i = 0; i < list.size(); i++) {
              int size = list.get(i).getButtons().size();
              for (int m = 0; m < size; m++) {
                list.get(i).getButtons().get(m).setEnabled(true);
              }
            }
            // enables the casting vote button
            castButt.setEnabled(true);
            // disables the login button
            loginButt.setEnabled(false);
          }
       } else if (actionButt == castButt) { // if user casts their vote
         String[] options = {"Yes", "No", "Cancel"};
         int n = JOptionPane.showOptionDialog(null, "Confirm?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
         JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
         // if user confirms vote
         if (n == 0) {
           JOptionPane.showMessageDialog(null, "Thanks for voting!");
           // changes voter status
           for(int i = 0; i < _list.size(); i++) {

             if(tempID.equals(_list.get(i).getVoterID())) {
               _list.get(i).setHasVoted(true);
             }
           }
           // resets window for next user to use
           for (int k = 0; k < list.size(); k++) {
             for (int m = 0; m < list.get(k).getButtons().size(); m++) {
               list.get(k).getButtons().get(m).setEnabled(false);
               list.get(k).getButtons().get(m).setForeground(Color.BLACK);
             }
           }
           castButt.setEnabled(false);
           loginButt.setEnabled(true);
         }
         // writes updated information into a file
         // THIS DOES NOT WORK AND I DONT KNOW WHY (exception not allowed)
        //printWriter();

       } else { // if user votes on candidates
         JButton temp = null;
         Ballot tempBallot = null;
         // assigns clicked button in specific panel to temp
         for (int i = 0; i < list.size(); i++) {
           int size = list.get(i).getButtons().size();
           for (int m = 0; m < size; m++) {
             if (actionButt == list.get(i).getButtons().get(m)) {
               temp = list.get(i).getButtons().get(m);
               list.get(i).setSelected(temp);
               tempBallot = list.get(i);
               break;
             }
           }
         }
         // set specific panel buttons to black
         for (int i = 0; i < list.size(); i++) {
           int size = list.get(i).getButtons().size();
           if (tempBallot == list.get(i)) {
             for(int m = 0; m < list.get(i).getButtons().size(); m++) {
               list.get(i).getButtons().get(m).setForeground(Color.BLACK);
             }
           }
         }
         // set final clicked button to red
         temp.setForeground(Color.RED);
         counter++;
      } // end
     } // end actionPerformed ()
   } // end class ButtonListener

   // writes updated information into a new text file and deletes the old one
   public static void printWriter() throws IOException {
     // creates a temporary file name
     String filename;
     filename = "voters.txt";
     // opens the file
     PrintWriter outFile = new PrintWriter(filename);
     // writes out a new voter text file
     for (int i = 0; i < _list.size(); i++) {
       outFile.println(_list.get(i).getVoterID() + ":" + _list.get(i).getVoterName() + ":" + _list.get(i).getHasVoted());
     }
     outFile.close();

     for(int i = 0; i < list.size(); i ++) {
       outFile = new PrintWriter(list.get(i).getBallotID());
       for (int j = 0; j < list.get(i).getCandidates().length; j++) {
         outFile.println(list.get(i).getCandidates()[j] + ":" + counter);
       }
       outFile.close();
     }
   } // end printWriter()
 } // end class main
