import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Voter {
  // Instance Variables
  private String _voterID;
  private String _voterName;
  private boolean _hasVoted;

  // Initializes instance variables
  public Voter() {
    _voterID = "";
    _voterName = "";
  } // end Voter()


  // Methods used to give value to the private variable
  public void setVoterID(String voterID) {
    _voterID = voterID;
  } // end setVoterID()

  public void setVoterName(String voterName) {
    _voterName = voterName;
  } // end setVoterName()

  public void setHasVoted(boolean _hasVoted) {
    this._hasVoted = _hasVoted;
  } // end setHasVoted()


  // Methods used to access the value of the private varaibles
  public String getVoterID()    {return _voterID;} // end getVoterID()
  public String getVoterName()    {return _voterName;} // end getVoterName()
  public boolean getHasVoted()    {return _hasVoted;} // end getHasVoted()

} // end class Voter
