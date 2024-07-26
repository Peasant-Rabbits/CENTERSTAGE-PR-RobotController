package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.MarkerCallback;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
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

@Autonomous(name="2+0 Red Far, Stage Door")
public class RRAutonRedFarStage extends LinearOpMode {
    Objective objective = Objective.INITIALISE;
    OpenCvWebcam webcam;
    int randomizationResult = 2;
    boolean pReady, yReady, scoredPurple;
    boolean scoreRight = true;
    boolean parkRight;

    TrajectorySequence purple, park;
    Trajectory yellow;
    Trajectory yellowLL, yellowML, yellowRL;
    Trajectory yellowLR, yellowMR, yellowRR;

    @Override
    public void runOpMode() throws InterruptedException {
        Project1Hardware robot = new Project1Hardware();
        robot.init(hardwareMap, telemetry);
        robot.reset();
        robot.retractSlider();
        robot.bothClawClose();

        ElapsedTime timer1 = new ElapsedTime();
        ElapsedTime timer2 = new ElapsedTime();
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(-40.33, -62.80, Math.toRadians(90.00));
        Pose2d nowPose;

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

        drive.setPoseEstimate(startPose);

        TrajectorySequence purpleL = drive.trajectorySequenceBuilder(startPose)
                .splineToSplineHeading(new Pose2d(-30.22, -10.33, Math.toRadians(55.00)), Math.toRadians(65.00))
                .build();
        TrajectorySequence purpleM = drive.trajectorySequenceBuilder(startPose)
                .splineToSplineHeading(new Pose2d(-29.84, -8.68, Math.toRadians(62.47)), Math.toRadians(65.00))
                .build();
        TrajectorySequence purpleR = drive.trajectorySequenceBuilder(startPose)
                .splineToSplineHeading(new Pose2d(-38.39, -15.49, Math.toRadians(125.86)), Math.toRadians(80.00))
                .build();

        MarkerCallback transitionCallback = () -> {robot.setClawPAngle(180); robot.setArm(147.5);};
        TrajectoryVelocityConstraint param1 = SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH);
        TrajectoryAccelerationConstraint param2 = SampleMecanumDrive.getAccelerationConstraint(35);

        yellowLL = drive.trajectoryBuilder(purpleL.end())
                .lineToSplineHeading(new Pose2d(30.48, -14.44, Math.toRadians(0.00)))
                .addSpatialMarker(new Vector2d(30.48, -14.44), transitionCallback)
                .splineToConstantHeading(new Vector2d(35.20, -27.61), Math.toRadians(-45.00), param1, param2)
                .build();
        yellowML = drive.trajectoryBuilder(purpleM.end())
                .lineToSplineHeading(new Pose2d(30.48, -14.44, Math.toRadians(0.00)))
                .addSpatialMarker(new Vector2d(30.48, -14.44), transitionCallback)
                .splineToConstantHeading(new Vector2d(35.20, -33.51), Math.toRadians(-50.00), param1, param2)
                .build();
        yellowRL = drive.trajectoryBuilder(purpleR.end())
                .splineToSplineHeading(new Pose2d(-20.91, -14.44, Math.toRadians(0.00)), Math.toRadians(0.00))
                .splineTo(new Vector2d(30.48, -14.44), Math.toRadians(0.00))
                .addSpatialMarker(new Vector2d(30.48, -14.44), transitionCallback)
                .splineToConstantHeading(new Vector2d(35.20, -39.01), Math.toRadians(-60.00), param1, param2)
                .build();

        yellowLR = drive.trajectoryBuilder(purpleL.end())
                .lineToSplineHeading(new Pose2d(30.48, -12.84, Math.toRadians(0.00)))
                .addSpatialMarker(new Vector2d(30.48, -14.44), transitionCallback)
                .splineToConstantHeading(new Vector2d(35.20, -30.31), Math.toRadians(-45.00), param1, param2)
                .build();
        yellowMR = drive.trajectoryBuilder(purpleM.end())
                .lineToSplineHeading(new Pose2d(30.48, -14.44, Math.toRadians(0.00)))
                .addSpatialMarker(new Vector2d(30.48, -14.44), transitionCallback)
                .splineToConstantHeading(new Vector2d(35.20, -36.51), Math.toRadians(-50.00), param1, param2)
                .build();
        yellowRR = drive.trajectoryBuilder(purpleR.end())
                .splineToSplineHeading(new Pose2d(-20.91, -14.44, Math.toRadians(0.00)), Math.toRadians(0.00))
                .splineTo(new Vector2d(30.48, -14.44), Math.toRadians(0.00))
                .addSpatialMarker(new Vector2d(30.48, -14.44), transitionCallback)
                .splineToConstantHeading(new Vector2d(35.20, -41.61), Math.toRadians(-60.00), param1, param2)
                .build();

