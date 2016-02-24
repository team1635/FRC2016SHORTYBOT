package org.usfirst.frc.team1635.robot.commands;

import org.usfirst.frc.team1635.robot.Robot;
import org.usfirst.frc.team1635.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightOffLowBar extends Command {

    public DriveStraightOffLowBar() {
    	requires(Robot.drivetrain);
    	this.setTimeout(RobotMap.kLowBarTimeUnderBar);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.driveStraight();
    }

    // Make this return true when the robot starts climbing the defense
    protected boolean isFinished() {
    	double pitch;
    	boolean done = false;
    	
    	pitch = Robot.drivetrain.getPitch();
    	if (this.isTimedOut() && 
    			(pitch >= RobotMap.kLowBarFlatLow) &&
        		(pitch <= RobotMap.kLowBarFlatHigh)) {
        	   done = true;
    	} else {
    		done = false;
    	}
    	
    	return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
