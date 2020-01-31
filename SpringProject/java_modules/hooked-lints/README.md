# hooked-lints

Git hooks applying lints on pre-commit phase for JS and Java code

##Create this `sonar-config.sh` file in the root of your project
```shell script
#!/bin/bash
 
projectKey="project key as found on SonarQube" 
#REQUIRED

projectName="project name as found on SonarQube" 
#REQUIRED

token="token created at https://sq.prod.tio.elsevier.systems/account/security/" 
#REQUIRED

binaries="route/to/compiled/java/classes" 
#REQUIRED if there is > 1 "*.java" file

branchName="name of this branch" 
#OPTIONAL unless branchTarget has been set
#(leave blank if master branch, branch type defaults to short lived branch when branchName specified, for long lived branches start name with "branch" or "release")

branchTarget="name of branch that this branch will eventually be merged into" 
#OPTIONAL
#(leave blank if master branch, defaults to master if branchName specified, for long lived branches this parameter describes which branch to sync issues from)
```

---
###How to use maven plugin  
```xml
<build>
        <plugins>
            <plugin>
                <groupId>com.mendeley.core</groupId>
                <artifactId>hooked-lints</artifactId>
                <version>1.0.0</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>hooked-lints-install</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```