        waitForStart();
        switch (randomizationResult) {
            case 1:
                if (scoreRight) yellow = yellowLR; else yellow = yellowLL;
                purple = purpleL;
                break;
            default:
            case 2:
                if (scoreRight) yellow = yellowMR; else yellow = yellowML;
                purple = purpleM;
                break;
            case 3:
                if (scoreRight) yellow = yellowRR; else yellow = yellowRL;
                purple = purpleR;
                break;
        }

        webcam.stopRecordingPipeline();
        webcam.stopStreaming();
        timer1.reset();

        while (opModeIsActive()) {
            nowPose = drive.getPoseEstimate();

            if (objective == Objective.INITIALISE) {
                objective = Objective.PATH_TO_PURPLE;
            }

            if (objective == Objective.PATH_TO_PURPLE) {
                if (!pReady) {drive.followTrajectorySequence(purple); pReady = true; timer1.reset();}
                robot.clawPIntake();
                switch (randomizationResult) {
                    case 1: robot.setSlider(650); break;
                    default: case 2: robot.setSlider(350); break;
                    case 3: robot.setSlider(550); break;
                }

                if (Math.abs(robot.slider.getCurrentPosition() - robot.slider.getTargetPosition()) < 5 || timer1.milliseconds() > 2060) {
                    objective = Objective.SCORE_PURPLE;
                    timer1.reset();
                }
            }

            if (objective == Objective.SCORE_PURPLE) {
                if (timer1.milliseconds() > 2760 || scoredPurple && timer2.milliseconds() > 300) {
                    robot.bothClawClose();
                    robot.arm.setPower(0);
                    robot.arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                    objective = Objective.PATH_TO_YELLOW;
                } else if (timer1.milliseconds() > 2360 || scoredPurple) {
                    robot.clawPScoring();
                    robot.retractSlider();
                } else if (timer1.milliseconds() > 0) {
                    robot.rightClawOpen();
                    scoredPurple = true;
                    timer2.reset();
                }
            }

            if (objective == Objective.PATH_TO_YELLOW) {
                if (!yReady) {
                    drive.followTrajectory(yellow);
                    yReady = true;
                }

                if ((robot.getArmAngle() > 135) && yReady) {
                    robot.setSlider(430);

                    if (robot.slider.getCurrentPosition() > 427) {
                        timer1.reset();
                        objective = Objective.SCORE_YELLOW;
                    }
                }
            }

            if (objective == Objective.SCORE_YELLOW) {
                if (timer1.milliseconds() > 705) {robot.setArm(0); robot.bothClawClose();}
                else if (timer1.milliseconds() > 450) robot.retractSlider();
                else if (timer1.milliseconds() > 150) robot.leftClawOpen();

                if (robot.getArmAngle() < 5) objective = Objective.PARK;

                if (parkRight) {
                    park = drive.trajectorySequenceBuilder(nowPose)
                            .lineToConstantHeading(new Vector2d(50.97, -62.18))
                            .addTemporalMarker(() -> objective = Objective.END)
                            .build();
                } else {
                    park = drive.trajectorySequenceBuilder(nowPose)
                            .lineToConstantHeading(new Vector2d(50.97, -11.06))
                            .addTemporalMarker(() -> objective = Objective.END)
                            .build();
                }
            }

            if (objective == Objective.PARK) {
                drive.followTrajectorySequence(park);
            }

            if (objective == Objective.END) {
                robot.arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                robot.arm.setPower(0);
            }

            drive.update();
            telemetry.addData("Objective", objective);
            if (parkRight) telemetry.addData("Park", "Right");
            else telemetry.addData("Park", "Left");
            telemetry.addLine();
            telemetry.addData("X", nowPose.getX());
            telemetry.addData("Y", nowPose.getY());
            telemetry.addData("H(D)", Math.toDegrees(nowPose.getHeading()));
            telemetry.addData("H(R)", nowPose.getHeading());
            telemetry.update();
        }
    }

    class TeamPropPipeline extends OpenCvPipeline {
        Mat YCbCr = new Mat();
        Mat leftCrop, middleCrop, rightCrop;
        double avgLFinal, avgMFinal, avgRFinal;
        double leftTarget, middleTarget, rightTarget;
        Mat output = new Mat();
        Scalar rectColour = new Scalar(0, 0.0, 255.0);

        public Mat processFrame(Mat input) {
            Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);
            telemetry.addLine("Pipeline running.");

            Rect leftRect = new Rect(0, 100, 100, 79);
            Rect middleRect = new Rect(280, 100, 100, 79);
            Rect rightRect = new Rect(539, 150, 100, 79);

            input.copyTo(output);
            Imgproc.rectangle(output, leftRect, rectColour, 2);
            Imgproc.rectangle(output, middleRect, rectColour, 2);
            Imgproc.rectangle(output, rightRect, rectColour, 2);

            leftCrop = YCbCr.submat(leftRect);
            middleCrop = YCbCr.submat(middleRect);
            rightCrop = YCbCr.submat(rightRect);

            Core.extractChannel(leftCrop, leftCrop,1);  // Channel 2 = red
            Core.extractChannel(middleCrop, middleCrop, 1);
            Core.extractChannel(rightCrop, rightCrop, 0);

            Scalar leftAverage = Core.mean(leftCrop);
            Scalar middleAverage = Core.mean(middleCrop);
            Scalar rightAverage = Core.mean(rightCrop);

//            leftAverageFinal = Math.abs(leftAverage.val[0] - 105);
//            middleAverageFinal = Math.abs(middleAverage.val[0] - 105);
//            rightAverageFinal = Math.abs(rightAverage.val[0] - 105);

            avgLFinal = Math.abs(leftAverage.val[0] - leftTarget);
            avgMFinal = Math.abs(middleAverage.val[0] - middleTarget);
            avgRFinal = Math.abs(rightAverage.val[0] - rightTarget);

            if ((avgLFinal < avgMFinal) && (avgLFinal < avgRFinal)) {
                telemetry.addLine("LEFT");
                randomizationResult = 1;
            } else if ((avgMFinal < avgLFinal) && (avgMFinal < avgRFinal)) {
                telemetry.addLine("MIDDLE");
                randomizationResult = 2;
            } else {
                telemetry.addLine("RIGHT");
                randomizationResult = 3;
            }

            if (scoreRight) telemetry.addData("Score on", "Right");
            else telemetry.addData("Score on", "Left");
            if (parkRight) telemetry.addData("Park", "Right");
            else telemetry.addData("Park", "Left");
            telemetry.addLine();

            if (gamepad1.dpad_left) leftTarget = leftAverage.val[0];
            if (gamepad1.dpad_up) middleTarget = middleAverage.val[0];
            if (gamepad1.dpad_right) rightTarget = rightAverage.val[0];

            if (gamepad1.square) parkRight = false;
            else if (gamepad1.circle) parkRight = true;
            if (gamepad1.left_bumper) scoreRight = false;
            else if (gamepad1.right_bumper) scoreRight = true;

            telemetry.addData("leftAvg", leftAverage.val[0]);
            telemetry.addData("rightAvg", rightAverage.val[0]);
            telemetry.addData("middleAvg", middleAverage.val[0]);
            telemetry.addLine();
            telemetry.addData("left", avgLFinal);
            telemetry.addData("middle", avgMFinal);
            telemetry.addData("right", avgRFinal);
            telemetry.addData("result", randomizationResult);
            telemetry.addLine();
            telemetry.addData("leftTarget", leftTarget);
            telemetry.addData("middleTarget", middleTarget);
            telemetry.addData("rightTarget", rightTarget);
            telemetry.update();

            return output;
        }
    }

    enum Objective {
        INITIALISE,
        PATH_TO_PURPLE,
        SCORE_PURPLE,
        PATH_TO_YELLOW,
        SCORE_YELLOW,
        PARK,
        END
    }
}
