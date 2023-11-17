package org.firstinspires.ftc.teamcode.OPENCVFAIL;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous (name="BlueDetectionAuto", group = "Auto")
public class BlueDetectionAuto extends LinearOpMode {
        OpenCvCamera webcam;
        @Override
        public void runOpMode() throws InterruptedException {
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
            ObjectDetection detector = new ObjectDetection(telemetry);
            webcam.setPipeline(detector);

            webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
                    {
                        @Override
                        public void onOpened()
                        {
                            webcam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
                        }

                        @Override
                        public void onError(int errorCode) {}
                    });
            waitForStart();
            switch (detector.getLocation()){
                case Left:
                    //...
                    break;
                case Right:
                    //...
                    break;
                case Not_Found:
                    //...
            }
            webcam.stopStreaming();
        }
    }
