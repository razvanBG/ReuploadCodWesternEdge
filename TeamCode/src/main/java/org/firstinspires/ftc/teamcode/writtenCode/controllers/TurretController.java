package org.firstinspires.ftc.teamcode.writtenCode.controllers;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.writtenCode.RobotMap;

@Configurable
public class TurretController {
    public enum TurretStatus {
        INIT,
        RUNTO;
    }
    public TurretStatus currentStatus = TurretStatus.INIT;
    public TurretStatus previousStatus = null;

    // TODO: set neutral/center position for the new robot
    public static double init_position = 0.5;

    public Servo turret1 = null;
    public Servo turret2 = null;

    public TurretController(RobotMap robot) {
        this.turret1 = robot.turret1;
        this.turret2 = robot.turret2;
    }

    public void update(double runto_target) {
        if (currentStatus != previousStatus || currentStatus == TurretStatus.RUNTO) {
            previousStatus = currentStatus;
            switch (currentStatus) {
                case INIT:
                    turret1.setPosition(init_position);
                    turret2.setPosition(init_position);
                    break;
                case RUNTO:
                    turret1.setPosition(runto_target);
                    turret2.setPosition(runto_target);
                    break;
            }
        }
    }
}
