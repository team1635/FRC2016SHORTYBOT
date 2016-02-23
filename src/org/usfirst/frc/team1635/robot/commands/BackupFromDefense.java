package org.usfirst.frc.team1635.robot.commands;

import org.usfirst.frc.team1635.robot.Robot;
import org.usfirst.frc.team1635.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BackupFromDefense extends Command {

    public BackupFromDefense() {
    	requires(Robot.drivetrain);
    	this.setTimeout(RobotMap.kBackupFromDefenseTime);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.drive(-1 * RobotMap.kBackupFromDefenseSpeed
    			, -1 * RobotMap.kBackupFromDefenseSpeed);
    }

    // Make this return true after some time elapsed
    protected boolean isFinished() {
        return this.isTimedOut();
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
