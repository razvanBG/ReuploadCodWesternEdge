package org.firstinspires.ftc.teamcode.writtenCode.controllers;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.writtenCode.RobotMap;

@Configurable
public class TransferController {
    public enum TransferStatus {
        COLLECT,
        REVERSE,
        TRANSFER,
        OFF;
    }
    public TransferStatus currentStatus = TransferStatus.OFF;
    public TransferStatus previousStatus = null;

    // TODO: tune powers for the new robot
    public static double collectpow = 0;
    public static double transferpow = 0;
    public static double reversepow = 0;

    public DcMotorEx transfer = null;

    public TransferController(RobotMap robot) { this.transfer = robot.transfer; }

    public void update() {
        if (currentStatus != previousStatus) {
            previousStatus = currentStatus;
            switch (currentStatus) {
                case COLLECT:
                    this.transfer.setPower(collectpow);
                    break;
                case TRANSFER:
                    this.transfer.setPower(transferpow);
                    break;
                case REVERSE:
                    this.transfer.setPower(reversepow);
                    break;
                case OFF:
                    this.transfer.setPower(0);
                    break;
            }
        }
    }
}
