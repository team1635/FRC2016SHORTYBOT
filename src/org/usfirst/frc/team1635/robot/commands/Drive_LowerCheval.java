package org.usfirst.frc.team1635.robot.commands;

import org.usfirst.frc.team1635.robot.Robot;
import org.usfirst.frc.team1635.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive_LowerCheval extends Command {
	private boolean isDone;

    public Drive_LowerCheval() {
    	requires(Robot.intaker);
    	requires(Robot.drivetrain);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isDone = false;
    	Robot.intaker.resetOnTarget();
    	Robot.drivetrain.resetOnTarget();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.drive_65(RobotMap.kChevalDriveSpeed);
    	if(Robot.drivetrain.isOnTarget()){
    		Robot.intaker.lowerIntaker();
    		Timer.delay(2.5);
    		isDone = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intaker.stopIntaker();
    	Robot.drivetrain.stopDrive();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	isDone = true;
    	end();
    }
}
