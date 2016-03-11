package org.usfirst.frc.team1635.robot;


import org.usfirst.frc.team1635.robot.commands.AutonomousChevalBob;
import org.usfirst.frc.team1635.robot.commands.StopCheval;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
//import org.usfirst.frc.team1635.robot.commands.DriveWithJoystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 *
 *: @Author : Jing Wei Li (SKRUB_HUNTER) , Miguel Cruz (@Acelogic_ )
 *
 */
public class OI {
	private Joystick stick;
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	public OI(){
		stick = new Joystick(0);
		
		new JoystickButton(stick, 1).whenPressed(new AutonomousChevalBob());
//		new JoystickButton(stick, 4).whenPressed(new Raise_LowerIntaker(false));
		new JoystickButton(stick, 2).whenPressed(new StopCheval());//stop the cheval if needed
		
	}
	
	public Joystick getJoystick() {
		return stick;
	}
	
}


