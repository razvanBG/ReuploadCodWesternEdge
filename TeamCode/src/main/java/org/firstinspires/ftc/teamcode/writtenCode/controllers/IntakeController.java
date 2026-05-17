package org.firstinspires.ftc.teamcode.writtenCode.controllers;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.writtenCode.RobotMap;

@Configurable
public class IntakeController {
    public enum IntakeStatus {
        COLLECT,
        REVERSE,
        TRANSFER,
        OFF;
    }
    public IntakeStatus currentStatus = IntakeStatus.OFF;
    public IntakeStatus previousStatus = null;

    // TODO: tune powers for the new robot
    public static double collectpow = 0;
    public static double transferpow = 0;
    public static double reversepow = 0;

    public DcMotorEx intake = null;

    public IntakeController(RobotMap robot) { this.intake = robot.intake; }

    public void update() {
        if (currentStatus != previousStatus) {
            previousStatus = currentStatus;
            switch (currentStatus) {
                case COLLECT:
                    this.intake.setPower(collectpow);
                    break;
                case TRANSFER:
                    this.intake.setPower(transferpow);
                    break;
                case REVERSE:
                    this.intake.setPower(reversepow);
                    break;
                case OFF:
                    this.intake.setPower(0);
                    break;
            }
        }
    }
}
