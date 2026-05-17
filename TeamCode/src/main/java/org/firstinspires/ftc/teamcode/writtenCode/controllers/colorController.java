package org.firstinspires.ftc.teamcode.writtenCode.controllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "colorsensor", group = "Tuning")
public class colorController extends OpMode {

    private ColorSensor sensor1, sensor2, sensor3;
    private DistanceSensor dist1, dist2, dist3;

    int r1, g1, b1, a1;
    int r2, g2, b2, a2;
    int r3, g3, b3, a3;
    double d1, d2, d3;

    @Override
    public void init() {
        sensor1 = hardwareMap.get(ColorSensor.class, "sensor1");
        sensor2 = hardwareMap.get(ColorSensor.class, "sensor2");
        sensor3 = hardwareMap.get(ColorSensor.class, "sensor3");
        sensor1.enableLed(true);
        sensor2.enableLed(true);
        sensor3.enableLed(true);
        dist1 = hardwareMap.get(DistanceSensor.class, "sensor1");
        dist2 = hardwareMap.get(DistanceSensor.class, "sensor2");
        dist3 = hardwareMap.get(DistanceSensor.class, "sensor3");
    }

    @Override
    public void loop() {
        r1 = sensor1.red();   g1 = sensor1.green(); b1 = sensor1.blue(); a1 = sensor1.alpha();
        r2 = sensor2.red();   g2 = sensor2.green(); b2 = sensor2.blue(); a2 = sensor2.alpha();
        r3 = sensor3.red();   g3 = sensor3.green(); b3 = sensor3.blue(); a3 = sensor3.alpha();

        d1 = dist1.getDistance(DistanceUnit.CM);
        d2 = dist2.getDistance(DistanceUnit.CM);
        d3 = dist3.getDistance(DistanceUnit.CM);

        telemetry.addData("Sensor1 R G B Alpha Dist", r1 + " " + g1 + " " + b1 + " " + a1 + " " + d1);
        telemetry.addData("Sensor2 R G B Alpha Dist", r2 + " " + g2 + " " + b2 + " " + a2 + " " + d2);
        telemetry.addData("Sensor3 R G B Alpha Dist", r3 + " " + g3 + " " + b3 + " " + a3 + " " + d3);
        telemetry.update();
    }
}
