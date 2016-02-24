package org.usfirst.frc.team1635.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * 
 * 
 * : @author: Jing Wei Li (SKRUB_HUNTER) , Miguel Cruz (@Acelogic_ )
 */
public class RobotMap {
	//PWM ports
	public static int kDriveTrain_FrontLeftMotorController = 1;//0
	public static int kDriveTrain_BackLeftMotorController = 0;//0 official
	public static int kDriveTrain_FrontRightMotorController = 3;//3
	public static int kDriveTrain_BackRightMotorController = 2;//2
	public static int kIntakerTalonPort = 4;//used to be 3
	public static int kLeftWinchPort = 5;//official
	public static int kRightWinchPort = 6;//official
	
	//constants
    public static int kPressureLimit = 15;
    
    //Cheval Bob Auto
    public static double kChevalDefenseIncline = -2.0; 
    public static double kBackupFromDefenseSpeed = 0.45;
    public static double kBackupFromDefenseTime = 0.4;
    public static double kChevalRaiseIntakeDelay = 0.1;
    public static double kChevalDriveSpeed = 0.60;
    public static double kDriveOffCheval = 3.0;
    
    //Low Bar Auto
    //public static double kLowBarTimeUnderBar = 1.0; too long, stops on the low bar
    public static double kLowBarDefenseIncline = -4.0;
    public static double kLowBarTimeUnderBar = 1.5;
    //    public static double kLowBarTimeUnderBar = 3.0; too long, goes too far.
    public static double kLowBarFlatLow = 0.5;
    public static double kLowBarFlatHigh = 1.5;
    public static double kLowBarToTurn = 1.15;
    public static double kLowBarSpinAngle = 57.0;
    public static double kLowBarSpinSpeed = 0.65;
    public static double kLowBarToGoal = 2.0;
    public static double kLowBarDriveSpeed = 0.80;
    
    //AnalogInputs
    public static int kPressureAnalogPort = 3;//0
    
   //solenoid ports on the PCM 
    public static int kIntakerLifterPort = 4;//solenoid
    public static int kHookExtenderPort = 3;//TODO used to be port 0
    public static int kHookRaiserPort = 2;
    public static int kGearShifterPort = 0;
    
    //DIO ports
    public static int kFirstSwitchPort = 0;
    public static int kSecondSwitchPort =1;
    
    //4 the camera
    public static final String camNameCenter = "cam0";
	public static final String camNameRight = "cam1";
	public static final int imgQuality = 15;
}
