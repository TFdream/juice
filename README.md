# Juice
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Release Version](https://img.shields.io/badge/release-1.0.0-red.svg)](https://github.com/TFdream/juice/releases) [![Build Status](https://travis-ci.org/TFdream/juice.svg?branch=master)](https://travis-ci.org/TFdream/juice)

## Overview
开箱即用的Java Web开发框架，内建分布式锁、限流、分布式链路追踪等特性。

## Features
* 常用工具类及Java SPI扩展；
* 基于Redis的分布式实现；
* 多种限流策略实现；
* 分布式链路追踪，跨应用传递traceId；
* [动态线程池实现](https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html)；

## Requirements
The minimum requirements to run the quick start are:
* JDK 1.8 or above
* A java-based project management software like [Maven](https://maven.apache.org/) or [Gradle](http://gradle.org/)

## Installation
```
> git clone git@github.com:TFdream/juice.git
> mvn clean install -DskipTests
```

Download the latest JAR or grab via Maven:
```
<dependency>
    <groupId>io.infinityclub</groupId>
    <artifactId>juice-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

or Gradle:
```
compile 'io.infinityclub:juice-spring-boot-starter:1.0.0'
```

## Quick Start
[Wiki](https://github.com/TFdream/juice/wiki/Quick_Start)

## License
Juice 基于 [Apache License 2.0](https://github.com/TFdream/juice/blob/master/LICENSE) 协议。

## Contacts
邮箱：ricky_feng@163.com