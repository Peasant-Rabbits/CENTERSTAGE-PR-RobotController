package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name="AutonTest2")
public class AutonTest2 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Hardware_v2 robot = new Hardware_v2();
        robot.init(hardwareMap, telemetry);
        robot.reset();

        // Variables
        double xTarget=0;
        double yTarget=0;
        double headingTarget=0;
        double botHeading;
        double left_x;
        double left_y;
        double rot_x;
        double lx  ;
        double ly;
        double denominator;
        double error1;
        double kp1 = 0.05;
        double error2;
        double kp2 = 0.05;
        double error3;
        double kp3 = 0.3;
        double lastError3=0;
        double kd3=0;

        int aprilTagResult = 0;
        int moveStep = 1;
        ElapsedTime timer1 = new ElapsedTime();
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.setPoseEstimate(new Pose2d(-36, -62, Math.toRadians(270)));
        waitForStart();


        while (opModeIsActive()) {

            if (gamepad1.dpad_up) kp3 += 0.01;
            if (gamepad1.dpad_down) kp3 -= 0.01;
            Pose2d poseEstimate = drive.getPoseEstimate();

            if (moveStep == 1) {
                xTarget = -36;
                yTarget = -36;
                headingTarget = 270;
                if (Math.abs(poseEstimate.getY() + 36) > 2) {
                    timer1.reset();
                }
                if (timer1.milliseconds() > 300) {
                    moveStep = 2;
                }
            }
            if (moveStep == 2) {
                xTarget = -36;
                yTarget = -36;
                headingTarget = 0;

            }


            botHeading = poseEstimate.getHeading();


            left_y = (xTarget - poseEstimate.getX()) * kp1;
            left_x = (poseEstimate.getY() - yTarget) * kp2;
            error3 = (Math.toRadians(headingTarget) - botHeading);
            if (error3 > Math.PI) {
                error3 -= 2 * Math.PI;
            }
            if (error3 < -Math.PI) {
                error3 += 2 * Math.PI;
            }


            rot_x = -((error3) * kp3 + ((error3 - lastError3) * kd3));
            if (Math.abs(error3) < Math.toRadians(2)) {
                rot_x = 0;
            }
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

            telemetry.update();
        }
    }
}