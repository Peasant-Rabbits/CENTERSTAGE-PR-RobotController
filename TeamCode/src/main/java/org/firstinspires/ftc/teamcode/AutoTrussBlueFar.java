package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

//This program is for movement only)
@TeleOp(name = "Auto Truss Blue Far")

public class AutoTrussBlueFar extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory GoToMarkedLines = drive.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(25,25), Math.toRadians(0))
                .build();

        Trajectory GoToBackdrop = drive.trajectoryBuilder(new Pose2d(25,25,Math.toRadians(0)))
                .splineTo(new Vector2d(25,100), Math.toRadians(90))
                .build();

        Trajectory Parking = drive.trajectoryBuilder(new Pose2d(25,100,Math.toRadians(90)))
                .splineTo(new Vector2d(25,100),Math.toRadians(90))
                .build();

        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectory(GoToMarkedLines);
        sleep(100);
        drive.followTrajectory(GoToBackdrop);
        sleep(100);
        drive.followTrajectory(Parking);
    }
}
