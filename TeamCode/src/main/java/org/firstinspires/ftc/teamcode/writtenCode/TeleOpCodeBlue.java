package org.firstinspires.ftc.teamcode.writtenCode;

import static org.firstinspires.ftc.teamcode.writtenCode.auto.AutoTemplate.endPose;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.ForbarController;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.HoodController;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.IntakeController;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.PIDFController;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.StopperController;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.TransferController;
import org.firstinspires.ftc.teamcode.writtenCode.controllers.TurretController;

@Configurable
@TeleOp(name = "TeleOpCode Blue", group = "Linear OpMode")
public class TeleOpCodeBlue extends LinearOpMode {

    public void setMotorRunningMode(DcMotor leftFront, DcMotor leftBack, DcMotor rightFront,
                                    DcMotor rightBack, DcMotor.RunMode runningMode) {
        leftFront.setMode(runningMode);
        rightFront.setMode(runningMode);
        leftBack.setMode(runningMode);
        rightBack.setMode(runningMode);
    }

    public void setMotorZeroPowerBehaviour(DcMotor leftFront, DcMotor leftBack, DcMotor rightFront,
                                           DcMotor rightBack, DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        leftFront.setZeroPowerBehavior(zeroPowerBehavior);
        rightFront.setZeroPowerBehavior(zeroPowerBehavior);
        leftBack.setZeroPowerBehavior(zeroPowerBehavior);
        rightBack.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public void robotCentricDrive(DcMotor leftFront, DcMotor leftBack,
                                  DcMotor rightFront, DcMotor rightBack, double rate) {

        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x * 1.05;
        double rx = gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double leftFrontPower = (y + x + rx) / denominator * rate;
        double leftBackPower = (y - x + rx) / denominator * rate;
        double rightFrontPower = (y - x - rx) / denominator * rate;
        double rightBackPower = (y + x - rx) / denominator * rate;

        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
    }

    private final ElapsedTime GlobalTimer = new ElapsedTime();

    private Follower follower;

    private PIDFController controller;
    private DcMotorEx flywheelR, flywheelL;

    // TODO: tune for the new robot
    public static double P = 0, I = 0, kD = 0, kV = 0, kS = 0;
    public static double targetVelocity, velocity;

    public static Pose startingPose = new Pose(0, 0, 0);

    public double rate = 1;

    @Override
    public void runOpMode() throws InterruptedException {

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(endPose);
        follower.update();

        RobotMap robot = new RobotMap(hardwareMap);

        IntakeController intakeController = new IntakeController(robot);
        TransferController transferController = new TransferController(robot);
        HoodController hoodController = new HoodController(robot);
        StopperController stopperController = new StopperController(robot);
        TurretController turretController = new TurretController(robot);
        ForbarController forbarController = new ForbarController(robot);

        flywheelR = hardwareMap.get(DcMotorEx.class, "flywheelMotorR");
        flywheelR.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheelL = hardwareMap.get(DcMotorEx.class, "flywheelMotorL");
        flywheelL.setDirection(DcMotorSimple.Direction.REVERSE);

        controller = new PIDFController(P, I, kD, 0.0);

        intakeController.update();
        transferController.update();
        hoodController.update(0);
        stopperController.update();
        turretController.update(0.5);
        forbarController.update();

        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBack");

        MotorConfigurationType mct1, mct2, mct3, mct4;
        mct1 = rightBack.getMotorType().clone();
        mct1.setAchieveableMaxRPMFraction(1.0);
        rightBack.setMotorType(mct1);

        mct2 = rightFront.getMotorType().clone();
        mct2.setAchieveableMaxRPMFraction(1.0);
        rightFront.setMotorType(mct2);

        mct3 = leftFront.getMotorType().clone();
        mct3.setAchieveableMaxRPMFraction(1.0);
        leftFront.setMotorType(mct3);

        mct4 = leftBack.getMotorType().clone();
        mct4.setAchieveableMaxRPMFraction(1.0);
        leftBack.setMotorType(mct4);

        setMotorRunningMode(leftFront, leftBack, rightFront, rightBack,
                DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        setMotorZeroPowerBehaviour(leftFront, leftBack, rightFront, rightBack,
                DcMotor.ZeroPowerBehavior.BRAKE);

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        waitForStart();
        GlobalTimer.reset();

        while (opModeIsActive()) {

            if (isStopRequested()) return;

            follower.update();

            robotCentricDrive(leftFront, leftBack, rightFront, rightBack, rate);

            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);
            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);

            // TODO: mechanism / shooter / turret logic for the new robot.
            // The flywheel velocity PIDF skeleton is wired below as a starting point.
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

            telemetry.addData("position", follower.getPose());
            telemetry.addData("targetVel", targetVelocity);
            telemetry.addData("curVel", velocity);
            telemetry.update();
        }
    }
}
