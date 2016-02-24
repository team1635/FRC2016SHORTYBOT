
package org.usfirst.frc.team1635.robot.subsystems;

import org.usfirst.frc.team1635.robot.RobotMap;
import org.usfirst.frc.team1635.robot.commands.DriveWithJoystick;

import NavxMXP.AHRS;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @Authors : Jing Wei Li (SKRUB_HUNTER) , Miguel Cruz (@Acelogic_ )
 */
public class DriveTrain extends Subsystem {
	private SpeedController frontLeft, backLeft, frontRight, backRight;
	private RobotDrive drive;
	private Solenoid gearShifter;
	SerialPort serial_port;
	double degrees, DistanceToStop;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	AHRS imu; // This class can only be used w/the navX MXP.
	boolean first_iteration, onTarget, direction;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new DriveWithJoystick());
	}

	public DriveTrain() {
		super();

		frontLeft = new Victor(RobotMap.kDriveTrain_FrontLeftMotorController);
		backLeft = new Victor(RobotMap.kDriveTrain_BackLeftMotorController);
		frontRight = new Victor(RobotMap.kDriveTrain_FrontRightMotorController);
		backRight = new Victor(RobotMap.kDriveTrain_BackRightMotorController);

		drive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);

		gearShifter = new Solenoid(RobotMap.kGearShifterPort);

		LiveWindow.addActuator("Drive Train", "Front_Left Motor", (Victor) frontLeft);
		LiveWindow.addActuator("Drive Train", "Back Left Motor", (Victor) backLeft);
		LiveWindow.addActuator("Drive Train", "Front Right Motor", (Victor) frontRight);
		LiveWindow.addActuator("Drive Train", "Back Right Motor", (Victor) backRight);

		try {
			serial_port = new SerialPort(57600, SerialPort.Port.kMXP);

			byte update_rate_hz = 50;
			// imu = new IMU(serial_port,update_rate_hz);
			// imu = new IMUAdvanced(serial_port,update_rate_hz);
			imu = new AHRS(serial_port, update_rate_hz);
		} catch (Exception ex) {
			ex.printStackTrace();

		}

		if (imu != null) {
			LiveWindow.addSensor("IMU", "Gyro", imu);
		}
		first_iteration = true;

		// When calibration has completed, zero the yaw
		// Calibration is complete approaximately 20 seconds
		// after the robot is powered on. During calibration,
		// the robot should be still

		boolean is_calibrating = imu.isCalibrating();
		if (first_iteration && !is_calibrating) {
			Timer.delay(0.3);
			imu.zeroYaw();
			first_iteration = false;
		}

	}

	/**
	 * f you if you don't understand
	 * 
	 * @return gyroscope angles
	 */
	public double obtainYaw() {
		return imu.getYaw();
	}

	public void log() {
		// SmartDashboard.putBoolean("gearStatus", gearShifter.get());
		SmartDashboard.putNumber("gyro", obtainYaw());
		SmartDashboard.putNumber("pitch", getPitch());
		SmartDashboard.putNumber("roll", getRoll());
	}

	public void drive(Joystick joy) {
		// driveSquared( -1 * joy.getY() , -1 * joy.getRawAxis(5) * 0.9);
		drive.tankDrive(joy.getY(), joy.getRawAxis(5));
	}
	public void driveWithSpeed(double velocity){
		drive.tankDrive(velocity, velocity);
		
	}
	
	public void spin() {
		drive.tankDrive(RobotMap.kLowBarSpinSpeed, RobotMap.kLowBarSpinSpeed * -1);
	}

	/*
	 * unlimited drive for timeouts
	 */
	public void unlimitedDrive(double leftspd, double rightspd) {
		drive.tankDrive(-leftspd, -rightspd);
	}

	/**
	 * 
	 * @param REKTstick
	 *            Most Horrible Driving Method Yet IGN-1000/10
	 *            "Horrible Never Again" - Forbes
	 */
	public void Chrys_s_DeeznutsIllegalDriveMethod(Joystick REKTstick) {
		drive.tankDrive(REKTstick.getY(), REKTstick.getRawAxis(5));

	}

	/* Can be replaced by stop() */
	public void stopDrive() {
		drive.tankDrive(0, 0);
	}

	/* Bob */
	public void stop() {
		drive.tankDrive(0, 0);
	}

	/* Can be replaced with drive(double, double) */
	public void driveWithParameters(double left, double right) {
		drive.tankDrive(left, right);
	}

	/* Bob */
	public void drive(double left, double right) {
		drive.tankDrive(left, right);
	}

	public void shiftGearsTwoButton(Joystick joy) {
		if (joy.getRawButton(9)) {
			gearShifter.set(false);
		} else if (joy.getRawButton(10)) {
			gearShifter.set(true);
		}
	}

	public void getGearStatus() {
		if (!gearShifter.get()) {
			System.out.println("high gear");
			// SmartDashboard.put
		} else {
			System.out.println("low gear");
		}
	}

	public void shiftGears(Joystick stick) {// button x
		if (stick.getRawButton(3)) {
			if (gearShifter.get()) {
				gearShifter.set(false);
			} else if (!gearShifter.get()) {
				gearShifter.set(true);
			}
		}
	}

	public void resetOnTarget() {
		onTarget = false;
	}

	public boolean isOnTarget() {
		return onTarget;
	}

	/**
	 * set the direction and degrees for rotation
	 * 
	 * @param deg
	 *            angles for rotation
	 * @param dir
	 *            direction for rotation: true - clockwise, false -
	 *            counterClockwise
	 */
	public void setRotation(double deg, boolean dir) {
		imu.zeroYaw();
		this.degrees = deg;
		this.direction = dir;
	}

	/**
	 * rotate to a certain angle
	 */
	public void AngularRotation() {
		onTarget = false;
		if (direction) {// turn to the right
			if (obtainYaw() < degrees + 1.0 && obtainYaw() > degrees - 1.0) {
				drive.tankDrive(0, 0);
				onTarget = true;
			} else {
				drive.tankDrive(0.5, -0.5);
			}
		} else if (!direction) {// turn to the left
			double inverted = -degrees;
			if (obtainYaw() < inverted + 1.0 && obtainYaw() > inverted - 1.0) {
				drive.tankDrive(0, 0);
				onTarget = true;
			} else {
				drive.tankDrive(-0.5, 0.5);
			}
		}
	}

	public double getPitch() {
		return imu.getPitch();
	}
	public double getRoll(){
		return imu.getRoll();
	}

	public void drive_65(double speed) {
		if (getPitch() < -1) {
			drive.tankDrive(0, 0);
			onTarget = true;
		} else {
			// drive.tankDrive(0.65, 0.65);
			correctWhileDrivingWOPitch(speed);
			//Timer.delay(3);
		}
		//correctWhileDrivingWOPitch();
	}

	public void driveStraight(double spd) {
	   correctWhileDrivingWOPitch(spd);
	}

	/**
	 * Drive while maintaining the correct direction with the gyro on the NavX
	 */
	public void correctWhileDriving() {
		log();

		if (getPitch() < 4 && getPitch() > -4) {
			if (obtainYaw() > 0) {
				if (obtainYaw() < 1.5 && obtainYaw() > 0) {
					drive.tankDrive(0.85, 0.85);
				} else if (obtainYaw() > 1.5 && obtainYaw() < 4) {
					drive.tankDrive(-0.35, 0.35);
				} else if (obtainYaw() > 4) {
					drive.tankDrive(-0.45, 0.45);
				}
			} else if (obtainYaw() < 0) {
				if (obtainYaw() > -1.5 && obtainYaw() < 0) {
					drive.tankDrive(0.85, 0.85);
				} else if (obtainYaw() < -1.5 && obtainYaw() > -4) {
					drive.tankDrive(0.35, -0.35);
				} else if (obtainYaw() < -4) {
					drive.tankDrive(0.45, -0.45);
				}
			}
		} else {
			drive.tankDrive(0.75, 0.75);
		}
	}

	public void correctWhileDrivingWOPitch(double driveSpeed) {
		log();

		if (obtainYaw() > 0) {
			if (obtainYaw() < 1.5 && obtainYaw() > 0) {
				drive.tankDrive(driveSpeed, driveSpeed);
			} else if (obtainYaw() > 1.5 && obtainYaw() < 4) {
				drive.tankDrive(-0.35, 0.35);
			} else if (obtainYaw() > 4) {
				drive.tankDrive(-0.45, 0.45);
			}
		} else if (obtainYaw() < 0) {
			if (obtainYaw() > -1.5 && obtainYaw() < 0) {
				drive.tankDrive(driveSpeed, driveSpeed);
			} else if (obtainYaw() < -1.5 && obtainYaw() > -4) {
				drive.tankDrive(0.35, -0.35);
			} else if (obtainYaw() < -4) {
				drive.tankDrive(0.45, -0.45);
			}
		}

	}
	

	public void reset() {
		imu.zeroYaw();
		
		// sonar.resetAccumulator();
		onTarget = false;
		imu.resetDisplacement();

		// getDistanceSonar()=0;
	}

	public double convertNavXtoInches() {
		double inches = imu.getDisplacementX() * 1.116;
		return inches;
	}

	public void setDistToStop(double dist_) {
		this.DistanceToStop = dist_;
	}

	public void NavxDriveToSetPoint() {
		// double dist = imu.getDisplacementX();
		double dist = convertNavXtoInches();
		SmartDashboard.putNumber("autonomous distance", dist);

		if (dist >= DistanceToStop) {
			drive.tankDrive(0, 0);
			onTarget = true;
		} else {
			drive.tankDrive(-0.3, -0.3);
		}
	}

	public void brake() {
		drive.tankDrive(-0.65, -0.65);
		Timer.delay(0.5);
		drive.tankDrive(0, 0);
		onTarget = true;
	}

	public void unlimitedDrive(double spd) {
		drive.tankDrive(spd, spd);
		SmartDashboard.putNumber("vertical displacement", imu.getDisplacementY());
	}

}
