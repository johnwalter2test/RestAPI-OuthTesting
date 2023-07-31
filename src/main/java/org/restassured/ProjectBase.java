package org.restassured;

import org.restassured.javabeans.CustomData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public abstract class ProjectBase {
    protected static Properties properties;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected static CustomData customData = new CustomData();

}
