# Vagabond

An idea for an infinite game to pass the time with.

## Architecture tests

something like

```java
package com.aspatel.vagabond;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Repository;

@AnalyzeClasses(packages = "com.aspatel.vagabond")
public class ArchitectureTests {

  private static final String BASE_PACKAGE = "com.aspatel.vagabond";

  @ArchTest
  public static final ArchRule testInfrastructureAdapterNames =
      classes()
          .that()
          .resideInAPackage(BASE_PACKAGE + ".infrastructure.adapter")
          .should()
          .beAnnotatedWith(Repository.class)
          .andShould()
          .haveSimpleNameEndingWith("Adapter");

  //  @ArchTest
  //  public static final ArchRule testDomainDependencies =
  //      classes()
  //          .that()
  //          .resideInAPackage(BASE_PACKAGE + ".domain..")
  //          .should()
  //          .onlyDependOnClassesThat()
  //          .resideInAnyPackage(BASE_PACKAGE + ".domain..", "java..");
  //
  //  @ArchTest
  //  public static final ArchRule domainServicePortsShouldBeInterfaces =
  //      classes()
  //          .that()
  //          .resideInAPackage(BASE_PACKAGE + ".domain.service.port..")
  //          .should()
  //          .beInterfaces();
}
```

## Zipkin auto

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-tracing</artifactId>
    </dependency>
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-tracing-bridge-brave</artifactId>
    </dependency>
    <dependency>
        <groupId>io.zipkin.reporter2</groupId>
        <artifactId>zipkin-reporter-brave</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
</dependencies>
```

## Jib (build containers)

Property:

```xml
<properties>
    <!-- Available base images: https://github.com/GoogleContainerTools/distroless?tab=readme-ov-file#debian-12 -->
    <jib-maven-plugin.base-image>gcr.io/distroless/java21-debian12:nonroot</jib-maven-plugin.base-image>
</properties>
```
At the `package` phase:

```xml

<plugin>
    <groupId>com.google.cloud.tools</groupId>
    <artifactId>jib-maven-plugin</artifactId>
    <version>${jib-maven-plugin.version}</version>
    <configuration>
        <from>
            <image>${jib-maven-plugin.base-image}</image>
        </from>
        <to>
            <image>${project.artifactId}:${project.version}</image>
        </to>
    </configuration>
</plugin>
```

Additional profiles:

```xml

<profiles>
    <profile>
        <id>container-push</id>
        <properties>
            <container.registry>${env.CONTAINER_REGISTRY}</container.registry>
            <container.image.name>${container.registry}/${project.artifactId}</container.image.name>
        </properties>
        <build>
            <plugins>
                <plugin>
                    <groupId>com.google.cloud.tools</groupId>
                    <artifactId>jib-maven-plugin</artifactId>
                    <executions>
                        <execution>
                            <goals>
                                <goal>build</goal>
                            </goals>
                            <phase>package</phase>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </build>
    </profile>
    <profile>
        <id>container-local</id>
        <properties>
            <container.image.name>${project.artifactId}</container.image.name>
        </properties>
        <build>
            <plugins>
                <plugin>
                    <groupId>com.google.cloud.tools</groupId>
                    <artifactId>jib-maven-plugin</artifactId>
                    <executions>
                        <execution>
                            <goals>
                                <goal>buildTar</goal>
                            </goals>
                            <phase>package</phase>
                            <configuration>
                                <outputPaths>
                                    <tar>${project.build.directory}/${project.artifactId}-${project.version}.tar</tar>
                                </outputPaths>
                            </configuration>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </build>
    </profile>
</profiles>
```