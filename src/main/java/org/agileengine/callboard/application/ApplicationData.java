package org.agileengine.callboard.application;

public class ApplicationData {
    public static final String DOT = ".";
    public static final String ORG = "org";
    public static final String ORGANISATION_NAME = "agileengine";
    public static final String APPLICATION_NAME = "callboard";
    public static final String GROUP_NAME = ORG + DOT + ORGANISATION_NAME;
    public static final String APPLICATION_FULL_NAME = GROUP_NAME + DOT + APPLICATION_NAME;
    public static final String PACKAGE_APPLICATION = APPLICATION_FULL_NAME + DOT + "application";
    public static final String PACKAGE_LOGGING = PACKAGE_APPLICATION + DOT + "logging";
    public static final String PACKAGE_PERSISTENCE = APPLICATION_FULL_NAME + DOT + "persistence";
    public static final String PACKAGE_DAO = PACKAGE_PERSISTENCE + DOT + "dao";
    public static final String PACKAGE_SERVICE = APPLICATION_FULL_NAME + DOT + "service";
}
