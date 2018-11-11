/*  Java assignment Starter seed file

This is a Java application that could be used by, say, a tourist
information office to present the details of a cycle ride in
the area. The application cou/*  CSCU9A2 Java assignment Spring 2017      Starter seed file

This is a Java application that could be used by, say, a tourist
information office to present the details of a cycle ride in
the area. The application could be left running on a computer screen
in the office, and visitors could browse the tour details as they
wished.

The route is described by the contents of three arrays:
spotHeights : containing the height (an int number of metres) at each kilometre point of the route,
including the start and end points
towns :       containing the town name (a String), if any, at each point

This starter file compiles and runs, but only does a little of the final solution.

 */
/**
 * Description - Cycle Tourist Information Assignment
 * Due Date - Monday 27th March - 6pm. 
 * Work started: 4th March
 * Work Finished: 25th March
 * Author Student Number - 2512860
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class LukesCycleTour extends JFrame
{
    /**
     * Frame layout constants
     */
    private static final int PANEL_WIDTH = 550;   // The graphics panel dimensions
    private static final int PANEL_HEIGHT = 400;

    private static final int FRAME_WIDTH = PANEL_WIDTH + 20;     // To allow suitable borders & space for widgets
    private static final int FRAME_HEIGHT = PANEL_HEIGHT + 140;

    private static final int BLUE_BLOB_SIZE = 5; //sets size of blue circle to 5
    private static final int RED_CICRCLE_SIZE = 10; // sets size of red circle to 10

    /**
     * 50 kilometres of spot heights: metres above sea level at 1 kilometre intervals
     * The kilometre points are at 0km, 1km, 2km, ... 49km, 50km, so 51 heights altogether
     * **** YOU DO NOT NEED TO CHANGE THIS ARRAY ****
     */
    private final int[] spotHeights =
        { 100,  // At 0 km, the start
            120,120,150,120,200,200,210,220,230,240,   // Heights at 1-10 km
            300,300,300,250,250,200,150,150,150,130,   // Heights at 11-20 km
            120,120,150,120,200,200,210,220,230,240,   // Heights at 21-30 km
            300,300,300,250,250,200,150,150,150,130,   // Heights at 31-40 km
            120,120,150,120,200,200,210,220,230,240 }; // Heights at 41-50 km (the end)

    /**
     * Town names, if any, at the kilometre points (empty string where there is no town)
     * **** YOU DO NOT NEED TO CHANGE THIS ARRAY ****
     */ 
    private final String[] towns =
        { "Berwick",  // At 0 km
            "","Edinburgh","","","","Falkirk","","","","",   // Towns at 1-10 km
            "","","","","","Stirling","","","","",           // Towns at 11-20 km
            "","Doune","","","","","","Dunblane","","",      // Towns at 21-30 km
            "","","","Ashfield","","","","Kinbuck","","",    // Towns at 31-40 km
            "","","Perth","","","","","","","Aviemore" };    // Towns at 41-50 km

    /**
     * Array of Empty Strings, Selected location will be used as an index to input notes about the locations. 
     * 50 Empty Strings. Ideally a database would be used
     */
    private final String[] notes = 
        {
            "","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","","","","","","","","","","","","","",
        };
    /**
     * This variable will always hold the index in the arrays of the currently selected location,
     * which can only be at a kilometre point. 
     * (Later stages of the assignment)
     */
    private int selectedLocation = 0;                                 // Initially the start of the cycle route. Used throughout as a reference

    /**
     * This is a useful colour for drawing the selected location marker.
     * The idea is that it will help red/green colour blind people see the red marker
     * against the green route segments.
     * (Later stages of the assignment)
     */ 
    private final Color selectionColour = Color.red.darker();

    /**
     * Now some constants that fix the position of the route image on the screen,
     * and provide scale information. All drawing actions, and other screen coordinate
     * computations are carried out using these constants.
     * *** You should NOT NEED to alter these definitions. You may need to ADD to them. ***
     */
    private final int seaLevelY = 270,                                // Pixels down from top of window for baseline of route diagram
    fromLeftX = 20,                                 // Pixels from left of window for spotHeights[0]
    spotHeightInterval = 10,                        // Pixels per km horizontally (that is, between spot heights)
    rightSideX = fromLeftX+(spotHeights.length-1)*spotHeightInterval,  // Pixels from left of window for spotHeights[50]
    verticalScale = 5;                              // Metres per pixel vertically

    // GUI widget definitions:
    private JPanel panel;                                             // For drawing the cycle route on;
    private Color backgroundColour =  Color.white;                    // Drawing panel background colour

    // main: Launch the CycleTour program.
    public static void main( String[] args )
    {
        LukesCycleTour frame = new LukesCycleTour(); // creates frame called CycleTour
        frame.setSize( FRAME_WIDTH, FRAME_HEIGHT ); // sets the size of the frame to the final variables created above
        frame.createGUI(); // calls creat gui 
        frame.setVisible( true ); // sets frame to visible
        frame.setTitle("Cycle Tourist Information Student Number 2512860"); // sets title to text inside quotation marks
        frame.setLocationRelativeTo(null);
    } // End of main

    // createGUI: Set up the graphical user interface.
    private void createGUI()
    {
        // Set up main window characteristics
        setDefaultCloseOperation( EXIT_ON_CLOSE ); // closes window when x pressed
        Container window = getContentPane();
        window.setLayout( new FlowLayout() ); // using flow layout

        // Set up the title
        Font titleFont = new Font("Serif", Font.BOLD, 20); // creates a font style called titleFont
        JLabel title = new JLabel("                           Cycle Tourist Information                        " ); // new jlabel called title and spaces included to allow it to be placed on new line
        title.setFont(titleFont); // sets the font to the style "titleFont" created earlier
        window.add(title); // adds the title to the window

        // Set up the panel for drawing the cycle tour on
        panel = new JPanel() 
        {
            // paintComponent is called automatically when a screen refresh is needed
            public void paintComponent(Graphics g)
            {
                // g is a cleared panel area
                super.paintComponent(g); // Paint the panel's background
                paintScreen(g);          // Then the required graphics

            }
        };

        JButton[] buttons = createButtons(panel); //creates an array called buttons. Create buttons is called which makes the array with the four main buttons

        for(int i = 0; i < buttons.length; i++) // Loop beginning at 0 until it reaches buttons length, to add all buttons to the pannel
        {
            window.add(buttons[i]); //adds each button to the window
        }

        panel.setPreferredSize( new Dimension( PANEL_WIDTH, PANEL_HEIGHT ) ); // sets dimensions previously declared as final variables
        panel.setBackground( backgroundColour );

        window.add( panel ); // adds panel to the window
        window.add( createAddNoteButton() ); // this cannot be called in array as needs to be placed under canvas
        window.add( createJumpToButton() );

    } // End of createGUI

    /**
     * This array creates all 4 of the main butttons and stores them in an array. Returning them all to be called in a loop.
     */
    private JButton[] createButtons(JPanel panel)
    {
        JButton[] buttons = new JButton[4];   // Creates an array called buttons of size 4

        buttons[0] = createStartButton(); // position 0 in the array calls the create start button method which in itself holds the start button
        buttons[1] = createPreviousButton(); // position 1 in the array calls the create previous button method which in itself holsd the start button
        buttons[2] = createNextButton(); // position 2 in the array calls the create next button method which in itself holds the next button
        buttons[3] = createEndButton(); // position 3 in the array calls the create end button method which in itself holds the end button

        return buttons; // returns all the created buttons
    }

    /**
     * Creates a button that when clicked repaints the canvas
     * and sets the selectedLocation to 0 which will cause
     * the red circle to be drawn at point 0
     */
    private JButton createStartButton()
    {
        JButton startButton = new JButton ("Start <<"); // new button displaying start. 
        startButton.addActionListener(new ActionListener()//adds an action listener (when button is pressed do this action)
            {
                public void actionPerformed(ActionEvent e)
                {
                    selectedLocation = 0; // sets selcted location to 0 therefore bringing red circle back to 0 
                    repaint(); // repaint the whole canvas updating all the information and position of red circle
                }
            });
        return startButton; // returns the button and its details
    }

    /**
     * Creates a buytton that when clicked repaints the canvas
     * and sets the selected location to the previous point on the route
     * which will cause the red circle to go back one (to be drawn at the previousa location.)
     * however will not allow you to go before the starting position
     */
    private JButton createPreviousButton()
    {
        JButton previousButton = new JButton ("Previous <"); // new button displaying previous
        previousButton.addActionListener(new ActionListener()//adds an action listener (when button is pressed do this action)
            {
                public void actionPerformed(ActionEvent e)
                {
                    if(selectedLocation > 0) // this statement means that previous will only do something if it is not at position 0
                    {
                        selectedLocation --; // move selected location down one and so red circle moves back one
                        repaint();// repaint the whole canvas updating all the information and position of red circle
                    }
                }
            });
        return previousButton; // returns the button and its details
    }

    /**
     * Creates a button that when clicked repaints the canvas
     * and sets the selection location to the next point on the route
     * which will cause the red circle to be updated and drawn on the next location 
     * will not allow you to go further than the final position on the route
     */
    private JButton createNextButton()
    {
        JButton nextButton = new JButton ("Next > ");    // creates new button displaying next    
        nextButton.addActionListener(new ActionListener()//adds an action listener (when button is pressed do this action)
            {
                public void actionPerformed(ActionEvent e)
                {
                    if(selectedLocation < spotHeights.length) // this statement means thatnext will only do something  if the selected location is less than 50 it will 
                    {
                        selectedLocation ++; // increment selected location by 1 and so red circle moves forward one
                        repaint();// repaint the whole canvas updating all the information and position of red circle
                    }
                }
            });

        return nextButton; // returns the button and its details
    }

    /**
     * Creates a button that when clicked repaints the canvas 
     * and sets the selection location to the end of the route which
     * wioll cause the red circle to be drawn at the last location 
     * based on the number of elements in the spotheights array
     */
    private JButton createEndButton()
    {
        JButton endButton = new JButton ("End >>");// creates new button displaying end
        endButton.addActionListener(new ActionListener()//adds an action listener (when button is pressed do this action)
            {
                public void actionPerformed(ActionEvent e)
                {
                    selectedLocation = (spotHeights.length-1 ); // sets seleted location to the length of the array (the final location) and therefore updating the red circle
                    repaint();// repaint the whole canvas updating all the information and position of red circle
                }
            });

        return endButton; // returns the button and its details
    }

    /**
     * Creates a button that when clicked repaints the canvas
     * and produces a dialog box asking for a note to be added
     * which will allow a string of info to be displayed at a selected location
     */
    private JButton createAddNoteButton()
    {
        JButton addNoteButton = new JButton ("Add/Edit Note"); // creates new button displaying add / edit note
        addNoteButton.addActionListener(new ActionListener() //adds an action listener (when button is pressed do this action)
            {
                public void actionPerformed(ActionEvent e) 
                {
                    String note = JOptionPane.showInputDialog("Add a note for this location"); // produces message asking for note. string is stored in variable called note
                    notes[selectedLocation] = note; // sets the string just iunput into the note array declared earlier at position selected location (the current location)
                    repaint();// repaint the whole canvas updating all the information and position of red circle
                }
            });

        return addNoteButton; // returns the button and its details
    }

    /**
     * Creates a button that when clicked repaints the canvas
     * and produces a dialog box asking for a desired jump location
     * which when entered updates the red circle to said location
     */
    private JButton createJumpToButton()
    {
        JButton jumpToButton = new JButton("Jump To Desired Location"); // creatse button displaying jump to  desired location
        jumpToButton.addActionListener(new ActionListener()//adds an action listener (when button is pressed do this action)
            {
                public void actionPerformed(ActionEvent e)
                {

                    selectedLocation = Integer.parseInt( JOptionPane.showInputDialog("Enter Location (In km) That You Would Like To Jump To (Between 1-50)")); // sets selected location to what they input into dialouge box.  Parse int used to convert string to integer
                    while (selectedLocation < 1 || selectedLocation > 50 ) // checks the number input is within range and will continue to ask until a valid number is input
                    { 
                        selectedLocation = Integer.parseInt( JOptionPane.showInputDialog("Incorrect Please,Enter Location (In km) That You Would Like To Jump To. MUST be between 1-50")); // 
                    }
                    repaint();// repaint the whole canvas updating all the information and position of red circle

                }
            });
        return jumpToButton; // returns the button and its details
    }

    /**
     * paintScreen: Prepare graphics panel for redisplaying, and redisplay it
     * Will be called with a blanked graphics area
     */
    private void paintScreen(Graphics g)
    {
        drawCanvasElements(g);
    } // End of paintScreen

    /**
     * Main method for drawing the canvas. Calls all required methods to display information. 
     */

    private void drawCanvasElements(Graphics g)
    {
        for (int i=0; i < spotHeights.length - 1; i++) // loops the length of the array drawing the route segments throughout
        {
            drawRouteSegment(g, i);
        }

        drawTowns(g); // calls draw towns method marking on the blue dots
        drawKey(g); // calls draw key method drawing the information belopw the route
        drawRuler(g); // calls the draw ruler method drawing the ruler below the route
        drawInfo(g);// calls the draw info method, drawing all the info such as height etc 
        drawStartAndEnd(g);// calls the start and end method drawing the start and end at either side of the route
        drawRedCircleAtSelectedLocation(g); // draws the red circles at the current/selected location

    }

    /**
     * Method draws the towns (blue blobs) at each town location
     */
    private void drawTowns(Graphics g)
    {
        int yCord, xCord = 0; // initalise the x and y cord

        for (int i = 0; i < towns.length; i++) // looping through towns
        {
            if (!towns[i].equals("")) // if statement only executes if the current position in the town array isnt empty - meaning there is a town there
            {
                xCord = fromLeftX + i * spotHeightInterval;// finds x cord 
                yCord = seaLevelY - spotHeights[i] / verticalScale; // finds y cord
                drawBlueBlob(g, xCord, yCord); // calls draw blue blob method passing through the calculated x and y cords 
            }
        }

    }

    /**
     *  method for placing blue blob on each marker needed
     */
    private void drawBlueBlob (Graphics g, int x, int y) 
    {
        int offset = calcOffset(BLUE_BLOB_SIZE); // offset used to place blob slightly above the segment
        int yCord = y - offset;
        int xCord = x - offset;

        g.setColor (Color.blue); // sets colour as blue
        g.fillRect(xCord,yCord,5,5); // creates filled blue oval 5 by 5
    }

    /**
     * method for drawing the key below the route segments
     */
    private void drawKey(Graphics g)
    {
        //blue blob information
        int xCord = getWidth() / 5; // position x cord
        int yCord = (getHeight() / 100) * 65; // position y cord
        drawBlueBlobKey(g, xCord, yCord); // method for drawing blue blob, passing graphics and two cords

        //red circle information
        xCord = getWidth() / 5; // position x cord
        yCord = (getHeight() / 100) * 70;// position y cord
        drawRedCircleKey(g, xCord, yCord);// method for drawing red circle, passing graphics and two cords
    }

    /**
     * Method for drawing bluue blobs section of key
     */
    private void drawBlueBlobKey(Graphics g, int xCord, int yCord)
    {
        drawBlueBlob(g, xCord, yCord);// draws the blue blob
        g.setFont(new Font("default", Font.BOLD, 12)); // sets font metrics
        g.drawString("Blue blobs mark towns", xCord + 15, yCord + 3); // draws a string after the blue blob explaining what it means
    }

    /**
     * Method for drawing red circle section of key
     */
    private void drawRedCircleKey(Graphics g, int xCord, int yCord)
    {
        drawRedCircle(g, xCord, yCord); // draws red circle
        g.setFont(new Font("default", Font.BOLD, 12)); // sets font metrics
        g.drawString("The red circle indicates the selected location", xCord + 15, yCord + 6); // draws a string after the red circle explaining what it means

    }

    /**
     * Draws Ruler underneath the green segments
     */
    private void drawRuler(Graphics g)
    {
        int y1 = seaLevelY + 10; // As the segments begin at a y coord equal to sealevel, want baseline to be 10 below that
        int y2 = seaLevelY + 5; // 1km indicators on the ruler
        int x;

        g.setColor(Color.black); // sets line colour to black
        g.drawLine(fromLeftX, y1, (towns.length + 1) * spotHeightInterval, y1); //draws the main base line for the ruler. starting at position from left where segment starts. 10 below base of green segment, to 50 km mark, and again 10 below 

        for(int i = 0; i < towns.length ; i++) // loop goes to full 50km to draw small lines at each 1km mark 
        {
            x = fromLeftX + (spotHeightInterval*i); //As loop executes, updates x co ordinate to the spot height, declared earlier as 10 multiplied by the position in the loop
            g.drawLine( x, y1, x, y2); //draws the 1km vertical lines on the ruler, with a set x cord going from the base line to 5 up 

            if ( (i % 10) == 0) //Here i use a modulus calculation to draw on a 10km mark at each 10km. This will ONLY exeecute. if the remainder of the number divided by 10 = 0. EG 10,20,30,40,50
            {
                g.setFont(new Font("default", Font.BOLD, 12)); // Sets the font 
                g.drawString(String.valueOf(i), x - (spotHeightInterval / 2), y1 + spotHeightInterval + 1); // draws the value of i (which will only be 0,10,20,30,40,50 below baseline)
            }
        }

        g.drawString("km", (towns.length + 2) * spotHeightInterval, y1); //draws 'km' at the end of the ruler
    }

    /**
     * Method contains code to draw the info such as selected location, distance from start, height above sea level 
     */
    private void drawInfo(Graphics g)
    {
        String info = //declares string including all the required info
            "Information about the selected location" + "\n" // displays info about selected location then \n makes a new line 
            + "Distance from start: " + selectedLocation + " km" + "\n" // displays info about distance from start then \n makes a new line 
            + "Height: " + spotHeights[selectedLocation] + "m" + "\n"; // displays info about  height then \n makes a new line 

        int xCord = getWidth() / 100 * 5; // sets x cord for information to be displayed at by retrieving the width of the frame. this makes the program scaleable
        int yCord = (getHeight() / 100) * 5;// sets y cord for information to be displayed at by retrieving the height of the frame. this makes the program scaleable

        g.setColor (Color.black); //sets the colour of the text to black
        g.setFont(new Font("default", Font.BOLD, 12)); // sets font details

        for (String line : info.split("\n")) // info.splits splits the string everytime \n is read, and therefore placing the elements into an array. EG line[0] = Information about selected location, line[1]= Distance from  start:__ km 
        {
            g.drawString(line, xCord, yCord += g.getFontMetrics().getHeight()); // draws string -- the line in line[i] , the location of where to put the text -- however, font metrics stores all the information about the font. next  get height is then used, retrieves the font height and is added to the y co ordinate to place each line below the previous
        }

        if(towns[selectedLocation] != "") // checks the town array at the position of selected location and if the string is not empty, meaning it is a town, draw the information inside the statement. This also means that town and information doesnt have to be unessecarlily displayed if the selected location is not a town
        {
            g.drawString("Town: " + towns[selectedLocation], xCord, yCord += g.getFontMetrics().getHeight()); // draws a string saying town, the string inside the array town at index of selected location and again at the correct co ordinates

        }

        if(notes[selectedLocation] != "") // repeats idea of if statement above
        {
            g.drawString("Note: " + notes[selectedLocation], xCord, yCord += g.getFontMetrics().getHeight());

        }

    }

    /**
     * Draws the route segments. 
     */
    private void drawRouteSegment(Graphics g, int index) {

        // The method draws the route segment from index to index+1
        // at an appropriate place on the screen, determined by the
        // constants above, as a filled green trapezium, a bit like this:
        //
        //                 rightY:
        //              x height at
        //             /|  index+1
        //  leftY:    / |
        //  height   /  |
        // at index x   |
        //          |   |
        //          |   |
        //          -----
        //
        // It uses the fillPolygon Graphics method
        int leftY = seaLevelY-spotHeights[index]/verticalScale;        // Height at index above sea level.
        int rightY = seaLevelY-spotHeights[index+1]/verticalScale;     // Height at index+1 above sea level.
        int leftX = fromLeftX+index*spotHeightInterval;                // Distance from left of diagram to index.
        int rightX = leftX+spotHeightInterval;                         // Distance from left of diagram to index+1.
        int[] xArray = { leftX, leftX, rightX, rightX };               // (x,y) coordinates of the four corners,
        int[] yArray = { seaLevelY, leftY, rightY, seaLevelY };        // clockwise from the bottom left.
        g.setColor(Color.green);
        g.fillPolygon(xArray, yArray, 4);                              // Array of x coords, of y coords, and the number of coords

    } // End of drawRouteSegment

    /**
     * method for drawing start and end tags
     */
    private void drawStartAndEnd(Graphics g)
    {
        drawText(g, "Start", -15, 0); // -15 sits start just above green line at km 0
        drawText(g, "End", -15, spotHeights.length - 1);// -15 sits start just above green line at 50km mark
    }

    /**
     * used to position the start and end tags
     */
    private void drawText(Graphics g, String text, int offset, int position)
    {
        int yCord = seaLevelY - spotHeights[position] / verticalScale + offset;
        int xCord = fromLeftX + position * spotHeightInterval + offset;
        g.drawString(text, xCord, yCord); 
    }

    /**
     * 
     */
    private void drawRedCircleAtSelectedLocation(Graphics g)
    {
        int yCord = seaLevelY-spotHeights[selectedLocation]/verticalScale;
        int xCord = fromLeftX+selectedLocation*spotHeightInterval;
        drawRedCircle(g, xCord, yCord);
    }

    /**
     * method for drawing the red circle 
     */
    private void drawRedCircle (Graphics g, int xCord, int yCord) 
    {
        int offset = calcOffset(RED_CICRCLE_SIZE); // calcualtes the offset using cricle size
        yCord = yCord - offset; // calcs new y cord relative to offset
        xCord = xCord - offset; // calcs new x cord relative to offset

        g.setColor (Color.red); // sets colour to red
        g.drawOval(xCord,yCord,10,10); // draws the oval 
    }

    /**
     * This method is pure asthetic and is just used to make markers sit slightly above the graph like shown in the screenshots given
     */
    private int calcOffset(int num)
    {
        Double result = num * 0.6;// sits the blob slightly above the graph
        int intResult = result.intValue(); // converst the double into integer as method can only return integer
        return intResult; // returns offset
    }

} // End of CycleTour application
