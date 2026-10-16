/************************ PROJECT ZIXI ************************/
/* Copyright (c) 2026 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/
package com.stuypulse.robot.constants;

import static org.wpilib.units.Units.Inches;
import static org.wpilib.units.Units.Meters;

import com.stuypulse.robot.Robot;
import com.stuypulse.robot.subsystems.swerve.Swerve;

import org.wpilib.math.geometry.Pose2d;
import org.wpilib.math.geometry.Pose3d;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.math.geometry.Rotation3d;
import org.wpilib.math.geometry.Translation2d;
import org.wpilib.math.geometry.Translation3d;
import org.wpilib.math.util.Units;
import org.wpilib.smartdashboard.Field2d;
import org.wpilib.smartdashboard.FieldObject2d;

import com.pathplanner.lib.util.FlippingUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** This interface stores information about the field elements. */
public interface Field {

    public static final Field2d FIELD2D = new Field2d();

    double WIDTH = Units.inchesToMeters(317.000);

    double LENGTH = Units.inchesToMeters(650.000);

    // Alliance relative hub center coordinates
    public static final Pose2d hubCenter =
            new Pose2d(Units.inchesToMeters(182.11), WIDTH / 2.0, new Rotation2d());

    public static final Pose3d hubCenter3d =
            new Pose3d(
                    hubCenter.getX(), hubCenter.getY(), Units.inchesToMeters(72), Rotation3d.ZERO);

    public static Pose2d getHubPose() {
        return Robot.isBlue() ? hubCenter : transformToOppositeAlliance(hubCenter);
    }

    // TODO: Get exact measurement
    public static Pose2d manualShotPose =
            new Pose2d(Inches.of(43.51 + 16.55), Meters.of(WIDTH / 2), new Rotation2d());

    public static Pose2d getManualShotPose() {
        return Robot.isBlue() ? manualShotPose : transformToOppositeAlliance(manualShotPose);
    }

    // 1.0 meters from driverstation wall and field wall
    public final Pose2d leftFerryZone = new Pose2d(1.0, WIDTH - 1.0, new Rotation2d());

    public final Pose2d rightFerryZone = new Pose2d(1.0, 1.0, new Rotation2d());

    public static Pose2d getFerryZonePose(Translation2d robot) {
        if (robot.getDistance(leftFerryZone.getTranslation())
                > robot.getDistance(rightFerryZone.getTranslation())) {
            return rightFerryZone;
        } else {
            return leftFerryZone;
        }
    }

    // ZONE COORDINATES
    // TODO: Temporary, need to get actual distance from outpost when intake pivot
    // is deployed
    public static final Pose2d outpost =
            new Pose2d(Units.inchesToMeters(25), Units.inchesToMeters(26.22), Rotation2d.k180deg);

    public static final Pose2d allianceZone =
            new Pose2d(Units.inchesToMeters(182.11), WIDTH, new Rotation2d());

    public static boolean inAllianceZone() {
        return Swerve.getInstance().getPose().getX() < allianceZone.getX();
    }

    // AprilTagFieldLayout APRIL_TAG_LAYOUT =
    // AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded);
    // /** APRILTAGS ** */
    // enum NamedTags {

    //         // #1
    //         RED_RIGHT_TRENCH_NZ,
    //         RED_HUB_RIGHT_SIDE_MID,
    //         RED_HUB_BACK_SIDE_LEFT,
    //         RED_HUB_BACK_SIDE_MID,
    //         // #5
    //         RED_HUB_LEFT_SIDE_MID,
    //         RED_LEFT_TRENCH_NZ,
    //         RED_LEFT_TRENCH_AZ,
    //         RED_HUB_LEFT_SIDE_RIGHT,
    //         RED_HUB_FRONT_SIDE_LEFT,
    //         // #10
    //         RED_HUB_FRONT_SIDE_MID,
    //         RED_HUB_RIGHT_SIDE_LEFT,
    //         RED_RIGHT_TRENCH_AZ,
    //         RED_HP_MID,
    //         RED_HP_RIGHT,
    //         // #15
    //         RED_TOWER_MID,
    //         RED_TOWER_RIGHT,
    //         BLUE_RIGHT_TRENCH_NZ,
    //         BLUE_HUB_RIGHT_SIDE_MID,
    //         BLUE_HUB_BACK_SIDE_LEFT,
    //         // #20
    //         BLUE_HUB_BACK_SIDE_MID,
    //         BLUE_HUB_LEFT_SIDE_MID,
    //         BLUE_LEFT_TRENCH_NZ,
    //         BLUE_LEFT_TRENCH_AZ,
    //         BLUE_HUB_LEFT_SIDE_RIGHT,
    //         // #25
    //         BLUE_HUB_FRONT_SIDE_LEFT,
    //         BLUE_HUB_FRONT_SIDE_MID,
    //         BLUE_HUB_RIGHT_SIDE_LEFT,
    //         BLUE_RIGHT_TRENCH_AZ,
    //         BLUE_HP_MID,
    //         // #30
    //         BLUE_HP_RIGHT,
    //         BLUE_TOWER_MID,
    //         BLUE_TOWER_RIGHT;

