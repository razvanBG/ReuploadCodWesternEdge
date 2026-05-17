package org.firstinspires.ftc.teamcode.writtenCode.controllers;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.writtenCode.RobotMap;

@Configurable
public class ForbarController {
    public enum ForbarStatus {
        COLLECT,
        UP,
        DEFENSE;
    }
    public ForbarStatus currentStatus = ForbarStatus.UP;
    public ForbarStatus previousStatus = null;

    // TODO: set positions for the new robot
    public static double forbarCollectPosition = 0;
    public static double forbarUpPosition = 0;
    public static double forbarDefensePosition = 0;

    public Servo forbar1 = null;
    public Servo forbar2 = null;

    public ForbarController(RobotMap robot) {
        this.forbar1 = robot.forbar1;
        this.forbar2 = robot.forbar2;
    }

    public void update() {
        if (currentStatus != previousStatus) {
            previousStatus = currentStatus;
            switch (currentStatus) {
                case UP:
                    this.forbar1.setPosition(forbarUpPosition);
                    this.forbar2.setPosition(forbarUpPosition);
                    break;
                case COLLECT:
                    this.forbar1.setPosition(forbarCollectPosition);
                    this.forbar2.setPosition(forbarCollectPosition);
                    break;
                case DEFENSE:
                    this.forbar1.setPosition(forbarDefensePosition);
                    this.forbar2.setPosition(forbarDefensePosition);
                    break;
            }
        }
    }
}
