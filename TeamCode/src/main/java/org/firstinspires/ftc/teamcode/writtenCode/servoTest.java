package org.firstinspires.ftc.teamcode.writtenCode;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Configurable
@TeleOp(name = "Servo Test", group = "Tuning")
public class servoTest extends LinearOpMode {

    // TODO: set the config name and position for the servo you want to test
    public static String servoName = "servo1";
    public static double pos = 0.5;

    private Servo servo;

    @Override
    public void runOpMode() {
        servo = hardwareMap.get(Servo.class, servoName);
        servo.setPosition(pos);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            servo.setPosition(pos);
            telemetry.addData("servo", servoName);
            telemetry.addData("pos", pos);
            telemetry.update();
        }
    }
}
