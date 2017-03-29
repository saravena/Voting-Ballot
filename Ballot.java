import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ballot extends JPanel {
  // Instance Variables
  private String _ballotID;
  private String _ballot;
  private String[] _candidates;
  private ArrayList<JButton> ballButtList;
  private JButton selected;

  // Intializes instance variables
  public Ballot() {
    _ballotID = "";
    _ballot = "";
    _candidates = null;
    ballButtList = new ArrayList<JButton>();
  } // end Ballot()


  // Methods used to give value to the private variables
  public void setBallotID(String ballotID) {
    _ballotID = ballotID;
  } // end setBallotID()

  public void setBallot(String ballot) {
    _ballot = ballot;
  } // end setBallot()

  public void setCandidates(String[] numCandidates) {
    _candidates = new String[numCandidates.length];
    for(int i = 0; i < numCandidates.length; i++) {
      _candidates[i] = numCandidates[i];
    }
  } // end setCandidates()

  public void setButtons(JButton button) {
    ballButtList.add(button);
  } // end setButtons()

  public void setSelected(JButton buttonSel) {
    selected = buttonSel;
  } // end setSelected()



  //Methods used to access the value of private variables
  public String getBallotID()    {return _ballotID;} // end getBallotID()
  public String getBallot()    {return _ballot;} // end getBallot()
  public String[] getCandidates() {
    return _candidates;
  } // end getCandidates()
  public ArrayList<JButton> getButtons()  {return ballButtList;} // end getButtons()
  public JButton setSelected()  {return selected;} // end setSelected()

} // end class Ballot
