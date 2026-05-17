package org.firstinspires.ftc.teamcode.writtenCode.auto;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.writtenCode.RobotMap;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.ForbarController;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.HoodController;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.IntakeController;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.PIDFController;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.StopperController;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.TransferController;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.TurretController;

@Configurable
@Autonomous(name = "Auto Template", group = "Autonomous")
public class AutoTemplate extends OpMode {

    private Follower follower;
    private Timer pathTimer, opmodeTimer;

    // Shared with TeleOp so it can pick up where autonomous ended.
    public static Pose endPose = new Pose(0, 0, 0);

    private PIDFController controller;
    private DcMotorEx flywheelR, flywheelL;

    // TODO: tune for the new robot
    public static double P = 0, I = 0, kD = 0, kV = 0, kS = 0;
    public static double targetVelocity, velocity;

    private int pathState;

    // TODO: set the real starting pose for the new robot
    private final Pose startPose = new Pose(0, 0, Math.toRadians(0));

    private Paths paths;

    private IntakeController intakeController;
    private TransferController transferController;
    private HoodController hoodController;
    private StopperController stopperController;
    private TurretController turretController;
    private ForbarController forbarController;

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                // TODO: first action / follow first path
                follower.followPath(paths.Path1);
                setPathState(1);
                break;

            case 1:
                if (!follower.isBusy()) {
                    // TODO: next action, then advance the state machine
                    setPathState(-1);
                }
                break;
        }
    }

    private void setPathState(int state) {
        pathState = state;
        pathTimer.resetTimer();
    }

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        flywheelR = hardwareMap.get(DcMotorEx.class, "flywheelMotorR");
        flywheelR.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheelL = hardwareMap.get(DcMotorEx.class, "flywheelMotorL");
        flywheelL.setDirection(DcMotorSimple.Direction.REVERSE);

        controller = new PIDFController(P, I, kD, 0.0);

        RobotMap robot = new RobotMap(hardwareMap);
        intakeController = new IntakeController(robot);
        transferController = new TransferController(robot);
        hoodController = new HoodController(robot);
        stopperController = new StopperController(robot);
        turretController = new TurretController(robot);
        forbarController = new ForbarController(robot);

        intakeController.update();
        transferController.update();
        hoodController.update(0);
        stopperController.update();
        turretController.update(0.5);
        forbarController.update();

        pathTimer = new Timer();
        opmodeTimer = new Timer();
        paths = new Paths(follower);
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        controller.setPIDF(P, I, kD, kV * targetVelocity + kS);
        velocity = flywheelL.getVelocity();
        flywheelR.setPower(controller.calculate(targetVelocity - velocity));
        flywheelL.setPower(controller.calculate(targetVelocity - velocity));

        intakeController.update();
        transferController.update();
        hoodController.update(0);
        stopperController.update();
        turretController.update(0.5);
        forbarController.update();

        endPose = follower.getPose();

        telemetry.addData("Path State", pathState);
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.update();
    }

    @Override
    public void stop() {
        endPose = follower.getPose();
        follower.breakFollowing();
    }

    public static class Paths {
        public PathChain Path1;

        public Paths(Follower follower) {
            // TODO: define real paths for the new robot
            Path1 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(0, 0),
                                    new Pose(10, 0)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(0))
                    .build();
        }
    }
}
