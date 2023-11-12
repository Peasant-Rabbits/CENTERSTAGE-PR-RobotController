package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="v2 TeleOp")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Hardware robot = new Hardware();
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");  // Robot finishes initialization. Here we output a debug message.
        telemetry.update();
        robot.reset();  // Reset robot?
        waitForStart();
        telemetry.addData("Status", "Running");
        telemetry.update();

        while (opModeIsActive()) {
            // Fieldcentric
            double bot_heading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            double left_x = gamepad1.left_stick_x;
            double left_y = -gamepad1.left_stick_y;
            double rot_x = gamepad1.right_stick_x * 0.8;  // Make rotation less intense (80%)
            double lx = left_x * Math.cos(-bot_heading) - left_y * Math.sin(-bot_heading);
            double ly = left_x * Math.sin(-bot_heading) + left_y * Math.cos(-bot_heading);
            double denominator = Math.max(Math.abs(left_x) + Math.abs(left_y) + Math.abs(rot_x), 1);

            robot.motorFrontLeft.setPower(-(lx + ly + rot_x) / denominator);
            robot.motorBackLeft.setPower((-lx + ly + rot_x) / denominator);
            robot.motorFrontRight.setPower((ly - lx - rot_x) / denominator);
            robot.motorBackRight.setPower(-(lx + ly - rot_x) / denominator);

            // Reset IMU
            if (gamepad1.share) {
                robot.resetIMU();
            }

            // Slider height
            if (gamepad1.square) {robot.setSliderPosition(0);}
            if (gamepad1.cross) {robot.setSliderPosition(1);}
            if (gamepad1.circle) {robot.setSliderPosition(2);}
            if (gamepad1.triangle) {robot.setSliderPosition(3);}

            // Arm
            if (gamepad1.dpad_up) {robot.setArmPosition(1);}  // Scoring position
            else if (gamepad1.dpad_down) {robot.setArmPosition(0);}  // Intake position

            // Claw
            if (gamepad1.left_bumper) {robot.closeClaw(0);}
            if (gamepad1.right_bumper) {robot.closeClaw(1);}

            telemetry.addData("armLeft", robot.servoArmLeft.getPosition());
            telemetry.addData("armRight", robot.servoArmRight.getPosition());
            telemetry.update();
        }
    }
}
