package org.firstinspires.ftc.teamcode.archive.v2;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Disabled
@Autonomous(name="jerry buffed long lmao")
public class AutonTest3 extends LinearOpMode {
    OpenCvWebcam webcam = null;
    int randomizationResult = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        Hardware_v2 robot = new Hardware_v2();
        robot.init(hardwareMap, telemetry);
        robot.reset();

        // Variables
        double xTarget = 0;
        double yTarget = 0;
        double headingTarget = 0;
        double botHeading;
        double left_x;
        double left_y;
        double rot_x;
        double lx;
        double ly;
        double denominator;
        double error1;
        double lastError1=0;
        double integral1=0;
        double kp1 = 0.05;
        double ki1 =0;
        double kd1 =0;
        double error2;
        double lastError2=0;
        double integral2=0;
        double kp2 = 0.05;
        double ki2 =0;
        double kd2 =0;
        double error3;
        double lastError3 = 0;
        double integral3=0;
        double kp3 = 0.6;
        double ki3=0;
        double kd3 = 0;




        int moveStep = 1;
        ElapsedTime timer1 = new ElapsedTime();
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        FtcDashboard.getInstance().startCameraStream(webcam, 0);

        webcam.setPipeline(new TeamPropPipeline());
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {webcam.startStreaming(640, 360, OpenCvCameraRotation.UPRIGHT);}

