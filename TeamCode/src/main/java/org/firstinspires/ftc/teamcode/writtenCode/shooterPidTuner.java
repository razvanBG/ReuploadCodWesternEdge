package org.firstinspires.ftc.teamcode.writtenCode;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.writtenCode.controllers.PIDFController;

@Configurable
@TeleOp(name = "Shooter PID Tuner", group = "Tuning")
public class shooterPidTuner extends LinearOpMode {

    private PIDFController controller;
    private DcMotorEx motor;
    private DcMotorEx motor1;

    public static double targetVelocity, velocity;

    // TODO: tune for the new robot
    public static double P = 0, I = 0, kV = 0, kS = 0, kD = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotorEx.class, "flywheelMotorR");
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor1 = hardwareMap.get(DcMotorEx.class, "flywheelMotorL");
        motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        controller = new PIDFController(P, I, kD, 0.0);

        waitForStart();

        while (opModeIsActive()) {
            if (isStopRequested()) return;

            controller.setPIDF(P, I, kD, kV * targetVelocity + kS);
            velocity = motor1.getVelocity();
            motor.setPower(controller.calculate(targetVelocity - velocity));
            motor1.setPower(controller.calculate(targetVelocity - velocity));

            telemetry.addData("Target Velocity", targetVelocity);
            telemetry.addData("Current Velocity", velocity);
            telemetry.update();
        }
    }
}
