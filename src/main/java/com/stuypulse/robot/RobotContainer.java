/************************ PROJECT ZIXI ************************/
/* Copyright (c) 2026 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/
package com.stuypulse.robot;

import static org.wpilib.units.Units.MetersPerSecond;
import static org.wpilib.units.Units.RadiansPerSecond;
import static org.wpilib.units.Units.RotationsPerSecond;

import com.stuypulse.robot.commands.auton.AutonomousRoutines;
import com.stuypulse.robot.constants.GlobalPorts;
import com.stuypulse.robot.subsystems.swerve.CommandSwerveDrivetrain;
import com.stuypulse.robot.subsystems.swerve.TunerConstants;

import org.wpilib.command2.Command;
import org.wpilib.command2.Commands;
import org.wpilib.command2.button.CommandXboxController;
import org.wpilib.command2.sysid.SysIdRoutine.Direction;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.tunable.Selectable;
import org.wpilib.tunable.Tunables;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

public class RobotContainer {
    private final double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
    private double MaxAngularRate =
            RotationsPerSecond.of(0.75)
                    .in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    // Gamepads
    public final CommandXboxController driver =
            new CommandXboxController(GlobalPorts.Gamepad.DRIVER); // model-agnostic controller
    // public final CommandGamepad operator = new CommandGamepad(GlobalPorts.Gamepad.OPERATOR);

    private final SwerveRequest.FieldCentric drive =
            new SwerveRequest.FieldCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    // Subsystems
    private final CommandSwerveDrivetrain swerve = TunerConstants.createDrivetrain();

    // Autons
    private static Selectable<Command> autonChooser = new Selectable<Command>();

    // Robot container

    public RobotContainer() {
        configureLogging();
        configureDefaultCommands();
        configureButtonBindings();
        configureAutons();
    }

    /***************/
    /*** LOGGING ***/
    /***************/

    private void configureLogging() {}

    /****************/
    /*** DEFAULTS ***/
    /****************/

    private void configureDefaultCommands() {}

    /***************/
    /*** BUTTONS ***/
    /***************/

    private void configureButtonBindings() {
        swerve.setDefaultCommand(
                // Drivetrain will execute this command periodically
                swerve.applyRequest(
                        () ->
                                drive.withVelocityX(
                                                -driver.getLeftY()
                                                        * MaxSpeed) // Drive forward with negative Y
                                        // (forward)
                                        .withVelocityY(
                                                -driver.getLeftX()
                                                        * MaxSpeed) // Drive left with negative X
                                        // (left)
                                        .withRotationalRate(
                                                -driver.getRightX()
                                                        * MaxAngularRate) // Drive counterclockwise
                        // with negative X (left)
                        ));

        driver.dpadDown().onTrue(Commands.runOnce(() -> swerve.resetRotation(Rotation2d.ZERO)));
    }

    /**************/
    /*** AUTONS ***/
    /**************/

    public void configureAutons() {
        autonChooser.addDefault("Do Nothing", AutonomousRoutines.doNothingAuton());

        autonChooser.add("Sysid Quasi Forward", swerve.sysIdQuasistatic(Direction.FORWARD));
        autonChooser.add("Sysid Quasi Reverse", swerve.sysIdQuasistatic(Direction.REVERSE));
        autonChooser.add("Sysid Dynamic Forward", swerve.sysIdDynamic(Direction.FORWARD));
        autonChooser.add("Sysid Dynamic Reverse", swerve.sysIdDynamic(Direction.REVERSE));

        Tunables.publish("Auton", autonChooser);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return The command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return autonChooser.getSelected();
    }
}
