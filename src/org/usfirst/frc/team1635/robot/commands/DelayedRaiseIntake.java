package org.usfirst.frc.team1635.robot.commands;
import org.usfirst.frc.team1635.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class DelayedRaiseIntake extends Command {

	public DelayedRaiseIntake(double delay) {
		requires(Robot.intaker);
		setTimeout(delay); //give the pistons time to work.
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.intaker.raiseIntaker();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
