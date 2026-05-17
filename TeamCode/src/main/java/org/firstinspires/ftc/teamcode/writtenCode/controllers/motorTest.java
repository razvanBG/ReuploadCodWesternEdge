package org.firstinspires.ftc.teamcode.writtenCode.controllers;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Configurable
@TeleOp(name = "Motor Test", group = "Tuning")
public class motorTest extends LinearOpMode {

    // TODO: set the config name and power for the motor you want to test
    public static String motorName = "motor";
    public static double power = 0;

    private DcMotorEx motor;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class, motorName);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            motor.setPower(power);
            telemetry.addData("motor", motorName);
            telemetry.addData("power", power);
            telemetry.update();
        }
    }
}
