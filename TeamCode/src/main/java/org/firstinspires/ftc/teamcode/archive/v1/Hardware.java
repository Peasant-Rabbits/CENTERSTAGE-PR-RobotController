package org.firstinspires.ftc.teamcode.archive.v1;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DistanceSensor;

public class Hardware {
    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackLeft;
    DcMotor motorBackRight;
    //DcMotor motorHeightLeft;
    //DcMotor motorHeightRight;
    Servo servoArmRight;
    Servo servoArmLeft;
    Servo servoIntakePitchLeft;
    Servo servoIntakePitchRight;
    CRServo servoIntakeLeft;
    CRServo servoIntakeRight;
    Servo servoDroneLauncher;
    //DistanceSensor sensorDistance;
    IMU imu;

    public void init(HardwareMap hardwareMap) {
        this.motorFrontLeft = hardwareMap.get(DcMotor.class, "motorFrontLeft");
        this.motorFrontRight = hardwareMap.get(DcMotor.class, "motorFrontRight");
        this.motorBackLeft = hardwareMap.get(DcMotor.class, "motorBackLeft");
        this.motorBackRight = hardwareMap.get(DcMotor.class, "motorBackRight");
        //this.motorHeightLeft = hardwareMap.get(DcMotor.class, "motorHeightLeft");
        //this.motorHeightRight = hardwareMap.get(DcMotor.class, "motorHeightRight");
        this.servoArmLeft = hardwareMap.get(Servo.class, "servoArmLeft");
        this.servoArmRight = hardwareMap.get(Servo.class, "servoArmRight");
        this.servoIntakeLeft = hardwareMap.get(CRServo.class, "servoIntakeLeft");
        this.servoIntakeRight = hardwareMap.get(CRServo.class, "servoIntakeRight");
        //this.servoDroneLauncher = hardwareMap.get(Servo.class, "servoDroneLauncher");
        //this.sensorDistance = hardwareMap.get(DistanceSensor.class, "sensorDistance");
        this.imu = hardwareMap.get(IMU.class, "imu");
    }

    public void reset() {
        this.motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        // this.motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        this.motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        this.motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        // Brake
        this.motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
    }

    public void resetIMU() {
        this.imu.resetYaw();
    }
}
