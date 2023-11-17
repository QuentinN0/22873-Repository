package org.firstinspires.ftc.teamcode.OPENCVFAIL;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class ObjectDetection extends OpenCvPipeline {
    Telemetry telemetry;
    Mat mat = new Mat();
    public enum Location{
        Left,
        Right,
        Not_Found
    }
    private Location location;
    static final Rect LEFT_ROI = new Rect(
            new Point(60, 35),
            new Point(120,75));
    static final Rect RIGHT_ROI = new Rect(
            new Point(140, 35),
            new Point(200,75));
    static double PERCENT_COLOUR_THRESHOLD = 0.4;


    public ObjectDetection(Telemetry t) {telemetry = t;}

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        Scalar lowHSV = new Scalar(0,25,25);
        Scalar highHSV = new Scalar(25,255,255);


        Core.inRange(mat, lowHSV, highHSV, mat);
        Mat left = mat.submat(LEFT_ROI);
        Mat right = mat.submat(RIGHT_ROI);

        double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;
        double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area() / 255;

        left.release();
        right.release();

        telemetry.addData("Left raw value", (int) Core.sumElems(left).val[0]);
        telemetry.addData("Right raw value", (int) Core.sumElems(right).val[0]);
        telemetry.addData("Left Percentage", Math.round(leftValue * 100) + "%");
        telemetry.addData("Right Percentage", Math.round(rightValue * 100) + "%");

        boolean objectLeft = leftValue >PERCENT_COLOUR_THRESHOLD;
        boolean objectRight = rightValue > PERCENT_COLOUR_THRESHOLD;

        if (objectLeft) {
            location = Location.Left;
            telemetry.addData("Object Location", "Left");
            //object is left
        }

        else if (objectRight) {
            location = Location.Right;
            telemetry.addData("Object Location", "Right");
            // object is right
        }
        else {
            location = Location.Not_Found;
            telemetry.addData("Object Location", "Not Found");
            //not found
            }


        telemetry.update();
        Imgproc.cvtColor(mat,mat, Imgproc.COLOR_GRAY2BGR);

        Scalar colorObject = new Scalar(0,255,0);
        Scalar noObject = new Scalar(255,0,0);

        Imgproc.rectangle(mat, LEFT_ROI, location == Location.Left? colorObject:noObject);
        Imgproc.rectangle(mat, RIGHT_ROI, location == Location.Right? colorObject:noObject);

        return mat;
    }
    public Location getLocation(){
        return location;
    }
}
