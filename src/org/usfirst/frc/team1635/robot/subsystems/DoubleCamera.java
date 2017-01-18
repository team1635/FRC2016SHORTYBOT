package org.usfirst.frc.team1635.robot.subsystems;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DoubleCamera extends Subsystem {
	boolean cameraStatus;
	Thread visionThread;
	public Mat mat;
	public GripPipeline grip;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public DoubleCamera() {
		new Thread(() -> {
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(320, 240);
			camera.setFPS(24);

			CvSink cvSink = CameraServer.getInstance().getVideo();
			CvSource outputStream = CameraServer.getInstance().putVideo("CameraSource", 320, 240);

			Mat source = new Mat();
			Mat output = new Mat();
			
			boolean visionSwitcher = true; // TRUE For Regular Cam / FALSE For CV Filters 
			
			while (visionSwitcher == true) {
				cvSink.grabFrame(source);
				Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
				outputStream.putFrame(output);
			}
			
			while (visionSwitcher == false){ 
				
				
			}
			
			
		}).start();
	}

	//

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new DualCameras());
		// setDefaultCommand(new VisionProcessing());
	}

}
