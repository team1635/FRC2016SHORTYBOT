package org.usfirst.frc.team1635.robot.subsystems;


import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1635.robot.commands.VisionProcessing;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;

//import com.ni.vision.NIVision;
//import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DoubleCamera extends Subsystem {
	private int camCenter;
	private int camRight;
	private int curCam;
	//private Image frame;
	private CameraServer server;
	private UsbCamera camera;
	private Joystick stick;
	boolean cameraStatus;
	Thread visionThread;
	public Mat mat;
	public GripPipeline grip;
	
	
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public DoubleCamera(){
		//server = CameraServer.getInstance(); // single camera
        //server.setQuality(50);
        //server.startAutomaticCapture("cam0");
		
		//CameraServer.getInstance().startAutomaticCapture();
		
		visionThread = new Thread(() -> {
			// Get the UsbCamera from CameraServer
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			//GripPipeline.process(mat);
			// Set the resolution
			camera.setResolution(480, 320);
			camera.setFPS(24);

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo();
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 480, 320);

			// Mats are very memory expensive. Lets reuse this Mat.
			
			mat = new Mat();

		// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			grip = new GripPipeline(mat);
			
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if (cvSink.grabFrame(mat) == 0) {
					
					// Send the output the error.
					outputStream.notifyError(cvSink.getError());
					// skip the rest of the current iteration
					continue;
				}
				
				grip.process();	
				System.out.println("Mat generated while loop");
				// Put a rectangle on the image
				Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
						new Scalar(255, 255, 255), 5);
				// Give the output stream a new image to display
				
				//outputStream.putFrame(mat);
				outputStream.putFrame(grip.cvErodeOutput());
			}
			
			boolean runningMat = true;
			int count = 0;
			while (runningMat){
				System.out.println("Mat generated runningMat" + count);
				grip.process();	
				outputStream.putFrame(grip.cvErodeOutput());
				count += 1;
				//outputStream.
			}
			
					
		});
		visionThread.setDaemon(true);
		visionThread.start();
		
//		GripPipeline grip = new GripPipeline(mat_);
			
//		stick = Robot.oi.getJoystick();
//		// Get camera ids by supplying camera name ex 'cam0', found on roborio web interface
//        camCenter = NIVision.IMAQdxOpenCamera(RobotMap.camNameCenter, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
//        camRight = NIVision.IMAQdxOpenCamera(RobotMap.camNameRight, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
//        curCam = camCenter;
//        // Img that will contain camera img
//        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
//        // Server that we'll give the img to
//        server = CameraServer.getInstance();
//        server.setQuality(RobotMap.imgQuality);
//        server.setSize(0);// limit the resolution to 160*120
//        
        
	}
	
//	public void runGrip(Mat input){
//		
//		
//	}
	
	public void loopGrip(){
		grip.process();	
	}
	
	public void StartCam0(){
		server = CameraServer.getInstance();
		//server.setQuality(15);		
		//server.startAutomaticCapture("cam0");;
		
	}
	public void StartCam1(){
		server = CameraServer.getInstance();
		//server.setQuality(15);		
		//server.startAutomaticCapture("cam1");
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new DualCameras());
    	//setDefaultCommand(new VisionProcessing());
    }
    public void init()
	{
    	
		changeCam(camCenter);
	}
	
	public void run()
	{
		if(stick.getRawButton(2))
			changeCam(camCenter);
		
		if(stick.getRawButton(1))
			changeCam(camRight);
		
		updateCam();
	}
	
	
	
	public void runWithOneButton(){
		boolean camSwitch = false;
		boolean toggle = true;		
		cameraStatus = stick.getRawButton(2);        
		 
		if (toggle && cameraStatus) {  // Only execute once per Button push
		  toggle = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
		  if (camSwitch) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
		    camSwitch= false;
		    changeCam(camCenter);
		  } else {
		    camSwitch= true;
		    changeCam(camRight);
		  }
		} else if(!cameraStatus) { 
		    toggle = true; // Button has been released, so this allows a re-press to activate the code above.
		}
		updateCam();
		
	}
	
	public void runCamZero(){
		changeCam(camCenter);
		updateCam();
	}
	
	public void runCamOne(){
		changeCam(camRight);
		updateCam();
	}
	
	/**
	 * Stop aka close camera stream
	 */
	public void end()
	{
		//NIVision.IMAQdxStopAcquisition(curCam);
		
	}
	
	/**
	 * Change the camera to get imgs from to a different one
	 * @param newId for camera
	 */
	public void changeCam(int newId)
    {
		//NIVision.IMAQdxStopAcquisition(curCam);
    	//NIVision.IMAQdxConfigureGrab(newId);
    	//NIVision.IMAQdxStartAcquisition(newId);
    	curCam = newId;
    }
    
	/**
	 * Get the img from current camera and give it to the server
	 */
    public void updateCam()
    {
    	//NIVision.IMAQdxGrab(curCam, frame, 1);
        //server.setImage(frame);
    }
}

