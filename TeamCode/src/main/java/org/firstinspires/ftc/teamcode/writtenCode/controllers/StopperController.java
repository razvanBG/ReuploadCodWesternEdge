package org.firstinspires.ftc.teamcode.writtenCode.controllers;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.writtenCode.RobotMap;

@Configurable
public class StopperController {
    public enum StopperStatus {
        SHOOT,
        NOSHOOT;
    }
    public StopperStatus currentStatus = StopperStatus.NOSHOOT;
    public StopperStatus previousStatus = null;

    // TODO: set positions for the new robot
    public static double stopperOffPosition = 0;
    public static double stopperOnPosition = 0;

    public Servo stopper = null;

    public StopperController(RobotMap robot) { this.stopper = robot.stopper; }

    public void update() {
        if (currentStatus != previousStatus) {
            previousStatus = currentStatus;
            switch (currentStatus) {
                case NOSHOOT:
                    this.stopper.setPosition(stopperOnPosition);
                    break;
                case SHOOT:
                    this.stopper.setPosition(stopperOffPosition);
                    break;
            }
        }
    }
}
