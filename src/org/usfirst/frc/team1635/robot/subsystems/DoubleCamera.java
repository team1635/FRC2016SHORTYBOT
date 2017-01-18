package org.usfirst.frc.team1635.robot.subsystems;

import javax.swing.text.DefaultEditorKit.CopyAction;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.sun.javafx.collections.NonIterableChange.GenericAddRemoveChange;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DoubleCamera extends Subsystem implements VisionRunner.Listener<GripPipeline> {
	boolean cameraStatus;
	public Thread cameraThread;
	public VisionThread processingThread;
	public GripPipeline gripPipeline;
	public boolean pipelineRan = false;
	public final Object visionLock = new Object();

	Mat vidSource = new Mat();
	Mat output = new Mat();

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public DoubleCamera() {
		new Thread(() -> {
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(320, 240);
			camera.setFPS(24);

			CvSink cvSink = CameraServer.getInstance().getVideo();
			CvSource outputStream = CameraServer.getInstance().putVideo("CameraSource", 320, 240);

			while (true) {
				cvSink.grabFrame(vidSource);
				Imgproc.cvtColor(vidSource, output, Imgproc.COLOR_BGR2GRAY);
				outputStream.putFrame(output);
			}

		}).start();
	}

	@Override
	public void copyPipelineOutputs(GripPipeline pipeline) {
		synchronized (visionLock) {
			this.pipelineRan = true;
		}

	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new DualCameras());
		// setDefaultCommand(new VisionProcessing());
	}
}
