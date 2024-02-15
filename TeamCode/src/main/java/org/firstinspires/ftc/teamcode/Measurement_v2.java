package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="v2 Measurement")
public class Measurement_v2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Hardware_v2 robot = new Hardware_v2();
        robot.init(hardwareMap, telemetry);

        robot.motorSliderLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorSliderRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("FL", robot.motorFL.getPower());
            telemetry.addData("FR", robot.motorFR.getPower());
            telemetry.addData("BL", robot.motorBL.getPower());
            telemetry.addData("BR", robot.motorBR.getPower());
            telemetry.addData("Slider (Left)", robot.motorSliderLeft.getCurrentPosition());
            telemetry.addData("Slider (Right)", robot.motorSliderRight.getCurrentPosition());
            telemetry.update();

            if (gamepad1.triangle) {
                robot.motorSliderLeft.setPower(1);
            } else {
                robot.motorSliderLeft.setPower(0);
            }

            if (gamepad1.cross) {
                robot.motorSliderRight.setPower(1);
            } else {
                robot.motorSliderRight.setPower(1);
            }
        }
    }
}
