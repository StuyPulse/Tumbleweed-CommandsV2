/************************ PROJECT ZIXI ************************/
/* Copyright (c) 2026 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/
package com.stuypulse.robot;

import com.stuypulse.robot.util.PeriodicSubsystem;

import org.wpilib.command2.Command;
import org.wpilib.command2.CommandScheduler;
import org.wpilib.driverstation.Alliance;
import org.wpilib.driverstation.MatchState;
import org.wpilib.framework.TimedRobot;

import java.util.Optional;

/**
 * Robot Class
 *
 * <p>This is the main class for robot code, instantiated in {@link com.stuypulse.robot.Main} It
 * extends TimedRobot, meaning that the methods in this class are called automatically during
 * specific states of the robot.
 */
public class Robot extends TimedRobot {
    /**
     * Checks the alliance the robot is on
     *
     * @return true if the robot is on the blue alliance, false if the robot is on the red alliance,
     *     and false if alliance cannot be determined.
     */
    public static boolean isBlue() {
        final Optional<Alliance> alliance = MatchState.getAlliance();
        if (alliance.isPresent()) {
            return alliance.get() == Alliance.BLUE;
        }
        return false;
    }

    private final RobotContainer robot;
    private Command auto;

    public Robot() {
        robot = new RobotContainer();

        // switch (GlobalSettings.CURRENT_MODE) {
        //     case REAL -> {
        //         // Running on a real robot, log to a USB stick ("/U/logs")
        //         Logger.addDataReceiver(new WPILOGWriter());
        //         Logger.addDataReceiver(new NT4Publisher());
        //     }

        //     case SIM -> {
        //         // Running a physics simulator, log to NT
        //         Logger.addDataReceiver(new NT4Publisher());
        //     }

        //     case REPLAY -> {
        //         // Replaying a log, set up replay source
        //         setUseTiming(false); // Run as fast as possible
        //         String logPath = LogFileUtil.findReplayLog();
        //         Logger.setReplaySource(new WPILOGReader(logPath));
        //         Logger.addDataReceiver(
        //                 new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
        //     }
        // }
    }

    /*************************/
    /*** ROBOT SCHEDULEING ***/
    /*************************/

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();

        PeriodicSubsystem.runAllPeriodicAfterScheduler();
    }

    /*********************/
    /*** DISABLED MODE ***/
    /*********************/

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    /***********************/
    /*** AUTONOMOUS MODE ***/
    /***********************/

    @Override
    public void autonomousInit() {
        auto = robot.getAutonomousCommand();

        if (auto != null) {
            CommandScheduler.getInstance().schedule(auto);
        }
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void autonomousExit() {}

    /*******************/
    /*** TELEOP MODE ***/
    /*******************/

    @Override
    public void teleopInit() {
        if (auto != null) {
            auto.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {}

    @Override
    public void teleopExit() {}

    /*************************/
    /*** UTILITY/TEST MODE ***/
    /*************************/

    @Override
    public void utilityInit() {}

    @Override
    public void utilityPeriodic() {}

    @Override
    public void utilityExit() {}
}
