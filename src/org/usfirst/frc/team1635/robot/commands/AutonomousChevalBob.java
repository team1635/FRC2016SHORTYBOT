package org.usfirst.frc.team1635.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team1635.robot.RobotMap;

/**
 * @author: Jing Wei Li (SKRUB_HUNTER) , Miguel Cruz (@Acelogic_ )
 */
public class AutonomousChevalBob extends CommandGroup {
    
    public  AutonomousChevalBob() {
    	addSequential(new DriveStraightToDefenseIncline(RobotMap.kChevalDefenseIncline));
    	addSequential(new LowerIntake()); //TODO: Maybe should be done in parallel
    	addSequential(new BackupFromDefense());
    	addParallel(new DriveTimeoutWithCorrection(RobotMap.kDriveOffCheval));
    	addSequential(new DelayedRaiseIntake(RobotMap.kChevalRaiseIntakeDelay)); //TODO: I think we need to wait before this kicks off
    	        
    	// Add Commands here:
        //addSequential(new Command1());
        //addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
