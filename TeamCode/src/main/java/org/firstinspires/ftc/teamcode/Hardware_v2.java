package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This class represents the robot object.
 */
public class Hardware_v2 {
    DcMotor motorFL, motorFR;
    DcMotor motorBL, motorBR;
    DcMotorEx motorSliderLeft, motorSliderRight;
    Servo servoClawUpper, servoClawLower;
    Servo servoClawPitchLeft, servoClawPitchRight;
    Servo servoArmLeft, servoArmRight;
    CRServo servoDrone;
    IMU imu;
    Telemetry telemetry;
//    AnalogInput a0;
//    AnalogInput a1;

    boolean clawUpperOpen;
    boolean clawLowerOpen;
    boolean isInScoringPosition = false;

    final static double OFFSET_SERVO_ARM_LEFT = 0;
    final static double OFFSET_SERVO_ARM_RIGHT = 0;
    final static double OFFSET_SERVO_CLAW_PITCH_LEFT = 0;
    final static double OFFSET_SERVO_CLAW_PITCH_RIGHT = 0;

    final static double ARM_INTAKE = 0.85;
    final static double ARM_LIFTED = 1;
    final static double ARM_SCORING = 0.3;
    final static double CLAW_PITCH_INTAKE = 0.935;
    final static double CLAW_PITCH_LIFTED = 0.7;
    final static double CLAW_PITCH_SCORING = 0.55;

