package org.usfirst.frc.team1635.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1635.robot.RobotMap;

/**
 * @author: Jing Wei Li (SKRUB_HUNTER) , Miguel Cruz (@Acelogic_ )
 */
public class AutonomousLowBar extends CommandGroup {
    
    public  AutonomousLowBar() {
    	addSequential(new LowerIntake()); 
    	addSequential(new DriveStraightToDefenseIncline(RobotMap.kLowBarDefenseIncline));
    	addSequential(new DriveStraightOffLowBar());
    	addSequential(new DriveTimeoutWithCorrection(RobotMap.kLowBarToTurn));
    	
    	//addSequential(new SpinToAngle(RobotMap.kLowBarSpinAngle));
		addSequential(new RotateToSetPoint(RobotMap.kLowBarSpinAngle, true));
    	//addSequential(new DriveStraightToDefenseIncline(RobotMap.kLowBarDefenseIncline));
		//addSequential(new DriveTimeoutWithCorrection(1.0));
		addSequential(new DriveTimeout(0.9, 1.5));
    	addSequential(new RollIn_OutTheBall(false));
    	
    }
}
