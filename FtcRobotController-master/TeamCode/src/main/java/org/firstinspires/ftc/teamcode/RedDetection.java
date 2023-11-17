package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class RedDetection extends OpenCvPipeline {
    private Mat workingMatrix = new Mat();
    public String position = "Left";
    public RedDetection(){

    }

    @Override
    public final Mat processFrame(Mat input){
        input.copyTo(workingMatrix);
        if(workingMatrix.empty()){
            return input;
        }
        Imgproc.cvtColor(workingMatrix,workingMatrix,Imgproc.COLOR_RGB2YCrCb);

        Mat matLeft = workingMatrix.submat(0,150,10,50);
        Mat matRight = workingMatrix.submat(0,150,80,120);
        Mat matCenter = workingMatrix.submat(0,150,150,190);

        Imgproc.rectangle(workingMatrix, new Rect(10,120,40,30), new Scalar(0,0,255));
        Imgproc.rectangle(workingMatrix, new Rect(80,120,40,30), new Scalar(0,0,255));
        Imgproc.rectangle(workingMatrix, new Rect(150,120,40,30), new Scalar(0,0,255));


        double leftTotal = Core.sumElems(matLeft).val[2];
        double rightTotal = Core.sumElems(matRight).val[2];
        double centerTotal = Core.sumElems(matCenter).val[2];

        if(leftTotal>rightTotal){
            if(leftTotal>centerTotal){
                //left is object
                position = "LEFT";
            } else{
                //right is object
                position = "RIGHT";
            }
        } else{
            if(centerTotal>rightTotal){
                //center is object
                position = "CENTER";
            } else{
                //right is object
                position = "RIGHT";
            }
        }
        return workingMatrix;

    }
}