    /**
     * Init method. Call upon the initialisation of an OpMode. Maps hardware to its variables. Call <code>reset()</code> afterwards.
     * @param hardwareMap the HardwareMap object used to map hardware.
     */
    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        motorFL = hardwareMap.get(DcMotor.class, "motorFL");
        motorFR = hardwareMap.get(DcMotor.class, "motorFR");
        motorBL = hardwareMap.get(DcMotor.class, "motorBL");
        motorBR = hardwareMap.get(DcMotor.class, "motorBR");
        motorSliderLeft = hardwareMap.get(DcMotorEx.class, "motorSliderLeft");
        motorSliderRight = hardwareMap.get(DcMotorEx.class, "motorSliderRight");
        servoClawUpper = hardwareMap.get(Servo.class, "servoClawUpper");
        servoClawLower = hardwareMap.get(Servo.class, "servoClawLower");
        servoClawPitchLeft = hardwareMap.get(Servo.class, "servoClawPitchLeft");
        servoClawPitchRight = hardwareMap.get(Servo.class, "servoClawPitchRight");
        servoArmLeft = hardwareMap.get(Servo.class, "servoArmLeft");
        servoArmRight = hardwareMap.get(Servo.class, "servoArmRight");
        servoDrone = hardwareMap.get(CRServo.class, "servoDrone");
        imu = hardwareMap.get(IMU.class, "imu");
//        a0 = hardwareMap.get(AnalogInput.class, "a0");
//        a1 = hardwareMap.get(AnalogInput.class, "a1");

        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT, RevHubOrientationOnRobot.UsbFacingDirection.UP)));
        this.telemetry = telemetry;
    }

    public void reset() {
        motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorFL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFR.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.FORWARD);
        motorSliderLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorSliderRight.setDirection(DcMotorSimple.Direction.REVERSE);
        servoClawPitchLeft.setDirection(Servo.Direction.FORWARD);
        servoClawPitchRight.setDirection(Servo.Direction.REVERSE);
        servoClawUpper.setDirection(Servo.Direction.FORWARD);
        servoClawLower.setDirection(Servo.Direction.FORWARD);
        servoArmLeft.setDirection(Servo.Direction.FORWARD);
        servoArmRight.setDirection(Servo.Direction.REVERSE);
        servoDrone.setDirection(DcMotorSimple.Direction.REVERSE);

        motorSliderLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorSliderRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    // Regular methods.

    /** Opens upper claw and sets <code>clawUpperOpen</code> to <code>true</code>.*/
    public void openUpperClaw() {servoClawUpper.setPosition(0); clawUpperOpen = true;}

    /** Opens lower claw and sets <code>clawLowerOpen</code> to <code>true</code>.*/
    public void openLowerClaw() {servoClawLower.setPosition(1); clawLowerOpen = true;}

    /** Closes upper claw and sets <code>clawUpperOpen</code> to <code>false</code>.*/
    public void closeUpperClaw() {servoClawUpper.setPosition(0.3); clawUpperOpen = false;}

    /** Closes lower claw and sets <code>clawLowerOpen</code> to <code>false</code>.*/
    public void closeLowerClaw() {servoClawLower.setPosition(0.72); clawLowerOpen = false;}

    /**
     * Sets slider positions.
     * @param position The target position, typically the backdrop's set line height. Integer value from 0-2.
     * @param power The speed of the motors to run on. Double value from 0-1.
     */
    public void setSliderPosition(int position, double power) {
        switch (position) {
            case 0:  // Fully lowered.
                motorSliderLeft.setTargetPosition(0);
                motorSliderRight.setTargetPosition(0);
                break;
            case 1:  // Backdrop scoring position.
                motorSliderLeft.setTargetPosition(1200);
                motorSliderRight.setTargetPosition(1200);
                break;
            case 2:  // Rigging hold position.
                motorSliderLeft.setTargetPosition(310);
                motorSliderRight.setTargetPosition(310);
            case 3:
                motorSliderLeft.setTargetPosition(745);
                motorSliderRight.setTargetPosition(745);
        }

        motorSliderLeft.setPower(power);
        motorSliderRight.setPower(power);
        motorSliderLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorSliderRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Sets slider positions. Speed is set to 1. Pass a speed parameter for speed control.
     * @param position The target position, typically the backdrop's set line height. Integer value from 0-2.
     */
    public void setSliderPosition(int position) {setSliderPosition(position, 0.7);}

    /**
     * Sets the robot's arm and claw to its intake position.
     */
    public void setIntakePosition() {
        servoArmLeft.setPosition(OFFSET_SERVO_ARM_LEFT + ARM_INTAKE);
        servoArmRight.setPosition(OFFSET_SERVO_ARM_RIGHT + ARM_INTAKE);
        servoClawPitchLeft.setPosition(OFFSET_SERVO_CLAW_PITCH_LEFT + CLAW_PITCH_INTAKE);
        servoClawPitchRight.setPosition(OFFSET_SERVO_CLAW_PITCH_RIGHT + CLAW_PITCH_INTAKE);
        isInScoringPosition = false;
    }

    /**
     * Sets the robot's arm and claw to a lifted position to prevent field damage.
     */
    public void setTransferPosition() {
        servoArmLeft.setPosition(OFFSET_SERVO_ARM_LEFT + ARM_LIFTED);
        servoArmRight.setPosition(OFFSET_SERVO_ARM_RIGHT + ARM_LIFTED);
        servoClawPitchLeft.setPosition(OFFSET_SERVO_CLAW_PITCH_LEFT + CLAW_PITCH_LIFTED);
        servoClawPitchRight.setPosition(OFFSET_SERVO_CLAW_PITCH_RIGHT + CLAW_PITCH_LIFTED);
        isInScoringPosition = false;
    }

    /**
     * Sets the robot's arm and claw to its scoring position.
     */
    public void setScoringPosition() {
        servoClawPitchLeft.setPosition(OFFSET_SERVO_CLAW_PITCH_LEFT + CLAW_PITCH_SCORING);
        servoClawPitchRight.setPosition(OFFSET_SERVO_CLAW_PITCH_RIGHT + CLAW_PITCH_SCORING);
        servoArmLeft.setPosition(OFFSET_SERVO_ARM_LEFT + ARM_SCORING);
        servoArmRight.setPosition(OFFSET_SERVO_ARM_RIGHT + ARM_SCORING);
        isInScoringPosition = true;
    }
}