    //         public final AprilTag tag;

    //         public int getID() {
    //                 return tag.id();
    //         }

    //         public Pose3d getLocation() {
    //                 return Robot.isBlue() ? tag.location() :
    // transformToOppositeAlliance(tag.location());
    //         }

    //         private NamedTags() {
    //                 tag = APRILTAGS[ordinal()];
    //         }
    // }

    public final int[] RED_HUB_TAG_IDS = {2, 3, 4, 5, 8, 9, 10, 11};

    public final int[] BLUE_HUB_TAG_IDS = {18, 19, 20, 21, 24, 25, 26, 27};

    public final int[] RED_TRENCH_TAG_IDS = {1, 6, 7, 12};

    public final int[] BLUE_TRENCH_TAG_IDS = {17, 22, 23, 28};

    public final int[] RED_TOWER_TAG_IDS = {15, 16};

    public final int[] BLUE_TOWER_TAG_IDS = {31, 32};

    public final int[] RED_HP_TAG_IDS = {13, 14};

    public final int[] BLUE_HP_TAG_IDS = {29, 30};

    public final int[] ALL_TAGS = {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
        26, 27, 28, 29, 30, 31, 32
    };

    public final Pose2d blueHubCenter =
            new Pose2d(
                    Units.inchesToMeters(158.60), Units.inchesToMeters(158.84), new Rotation2d());

    public static Pose2d getAllianceHubPose() {
        return (Robot.isBlue() ? blueHubCenter : transformToOppositeAlliance(blueHubCenter));
    }

    /* TRANSFORM FUNCTIONS */
    public static Pose3d transformToOppositeAlliance(Pose3d pose) {
        Pose3d rotated = pose.rotateBy(new Rotation3d(0, 0, Math.PI));
        return new Pose3d(
                rotated.getTranslation().plus(new Translation3d(LENGTH, WIDTH, 0)),
                rotated.getRotation());
    }

    public static Pose2d transformToOppositeAlliance(Pose2d pose) {
        // Pose2d rotated = pose.rotateBy(Rotation2d.fromDegrees(180));
        // return new Pose2d(
        // rotated.getTranslation().plus(new Translation2d(LENGTH, WIDTH)),
        // rotated.getRotation());
        // return new Pose2d(
        // rotated.getTranslation().plus(new Translation2d(LENGTH - pose.getX(), WIDTH -
        // pose.getY())),
        // rotated.getRotation()
        // );
        return FlippingUtil.flipFieldPose(pose);
    }

    public static Translation2d transformToOppositeAlliance(Translation2d translation) {
        return new Translation2d(LENGTH - translation.getX(), WIDTH - translation.getY());
    }

    public static List<Pose2d> transformToOppositeAlliance(List<Pose2d> poses) {
        List<Pose2d> newPoses = new ArrayList<>();
        for (Pose2d pose : poses) {
            newPoses.add(transformToOppositeAlliance(pose));
        }
        return newPoses;
    }

    public static Pose2d[] transformToOppositeAlliance(Pose2d[] poses) {
        return Arrays.stream(poses).map(Field::transformToOppositeAlliance).toArray(Pose2d[]::new);
    }

    /** * EMPTY FIELD POSES *** */
    Pose2d EMPTY_FIELD_POSE2D = new Pose2d(new Translation2d(-1, -1), new Rotation2d());

    Pose3d EMPTY_FIELD_POSE3D = new Pose3d(-1, -1, 0, new Rotation3d());

    public static void clearFieldObject(FieldObject2d fieldObject) {
        fieldObject.setPose(EMPTY_FIELD_POSE2D);
    }
}
