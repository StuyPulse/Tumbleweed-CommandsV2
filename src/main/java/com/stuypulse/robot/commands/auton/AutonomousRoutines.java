/************************ PROJECT ZIXI ************************/
/* Copyright (c) 2026 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/
package com.stuypulse.robot.commands.auton;

import org.wpilib.command2.Command;
import org.wpilib.command2.Commands;

/** Interface for all the command routines used in the autonomous period. */
public interface AutonomousRoutines {
    public static Command doNothingAuton() {
        return Commands.sequence(
                // nothing
                );
    }
}
