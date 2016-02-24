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
    	addSequential(new DriveStraightToDefenseIncline());
    	addSequential(new DriveStraightOffLowBar());
    	addSequential(new DriveTimeoutWithCorrection(RobotMap.kLowBarToTurn));
    	
    	//addSequential(new SpinToAngle(RobotMap.kLowBarSpinAngle));
		addSequential(new RotateToSetPoint(RobotMap.kLowBarSpinAngle, true));
		addSequential(new RaiseIntake());
    	addSequential(new DriveStraightToDefenseIncline());
    	addSequential(new LowerIntake());
    	addSequential(new RollIn_OutTheBall(false));
    	
    }
}
