package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

//This program is for movement only)
@TeleOp(name = "Auto Truss Red Far")

public class AutoTrussRedFar extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory GoToMarkedLines = drive.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(36,-36), Math.toDegrees(0))
                .build();

        Trajectory GoToBackdrop1 = drive.trajectoryBuilder(new Pose2d(36,-36,Math.toDegrees(0)))
                .splineTo(new Vector2d(36,60), Math.toDegrees(-90))
                .build();

        Trajectory Truss1 = drive.trajectoryBuilder(new Pose2d(36,60,Math.toDegrees(-90)))
                .splineTo(new Vector2d(12,12),Math.toDegrees(-90))
                .build();

        Trajectory Stack = drive.trajectoryBuilder(new Pose2d(12,12,Math.toDegrees(-90)))
                .splineTo(new Vector2d(12,-60),Math.toDegrees(-90))
                .build();

        Trajectory Truss2 = drive.trajectoryBuilder(new Pose2d(12,-60,Math.toDegrees(-90)))
                .splineTo(new Vector2d(12,12),Math.toDegrees(-90))
                .build();

        Trajectory GoToBackdrop2 = drive.trajectoryBuilder(new Pose2d(12,12,Math.toDegrees(-90)))
                .splineTo(new Vector2d(36,60), Math.toDegrees(-90))
                .build();

        Trajectory Parking = drive.trajectoryBuilder(new Pose2d(36,60,Math.toDegrees(-90)))
                .splineTo(new Vector2d(12,60),Math.toDegrees(-90))
                .build();




        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectory(GoToMarkedLines);
        drive.followTrajectory(GoToBackdrop1);
        drive.followTrajectory(Truss1);
        drive.followTrajectory(Stack);
        drive.followTrajectory(Truss2);
        drive.followTrajectory(GoToBackdrop2);
        drive.followTrajectory(Parking);


    }
}
