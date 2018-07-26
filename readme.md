## 1. 项目已经实现的功能
* 用户登录,注销, 添加用户,删除用户,查询用户,修改用户

## 2. 项目相关文档
* /src/main/resources/相关文档

## 3. 运行和测试
### 3.1. 数据库配置
* 运行时,修改`application.yml`中`spring.profiles.active=dev`
* 测试时,修改`application.yml`中`spring.profiles.active=test`

### 3.2. 运行

* 浏览器访问`http://localhost:8090/index.html`


## 4. 相关技术

* Springboot
* Mybatis
* Vue.js


## 5. 补充:Maven迁移至Gradle(以gradle 4.9为例)

### 5.1. 安装gradle

`https://gradle.org/install/#manually`

### 5.2. 配置gradle环境变量

* GRADLE_HOME=D:\develop\gradle-4.9
* Path=...;%GRADLE_HOME%\bin

### 5.3. 命令行执行`gradlew init`初始化项目


### 5.4. 修改gradle构建脚本,以支持运行springboot项目

`build.gradle`

```gradle
apply plugin: 'java'
apply plugin: 'maven'
apply plugin: 'org.springframework.boot'

group = 'cn.sitedev'
version = '0.0.1-SNAPSHOT'

description = """springboot-demo"""

sourceCompatibility = 1.8
targetCompatibility = 1.8
tasks.withType(JavaCompile) {
	options.encoding = 'UTF-8'
}

buildscript {
    repositories {
 		mavenCentral()
    }
	dependencies {
	    classpath('org.springframework.boot:spring-boot-gradle-plugin:2.0.1.RELEASE')
	}
}

// 用于解决执行gradlew build后,找不到mapper.xml文件的问题
processResources {
    from('src/main/java') {
        include '**/*'     // 导入里面的所有文件，也可以自定义正则表达式
        exclude "**/*.java" // 源码文件中.java 文件是不需要的
    }
}

repositories {
        
     maven { url "http://repo.maven.apache.org/maven2" }
}
dependencies {
    compile group: 'org.springframework.boot', name: 'spring-boot-starter-web', version:'2.0.1.RELEASE'
    compile group: 'mysql', name: 'mysql-connector-java', version:'5.1.46'
    compile group: 'org.mybatis.spring.boot', name: 'mybatis-spring-boot-starter', version:'1.3.2'
    compile group: 'cn.hutool', name: 'hutool-all', version:'4.0.9'
    compile group: 'commons-codec', name: 'commons-codec', version:'1.11'
    runtime group: 'org.springframework.boot', name: 'spring-boot-devtools', version:'2.0.1.RELEASE'
    testCompile group: 'junit', name: 'junit', version:'4.12'
    testCompile group: 'org.springframework.boot', name: 'spring-boot-starter-test', version:'2.0.1.RELEASE'
}
```

### 5.5. 命令行执行`gradlew build`构建项目

### 5.6. 命令行执行`gradlew bootRun`启动项目