            @Override
            public void onError(int errorCode) {}
        });

        telemetry.addData("Randomization", randomizationResult);
        telemetry.addData("Status", "Initialised");
        telemetry.update();
        drive.setPoseEstimate(new Pose2d(-36, -62, Math.toRadians(90)));

        waitForStart();
        webcam.stopRecordingPipeline();
        webcam.stopStreaming();
        while(!gamepad1.share){
            if(gamepad1.right_bumper) {
                if (gamepad1.triangle) {
                    kp1 += 0.01;
                }
                if (gamepad1.cross) {
                    kp1 += -0.01;
                }
                if (gamepad1.dpad_up){
                    kd1 += 0.01;
                }
                if (gamepad1.dpad_down){
                    kd1 += -0.01;
                }
                if(gamepad1.dpad_right){
                    ki1+=0.01;
                }
                if(gamepad1.dpad_left){
                    ki1+=-0.01;
                }
            } else {
                if (gamepad1.triangle) {
                    kp3 += 0.01;
                }
                if (gamepad1.cross) {
                    kp3 += -0.01;
                }
                if (gamepad1.dpad_up){
                    kd3 += 0.01;
                }
                if (gamepad1.dpad_down){
                    kd3 += -0.01;
                }
                if(gamepad1.dpad_right){
                    ki3+=0.01;
                }
                if(gamepad1.dpad_left){
                    ki3+=-0.01;
                }
            }


            telemetry.addData("kp1",kp1);
            telemetry.addData("ki1",ki1);
            telemetry.addData("kd1",kd1);
            telemetry.addData("kp3",kp3);
            telemetry.addData("ki3",ki3);
            telemetry.addData("kd3",kd3);

        }
        kp2=kp1;
        ki2=ki1;
        kd2=kd1;
        while (opModeIsActive()) {


            Pose2d poseEstimate = drive.getPoseEstimate();

            if (moveStep == 1) {
                xTarget = -36;
                yTarget = -36;
                headingTarget = 90;
                if ((Math.abs(poseEstimate.getY() - xTarget) > 1)|| (Math.abs(poseEstimate.getY() - xTarget) > 1)){
                    timer1.reset();
                }
                if (timer1.milliseconds() > 300) {
                    moveStep = 2;
                    timer1.reset();
                }
            }
            if (moveStep == 2) {


                if(randomizationResult==1) {
                    headingTarget = 180;
                    xTarget = -32;
                    yTarget = -30;
                }else if (randomizationResult==2){
                    headingTarget=90;
                    xTarget = -38;
                    yTarget=-36;
                }else {
                    headingTarget=0;
                    xTarget = -40;
                    yTarget = -36;
                }
                if ((Math.abs(poseEstimate.getY() - xTarget) > 1)|| (Math.abs(poseEstimate.getY() - xTarget) > 1)){
                    timer1.reset();
                }
                if (timer1.milliseconds() > 300) {
                    moveStep = 3;
                    timer1.reset();
                }
            }

            if (moveStep == 3) {


                if(randomizationResult==1) {
                    headingTarget = 180;
                    xTarget = -30;
                    yTarget =-32;
                }else if (randomizationResult==2){
                    headingTarget=90;
                    xTarget = -41;
                    yTarget=-36;
                }else {
                    headingTarget=0;
                    xTarget =-36;
                    yTarget =-34;
                }
                if ((Math.abs(poseEstimate.getY() - xTarget) > 1)|| (Math.abs(poseEstimate.getY() - xTarget) > 1)){
                    timer1.reset();
                }
                if (timer1.milliseconds() > 300) {
                    moveStep = 4;
                    timer1.reset();
                }
            }
            if (moveStep == 4) {


                if(randomizationResult==1) {
                    headingTarget = 180;
                    xTarget = -30;
                    yTarget =-32;
                }else if (randomizationResult==2){
                    headingTarget=90;
                    xTarget = -41;
                    yTarget=-36;
                }else {
                    headingTarget=0;
                    xTarget =-36;
                    yTarget =-34;
                }
                if ((Math.abs(poseEstimate.getY() - xTarget) > 1)|| (Math.abs(poseEstimate.getY() - xTarget) > 1)){
                    timer1.reset();
                }
                if (timer1.milliseconds() > 300) {
                    moveStep = 4;
                    timer1.reset();
                }
            }



            botHeading = poseEstimate.getHeading();
            error1 = (xTarget - poseEstimate.getX());
            left_y = ((error1) * kp1 + integral1*ki1 +((error1 - lastError1) * kd1));
            error2 = (poseEstimate.getY() - yTarget);
            left_x = ((error2) * kp2 + integral1*ki2 +((error2 - lastError2) * kd2));
            error3 = (Math.toRadians(headingTarget) - botHeading);
            if (error3 > Math.PI) {
                error3 -= 2 * Math.PI;
            }
            if (error3 < -Math.PI) {
                error3 += 2 * Math.PI;
            }


            rot_x = -((error3) * kp3 + integral3*ki3 +((error3 - lastError3) * kd3));
            if (Math.abs(error3) < Math.toRadians(2)) {
                rot_x = 0;
            }
            integral1 += error1;
            integral2 += error2;
            integral3 +=error3;
            lastError1 = error1;
            lastError2 = error2;
            lastError3 = error3;
            //rot_x = (headingTarget - poseEstimate.getHeading()) * -kp3;
            lx = left_x * Math.cos(-botHeading) - left_y * Math.sin(-botHeading);
            ly = left_x * Math.sin(-botHeading) + left_y * Math.cos(-botHeading);
            denominator = Math.max(Math.abs(left_x) + Math.abs(left_y) + Math.abs(rot_x), 1);
            robot.motorFL.setPower((lx + ly + rot_x) / denominator);
            robot.motorBL.setPower((-lx + ly + rot_x) / denominator);
            robot.motorFR.setPower((ly - lx - rot_x) / denominator);
            robot.motorBR.setPower((lx + ly - rot_x) / denominator);

            drive.update();

            telemetry.addData("ly", ly);
            telemetry.addData("error3", error3);
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", Math.toDegrees(poseEstimate.getHeading()));
            telemetry.addData("result",randomizationResult);
            telemetry.addData("Move",moveStep);

            telemetry.update();
        }
    }


    class TeamPropPipeline extends OpenCvPipeline {
        Mat YCbCr = new Mat();
        Mat leftCrop, middleCrop, rightCrop;
        double leftAverageFinal, middleAverageFinal, rightAverageFinal;
        Mat output = new Mat();
        Scalar rectColour = new Scalar(255.0, 0.0, 0.0);

        public Mat processFrame(Mat input) {
            Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);
            telemetry.addLine("Pipeline running.");

            // TODO: tune values when new car
            Rect leftRect = new Rect(30, 100, 100, 79);
            Rect middleRect = new Rect(300, 100, 100, 79);
            Rect rightRect = new Rect(539, 100, 100, 79);

            input.copyTo(output);
            Imgproc.rectangle(output, leftRect, rectColour, 2);
            Imgproc.rectangle(output, middleRect, rectColour, 2);
            Imgproc.rectangle(output, rightRect, rectColour, 2);

            leftCrop = YCbCr.submat(leftRect);
            middleCrop = YCbCr.submat(middleRect);
            rightCrop = YCbCr.submat(rightRect);

            Core.extractChannel(leftCrop, leftCrop, 2);  // Channel 2 = red
            Core.extractChannel(middleCrop, middleCrop, 2);
            Core.extractChannel(rightCrop, rightCrop, 2);

            Scalar leftAverage = Core.mean(leftCrop);
            Scalar middleAverage = Core.mean(middleCrop);
            Scalar rightAverage = Core.mean(rightCrop);

            leftAverageFinal = Math.abs(leftAverage.val[0] - 100);
            middleAverageFinal = Math.abs(middleAverage.val[0] - 100);
            rightAverageFinal = Math.abs(rightAverage.val[0] - 100);

            if ((leftAverageFinal < middleAverageFinal) && (leftAverageFinal < rightAverageFinal)) {
                telemetry.addLine("left");
                randomizationResult = 1;
            } else if ((middleAverageFinal < leftAverageFinal) &&  (middleAverageFinal < rightAverageFinal)) {
                telemetry.addLine("middle");
                randomizationResult = 2;
            } else {
                telemetry.addLine("right");
                randomizationResult = 3;
            }
            telemetry.addData("left", leftAverageFinal);
            telemetry.addData("middle", middleAverageFinal);
            telemetry.addData("right", rightAverageFinal);
            telemetry.addData("result",randomizationResult);

            telemetry.update();

            return output;
        }
    }
}