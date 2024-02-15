//package org.firstinspires.ftc.teamcode;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.roadrunner.geometry.Pose2d;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.imgproc.Imgproc;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvPipeline;
//import org.openftc.easyopencv.OpenCvWebcam;
//
//@Autonomous(name="BlueFarAnalog")
//public class AutonBlueFarAnalog extends LinearOpMode {
//    OpenCvWebcam webcam = null;
//    int randomizationResult = 0;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        Hardware_v2 robot = new Hardware_v2();
//        robot.init(hardwareMap, telemetry);
//        robot.reset();
//
//        // Variables
//        double xTarget = 0;
//        double yTarget = 0;
//        double headingTarget = 0;
//        double botHeading;
//        double left_x;
//        double left_y;
//        double rot_x;
//        double lx;
//        double ly;
//        double denominator;
//        double error1;
//        double error2;
//        double error3;
//        double lastError1 = 0;
//        double lastError2=0;
//        double lastError3 = 0;
//        double integral1 = 0;
//        double integral2=0;
//        double integral3=0;
//        double kp1 = 0.16;
//        double ki1 = 0;
//        double kd1 =0.3;
//        double kp2 = 0.05;
//        double ki2 =0;
//        double kd2 =0;
//        double kp3 = 0.6;
//        double ki3=0.001;
//        double kd3 = 1;
//
//        final double servoTarget = 0;
//
//        int moveStep = 1;
//
//        ElapsedTime timer1 = new ElapsedTime();
//        ElapsedTime auton30 = new ElapsedTime();
//        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
//
//        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
//        FtcDashboard.getInstance().startCameraStream(webcam, 0);
//
//        webcam.setPipeline(new TeamPropPipeline());
//        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
//            @Override
//            public void onOpened() {webcam.startStreaming(640, 360, OpenCvCameraRotation.UPRIGHT);}
//
//            @Override
//            public void onError(int errorCode) {}
//        });
//
//        telemetry.addData("Randomization", randomizationResult);
//        telemetry.addData("Status", "Initialised");
//        telemetry.update();
//        drive.setPoseEstimate(new Pose2d(-36, 62, Math.toRadians(270)));
//        robot.closeUpperClaw();
//        robot.closeLowerClaw();
//
//        waitForStart();
//
//        webcam.stopRecordingPipeline();
//        webcam.stopStreaming();
////        while(!gamepad1.share){
////            if(gamepad1.right_bumper) {
////                if (gamepad1.triangle) {
////                    kp1 += 0.01;
////                }
////                if (gamepad1.cross) {
////                    kp1 += -0.01;
////                }
////                if (gamepad1.dpad_up){
////                    kd1 += 0.01;
////                }
////                if (gamepad1.dpad_down){
////                    kd1 += -0.01;
////                }
////                if(gamepad1.dpad_right){
////                    ki1+=0.01;
////                }
////                if(gamepad1.dpad_left){
////                    ki1+=-0.01;
////                }
////            } else {
////                if (gamepad1.triangle) {
////                    kp3 += 0.01;
////                }
////                if (gamepad1.cross) {
////                    kp3 += -0.01;
////                }
////                if (gamepad1.dpad_up){
////                    kd3 += 0.01;
////                }
////                if (gamepad1.dpad_down){
////                    kd3 += -0.01;
////                }
////                if(gamepad1.dpad_right){
////                    ki3+=0.01;
////                }
////                if(gamepad1.dpad_left){
////                    ki3+=-0.01;
////                }
////            }
////
////
////            telemetry.addData("kp1",kp1);
////            telemetry.addData("ki1",ki1);
////            telemetry.addData("kd1",kd1);
////            telemetry.addData("kp3",kp3);
////            telemetry.addData("ki3",ki3);
////            telemetry.addData("kd3",kd3);
////
////        }
//
//
//        kp2 = kp1;
//        ki2 = ki1;
//        kd2 = kd1;
//        while (opModeIsActive()) {
//            Pose2d poseEstimate = drive.getPoseEstimate();
//
//            // Initial movement
//            if (moveStep == 1) {
//                robot.setIntakePosition();
//
//                xTarget = -37;
//                yTarget = 36;
//                headingTarget = 270;
//
//                if ((Math.abs(poseEstimate.getX() - xTarget) > 1) || (Math.abs(poseEstimate.getY() - yTarget) > 1)) {timer1.reset();}
//                if (timer1.milliseconds() > 300) {
//                    moveStep = 2;
//                    timer1.reset();
//                }
//            }
//
//            // Rotate (purple pixel)
//            if (moveStep == 2) {
//                if (randomizationResult == 1) {
//                    headingTarget = 0;
//                } else if (randomizationResult == 2) {
//                    robot.setIntakePosition();
//                    xTarget = -40;
//                    headingTarget = 90;
//                } else if (randomizationResult == 3) {
//                    headingTarget = 90;
//                }
//
//                if ((Math.abs(poseEstimate.getX() - xTarget) > 1) || (Math.abs(poseEstimate.getY() - yTarget) > 1)) {timer1.reset();}
//                if (timer1.milliseconds() > 300) {
//                    moveStep = 3;
//                    timer1.reset();
//                }
//            }
//
//            // Path to pixel position (purple pixel
//            if (moveStep == 3) {
//                if (randomizationResult == 1) {
//                    xTarget = -36;
//                    yTarget = 32;
//                    headingTarget = 0;
//                }
//                 else if (randomizationResult == 2) {
//
//                    xTarget = -37;
//                    yTarget = 13;
//                } else if(randomizationResult == 3) {
//
//                    xTarget = -47;
//                    yTarget = 15;
//                }
//
//
//
//                if ((Math.abs(poseEstimate.getX() - xTarget) > 1) || (Math.abs(poseEstimate.getY() - yTarget) > 1)) {timer1.reset();}
//                if (timer1.milliseconds() > 300) {
//                    moveStep = 4;
//                    timer1.reset();
//                }
//            }
//
//            // Score purple pixel
//            if (moveStep == 4) {
//                if ((Math.abs(poseEstimate.getX() - xTarget) > 1) || (Math.abs(poseEstimate.getY() - yTarget) > 1)) {timer1.reset();}
//                if (timer1.milliseconds() > 300) {
//                    moveStep = 5;
//                    robot.setIntakePosition();  // score
//
//                    timer1.reset();
//                }
//            }
//
//            // Also score purple pixel
//            if (moveStep == 5){
//                if (timer1.milliseconds() > 1000) {
//                    robot.openLowerClaw();
//                    telemetry.addLine("openClaw");
//                }
//
//                if (timer1.milliseconds() > 1500) {
//                    robot.setTransferPosition();
//                    moveStep = 6;
//                }
//            }
//
//            // Back up (yellow pixel)
//            if (moveStep == 6) {
//
//                xTarget = -47;
//                yTarget = 10;
//
//
//                headingTarget=90;
//
//
//                if ((Math.abs(poseEstimate.getX() - xTarget) > 3) || (Math.abs(poseEstimate.getY() - yTarget) > 3)) {timer1.reset();}
//                if (timer1.milliseconds() > 100) {
//                    moveStep = 7;
//                    robot.setScoringPosition();
//                    timer1.reset();
//                }
//            }
//
//
//            if (moveStep == 7) {
//                xTarget=47;
//                yTarget=10;
//                headingTarget=180;
//
//                if ((Math.abs(poseEstimate.getX() - xTarget) > 1)|| (Math.abs(poseEstimate.getY() - yTarget) > 1)){
//                    timer1.reset();
//                }
//                if (timer1.milliseconds() > 300) {
//                    moveStep = 8;
//                    //score
//
//                    timer1.reset();
//                }
//
//            }
//            if(moveStep==8){
//                drive.setMotorPowers( 0,0,0,0);
//
//                if ((Math.abs(poseEstimate.getX() - xTarget) > 1)|| (Math.abs(poseEstimate.getY() - yTarget) > 1)){
//                    timer1.reset();
//                }
//                if (timer1.milliseconds() >100) {
//                    moveStep = 9;
//                    robot.setTransferPosition();
//                    robot.closeLowerClaw();
//                    robot.closeUpperClaw();
//                    robot.setSliderPosition(3);
//
//                    //score
//
//                    timer1.reset();
//                }
//            }
//
//            if (moveStep == 9) {
//                xTarget = 52;  // 52 previously, board scoring position
//                if (randomizationResult == 3) {
//                    yTarget = 25.5;
//                }
//                if (randomizationResult == 2) {
//                    yTarget = 30;
//                }
//                if (randomizationResult == 1) {
//                    yTarget = 36;
//                }
//
//                if ((Math.abs(poseEstimate.getX() - xTarget) > 1) || (Math.abs(poseEstimate.getY() - yTarget) > 1) || robot.motorSliderLeft.getCurrentPosition() < 650) {
//                    timer1.reset();
//                }
//
//                if (robot.motorSliderLeft.getCurrentPosition() > 650) {
//                    robot.servoClawPitchLeft.setPosition(0.63);
//                    robot.servoClawPitchRight.setPosition(0.63);
//                    robot.servoArmLeft.setPosition(0.28);
//                    robot.servoArmRight.setPosition(0.28);
//                }
//                if (robot.a1.getVoltage() < servoTarget) {
//                    moveStep = 10;
//                    robot.openUpperClaw();
//                    robot.openLowerClaw();
//
//                    timer1.reset();
//                }
//            }
//
//            if (moveStep == 10) {
//                    if (timer1.milliseconds() > 300) {
//                        robot.setSliderPosition(1);
//                        robot.closeLowerClaw();
//                        robot.closeUpperClaw();
//                    }
//
//                    if (timer1.milliseconds() > 800) {robot.setTransferPosition();}
//                    if (timer1.milliseconds() > 1200) {
//                       moveStep=11;
//                       timer1.reset();
//                    }
//            }
//
//            if (moveStep == 11) {
//                robot.setSliderPosition(0);
//                drive.setMotorPowers( 0,0,0,0);
//            }
//
////            if (moveStep == 12) {
////                yTarget = 20;
////            }
//
//
//
//
//
////            if(robot.motorSliderLeft.getCurrentPosition()<800){
////                robot.setTransferPosition();
////            }
//
//
//
//            botHeading = poseEstimate.getHeading();
//            error1 = (xTarget - poseEstimate.getX());
//            left_y = ((error1) * kp1 + integral1*ki1 + ((error1 - lastError1) * kd1));
//            error2 = (poseEstimate.getY() - yTarget);
//            left_x = ((error2) * kp2 + integral1*ki2 + ((error2 - lastError2) * kd2));
//            error3 = (Math.toRadians(headingTarget) - botHeading);
//            if (error3 > Math.PI) {
//                error3 -= 2 * Math.PI;
//            }
//            if (error3 < -Math.PI) {
//                error3 += 2 * Math.PI;
//            }
//
//
//            rot_x = -((error3) * kp3 + integral3*ki3 +((error3 - lastError3) * kd3));
//            if (Math.abs(error3) < Math.toRadians(2)) {
//                rot_x = 0;
//            }
//            integral1 += error1;
//            integral2 += error2;
//            integral3 +=error3;
//            lastError1 = error1;
//            lastError2 = error2;
//            lastError3 = error3;
//            //rot_x = (headingTarget - poseEstimate.getHeading()) * -kp3;
//            lx = left_x * Math.cos(-botHeading) - left_y * Math.sin(-botHeading);
//            ly = left_x * Math.sin(-botHeading) + left_y * Math.cos(-botHeading);
//            denominator = Math.max(Math.abs(left_x) + Math.abs(left_y) + Math.abs(rot_x), 1);
//            robot.motorFL.setPower((lx + ly + rot_x) / denominator);
//            robot.motorBL.setPower((-lx + ly + rot_x) / denominator);
//            robot.motorFR.setPower((ly - lx - rot_x) / denominator);
//            robot.motorBR.setPower((lx + ly - rot_x) / denominator);
//            if (moveStep == 11) {
//                robot.motorBR.setPower(0);
//                robot.motorFR.setPower(0);
//                robot.motorBL.setPower(0);
//                robot.motorFL.setPower(0);
//                moveStep = 12;
//            }
//            drive.update();
//
//            telemetry.addData("ly", ly);
//            telemetry.addData("error3", error3);
//            telemetry.addData("x", poseEstimate.getX());
//            telemetry.addData("y", poseEstimate.getY());
//            telemetry.addData("heading", Math.toDegrees(poseEstimate.getHeading()));
//            telemetry.addData("result",randomizationResult);
//            telemetry.addData("Move",moveStep);
//            telemetry.addData("xError",Math.abs(poseEstimate.getY() - xTarget));
//            telemetry.addData("xError",Math.abs(poseEstimate.getY() - xTarget));
//            telemetry.addData("slide",robot.motorSliderLeft.getCurrentPosition());
//            telemetry.addData("stage", moveStep);
//            telemetry.addData("auton30", auton30.seconds());
//
//            telemetry.update();
//        }
//    }
//
//
//    class TeamPropPipeline extends OpenCvPipeline {
//        Mat YCbCr = new Mat();
//        Mat leftCrop, middleCrop, rightCrop;
//        double leftAverageFinal, middleAverageFinal, rightAverageFinal;
//        Mat output = new Mat();
//        Scalar rectColour = new Scalar(255.0, 0.0, 0.0);
//
//        public Mat processFrame(Mat input) {
//            Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);
//            telemetry.addLine("Pipeline running.");
//
//            // TODO: tune values when new car
//            Rect leftRect = new Rect(30, 100, 100, 79);
//            Rect middleRect = new Rect(300, 100, 100, 79);
//            Rect rightRect = new Rect(539, 100, 100, 79);
//
//            input.copyTo(output);
//            Imgproc.rectangle(output, leftRect, rectColour, 2);
//            Imgproc.rectangle(output, middleRect, rectColour, 2);
//            Imgproc.rectangle(output, rightRect, rectColour, 2);
//
//            leftCrop = YCbCr.submat(leftRect);
//            middleCrop = YCbCr.submat(middleRect);
//            rightCrop = YCbCr.submat(rightRect);
//
//            Core.extractChannel(leftCrop, leftCrop, 2);  // Channel 2 = red
//            Core.extractChannel(middleCrop, middleCrop, 2);
//            Core.extractChannel(rightCrop, rightCrop, 2);
//
//            Scalar leftAverage = Core.mean(leftCrop);
//            Scalar middleAverage = Core.mean(middleCrop);
//            Scalar rightAverage = Core.mean(rightCrop);
//
//            leftAverageFinal = Math.abs(leftAverage.val[0] - 145);
//            middleAverageFinal = Math.abs(middleAverage.val[0] - 145);
//            rightAverageFinal = Math.abs(rightAverage.val[0] - 145);
//
//            if ((leftAverageFinal < middleAverageFinal) && (leftAverageFinal < rightAverageFinal)) {
//                telemetry.addLine("left");
//                randomizationResult = 1;
//            } else if ((middleAverageFinal < leftAverageFinal) &&  (middleAverageFinal < rightAverageFinal)) {
//                telemetry.addLine("middle");
//                randomizationResult = 2;
//            } else {
//                telemetry.addLine("right");
//                randomizationResult = 3;
//            }
//            telemetry.addData("left", leftAverageFinal);
//            telemetry.addData("middle", middleAverageFinal);
//            telemetry.addData("right", rightAverageFinal);
//            telemetry.addData("result",randomizationResult);
//
//            telemetry.update();
//
//            return output;
//        }
//    }
//}