# Juice
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Release Version](https://img.shields.io/badge/release-0.1.0-red.svg)](https://github.com/TFdream/juice/releases) [![Build Status](https://travis-ci.org/TFdream/juice.svg?branch=master)](https://travis-ci.org/TFdream/juice)

## Overview
Juice is a programming toolkit for building micro-service (or elegant monoliths) applications in Java.

## Features
* Java common utility classes and SPI Extension.
* Distributed Lock built on top of the Redis and Zookeeper.
* Distributed RateLimiter which is similar to [Guava](https://github.com/google/guava)'s RateLimiter.

## Requirements
The minimum requirements to run the quick start are:
* JDK 1.7 or above
* A java-based project management software like [Maven](https://maven.apache.org/) or [Gradle](http://gradle.org/)

## Installation
```
> git clone git@github.com:TFdream/juice.git
> mvn clean install -DskipTests
```

Download the latest JAR or grab via Maven:
```
<dependency>
    <groupId>io.dreamstudio</groupId>
    <artifactId>juice-core</artifactId>
    <version>0.1.0</version>
</dependency>
```

or Gradle:
```
compile 'io.dreamstudio:juice-core:0.1.0'
```

## Quick Start
[Wiki](https://github.com/TFdream/juice/wiki/Quick_Start) <br>
[wiki-中文](https://github.com/TFdream/juice/wiki/zh_Quick_Start)

## License
Juice 基于 [Apache License 2.0](https://github.com/TFdream/juice/blob/master/LICENSE) 协议。
